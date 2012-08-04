package org.xythax.net.phandler.packets;

import org.xythax.content.actions.ActionManager;
import org.xythax.content.skills.GemCrafting;
import org.xythax.model.Client;
import org.xythax.net.phandler.Packet;

/**
 * Item on Item
 * 
 * @author Killamess
 */

public class ItemOnItem implements Packet {

	public static final int BALL_OF_WALL = 1759;
	public static final int CLAY = 434;
	public static final int JUG_OF_WATER = 1937;
	public static final int CHISEL = 1755;
	public static final int NEEDLE = 1733, THREAD = 1734;

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		int usedWithSlot = client.inStream.readUnsignedWord();
		int itemUsedSlot = client.inStream.readUnsignedWordA();

		int useWith = client.playerItems[usedWithSlot] - 1;
		int itemUsed = client.playerItems[itemUsedSlot] - 1;

		if (CHISEL == useWith || CHISEL == itemUsed) {
			for (int i = 0; i < GemCrafting.craftGem.length; i++) {
				if (CHISEL != useWith) {
					if (useWith == GemCrafting.craftGem[i][0]) {
						GemCrafting.craftGem(client, useWith);
						return;
					}
				} else if (CHISEL != itemUsed) {
					if (itemUsed == GemCrafting.craftGem[i][0]) {
						GemCrafting.craftGem(client, itemUsed);
						return;
					}
				}
			}
		}
		ActionManager.destructActions(client.getUsername());
		if (CLAY == useWith && JUG_OF_WATER == itemUsed || CLAY == itemUsed
				&& JUG_OF_WATER == useWith) {
			makeSoftClay(client);
			return;
		}
		if (BALL_OF_WALL == useWith || BALL_OF_WALL == itemUsed) {
			for (int i = 0; i < GemCrafting.mendItems.length; i++) {
				if (GemCrafting.mendItems[i][0] == useWith
						|| GemCrafting.mendItems[i][0] == itemUsed) {
					GemCrafting.string(client, i);
					break;
				}
			}
		}
	}

	public static void makeSoftClay(Client client) {
		if (client.getActionAssistant().isItemInInventory(434)
				&& client.getActionAssistant().isItemInInventory(1937)) {
			client.getActionAssistant().deleteItem(1937, 1);
			client.getActionSender().sendInventoryItem(1935, 1);
			client.getActionAssistant().deleteItem(434, 1);
			client.getActionSender().sendInventoryItem(1761, 1);
			client.getActionSender().sendMessage(
					"You mix the clay and the water.");
			client.getActionSender().sendMessage(
					"You now have some soft, workable clay.");
		}
	}

}