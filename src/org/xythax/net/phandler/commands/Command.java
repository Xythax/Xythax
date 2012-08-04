package org.xythax.net.phandler.commands;

import org.xythax.model.Client;

/**
 * Command interface.
 * 
 * @author Graham
 */
public interface Command {
	public void execute(Client client, String command);
}