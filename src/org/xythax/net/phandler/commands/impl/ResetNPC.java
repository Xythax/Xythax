package org.xythax.net.phandler.commands.impl;

import java.util.Map;

import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.NPC;
import org.xythax.model.combat.CombatEngine;
import org.xythax.net.phandler.commands.Command;
import org.xythax.utils.Constants;
import org.xythax.world.PlayerManager;

public class ResetNPC implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {

			for (Map.Entry<Integer, NPC> entry : GameEngine.getNpcManager().npcMap
					.entrySet()) {
				NPC n = entry.getValue();
				n.setHidden(true);
			}
			GameEngine.getNpcManager().npcMap.clear();
			GameEngine.getNpcManager().reloadSpawns();
			GameEngine.getGlobalActions().sendMessage("NPC Reset");

			for (int i2 = 0; i2 < Constants.MAX_PLAYERS; i2++) {

				Client client2 = (Client) PlayerManager.getPlayerManager()
						.getPlayers()[i2];

				if (client2 == null || client2.isDead())
					continue;

				if (client2.getTarget() != null) {
					if (client2.getTarget() instanceof NPC) {
						CombatEngine.resetAttack(client2, true);
					}
				}
			}
		}
	}

}