package org.xythax.net;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.xythax.core.Server;
import org.xythax.model.Client;
import org.xythax.net.db.Mysql;
import org.xythax.utils.BanProcessor;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;
import org.xythax.world.PlayerManager;

/**
 * Represents a client that has connected but has not yet logged in.
 * 
 * @author Graham
 */
public class Login {
	/**
	 * When the client connected.
	 */
	private long connectedAt;
	/**
	 * The timeout value in seconds.
	 */
	private static final int TIMEOUT = 1;

	/**
	 * The client's current state.
	 * 
	 * @author Graham
	 */
	private static enum State {
		LOGIN_START, LOGIN_READ1, LOGIN_READ2,
	}

	private State state = State.LOGIN_START;
	private Socket socket;
	public String connectedFrom;
	private Stream outStream = new Stream(new byte[1024]);
	private Stream inStream = new Stream(new byte[1024]);
	private Cryption inStreamDecryption;
	private Cryption outStreamDecryption;
	private long serverSessionKey = 0, clientSessionKey = 0;
	private int loginPacketSize, loginEncryptPacketSize;
	private String username = null, password = null;
	public PlayerManager manager;

	public Login(Socket s, String connectedFrom) throws IOException {
		this.socket = s;
		this.connectedFrom = connectedFrom;
		this.outStream.currentOffset = 0;
		this.inStream.currentOffset = 0;
		this.serverSessionKey = ((long) (java.lang.Math.random() * 99999999D) << 32)
				+ (long) (java.lang.Math.random() * 99999999D);
		this.connectedAt = System.currentTimeMillis();
	}

