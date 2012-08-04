package org.xythax.net.phandler.packets;

import org.xythax.model.Client;
import org.xythax.net.phandler.Packet;

/**
 * DialogueAction clicks
 * 
 * @author Ultimate
 */
public class DialogueAction implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		client.getActionSender().sendWindowsRemoval();
	}

}