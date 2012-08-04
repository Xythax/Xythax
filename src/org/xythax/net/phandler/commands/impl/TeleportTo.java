package org.xythax.net.phandler.commands.impl;

import org.xythax.model.Client;
import org.xythax.model.Player;
import org.xythax.net.phandler.commands.Command;
import org.xythax.world.PlayerManager;

public class TeleportTo implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 1) {
			if (command.length() > 7) {
				String name = command.substring(7);
				for (Player p : PlayerManager.getPlayerManager().getPlayers()) {
					if (p == null)
						continue;
					if (!p.isActive || p.disconnected)
						continue;
					if (p.getUsername().equalsIgnoreCase(name)) {
						client.teleportToZ = p.getHeightLevel();
						client.teleportToX = p.getAbsX();
						client.teleportToY = p.getAbsY();
					}
				}
			} else {
				client.getActionSender().sendMessage(
						"Syntax is ::teleto <name>.");
			}
		}
	}

}
