package org.xythax.net.phandler.commands.impl;

import org.xythax.model.Client;
import org.xythax.net.phandler.commands.Command;
import org.xythax.world.PlayerManager;

public class Kick implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() > 1) {
			if (command.length() > 5) {
				String name = command.substring(5);
				PlayerManager.getPlayerManager().kick(name);
				client.getActionSender().sendMessage(
						"You have kicked " + name + ".");
			} else {
				client.getActionSender()
						.sendMessage("Syntax is ::kick <name>.");
			}
		}
	}

}
