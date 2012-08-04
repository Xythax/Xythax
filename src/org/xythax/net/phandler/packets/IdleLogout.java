package org.xythax.net.phandler.packets;

import org.xythax.model.Client;
import org.xythax.net.phandler.Packet;

/**
 * Idle logout
 * 
 * @author Ultimate
 */

public class IdleLogout implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		// if (client.logoutDelay == 0) {
		// client.getActionSender().sendLogout();
		// }
	}

}