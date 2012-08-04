package org.xythax.model;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.xythax.content.FriendsHandler;
import org.xythax.content.PrayerHandler;
import org.xythax.content.TradeHandler;
import org.xythax.core.GameEngine;
import org.xythax.core.Server;
import org.xythax.model.combat.content.CombatMode;
import org.xythax.model.combat.content.Specials;
import org.xythax.model.combat.magic.AutoCast;
import org.xythax.net.ActionSender;
import org.xythax.net.Cryption;
import org.xythax.net.Stream;
import org.xythax.net.phandler.PacketManager;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;
import org.xythax.world.PlayerManager;

public class Client extends Player {
	public boolean hasStartedToFollow;
	public Stream inStream = null, outStream = null;
	public Cryption inStreamDecryption = null, outStreamDecryption = null;
	private Socket mySock;

	public int timeOutCounter = 0;
	public int lowMemoryVersion = 0;
	public int packetType = -1, packetSize = 0;

	private boolean muted = false;
	private boolean yellMuted = false;

	public void setInStream(Stream inStream) {
		this.inStream = inStream;
	}

	public void setOutStream(Stream outStream) {
		this.outStream = outStream;
	}

	public void setInStreamDecryption(Cryption inStreamDecryption) {
		this.inStreamDecryption = inStreamDecryption;
	}

	public Client(Socket s, int slot) {
		super(slot);
		mySock = s;
		outStream = new Stream(new byte[1024]);
		outStream.currentOffset = 0;
		inStream = new Stream(new byte[1024]);
		inStream.currentOffset = 0;
	}

