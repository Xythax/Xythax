package org.xythax.net.phandler.packets;

import org.xythax.model.Client;
import org.xythax.net.phandler.Packet;

/**
 * Killamess
 * 
 * @author killamess
 * 
 */
public class ItemOption implements Packet {

	@Override
	@SuppressWarnings("unused")
	public void handlePacket(Client client, int packetType, int packetSize) {
		int junk = client.inStream.readSignedWordA();
		int amount = client.inStream.readSignedWordBigEndian();
		int itemId = client.inStream.readSignedWordA();
		int moreJunk = client.inStream.readUnsignedWord();

	}

}