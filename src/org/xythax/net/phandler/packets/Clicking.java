package org.xythax.net.phandler.packets;

import org.xythax.model.Client;
import org.xythax.net.phandler.Packet;

/**
 * Clicking
 * 
 * @author Ultimate
 */

public class Clicking implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		int interfaceId = client.inStream.readUnsignedWordA();
		switch (interfaceId) {
		}
		client.cancelTasks();
	}

}
