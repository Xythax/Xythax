package org.xythax.net.phandler.commands.impl;

import org.xythax.model.Client;
import org.xythax.model.Player;
import org.xythax.net.phandler.commands.Command;
import org.xythax.world.PlayerManager;

public class TeleportToMe implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			if (command.length() > 9) {
				String name = command.substring(9);
				for (Player p : PlayerManager.getPlayerManager().getPlayers()) {
					if (p == null)
						continue;
					if (!p.isActive || p.disconnected)
						continue;
					if (p.getUsername().equalsIgnoreCase(name)) {
						p.teleportToZ = client.getHeightLevel();
						p.teleportToX = client.getAbsX();
						p.teleportToY = client.getAbsY();
					}
				}
			} else {
				client.getActionSender().sendMessage(
						"Syntax is ::teletome <name>.");
			}
		} else {
			client.getActionSender().sendMessage(
					"You do not have the correct rights to use this command.");
		}
	}

}
