package org.xythax.net.phandler.packets;

import org.xythax.model.Client;
import org.xythax.net.phandler.Packet;

/**
 * Move items packet
 * 
 * @author Graham
 */
public class MoveItems implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		// int moveWindow = client.inStream.readUnsignedWordA(); // junk
		// int itemFrom = client.inStream.readUnsignedWordA();// slot1
		// int itemTo = (client.inStream.readUnsignedWordA() - 128);// slot2
		int interfaceId = client.inStream.readSignedWordBigEndianA();
		boolean insertMode = client.inStream.readSignedByteC() == 1;
		int from = client.inStream.readSignedWordBigEndianA();
		int to = client.inStream.readSignedWordBigEndian();

		client.getContainerAssistant().moveBankItems(from, to, interfaceId,
				insertMode);
		client.getActionSender().sendBankReset();
		client.getActionSender().sendItemReset(3214);
		client.getActionSender().sendItemReset(5064);

	}

}
