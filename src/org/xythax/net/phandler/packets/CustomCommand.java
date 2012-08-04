package org.xythax.net.phandler.packets;

import org.xythax.model.Client;
import org.xythax.net.phandler.Packet;
import org.xythax.net.phandler.commands.CommandManager;

/**
 * Handles custom commands
 * 
 * @author Graham
 */
public class CustomCommand implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		String playerCommand = client.inStream.readString();
		client.println_debug("playerCommand: " + playerCommand);
		CommandManager.execute(client, playerCommand);
	}
}