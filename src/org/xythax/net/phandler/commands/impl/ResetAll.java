package org.xythax.net.phandler.commands.impl;

import org.xythax.model.Client;
import org.xythax.net.phandler.commands.Command;
import org.xythax.utils.Constants;

public class ResetAll implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 3) {
			for (int skill = 0; skill < Constants.MAX_SKILLS; skill++) {
				client.playerLevel[skill] = 1;
				client.playerXP[skill] = 0;
				client.getActionAssistant().refreshSkill(skill);
			}
		}
	}
}
