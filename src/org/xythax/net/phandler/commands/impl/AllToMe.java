package org.xythax.net.phandler.commands.impl;

import org.xythax.model.Client;
import org.xythax.net.phandler.commands.Command;

public class AllToMe implements Command {
	@Override
	public void execute(Client client, String command) {
		/*
		 * if (client.getPrivileges() >= 2) {
		 * 
		 * for (Player p : PlayerManager.getSingleton().getPlayers()) {
		 * 
		 * if (p == null || (Client) p == client) continue;
		 * 
		 * Client player = (Client) p;
		 * 
		 * Location.addNewRequest(p, client.getAbsX(), client.getAbsY(),
		 * client.getHeightLevel(), 0); player.getActionSender().sendMessage(
		 * "You have been teleported to " + client.getUsername() + "."); }
		 * 
		 * } else { client.getActionSender().sendMessage(
		 * "You do not have the correct rights to use this command."); }
		 */
	}
}