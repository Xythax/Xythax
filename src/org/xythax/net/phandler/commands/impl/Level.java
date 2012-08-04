package org.xythax.net.phandler.commands.impl;

import org.xythax.model.Client;
import org.xythax.net.phandler.commands.Command;

/**
 * Level commands
 * 
 * @authur Ultimate
 */
public class Level implements Command {

	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 0) {
			String[] parts = command.split(" ");
			try {
				int skillId = Integer.valueOf(parts[1]);
				int level = Integer.valueOf(parts[2]);
				if (level > 99)
					return;
				double amount = client.getXPForLevel(level + 1);
				if (skillId > 22)
					return;
				if (amount < 0)
					return;
				client.playerLevel[skillId] = client
						.getLevelForXP((int) amount);
				client.playerXP[skillId] = (int) amount;
				client.getActionAssistant().refreshSkill(skillId);
			} catch (Exception e) {
				client.getActionSender().sendMessage(
						"Syntax is ::level <skillId> <level>.");
				client.getActionSender()
						.sendMessage(
								"ATTACK = 0, DEFENCE = 1, STRENGTH = 2, HITPOINTS = 3, RANGED = 4, MAGIC = 6.");
			}
		}
	}

}
