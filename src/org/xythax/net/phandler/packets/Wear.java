package org.xythax.net.phandler.packets;

/**
 * Wear item
 *
 * @author Ultimate
 *
 **/

import org.xythax.content.actions.ActionManager;
import org.xythax.model.Client;
import org.xythax.net.phandler.Packet;

public class Wear implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		int wearID = client.inStream.readUnsignedWord();
		int wearSlot = client.inStream.readUnsignedWordA();
		@SuppressWarnings("unused")
		int interfaceID = client.inStream.readUnsignedWordA();

		if (client.hitpoints > 0 && !client.isBusy()) {
			if (client.getTradeHandler().getStage() == 0) {
				client.getEquipment().wearItem(wearID, wearSlot);
			}
		}
		ActionManager.destructActions(client.getUsername());
	}
}