	public void process() throws Exception, IOException {
		long diff = System.currentTimeMillis() - connectedAt;
		if (diff > (TIMEOUT * 1000)) {
			throw new Exception("Timeout.");
		}
		if (state == State.LOGIN_START) {
			if (fillInStream(2)) {
				if (inStream.readUnsignedByte() != 14) {
					throw new Exception("Expect login byte 14 from client.");
				}
				@SuppressWarnings("unused")
				int namePart = inStream.readUnsignedByte();
				for (int i = 0; i < 8; i++) {
					outStream.writeByte(0);
				}
				outStream.writeByte(0);
				outStream.writeQWord(serverSessionKey);
				directFlushOutStream();
				state = State.LOGIN_READ1;
				inStream.length -= 2;
			}
		}
		if (state == State.LOGIN_READ1) {
			if (fillInStream(2)) {
				int loginType = inStream.readUnsignedByte();
				if (loginType != 16 && loginType != 18) {
					throw new Exception("Unexpected login type " + loginType);
				}
				loginPacketSize = inStream.readUnsignedByte();
				loginEncryptPacketSize = loginPacketSize - (36 + 1 + 1 + 2);
				Utils.print_debug("LoginPacket size: " + loginPacketSize
						+ ", RSA packet size: " + loginEncryptPacketSize);
				if (loginEncryptPacketSize <= 0) {
					throw new Exception("Zero RSA packet size");
				}
				state = State.LOGIN_READ2;
				inStream.length -= 2;
			}
		}
		if (state == State.LOGIN_READ2) {
			if (fillInStream(loginPacketSize)) {
				if (inStream.readUnsignedByte() != 255
						|| inStream.readUnsignedWord() != 317) {
					throw new Exception(
							"Wrong login packet magic ID (expected 255, 317)");
				}
				int lowMemoryVersion = inStream.readUnsignedByte();
				Utils.print_debug("Client type: "
						+ ((lowMemoryVersion == 1) ? "low" : "high")
						+ " memory version");
				for (int i = 0; i < 9; i++) {
					Utils.print_debug("dataFileVersion[" + i + "]: 0x"
							+ Integer.toHexString(inStream.readDWord()));
				}
				loginEncryptPacketSize--;
				int tmp = inStream.readUnsignedByte();
				if (loginEncryptPacketSize != tmp) {
					throw new Exception("Encrypted packet data length ("
							+ loginEncryptPacketSize
							+ ") different from length byte thereof (" + tmp
							+ ")");
				}
				tmp = inStream.readUnsignedByte();
				if (tmp != 10) {
					throw new Exception("Encrypted packet Id was " + tmp
							+ " but expected 10");
				}
				clientSessionKey = inStream.readQWord();
				serverSessionKey = inStream.readQWord();
				inStream.readDWord(); // User ID

				username = Utils.formatUsername(inStream.readString());
				password = inStream.readString();

				int sessionKey[] = new int[4];
				sessionKey[0] = (int) (clientSessionKey >> 32);
				sessionKey[1] = (int) clientSessionKey;
				sessionKey[2] = (int) (serverSessionKey >> 32);
				sessionKey[3] = (int) serverSessionKey;
				inStreamDecryption = new Cryption(sessionKey);
				for (int i = 0; i < 4; i++)
					sessionKey[i] += 50;

				outStreamDecryption = new Cryption(sessionKey);
				outStream.packetEncryption = outStreamDecryption;

				int returnCode = 2; // ok
				int slot = manager.getFreeSlot();
				Client c = null;
				if (slot == -1) {
					returnCode = 7; // world full!
				} else if (username.length() == 0) {
					returnCode = 3; // please try again
				} else if (!username.matches("[a-zA-Z0-9 ]*")) {
					returnCode = 3; // please try again
				} else if (manager.isPlayerOn(username)) {
					returnCode = 5; // online
				} else if (BanProcessor.checkIP(connectedFrom)) {
					returnCode = 4; // banned
				} else if (BanProcessor.checkUser(username)) {
					returnCode = 4; // banned
				} else {
					try {
						Connection con = Mysql.getConnection();
						Statement s = con.createStatement();
						s.executeQuery("SELECT * FROM characters WHERE username = '"
								+ username + "'");
						ResultSet rs = s.getResultSet();
						if (rs.next()) {
							if (!password.equals(rs.getString("password"))) {
								returnCode = 3;
							} else {
								c = new Client(socket, slot);
								c.ID = rs.getInt("ID");
								int[] location = new int[3];
								location[0] = rs.getInt("absX");
								location[1] = rs.getInt("absY");
								location[2] = rs.getInt("heightLevel");
								if (location[0] == -1 || location[1] == -1) { // blackscreen
																				// fix.
																				// -
																				// killamess
									location[0] = Constants.SPAWN_X;
									location[1] = Constants.SPAWN_Y;
									location[2] = 0;
								}
								c.teleportToX = location[0];
								c.teleportToY = location[1];
								c.teleportToZ = location[2];
								c.hitpoints = rs.getInt("hitpoints");
								c.specialAmount = rs.getInt("specialAmount");
								c.skullTimer = rs.getInt("skullTimer");
								c.energy = rs.getInt("energy");
								c.ancients = rs.getBoolean("ancients");
								c.playerIsMember = rs
										.getBoolean("playerIsMember");
								c.dharokDamage = rs.getInt("dharokDamage");
								c.spellBook = rs.getInt("spellBook");
								c.appearanceSet = rs
										.getBoolean("appearanceSet");
								c.setPrivileges(rs.getInt("Rights"));
								c.SplitChat = rs.getBoolean("splitChat");
								c.combatMode = rs.getInt("combatmode");
								c.lastMeleeMode = rs.getInt("lastmeleemode");
								c.lastRangeMode = rs.getInt("lastrangemode");
								c.starter = rs.getInt("starter");
								c.autoRetaliate = rs
										.getBoolean("autoRetaliate");
								c.runConfig = rs.getInt("running");
								c.poisonDelay = rs.getInt("poisonDelay");
								c.poisonDamage = rs.getInt("poisonDamage");
								rs.close();

								s.executeQuery("SELECT * FROM lastkilled WHERE ID = '"
										+ c.ID + "'");
								rs = s.getResultSet();
								while (rs.next()) {
									int i = rs.getInt("slot");
									c.lastKilled[i] = rs.getString("killed");
								}
								rs.close();

								s.executeQuery("SELECT * FROM inventorys WHERE ID = '"
										+ c.ID + "'");
								rs = s.getResultSet();
								while (rs.next()) {
									int i = rs.getInt("slot");
									c.playerItems[i] = rs.getInt("item");
									c.playerItemsN[i] = rs.getInt("amount");
								}
								rs.close();

								s.executeQuery("SELECT * FROM equipments WHERE ID = '"
										+ c.ID + "'");
								rs = s.getResultSet();
								while (rs.next()) {
									int i = rs.getInt("slot");
									c.playerEquipment[i] = rs.getInt("item");
									c.playerEquipmentN[i] = rs.getInt("amount");
								}
								rs.close();

								s.executeQuery("SELECT * FROM banks WHERE ID = '"
										+ c.ID + "'");
								rs = s.getResultSet();
								while (rs.next()) {
									int i = rs.getInt("slot");
									c.bankItems[i] = rs.getInt("item");
									c.bankItemsN[i] = rs.getInt("amount");
								}
								rs.close();

								s.executeQuery("SELECT * FROM skills WHERE ID = '"
										+ c.ID + "'");
								rs = s.getResultSet();
								while (rs.next()) {
									int i = rs.getInt("slot");
									c.playerLevel[i] = rs.getInt("skill");
									c.playerXP[i] = rs.getInt("xp");
								}
								rs.close();

								s.executeQuery("SELECT * FROM looks WHERE ID = '"
										+ c.ID + "'");
								rs = s.getResultSet();
								while (rs.next()) {
									int i = rs.getInt("slot");
									c.playerLook[i] = rs.getInt("look");
								}
								rs.close();

								s.executeQuery("SELECT * FROM friends WHERE ID = '"
										+ c.ID + "'");
								rs = s.getResultSet();
								long[] friends = new long[200];
								while (rs.next()) {
									int i = rs.getInt("slot");
									friends[i] = rs.getLong("friend");
								}
								c.setFriends(friends);
								rs.close();
								s.executeQuery("SELECT * FROM ignores WHERE ID = '"
										+ c.ID + "'");
								rs = s.getResultSet();
								long[] ignores = new long[200];
								while (rs.next()) {
									int i = rs.getInt("slot");
									ignores[i] = rs.getLong("ignored");
								}
								c.setIgnores(ignores);
								s.close();
								rs.close();
								con.close();
								Mysql.release();

							}
						} else {
							// make a reg page
							returnCode = 18;
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnCode = 11;
					}
				}
				if (c != null) {
					c.setUsername(username);
					c.setPassword(password);
					c.setInStreamDecryption(inStreamDecryption);
					c.setInStream(inStream);
					c.setOutStream(outStream);
					c.packetSize = 0;
					c.packetType = -1;
					c.manager = manager;
					c.isActive = true;
					c.connectedFrom = connectedFrom;
					if (lowMemoryVersion == 1) {
						c.lowMemoryVersion = 1;
					} else {
						c.lowMemoryVersion = 0;
					}
					if (BanProcessor.checkMuted(c.getUsername())
							|| BanProcessor.checkIpMuted(c.getUsername())) {
						c.setMuted(true);
					}
					System.out
							.println(""
									+ c.getUsername()
									+ " logged in [idx="
									+ c.getUserID()
									+ ", online="
									+ (PlayerManager.getPlayerManager()
											.getPlayerCount() + 1) + "]");
				}
				outStream.writeByte(returnCode);
				if (returnCode == 2) {
					manager.addClient(slot, c);
					outStream.writeByte(c.getPrivileges()); // mod level
					outStream.writeByte(0); // no log
				} else {
					outStream.writeByte(0);
					outStream.writeByte(0);
				}
				directFlushOutStream();
				if (returnCode == 2) {
					Server.getIOThread().switchIoClientToClient(this, c);
				} else {
					throw new Exception("Login error.");
				}
				inStream.length -= loginPacketSize;
			}
		}
	}

	private void directFlushOutStream() throws java.io.IOException {
		ByteBuffer buf = ByteBuffer.allocate(outStream.currentOffset);
		buf.put(outStream.buffer, 0, outStream.currentOffset);
		buf.flip();
		Server.getIOThread()
				.writeReq(Server.getIOThread().socketFor(this), buf);
		outStream.currentOffset = 0; // reset
	}

	private boolean fillInStream(int ct) throws IOException {
		return inStream.length >= ct;
	}

	public void read(ByteBuffer buffer) {
		inStream.currentOffset = 0;
		buffer.flip();
		buffer.get(inStream.buffer, 0, buffer.limit());
		inStream.length = buffer.limit();
		try {
			try {
				process();
			} catch (Exception e) {
				Server.getIOThread().destroySocket(
						Server.getIOThread().socketFor(this), connectedFrom,
						true);
			}
		} catch (Exception ignored) {
		}
	}

	/**
	 * Have we timed out?
	 * 
	 * @return
	 */
	public boolean checkTimeout() {
		return (connectedAt + TIMEOUT) > System.currentTimeMillis();
	}
}