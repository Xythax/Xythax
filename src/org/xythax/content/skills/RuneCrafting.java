package org.xythax.content.skills;

import org.xythax.content.controllers.Animation;
import org.xythax.content.controllers.Graphics;
import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.map.TileManager;
import org.xythax.utils.Constants;

public class RuneCrafting {

	public static boolean runeCraftArea(Client client) {
		int[] myLocation = TileManager.currentLocation(client);

		return myLocation[0] >= 3024 && myLocation[0] <= 3054
				&& myLocation[1] >= 4817 && myLocation[1] <= 4846;
	}

	public static void craftRunesOnAltar(Client client, int requiredLevel,
			int exp, int item, int x2, int x3, int x4, int talismen) {

		int essamount = 0;
		if (!client.getActionAssistant().isItemInInventory(talismen)) {
			client.getActionSender().sendMessage(
					"You need "
							+ GameEngine.getItemManager()
									.getItemDefinition(talismen).getName()
									.toLowerCase()
							+ " to craft "
							+ GameEngine.getItemManager()
									.getItemDefinition(item).getName()
									.toLowerCase() + "s.");
			return;
		}
		if (client.playerLevel[Constants.RUNECRAFTING] < requiredLevel) {
			client.getActionSender().sendMessage(
					"You need a runecrafting level of " + requiredLevel + " .");
			return;
		}
		if (!client.getActionAssistant().isItemInInventory(1436)) {
			client.getActionSender().sendMessage(
					"You need "
							+ GameEngine.getItemManager()
									.getItemDefinition(1436).getName()
									.toLowerCase()
							+ " to craft "
							+ GameEngine.getItemManager()
									.getItemDefinition(item).getName()
									.toLowerCase() + "s.");
			return;
		}
		Graphics.addNewRequest(client, 186, 100, 0);
		Animation.addNewRequest(client, 791, 0);

		if (client.playerLevel[Constants.RUNECRAFTING] >= 0
				&& client.playerLevel[Constants.RUNECRAFTING] < x2) {
			essamount = client.getActionAssistant().getItemCount(1436);
		}
		if (client.playerLevel[Constants.RUNECRAFTING] >= x2
				&& client.playerLevel[Constants.RUNECRAFTING] < x3) {
			essamount = client.getActionAssistant().getItemCount(1436) * 2;
		}
		if (client.playerLevel[Constants.RUNECRAFTING] >= x4) {
			essamount = client.getActionAssistant().getItemCount(1436) * 3;
		}
		for (int i = 0; i < 28; i++) {
			client.getActionAssistant().deleteItem(1436,
					client.getActionAssistant().getItemSlot(1436), i);
		}
		client.getActionAssistant().addSkillXP(
				(exp * essamount) * Constants.SKILL_EXPERIENCE_MULTIPLIER,
				Constants.RUNECRAFTING);
		client.getActionSender().sendInventoryItem(item, essamount);
		client.getActionSender().sendMessage(
				"You craft "
						+ essamount
						+ " "
						+ GameEngine.getItemManager().getItemDefinition(item)
								.getName() + "s.");
		essamount = -1;
	}

}
