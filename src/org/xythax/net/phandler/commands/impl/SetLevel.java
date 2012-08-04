package org.xythax.net.phandler.commands.impl;

import org.xythax.model.Client;
import org.xythax.net.phandler.commands.Command;

public class SetLevel implements Command {
	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			if (command.length() > 9) {
				int level = Integer.valueOf(command.substring(9));
				client.combatLevel = level;
			} else {
				client.getActionSender().sendMessage(
						"Wrong syntax use ::setLevel <combat level>");
			}
		}
	}
}