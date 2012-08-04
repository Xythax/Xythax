package org.xythax.content.skills;

import org.xythax.content.actions.Action;
import org.xythax.content.actions.ActionManager;
import org.xythax.content.actions.Task;
import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.definition.XMLManager;
import org.xythax.model.definition.skills.Catches;
import org.xythax.utils.CommonStrings;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;

/**
 * 
 * @author Killamess
 * 
 */
public class Fishing implements Task {

	public static Catches fish(int fishingSpot) {
		for (Catches fish : XMLManager.catches) {
			if (fish.getFishingSpot() == fishingSpot)
				return fish;
		}
		return null;
	}

	public static void loadAction(Client client, int npc, int x, int y) {
		client.fishing = npc;
		client.fishingX = x;
		client.fishingY = y;

		if (destuct(client))
			return;

		client.getActionAssistant().turnTo(client.fishingX, client.fishingY);

		if (fish(npc) == null) {
			return;
		}
		client.getActionAssistant().startAnimation(fish(npc).getAnimation(), 0);
		client.getActionSender().sendMessage("You start Fishing.");
		ActionManager.addNewRequest(client, 16, 5);
	}

	public void calculateFishImport(Client client, int theCount) {
		if (theCount < 0 || theCount >= 3)
			return;

		int startOn = 0;
		int endOn = (startOn + Utils.random(theCount));

		if (fish(client.fishing) == null)
			return;

		while (fish(client.fishing).getCatches()[endOn] == -1)
			if (endOn > 0)
				endOn--;

		String fish = GameEngine.getItemManager()
				.getItemDefinition(fish(client.fishing).getCatches()[endOn])
				.getName();
		client.getActionSender().sendMessage(
				"You manage to catch a " + fish.toLowerCase() + ".");
		client.getActionSender().sendInventoryItem(
				fish(client.fishing).getCatches()[endOn], 1);
		client.getActionAssistant().startAnimation(
				fish(client.fishing).getAnimation(), 0);
		client.getActionAssistant().deleteItem(
				fish(client.fishing).getTools()[1],
				fish(client.fishing).getToolAmounts()[1]);
		client.getActionAssistant().addSkillXP(
				fishExp(fish(client.fishing).getCatches()[endOn])
						* Constants.SKILL_EXPERIENCE_MULTIPLIER,
				Constants.FISHING);
		client.updateRequired = true;
	}

	public static boolean destuct(Client client) {
		if (client.fishing < 1 || client.fishingX < 1 || client.fishingY < 1
				|| client.isBusy())
			return true;

		if (client.getActionAssistant().freeSlots() < 1) {
			client.getActionSender().sendMessage(CommonStrings.INV_FULL);
			return true;
		}
		if (fish(client.fishing) == null)
			return true;

		if (fish(client.fishing).getCatchLevels()[0] > client.playerLevel[Constants.FISHING]) {
			client.getActionSender().sendMessage(
					"You need a fishing level of "
							+ fish(client.fishing).getCatchLevels()[0]
							+ " to fish here.");
			return true;
		}
		int mainTool = fish(client.fishing).getTools()[0];
		int nextTool = fish(client.fishing).getTools()[1];

		if (!client.getActionAssistant().isItemInInventory(mainTool)) {
			client.getActionSender().sendMessage(
					"You need a "
							+ GameEngine.getItemManager()
									.getItemDefinition(mainTool).getName()
									.toLowerCase() + " to fish here.");
			return true;
		}
		if (!client.getActionAssistant().isItemInInventory(nextTool)
				&& nextTool != -1) {
			client.getActionSender().sendMessage(
					"You need a "
							+ GameEngine.getItemManager()
									.getItemDefinition(nextTool).getName()
									.toLowerCase() + " to fish here.");
			return true;
		}
		return false;
	}

	public void removeAction(Client client) {
		client.fishing = client.fishingX = client.fishingY = 0;
		client.getActionSender().sendAnimationReset();
		client.updateRequired = true;
	}

	@Override
	public void execute(Action currentAction) {
		currentAction.setActionType(Action.type.LOOPING);
	}

	@Override
	public void loop(Action currentAction) {

		currentAction.setCurrentTick(currentAction.getCurrentTick() - 1);

		if (currentAction.getCurrentTick() > 1) {
			return;
		}

		Client client = currentAction.getClient();

		if (client == null) {
			stop(currentAction);
			return;
		}
		if (fish(client.fishing) == null) {
			stop(currentAction);
			return;
		}
		int count = -1;

		if (client.playerLevel[Constants.FISHING] >= fish(client.fishing)
				.getCatchLevels()[0]
				&& fish(client.fishing).getCatchLevels()[0] != -1)
			count++;
		if (client.playerLevel[Constants.FISHING] >= fish(client.fishing)
				.getCatchLevels()[1]
				&& fish(client.fishing).getCatchLevels()[1] != -1)
			count++;
		if (client.playerLevel[Constants.FISHING] >= fish(client.fishing)
				.getCatchLevels()[2]
				&& fish(client.fishing).getCatchLevels()[2] != -1)
			count++;
		if (generateSuccess(client.playerLevel[Constants.FISHING],
				fish(client.fishing).getCatchLevels()[count]))
			calculateFishImport(client, count);
		else
			client.getActionAssistant().startAnimation(
					fish(client.fishing).getAnimation(), 0);

		if (destuct(client)) {
			stop(currentAction);
			return;
		}
		currentAction.setCurrentTick(5);
	}

	public boolean generateSuccess(int level, int levelReq) {
		if (level - 25 > levelReq)
			return true;
		if (level - 20 > levelReq)
			return Utils.random(1) == 0;
		if (level - 15 > levelReq)
			return Utils.random(2) == 0;
		if (level - 10 > levelReq)
			return Utils.random(3) == 0;
		if (level - 5 > levelReq)
			return Utils.random(4) == 0;
		return Utils.random(5) == 0;
	}

	@Override
	public void stop(Action currentAction) {
		removeAction(currentAction.getClient());
		currentAction.setActionType(Action.type.TRASHING);
	}

	public static final int fishExp(int fish) {
		switch (fish) {
		case 317: // Raw_shrimps
			return 10;
		case 321: // Raw_anchovies
			return 40;
		case 327: // Raw_sardine
			return 20;
		case 331: // Raw_salmon
			return 70;
		case 335: // Raw_trout
			return 50;
		case 341: // Raw_cod
			return 45;
		case 359: // Raw_tuna
			return 80;
		case 371: // Raw_swordfish
			return 100;
		case 377: // Raw_lobster
			return 90;
		default:
			return 30;
		}
	}
}