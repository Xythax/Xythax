package org.xythax.net.phandler.packets;

import org.xythax.content.actions.ActionManager;
import org.xythax.model.Client;
import org.xythax.model.combat.CombatEngine;
import org.xythax.net.phandler.Packet;

/**
 * Pickup item packet
 * 
 * @author Graham
 */
public class PickupItem implements Packet {
	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		int itemY = client.inStream.readSignedWordBigEndian();
		int itemID = client.inStream.readUnsignedWord();
		int itemX = client.inStream.readSignedWordBigEndian();
		ActionManager.destructActions(client.getUsername());
		client.println_debug("pickupItem: " + itemX + "," + itemY + " itemID: "
				+ itemID);
		client.pickup[0] = itemX;
		client.pickup[1] = itemY;
		client.pickup[2] = itemID;
		// client.getActionAssistant().pickUpItem(itemX, itemY, itemID);
		CombatEngine.resetAttack(client, false);
	}
}