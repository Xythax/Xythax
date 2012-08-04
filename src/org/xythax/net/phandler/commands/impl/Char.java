package org.xythax.net.phandler.commands.impl;

import org.xythax.model.Client;
import org.xythax.net.phandler.commands.Command;

public class Char implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 1) {
			client.getActionSender().sendInterface(3559);
		}
	}

}