package org.xythax.net.phandler.commands.impl;

import org.xythax.model.Client;
import org.xythax.model.combat.content.Specials;
import org.xythax.net.phandler.commands.Command;

public class Special implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 3) {
			client.setSpecialAmount(100);
			Specials.updateSpecialBar(client);
		}
	}
}