	@Override
	public void destruct() {
		if (mySock == null) {
			return;
		}

		System.out.println("" + getUsername() + " logged out [idx="
				+ getUserID() + ", online="
				+ (PlayerManager.getPlayerManager().getPlayerCount() - 1) + "]");

		friendsHandler.destruct();

		if (extraData.containsKey("shop")) {
			extraData.remove("shop");
			actionSender.sendWindowsRemoval();
		}
		if (tradeHandler.getCurrentTrade() != null) {
			if (tradeHandler.getCurrentTrade().isOpen()) {
				tradeHandler.declineTrade();
			}
		}
		try {
			disconnected = true;
			mySock = null;
			inStream = null;
			outStream = null;
			isActive = false;
			Server.getIOThread().destroySocket(
					Server.getIOThread().socketFor(this), connectedFrom, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.destruct();
	}

	public void flushOutStream() {
		if (disconnected || outStream.currentOffset == 0) {
			return;
		}
		synchronized (this) {
			if (outStream != null && this != null) {
				ByteBuffer buf = ByteBuffer.allocate(outStream.buffer.length);
				buf.put(outStream.buffer, 0, outStream.currentOffset);
				buf.flip();
				try {
					Server.getIOThread().writeReq(
							Server.getIOThread().socketFor(this), buf);
				} catch (Exception e) {
					e.printStackTrace();
				}
				outStream.currentOffset = 0;
				outStream.reset();
			}
		}
	}

	@Override
	public void initialize() {
		outStream.createFrame(249);
		outStream.writeByteA(1);
		outStream.writeWordBigEndianA(getUserID());

		actionSender.sendChatOptions(0, 0, 0);
		getActionAssistant().setSplitChat(SplitChat ? 0 : 1);
		for (int i = 0; i < Constants.MAX_SKILLS; i++) {
			actionAssistant.refreshSkill(i);
		}
		if (starter == 0) {
			actionSender.sendMessage("Welcome to " + Constants.SERVER_NAME);
			// actionSender.sendMessage("You have received a greetings package.");
			starter = 1;
			actionSender.sendInventoryItem(1351, 1);
			actionSender.sendInventoryItem(1265, 1);
			// actionSender.sendInventoryItem(29, 1);
			actionSender.sendInventoryItem(303, 1);
			actionSender.sendInventoryItem(1291, 1);
			actionSender.sendInventoryItem(1171, 1);
			actionSender.sendInventoryItem(556, 25);
			actionSender.sendInventoryItem(558, 25);
			actionSender.sendInventoryItem(841, 1);
			actionSender.sendInventoryItem(882, 25);

		} else {
			actionSender.sendMessage("Welcome back to " + Constants.SERVER_NAME
					+ ".");
			actionSender.sendMessage("There are "
					+ PlayerManager.getPlayerManager().getPlayerCount()
					+ " players online!");
		}
		outStream.createFrame(107);
		outStream.createFrame(68);
		actionSender.sendSidebar(1, 3917);
		actionSender.sendSidebar(2, 638);
		actionSender.sendSidebar(3, 3213);
		actionSender.sendSidebar(4, 1644);
		actionSender.sendSidebar(5, 5608);
		switch (spellBook) {
		case 2:
			actionSender.sendSidebar(6, 12855);
			break;
		case 3:
			actionSender.sendSidebar(6, 430); // 29999
			break;
		default:
			actionSender.sendSidebar(6, 1151);
			break;
		}
		actionSender.sendSidebar(7, -1);// 18128
		actionSender.sendSidebar(8, 5065);
		actionSender.sendSidebar(9, 5715);
		actionSender.sendSidebar(10, 2449);
		actionSender.sendSidebar(11, 904);
		actionSender.sendSidebar(12, 147);
		actionSender.sendSidebar(13, -1); // harp (music tab)
		actionSender.sendSidebar(0, 2423);
		actionSender.sendOption("Trade With", 3);
		actionSender.sendOption("Follow", 2);
		setFriendsSize(200);
		setIgnoresSize(100);
		setPlayerBankSize(352); // full bank (352) PND = 144
		if (skullTimer > 0)
			skullIcon = -1;

		updateEnergy();

		if (!appearanceSet) {
			actionSender.sendInterface(3559);
		} else {
			System.out.println("Appearance was already set");
		}
		GameEngine.getItemManager().reloadDrops(this);
		actionSender.sendQuest("Defence XP", 353);
		update();
		AutoCast.turnOff(this);
		if (runConfig > 0) {
			setRunning(true);
			isRunning2 = true;
			actionSender.sendConfig(429, 1);
		} else {
			setRunning(false);
			isRunning2 = false;
			actionSender.sendConfig(429, 0);
		}
		if (autoRetaliate) {
			actionSender.sendConfig(172, 1);
		} else {
			actionSender.sendConfig(172, 0);
		}
		CombatMode.setCombatMode(this, this.combatMode);
		actionSender.sendConfig(301, 0); // reset special
		Specials.updateSpecialBar(this);
		prayerHandler.resetAllPrayers(); // reset prayers
		actionSender.sendItemReset();
		actionSender.sendBankReset();

		equipment.setEquipment(playerEquipment[Constants.HELM], 1,
				Constants.HELM);
		equipment.setEquipment(playerEquipment[Constants.CAPE], 1,
				Constants.CAPE);
		equipment.setEquipment(playerEquipment[Constants.AMULET], 1,
				Constants.AMULET);
		equipment.setEquipment(playerEquipment[Constants.ARROWS],
				playerEquipmentN[Constants.ARROWS], Constants.ARROWS);
		equipment.setEquipment(playerEquipment[Constants.CHEST], 1,
				Constants.CHEST);
		equipment.setEquipment(playerEquipment[Constants.SHIELD], 1,
				Constants.SHIELD);
		equipment.setEquipment(playerEquipment[Constants.BOTTOMS], 1,
				Constants.BOTTOMS);
		equipment.setEquipment(playerEquipment[Constants.GLOVES], 1,
				Constants.GLOVES);
		equipment.setEquipment(playerEquipment[Constants.BOOTS], 1,
				Constants.BOOTS);
		equipment.setEquipment(playerEquipment[Constants.RING], 1,
				Constants.RING);
		equipment.setEquipment(playerEquipment[Constants.WEAPON], 1,
				Constants.WEAPON);

		equipment.sendWeapon();
		bonuses.calculateBonus();

		friendsHandler.initialize();
		flushOutStream();
	}

	public void convertMagic() {
		if (getSpellBook() <= 1) {
			setSpellBook(2);
			actionSender.sendSidebar(6, 12855);
			actionSender.sendMessage("You convert to ancient magic.");
		} else if (getSpellBook() == 2) {
			setSpellBook(1);
			actionSender.sendSidebar(6, 1151);
			actionSender.sendMessage("You convert to regular magic.");
		}
	}

	@Override
	public void update() {
		manager.updatePlayer(this, outStream);
		manager.updateNPC(this, outStream);

		if (zoneRequired) {
			Client c = this;
			c.doZoning();
			zoneRequired = false;
		}
		flushOutStream();
	}

	public void doZoning() {
		GameEngine.getItemManager().reload(this);
		GameEngine.getObjectManager().reload(this);
	}

	private int vengTimer;

	@Override
	public void setVengTimer() {
		this.vengTimer = 60;
	}

	@Override
	public int getVengTimer() {
		return vengTimer;
	}

	@Override
	public void deductVengTimer() {
		this.vengTimer--;
	}

	private boolean loggedOut = false;

	public void setLoggedOut(boolean loggedOut) {
		this.loggedOut = loggedOut;
	}

	public boolean isLoggedOut() {
		return loggedOut;
	}

	@Override
	public void process() {
		timeOutCounter++;

		if (isKicked || isLoggedOut()) {
			disconnected = true;
		}

		if (timeOutCounter > Constants.INGAME_TIMEOUT * 2) {
			actionSender.sendLogout();
		}

		if (logoutDelay > 0) {
			logoutDelay--;
		}

	}

	private ByteBuffer oldBuffer = null;

	public void read(ByteBuffer buffer) {
		if (disconnected || !isActive || outStream == null || inStream == null) {
			Utils.print_debug("Dropped packet due to null outstream/instream/inactive or disconnected client.");
			return;
		}
		buffer.flip();
		if (oldBuffer != null) {
			ByteBuffer newBuffer = ByteBuffer.allocate(buffer.limit()
					+ oldBuffer.limit());
			newBuffer.put(oldBuffer);
			newBuffer.put(buffer);
			oldBuffer = null;
			newBuffer.flip();
			buffer = newBuffer;
			newBuffer = null;
		}
		int avail = buffer.limit();
		while (true) {
			if (avail <= 0) {
				break;
			}
			if (packetType == -1) {
				if (avail > 0) {
					packetType = buffer.get() & 0xFF;
					packetType = (packetType - inStreamDecryption.getNextKey()) & 0xFF;
					packetSize = Utils.PACKET_SIZES[packetType];
					avail--;
				} else {
					break;
				}
			}
			if (packetSize == -1) {
				if (avail > 0) {
					packetSize = buffer.get() & 0xFF;
					avail--;
				} else {
					break;
				}
			}
			if (avail >= packetSize) {
				avail -= packetSize;
				buffer.get(inStream.buffer, 0, packetSize);
				inStream.currentOffset = 0;
				PacketManager.handlePacket(this, packetType, packetSize);
				timeOutCounter = 0;
				inStream.reset();
				packetType = -1;
			} else {
				break;
			}
		}
		if (avail > 0) {
			oldBuffer = ByteBuffer.allocate(avail);
			byte[] tmp = new byte[avail];
			buffer.get(tmp, 0, avail);
			oldBuffer.put(tmp);
			oldBuffer.flip();
			tmp = null;
		}
		buffer.clear();
	}

	public void cancelTasks() {
		if (extraData.containsKey("shop")) {
			extraData.remove("shop");
			actionSender.sendWindowsRemoval();
		}
		if (getTradeHandler().getCurrentTrade() != null) {
			if (getTradeHandler().getCurrentTrade().isOpen()) {
				getTradeHandler().declineTrade();
			}
		}
		actionSender.sendWindowsRemoval();

	}

	public void setYellMuted(boolean yellMuted) {
		this.yellMuted = yellMuted;
	}

	public boolean isYellMuted() {
		return yellMuted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}

	public boolean isMuted() {
		return muted;
	}

	private Map<String, Object> extraData = new HashMap<String, Object>();

	public Map<String, Object> getExtraData() {
		return extraData;
	}

	private ActionSender actionSender = new ActionSender(this);

	public ActionSender getActionSender() {
		return actionSender;
	}

	private ActionAssistant actionAssistant = new ActionAssistant(this);

	public ActionAssistant getActionAssistant() {
		return actionAssistant;
	}

	private ContainerAssistant containerAssistant = new ContainerAssistant(this);

	public ContainerAssistant getContainerAssistant() {
		return containerAssistant;
	}

	private Equipment equipment = new Equipment(this);

	public Equipment getEquipment() {
		return equipment;
	}

	private Bonuses bonuses = new Bonuses(this);

	public Bonuses getBonuses() {
		return bonuses;
	}

	private TradeHandler tradeHandler = new TradeHandler(this);

	public TradeHandler getTradeHandler() {
		return tradeHandler;
	}

	private FriendsHandler friendsHandler = new FriendsHandler(this);

	public FriendsHandler getFriendsHandler() {
		return friendsHandler;
	}

	private PrayerHandler prayerHandler = new PrayerHandler(this);

	public int[] walkToSpot;

	public PrayerHandler getPrayerHandler() {
		return prayerHandler;
	}
}