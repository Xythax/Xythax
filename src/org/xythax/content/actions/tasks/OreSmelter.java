package org.xythax.content.actions.tasks;

import org.xythax.content.actions.Action;
import org.xythax.content.actions.Task;
import org.xythax.content.controllers.Animation;
import org.xythax.core.GameEngine;
import org.xythax.utils.Constants;

public class OreSmelter implements Task {

	@Override
	public void execute(Action currentAction) {
		currentAction.getClient().getActionSender().sendWindowsRemoval();
		currentAction.getClient().smeltAmount = 28;
		currentAction.setCurrentTick(0);
		currentAction.setActionType(Action.type.LOOPING);
	}

	@Override
	public void loop(Action currentAction) {
		if (currentAction.getClient().oreId < 1
				|| currentAction.getClient().smeltAmount < 1) {
			stop(currentAction);
			return;
		}
		if (currentAction.getCurrentTick() > 0) {
			currentAction.setCurrentTick(currentAction.getCurrentTick() - 1);
			return;
		}
		currentAction.getClient().smeltAmount--;
		if (currentAction.getClient().getActionAssistant()
				.playerHasItem(currentAction.getClient().oreId, 1)) {
			for (int i = 0; i < oreConverter.length; i++) {
				if (oreConverter[i][0] == currentAction.getClient().oreId) {
					if (currentAction.getClient().playerLevel[Constants.CRAFTING] < oreConverter[i][2]) {
						currentAction
								.getClient()
								.getActionSender()
								.sendMessage(
										"You need a smithing level of "
												+ oreConverter[i][2]
												+ " to smith "
												+ GameEngine
														.getItemManager()
														.getItemDefinition(
																oreConverter[i][0])
														.getName()
														.toLowerCase() + ".");
						stop(currentAction);
						return;
					}
					// boolean multipleSmelt = false;
					switch (currentAction.getClient().oreId) {
					// ores that use more than 1 ore
					}
					currentAction.getClient().getActionAssistant()
							.deleteItem(currentAction.getClient().oreId, 1);
					currentAction.getClient().getActionSender()
							.sendInventoryItem(oreConverter[i][1], 1);
					currentAction
							.getClient()
							.getActionAssistant()
							.addSkillXP(
									oreConverter[i][3]
											* Constants.SKILL_EXPERIENCE_MULTIPLIER,
									Constants.SMITHING);
					break;
				}
			}

		} else {
			currentAction.getClient().getActionSender()
					.sendMessage("You have ran out of ore.");
			stop(currentAction);
			return;
		}
		Animation.addNewRequest(currentAction.getClient(), 899, 0);
		currentAction.setCurrentTick(2);
	}

	@Override
	public void stop(Action currentAction) {
		currentAction.getClient().oreId = 0;
		currentAction.getClient().smeltAmount = 0;
		currentAction.setActionType(Action.type.TRASHING);
	}

	public static final int[][] oreConverter = { { 444, 2357, 40, (int) 22.5 }, // gold
	};

	// bar type, ores used.
	public static final int[][] ores = { { 2357, 444, 0, 0 }, // gold

	};

}
