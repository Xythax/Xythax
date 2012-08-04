package org.xythax.net.phandler.commands.impl;

import org.xythax.content.JailSystem;
import org.xythax.content.controllers.Location;
import org.xythax.model.Client;
import org.xythax.model.Player;
import org.xythax.net.phandler.commands.Command;
import org.xythax.world.PlayerManager;

public class Jail implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			if (command.length() > 5) {
				String name = command.substring(5);

				if (client.getUsername() == name) {
					client.getActionSender().sendMessage(
							"You cannot jail yourself.");
					return;
				}
				client.getActionSender().sendMessage(
						"You have jailed " + name + ".");
				client.getActionSender().sendMessage(
						"You must now decided the severity of his 'crime'.");
				client.getActionSender()
						.sendMessage(
								"You must let the player express themselves before they receive punishment.");
				Location.addNewRequest(client, 3228, 3410, 0, 0);

				for (Player p : PlayerManager.getPlayerManager().getPlayers()) {

					if (p == null)
						continue;

					if (p.getUsername() == null)
						continue;

					if (p.getUsername().equalsIgnoreCase(name)) {
						JailSystem.addToJail((Client) p);
						break;
					}
				}

			} else {
				client.getActionSender()
						.sendMessage("Syntax is ::jail <name>.");
			}
		}
	}

}
