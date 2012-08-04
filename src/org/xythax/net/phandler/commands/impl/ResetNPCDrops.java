package org.xythax.net.phandler.commands.impl;

import java.util.Map;

import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.NPC;
import org.xythax.net.phandler.commands.Command;

public class ResetNPCDrops implements Command {

	@Override
	public void execute(Client client, String command) {

		if (client.getPrivileges() >= 2) {

			for (Map.Entry<Integer, NPC> entry : GameEngine.getNpcManager().npcMap
					.entrySet()) {

				NPC n = entry.getValue();

				n.getDefinition().getDrops().clear();
			}
			GameEngine.getNpcManager().loadDrops();
			GameEngine.getGlobalActions().sendMessage(
					"NPC drops have been updated");
		}
	}

}
