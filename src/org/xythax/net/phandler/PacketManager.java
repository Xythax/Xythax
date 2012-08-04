package org.xythax.net.phandler;

import org.xythax.model.Client;
import org.xythax.net.phandler.packets.ActionButtons;
import org.xythax.net.phandler.packets.Attack;
import org.xythax.net.phandler.packets.BankAll;
import org.xythax.net.phandler.packets.BankFive;
import org.xythax.net.phandler.packets.BankTen;
import org.xythax.net.phandler.packets.BankX;
import org.xythax.net.phandler.packets.CharacterDesign;
import org.xythax.net.phandler.packets.Chat;
import org.xythax.net.phandler.packets.ClickNPC;
import org.xythax.net.phandler.packets.Clicking;
import org.xythax.net.phandler.packets.CustomCommand;
import org.xythax.net.phandler.packets.DialogueAction;
import org.xythax.net.phandler.packets.DropItem;
import org.xythax.net.phandler.packets.FollowPlayer;
import org.xythax.net.phandler.packets.IdleLogout;
import org.xythax.net.phandler.packets.ItemOnItem;
import org.xythax.net.phandler.packets.ItemOnNpc;
import org.xythax.net.phandler.packets.ItemOnObject;
import org.xythax.net.phandler.packets.ItemOption;
import org.xythax.net.phandler.packets.LoadingComplete;
import org.xythax.net.phandler.packets.MagicOnInventoryItem;
import org.xythax.net.phandler.packets.MagicOnNPC;
import org.xythax.net.phandler.packets.MagicOnPlayer;
import org.xythax.net.phandler.packets.MoveItems;
import org.xythax.net.phandler.packets.ObjectClick;
import org.xythax.net.phandler.packets.PickupItem;
import org.xythax.net.phandler.packets.Remove;
import org.xythax.net.phandler.packets.TradeAction;
import org.xythax.net.phandler.packets.Unused;
import org.xythax.net.phandler.packets.UseItem;
import org.xythax.net.phandler.packets.Walk;
import org.xythax.net.phandler.packets.Wear;

/**
 * PacketsProcessor
 * 
 * @author Java'
 * @author Steven
 */
public class PacketManager {

	public PacketManager() {
	}

	public static final int MAX_PACKETS = 256;
	public static Packet[] packet = new Packet[MAX_PACKETS];

	public static void loadAllPackets() {
		int unhandledPackets = 0;
		/*
		 * Packets
		 */
		packet[237] = new MagicOnInventoryItem();
		packet[153] = new FollowPlayer();
		packet[202] = new IdleLogout();
		packet[101] = new CharacterDesign();
		packet[121] = new LoadingComplete();
		packet[210] = new LoadingComplete();
		packet[122] = new UseItem();
		packet[248] = new Walk();
		packet[164] = new Walk();
		packet[98] = new Walk();
		packet[57] = new ItemOnNpc();
		packet[4] = new Chat();
		packet[95] = new Chat();
		packet[188] = new Chat();
		packet[215] = new Chat();
		packet[133] = new Chat();
		packet[74] = new Chat();
		packet[126] = new Chat();
		packet[132] = new ObjectClick();
		packet[252] = new ObjectClick();
		packet[70] = new ObjectClick();
		packet[236] = new PickupItem();
		packet[73] = new TradeAction();
		packet[139] = new TradeAction();
		packet[103] = new CustomCommand();
		packet[214] = new MoveItems();
		packet[41] = new Wear();
		packet[145] = new Remove();
		packet[117] = new BankFive();
		packet[43] = new BankTen();
		packet[129] = new BankAll();
		packet[135] = new BankX();
		packet[208] = new BankX();
		packet[87] = new DropItem();
		packet[185] = new ActionButtons();
		packet[130] = new Clicking();
		packet[155] = new ClickNPC();
		packet[230] = new ClickNPC();
		packet[17] = new ClickNPC();
		packet[21] = new ClickNPC();
		packet[40] = new DialogueAction();
		packet[53] = new ItemOnItem();//
		packet[128] = new Attack();
		packet[72] = new Attack();
		packet[249] = new MagicOnPlayer();
		packet[131] = new MagicOnNPC();
		packet[192] = new ItemOnObject();
		packet[75] = new ItemOption();

		/*
		 * Loaded Packets Count
		 */
		for (int i = 0; i < MAX_PACKETS; i++) {
			if (packet[i] == null)
				packet[i] = new Unused();
		}

		int loadedPackets = MAX_PACKETS - unhandledPackets;
		System.out.println("Loaded " + loadedPackets + " packets.");
	}

	public static void handlePacket(Client c, int packetType, int packetSize) {
		try {
			// c.getActionSender().sendMessage(packetType+"");
			if (packet[packetType] != null && packetType <= MAX_PACKETS
					&& packetType >= 0) {
				packet[packetType].handlePacket(c, packetType, packetSize);
			} else {
				System.out.println("Unhandled Packet: " + packetType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}