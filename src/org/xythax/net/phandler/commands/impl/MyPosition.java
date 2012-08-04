package org.xythax.net.phandler.commands.impl;

import org.xythax.model.Client;
import org.xythax.net.phandler.commands.Command;

/**
 * My position command
 * 
 * @author Graham
 */
public class MyPosition implements Command {
	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2)
			client.getActionSender().sendMessage(
					"[SERVER] playerX: " + client.getAbsX() + " playerY: "
							+ client.getAbsY() + " playerHeight: "
							+ client.getHeightLevel() + ".");
	}
}