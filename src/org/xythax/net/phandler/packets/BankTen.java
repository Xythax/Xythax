package org.xythax.net.phandler.packets;

import org.xythax.content.actions.ActionManager;
import org.xythax.content.skills.GemCrafting;
import org.xythax.content.skills.SmithingMakeItem;
import org.xythax.model.Client;
import org.xythax.net.phandler.Packet;

/**
 * Bank five items
 * 
 * @author Graham
 */
public class BankTen implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		int interfaceID = client.inStream.readUnsignedWordA();
		int removeID = client.inStream.readUnsignedWordA();
		int removeSlot = client.inStream.readUnsignedWordA();
		ActionManager.destructActions(client.getUsername());
		System.out.println(interfaceID);
		if (interfaceID == 51347) {
			client.getContainerAssistant().bankItem(removeID, removeSlot, 10);
		} else if (interfaceID == 24452 || interfaceID == 24708
				|| interfaceID == 24964 || interfaceID == 25220
				|| interfaceID == 25476) {
			new SmithingMakeItem(client, removeID, 10);
		} else if (interfaceID == 1685) {
			client.getContainerAssistant().fromBank(removeID, removeSlot, 10);
		} else if (interfaceID == 15503) {
			client.getContainerAssistant().buyItem(removeID, removeSlot, 5);
		} else if (interfaceID == 61326) {
			client.getContainerAssistant().sellItem(removeID, removeSlot, 5);
		} else if (interfaceID == 64140) {
			client.getTradeHandler().tradeItem(removeID, removeSlot, 10);
		} else if (interfaceID == 22413) {
			client.getTradeHandler().removeItemFromTrade(removeID, removeSlot,
					10);
		} else if (interfaceID == 65436) {
			client.getContainerAssistant().bankItem(removeID, removeSlot, 10);
			client.getActionSender().sendItemReset(7423);
		} else if (interfaceID == 35216 || interfaceID == 36752
				|| interfaceID == 38288) {
			for (int i = 0; i < GemCrafting.ITEMS.length; i++) {
				if (GemCrafting.ITEMS[i][0] == removeID) {
					GemCrafting.startCrafter(client, GemCrafting.ITEMS[i][1],
							10, GemCrafting.ITEMS[i][2]);
				}
			}
		}

	}

}
