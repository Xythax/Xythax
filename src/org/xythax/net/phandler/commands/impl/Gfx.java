package org.xythax.net.phandler.commands.impl;

import org.xythax.model.Client;
import org.xythax.net.phandler.commands.Command;

public class Gfx implements Command {

	@Override
	public void execute(Client client, String command) {
		if (command.length() > 4) {
			int gfx = Integer.valueOf(command.substring(4));
			if (client.getPrivileges() >= 3) {
				client.getActionAssistant().createPlayerGfx(gfx, 0, true);
			}
		} else {
			client.getActionSender().sendMessage(
					"Wrong syntax use ::gfx <emote id>");
		}
	}

}
