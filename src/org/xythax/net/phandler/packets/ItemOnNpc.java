package org.xythax.net.phandler.packets;

import org.xythax.model.Client;
import org.xythax.net.phandler.Packet;

/**
 * Bank all items packet
 * 
 * @author Graham
 */
public class ItemOnNpc implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		// /int itemId = client.inStream.readSignedWordA();
		// int npcId = client.inStream.readSignedWordA();
		// int slot = client.inStream.readSignedWordBigEndian();
		client.getActionSender().sendMessage(
				"Items on npcs is not finished yet.");
		// System.out.println(itemId +" "+ npcId +" "+ slot +".");
		return;

	}
}
