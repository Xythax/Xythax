package org.xythax.net.phandler.commands.impl;

import org.xythax.model.Client;
import org.xythax.net.phandler.commands.Command;
import org.xythax.utils.Constants;

/**
 * 
 * @author Killamess Just a max stat command.
 * 
 */
public class Master implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			for (int skill = 0; skill < Constants.MAX_SKILLS; skill++) {
				client.playerLevel[skill] = client.getLevelForXP(14000000);
				client.playerXP[skill] = 14000000;
				client.getActionAssistant().refreshSkill(skill);
			}
		}
	}

}
