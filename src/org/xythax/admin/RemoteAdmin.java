package org.xythax.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.Statement;

import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.Player;
import org.xythax.net.db.Mysql;
import org.xythax.utils.BanProcessor;

public class RemoteAdmin implements Runnable {
	Socket client;
	BufferedReader reader;
	PrintWriter writer;
	Socket sock;
	private ServerSocket serverSocket;

	public class ReadRequest implements Runnable {
		BufferedReader reader;
		PrintWriter writer;

		public ReadRequest(Socket client) {
			try {
				writer = new PrintWriter(client.getOutputStream(), true);
				InputStreamReader isReader = new InputStreamReader(
						client.getInputStream());
				reader = new BufferedReader(isReader);
			} catch (Exception ex) {
				System.out
						.println("[WebAdmin]: Exception reading buffered of client socket (Function: ReadRequest(Socket))");
			}
		}

		public void run() {
			String msg;
			try {
				while ((msg = reader.readLine()) != null) {
					handleLine(msg);
				}
				reader.close();
			} catch (Exception ex) {
				System.out.println("[WebAdmin]: WebAdmin user disconnected.");
			}
		}

		public void handleLine(String msg) {
			String[] seperate = msg.split("\\|");

			String username = seperate[0];
			String password = seperate[1];
			int command = Integer.parseInt(seperate[2]);
			String[] parameters = null;
			parameters = seperate[3].split("\\:\\:");

			if (command == -1) {
				if (validLogin(username, password) == true) {
					reply("true");
				} else {
					reply("false");
				}
			} else {
				if (validLogin(username, password) == true)
					handleCommand(command, parameters);
				else
					reply("Your username is wrong, doesn't exist, or you don't have sufficient permission.");
			}
		}

