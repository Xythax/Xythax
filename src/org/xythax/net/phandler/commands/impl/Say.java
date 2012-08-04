package org.xythax.net.phandler.commands.impl;

import org.xythax.model.Client;
import org.xythax.net.phandler.commands.Command;

public class Say implements Command {

	@Override
	public void execute(Client client, String command) {
		if(client.getPrivileges() >= 2) {
			//DO SOMETHING
		} else {
		  client.getActionSender().sendMessage("no work");	
		}
		
	}

}
