package org.xythax.content.skills;

import java.util.ArrayList;
import java.util.List;

import org.xythax.content.actions.Action;
import org.xythax.content.actions.Task;
import org.xythax.content.controllers.Animation;
import org.xythax.content.objects.ObjectReplacer;
import org.xythax.content.objects.ObjectStorage;
import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.definition.XMLManager;
import org.xythax.model.definition.skills.Axes;
import org.xythax.model.definition.skills.Trees;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;
import org.xythax.world.ObjectManager;

/**
 * 
 * @author killamess
 * 
 */

public class WoodCutting implements Task {

	@Override
	public void execute(Action currentAction) {

		Client client = currentAction.getClient();

		if (client == null) {
			stop(currentAction);
			return;
		}
		int[] object = ObjectStorage.getDetails(currentAction.getClient());
		int[] objectLocation = new int[] { object[2], object[3],
				client.getHeightLevel() };

		if (tree(object[0]) == null || !canCutTree(objectLocation)) {
			stop(currentAction);
			return;
		}

		if (tree(object[0]).getLevel() > client.playerLevel[Constants.WOODCUTTING]) {
			client.getActionSender()
					.sendMessage(
							"You need a woodcutting level of "
									+ tree(object[0]).getLevel()
									+ " to cut this tree.");
			stop(currentAction);
			return;
		}
		if (!hasAxe(client)) {
			String logName = GameEngine.getObjectManager()
					.getDefinition(object[0]).getName();

			switch (object[0]) {
			case 1276:
			case 1278:
				client.getActionSender().sendMessage(
						"You need an axe to cut trees.");
				break;
			case 1383:
			case 1282:
			case 1286:
				client.getActionSender().sendMessage(
						"You need an axe to cut dead trees.");
				break;
			default:
				client.getActionSender().sendMessage(
						"You need an axe to cut " + logName.toLowerCase()
								+ " trees.");
				break;
			}
			stop(currentAction);
			return;
		}
		if (client.getActionAssistant().freeSlots() == 0) {
			client.getActionSender().sendMessage(
					"There is not enough space in your inventory.");
			stop(currentAction);
			return;
		}
		if (object[1] > 1)
			client.getActionAssistant().turnTo(object[2] + object[1] / 2,
					object[3] + object[1] / 2);
		else
			client.getActionAssistant().turnTo(object[2], object[3]);

		client.getActionSender().sendMessage("You swing your axe at the tree.");
		Animation.addNewRequest(client, getAxeAnimation(client), 1);
		currentAction.setActionType(Action.type.LOOPING);
	}

	@Override
	public void loop(Action currentAction) {

		currentAction.setCurrentTick(currentAction.getCurrentTick() - 1);

		if (currentAction.getCurrentTick() > 1)
			return;

		Client client = currentAction.getClient();

		if (client == null) {
			stop(currentAction);
			return;
		}
		int[] object = ObjectStorage.getDetails(currentAction.getClient());
		int[] objectLocation = new int[] { object[2], object[3],
				client.getHeightLevel() };

		if (tree(object[0]) == null || !canCutTree(objectLocation)) {
			stop(currentAction);
			return;
		}
		if (client.getActionAssistant().freeSlots() == 0) {
			client.getActionSender().sendMessage(
					"There is not enough space in your inventory.");
			stop(currentAction);
			return;
		}

		if (!hasAxe(client)) {
			String logName = GameEngine.getObjectManager()
					.getDefinition(object[0]).getName();

			switch (object[0]) {
			case 1276:
			case 1278:
				client.getActionSender().sendMessage(
						"You need an axe to cut trees.");
				break;
			case 1383:
			case 1282:
			case 1286:
				client.getActionSender().sendMessage(
						"You need an axe to cut dead trees.");
				break;
			default:
				client.getActionSender().sendMessage(
						"You need an axe to cut " + logName.toLowerCase()
								+ " trees.");
				break;
			}
			stop(currentAction);
			return;
		}

		int log = tree(object[0]).getLog();
		int exp = tree(object[0]).getXp();
		int level = tree(object[0]).getLevel();
		int axeStrength = 1;

		if (axe(client.playerEquipment[Constants.WEAPON]) != null)
			axeStrength = axe(client.playerEquipment[Constants.WEAPON])
					.getAxeStrength();

		if (currentAction.getCurrentTick() == 0
				&& level < Utils
						.random(client.playerLevel[Constants.WOODCUTTING]
								+ axeStrength)) {
			if (tree(object[0]).getLogAmount() == 1
					|| Utils.random(tree(object[0]).getLogAmount()) <= 3) {
				ObjectManager.newTempObject(tree(object[0]).getTreeId(),
						tree(object[0]).getStumpId(), objectLocation,
						tree(object[0]).getRespawnTime());
			}
			client.getActionSender().sendInventoryItem(log, 1);
			String logName = GameEngine.getItemManager().getItemDefinition(log)
					.getName();
			client.getActionSender().sendMessage(
					"You get some " + logName.toLowerCase() + " logs.");
			client.getActionAssistant().addSkillXP(
					exp * Constants.SKILL_EXPERIENCE_MULTIPLIER,
					Constants.WOODCUTTING);
		}
		if (currentAction.getCurrentTick() == 0) {
			currentAction.setCurrentTick(4);
			if (canCutTree(objectLocation)) {
				Animation.addNewRequest(client, getAxeAnimation(client), 1);
			} else {
				stop(currentAction);
				return;
			}
		}
	}

	@Override
	public void stop(Action currentAction) {
		if (currentAction.getClient() != null) {
			currentAction.getClient().getActionSender().sendAnimationReset();
		}
		currentAction.setActionType(Action.type.TRASHING);
	}

	public int getAxeAnimation(Client client) {
		for (Axes axe : XMLManager.axes) {
			if (axe.getAxeId() == client.playerEquipment[Constants.WEAPON]
					|| client.getActionAssistant().isItemInInventory(
							axe.getAxeId())) {
				return axe.getAxeAnimation();
			}
		}
		return 0;
	}

	public boolean hasAxe(Client client) {
		for (Axes axe : XMLManager.axes) {
			if (axe.getAxeId() == client.playerEquipment[Constants.WEAPON]
					|| client.getActionAssistant().isItemInInventory(
							axe.getAxeId())) {
				return true;
			}
		}
		return false;
	}

	public static Trees tree(int id) {
		for (Trees tree : XMLManager.trees) {
			if (tree.getTreeId() == id)
				return tree;
		}
		return null;
	}

	public static Axes axe(int id) {
		for (Axes axe : XMLManager.axes) {
			if (axe.getAxeId() == id)
				return axe;
		}
		return null;
	}

	public boolean canCutTree(int[] location) {

		if (ObjectManager.objectReplacements.size() == 0)
			return true;

		List<ObjectReplacer> newList = new ArrayList<ObjectReplacer>(
				ObjectManager.objectReplacements);

		for (ObjectReplacer r : newList) {
			int[] objectLocation = r.getLocation();
			if (location[0] == objectLocation[0]
					&& location[1] == objectLocation[1]
					&& location[2] == objectLocation[2]) {
				return false;
			}
		}
		newList.clear();
		return true;
	}

}
