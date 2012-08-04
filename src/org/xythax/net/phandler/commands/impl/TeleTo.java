package org.xythax.net.phandler.commands.impl;

import org.xythax.content.controllers.Location;
import org.xythax.model.Client;
import org.xythax.net.phandler.commands.Command;

public class TeleTo implements Command {

	@Override
	public void execute(Client client, String command) {

		if (client.getPrivileges() >= 2) {

			String[] parts = command.split(" ");

			try {

				int xCord = Integer.valueOf(parts[1]);
				int yCord = Integer.valueOf(parts[2]);

				Location.addNewRequest(client, xCord, yCord, 0, 1);

			} catch (Exception e) {
			}
		}

	}

}