		private void handleCommand(int opcode, String[] parameters) {
			boolean success = false;
			System.out.println("[WebAdmin]: Client executed command w/opcode '"
					+ opcode + "'.");
			switch (opcode) {
				case 1: // Send Message
					if (parameters.length == 2) {
						for (Player p : GameEngine.getPlayerManager()
								.getPlayers()) {
							if (p != null) {
								((Client) p).getActionSender().sendMessage(
										"[WebAdmin][" + parameters[0] + "]: "
												+ parameters[1]);
								reply("You have successfully send a global message");
								success = true;
							}
						}
					}
					if (!success)
						reply("An error has occured while sending a global message -- perhpaps no players are online.");
					break;

				case 2: // Give item to player
					if (parameters.length == 3) {
						int session;
						if ((session = getSession(parameters[0])) != -1) {
							Client s = (Client) GameEngine.getPlayerManager()
									.getPlayers()[session];
							s.getActionSender().sendInventoryItem(
									Integer.parseInt(parameters[1]),
									Integer.parseInt(parameters[2]));
							reply("You have successfully given the user the item.");
							s.getActionSender().sendMessage(
									"[WebAdmin]: You have recieved an item.");
							success = true;
						}
					}
					if (!success)
						reply("An error has occured while giving the item to the user");
					break;

				case 3: // Kick user
					if (parameters.length == 1) {
						int session;
						if ((session = getSession(parameters[0])) != -1) {
							Client s = (Client) GameEngine.getPlayerManager()
									.getPlayers()[session];
							s.disconnected = true;
							reply("You have successfully kicked the player.");
							success = true;
						}
					}
					if (!success)
						reply("An error has occured while kicking the user");
					break;

				case 4: // Check status
					if (parameters.length == 3) {
						if (getSession(parameters[0]) != -1) {
							reply("Online");
							success = true;
						}
					}
					if (!success)
						reply("Offline");
					break;

				case 5: // PM user
					if (parameters.length == 3) {
						int session;
						if ((session = getSession(parameters[0])) != -1) {
							Client s = (Client) GameEngine.getPlayerManager()
									.getPlayers()[session];
							s.getActionSender().sendMessage(
									"[WebAdmin-PM][" + parameters[1] + "]: "
											+ parameters[2]);
							reply("Message successfully sent to user");
							success = true;
						}
					}
					if (!success)
						reply("An error has occured while private messaging the user");
					break;

				case 6:// get players
					String users = "";
					for (Player p : GameEngine.getPlayerManager().getPlayers()) {
						if (p != null) {
							Client s = (Client) p;
							users += s.username + "::";
						}
					}
					reply(users);
					break;

				case 7:// get staff
					String staff = "";
					for (Player p : GameEngine.getPlayerManager().getPlayers()) {
						if (p != null) {
							Client s = (Client) p;
							staff += s.username + "," + s.privileges + "::";
						}
					}
					reply(staff);
					break;

				case 8: // IP Ban user
					if (parameters.length == 1) {
						int session;
						if ((session = getSession(parameters[0])) != -1) {
							Player p = GameEngine.getPlayerManager()
									.getPlayers()[session];
							Client s = (Client) p;
							BanProcessor.ban(p.username, p.ID,
									p.getConnectedFrom(), 1);
							s.disconnected = true;
							reply("User successfully ip banned");
							success = true;
						}
					}
					if (!success)
						reply("An error has occured while ip banning the user");
					break;

				case 9: // Ban user
					if (parameters.length == 1) {
						int session;
						if ((session = getSession(parameters[0])) != -1) {
							Player p = GameEngine.getPlayerManager()
									.getPlayers()[session];
							if (p != null) {
								Client s = (Client) p;
								BanProcessor.ban(p.username, p.ID,
										p.getConnectedFrom(),
										BanProcessor.TYPE_BAN);
								s.disconnected = true;
								reply("User successfully banned");
								success = true;
							}
						}
					}
					if (!success)
						reply("An error has occured while banning the user");
					break;

				case 10: // Un-ban user
					if (parameters.length == 1) {
						if (BanProcessor.unban(parameters[0],
								BanProcessor.TYPE_BAN)) {
							reply("User successfully un-banned");
							success = true;
						}
					}
					if (!success)
						reply("An error has occured while un-banning the user");
					break;

				case 11: // Mute user
					if (parameters.length == 1) {
						for (Player p : GameEngine.getPlayerManager()
								.getPlayers()) {
							if (p != null) {
								if (p.username == parameters[0]) {
									BanProcessor.ban(p.username, p.ID,
											p.connectedFrom,
											BanProcessor.TYPE_MUTE);
									reply("You have successfully muted the player");
									success = true;
								} else {
									continue;
								}
							} else {
								reply("User is offline.");
							}
						}
					}
					if (!success)
						reply("An error has occured while muting the user");
					break;

				case 12: // Un-mute user
					if (parameters.length == 1) {
						BanProcessor.unban(parameters[0],
								BanProcessor.TYPE_MUTE);
						reply("You have successfully un-muted the user");
						success = true;
					}
					if (!success)
						reply("An error has occured while un-muting the user");
					break;

				case 13: // IP mute user
					if (parameters.length == 1) {
						int session;
						if ((session = getSession(parameters[0])) != -1) {
							Player p = GameEngine.getPlayerManager()
									.getPlayers()[session];
							BanProcessor.ban(p.username, p.ID, p.connectedFrom,
									BanProcessor.TYPE_IP_MUTE);
							reply("You have successfully ip muted the user");
							success = true;
						}
					}
					if (!success)
						reply("An error has occured while ip muting the user");
					break;

				case 14: // Un-ip mute
					if (parameters.length == 1) {
						BanProcessor.unban(parameters[0],
								BanProcessor.TYPE_IP_MUTE);
						reply("You have successfully un-ip muted the user");
						success = true;
					}
					if (!success)
						reply("An error has occured while un-ip muting the user");
					break;

				case 15: // un-ip ban
					if (parameters.length == 1) {
						if (BanProcessor.unban(parameters[0],
								BanProcessor.TYPE_IP_BAN)) {
							reply("You have successfully un-ip banned the user");
							success = true;
						} else
							reply("User was never IP banned to begin with.");
					}
					if (!success)
						reply("An error has occured while un-ip banning the user");
					break;

				case 16: // Give mod
					if (parameters.length == 1) {
						int session;
						if ((session = getSession(parameters[0])) != -1) {
							Player p = GameEngine.getPlayerManager()
									.getPlayers()[session];
							p.privileges = 1;
							reply("You have successfully promoted the user to mod");
							success = true;
						}
					}
					if (!success)
						reply("An error has occured while promoting the user");
					break;

				case 17: // Give Admin
					if (parameters.length == 1) {
						int session;
						if ((session = getSession(parameters[0])) != -1) {
							Player p = GameEngine.getPlayerManager()
									.getPlayers()[session];
							p.privileges = 2;
							reply("You have successfully promoted the user to admin");
							success = true;
						}
					}
					if (!success)
						reply("An error has occured while promoting the user");
					break;
				default:
					reply("Opcode " + opcode);
					break;
			}
		}

		public int getSession(String user) {
			return -1;
		}

		public void reply(String reply) {
			try {
				writer.println(reply);
			} catch (Exception ex) {
				System.out
						.println("[WebAdmin]: Exception replying to client (Function: reply())");
			}
		}

		private boolean validLogin(String username, String password) {
			Statement s = null;
			ResultSet users = null;
			boolean r = false;
			try {
				s = Mysql.getConnection().createStatement();
				s.executeQuery("SELECT * FROM `characters` WHERE username=\""
						+ username + "\";");
				users = s.getResultSet();
				while (users.next()) {
					String name = users.getString("username");
					String pass = users.getString("password");
					int rights = users.getInt("rights");
					s.close();
					users.close();
					if (username.equalsIgnoreCase(name)
							&& password.equalsIgnoreCase(pass) && rights > 0) {
						r = true;
					}
				}
			} catch (Exception e) {
				System.out.println("Error grabbing user.");
				e.printStackTrace();
			} finally {
				try {
					if (!s.isClosed())
						s.close();
					if (!users.isClosed())
						users.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return r;
		}
	}

	public void run() {
		try {
			System.out.println("Running..");
			System.out.println("[WebAdmin] Listening on port 43590");
			serverSocket = new ServerSocket(43590);
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					System.out.println("Web cp thread shut down.");
				}
			});
			while (true) {
				client = serverSocket.accept();
				Thread readRequest = new Thread(new ReadRequest(client));
				readRequest.start();
				System.out
						.println("[WebAdmin]: A new client has connected to WebAdmin");
			}
		} catch (Exception ex) {
			System.out
					.println("[WebAdmin]: Exception initializing WebAdmin server (Function: init())");
			ex.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
