package org.xythax.net.phandler.packets;

import org.xythax.content.actions.ActionManager;
import org.xythax.model.Client;
import org.xythax.model.Item;
import org.xythax.net.phandler.Packet;

/**
 * Bank all items packet
 * 
 * @author Graham
 */
public class BankAll implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		int removeSlot = client.inStream.readUnsignedWordA();
		int interfaceID = client.inStream.readUnsignedWordA();
		int removeID = client.inStream.readUnsignedWordA();
		ActionManager.destructActions(client.getUsername());

		System.out.println(interfaceID);

		if (interfaceID == 4936) {
			if (Item.itemStackable[removeID]) {
				client.getContainerAssistant().bankItem(
						client.playerItems[removeSlot], removeSlot,
						client.playerItemsN[removeSlot]);
			} else {
				client.getContainerAssistant().bankItem(
						client.playerItems[removeSlot],
						removeSlot,
						client.getActionAssistant().getItemAmount(
								client.playerItems[removeSlot] - 1));
			}
		} else if (interfaceID == 5510) {
			client.getContainerAssistant().fromBank(
					client.bankItems[removeSlot], removeSlot,
					client.bankItemsN[removeSlot]);
		} else if (interfaceID == 7295) {
			if (Item.itemStackable[removeID]) {
				client.getContainerAssistant().bankItem(
						client.playerItems[removeSlot], removeSlot,
						client.playerItemsN[removeSlot]);
				client.getActionSender().sendItemReset(7423);
			} else {
				client.getContainerAssistant().bankItem(
						client.playerItems[removeSlot],
						removeSlot,
						client.getActionAssistant().getItemAmount(
								client.playerItems[removeSlot] - 1));
				client.getActionSender().sendItemReset(7423);
			}
		} else if (interfaceID == 3695) {
			client.getContainerAssistant().sellItem(removeID, removeSlot, 10);
		} else if (interfaceID == 4028) {
			client.getContainerAssistant().buyItem(removeID, removeSlot, 10);
		} else if (interfaceID == 3194) {
			client.getTradeHandler().tradeItem(removeID, removeSlot,
					client.playerItemsN[removeSlot]);
		} else if (interfaceID == 3543) {
			client.getTradeHandler().removeItemFromTrade(removeID, removeSlot,
					client.getTradeHandler().getOfferN()[removeSlot]);
		}

	}

}
