package org.xythax.net.phandler.commands.impl;

import org.xythax.model.Client;
import org.xythax.net.phandler.commands.Command;

public class Runes implements Command {
	int[] set = { 554, 555, 556, 557, 558, 559, 560, 561, 562, 563, 564, 565,
			566 };

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			for (int rune : set)
				client.getActionSender().sendInventoryItem(rune, 999);
		}
	}
}