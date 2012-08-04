package org.xythax.net.phandler.commands.impl;

import org.xythax.model.Client;
import org.xythax.model.Player;
import org.xythax.net.phandler.commands.Command;
import org.xythax.world.PlayerManager;


public class SaveAll implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			int saved = 0;
			for (Player p : PlayerManager.getPlayerManager().getPlayers()) {
				if (p == null)
					continue;
				saved++;
				PlayerManager.getPlayerManager().saveGame(p, false);
				client.getActionSender().sendMessage(
						"Game saved from " + p.getUsername() + ".");
			}
			client.getActionSender().sendMessage(
					"A total of " + saved + " characters were saved.");
		} else {
			client.getActionSender().sendMessage(
					"You do not have the correct rights to use this command.");
		}
	}

}
