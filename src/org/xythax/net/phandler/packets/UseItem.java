package org.xythax.net.phandler.packets;

import org.xythax.content.UsingItemHandler;
import org.xythax.content.actions.ActionManager;
import org.xythax.model.Client;
import org.xythax.net.phandler.Packet;
import org.xythax.utils.Utils;

public class UseItem implements Packet {

	@SuppressWarnings("unused")
	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {

		int interfaceID = client.inStream.readSignedWordBigEndianA();
		int itemSlot = client.inStream.readUnsignedWordA();
		int itemID = client.inStream.readUnsignedWordBigEndian();
		ActionManager.destructActions(client.getUsername());
		if (client.playerItems[itemSlot] == itemID + 1) {

			if (itemID == 405
					&& client.getActionAssistant().isItemInInventory(itemID)) {
				client.getActionAssistant().deleteItem(itemID, itemSlot, 1);
				client.getActionSender().sendInventoryItem(995,
						casketRewards[Utils.random(casketRewards.length - 1)]);
			}
			UsingItemHandler.useItem(client, itemID, itemSlot);
		}
	}

	private int[] casketRewards = { 1000, 1000, 1000, 1000, 1000, 1000, 1000,
			1000, 2500, 1000, 1000, 1000, 1000, 1000, 1000, 5000, 2500, 1000,
			1000, 1000, 1000, 7500, 5000, 2500, 1000, 1000, 10000, 7500, 5000,
			2500, 25000, 10000, 7500, 50000, 25000, 100000 };

}
