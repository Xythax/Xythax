package org.xythax.net.phandler;

import org.xythax.model.Client;

/**
 * Packet interface.
 * 
 * @author Graham
 */
public interface Packet {

	public void handlePacket(Client client, int packetType, int packetSize);
}
