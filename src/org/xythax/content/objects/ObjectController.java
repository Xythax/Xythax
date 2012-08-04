package org.xythax.content.objects;

import org.xythax.content.JailSystem;
import org.xythax.content.actions.Action;
import org.xythax.content.actions.ActionManager;
import org.xythax.content.actions.Task;
import org.xythax.content.controllers.Animation;
import org.xythax.content.controllers.Damage;
import org.xythax.content.controllers.Location;
import org.xythax.content.skills.GemCrafting;
import org.xythax.content.skills.RuneCrafting;
import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.Entity.CombatType;
import org.xythax.model.WorldObject;
import org.xythax.model.WorldObject.Face;
import org.xythax.utils.CommonStrings;
import org.xythax.utils.Constants;

public class ObjectController implements Task {
	public static void runAction(Client client) {
		if (System.currentTimeMillis() - client.actionTimer < 500
				|| client.isBusy()) {
			return;
		} else {
			client.actionTimer = System.currentTimeMillis();
		}
		int[] object = ObjectStorage.getDetails(client);
		if (object[1] > 1
				&& ObjectStorage.getDetail(client, ObjectConstants.OBJECT_TASK) != ObjectConstants.RUNE_CRAFT) {
			client.getActionAssistant().turnTo(object[2] + object[1] / 2,
					object[3] + object[1] / 2);
		} else {
			client.getActionAssistant().turnTo(object[2], object[3]);
		}
		int task = ObjectStorage.getDetail(client, ObjectConstants.OBJECT_TASK);
		if (GameEngine.getObjectManager().getDefinition(object[0]) != null) {
			if (GameEngine.getObjectManager().getDefinition(object[0])
					.getName().contains("Door")
					|| GameEngine.getObjectManager().getDefinition(object[0])
							.getName().contains("Gate")) {
				task = ObjectConstants.DOOR;
			}
		}

		switch (task) {
		case ObjectConstants.DOOR:
			GameEngine.getObjectManager()
					.addObject(
							new WorldObject(-1, object[2], object[3], 0,
									Face.NORTH, 0));
			break;
		case ObjectConstants.TREE:
			ActionManager.addNewRequest(client, 0, 4);
			break;

		case ObjectConstants.NO_ORE:
			client.getActionSender().sendMessage(
					"This rock does not contain any ore.");
			break;

		case ObjectConstants.BANK:
			client.getActionSender().sendBankInterface();
			break;

		case ObjectConstants.MINE:
			ActionManager.addNewRequest(client, 10, 4);
			break;

		case ObjectConstants.GEM_CRAFT:
		case ObjectConstants.ORE_SMELTING:
			if (client.oreId > 0) {
				if (client.getActionAssistant().isItemInInventory(444)) {
					ActionManager.addNewRequest(client, 9, 2);
					break;
				}
			}
			if (client.getActionAssistant().isItemInInventory(1592)
					|| client.getActionAssistant().isItemInInventory(1595)
					|| client.getActionAssistant().isItemInInventory(1597)) {
				GemCrafting.openInterface(client);
			}
			break;

		case ObjectConstants.RUNE_CRAFT:
			switch (object[0]) {
			case 7139:
				RuneCrafting.craftRunesOnAltar(client, 1, 5, 556, 30, 45, 60,
						1438);// air 2478
				break;
			case 7137:
				RuneCrafting.craftRunesOnAltar(client, 5, 6, 555, 30, 45, 60,
						1444);
				break;
			case 7130:
				RuneCrafting.craftRunesOnAltar(client, 9, 7, 557, 45, 55, 65,
						1440);
				break;
			case 7129:
				RuneCrafting.craftRunesOnAltar(client, 14, 7, 554, 50, 60, 70,
						1442);
				break;
			case 7140:
				RuneCrafting.craftRunesOnAltar(client, 20, 8, 559, 55, 65, 75,
						1454);
				break;
			case 7134:
				RuneCrafting.craftRunesOnAltar(client, 35, 9, 562, 60, 70, 80,
						1452);
				break;
			case 7133:
				RuneCrafting.craftRunesOnAltar(client, 44, 9, 561, 60, 74, 91,
						1462);
				break;
			// case 7135: RuneCrafting.craftRunesOnAltar(client, 54, 10, 563,
			// 65, 79, 93, 1458);
			// break;
			// case 7136: RuneCrafting.craftRunesOnAltar(client, 65, 10, 560,
			// 72, 84, 96, 1456);
			// break;
			// case 7141: RuneCrafting.craftRunesOnAltar(client, 77, 11, 565,
			// 89, 94, 99, 1450); //blood
			// break;
			default:
				// client.getActionSender().sendMessage("Available at this time.");
				break;
			}
			break;

		case ObjectConstants.COOKING_FURNACE:
			client.cookingAnimation = 883;
			client.getActionSender().sendCookOption(client.cooking);
			break;

		case ObjectConstants.LOG_FIRE:
			client.cookingAnimation = 883;
			client.getActionSender().sendCookOption(client.cooking);
			break;

		case ObjectConstants.STR_DOOR:
		case ObjectConstants.HAY_STACK:
		break;
		case ObjectConstants.MOULD_CRATE:
			if (!client.getActionAssistant().isItemInInventory(1592)) {
				client.getActionSender()
						.sendMessage("You found an Ring mould.");
				client.getActionSender().sendInventoryItem(1592, 1);
				break;
			} else if (!client.getActionAssistant().isItemInInventory(1597)) {
				client.getActionSender().sendMessage(
						"You found an Necklace mould.");
				client.getActionSender().sendInventoryItem(1597, 1);
				break;
			} else {
				client.getActionSender().sendMessage(
						"You find nothing of interest.");
				break;
			}

		case ObjectConstants.JUG:
			if (!client.getActionAssistant().isItemInInventory(
					ObjectConstants.JUG)) {
				break;
			} else if (client.getActionAssistant().isItemInInventory(
					ObjectConstants.JUG)) {

				Animation.addNewRequest(client, 894, 2);
				client.getActionAssistant().deleteItem(ObjectConstants.JUG, 1);
				client.getActionSender().sendInventoryItem(1937, 1);
				client.getActionSender().sendMessage("You fill the jug.");
				break;
			}
		case ObjectConstants.JAIL_EXIT:
			if (client.getAbsY() == 3407 && !JailSystem.inJail(client)) {
				client.forceMove = true;
				client.forceMovement[0] = client.getAbsX();
				client.forceMovement[1] = client.getAbsY() + 1;
				client.forceMovement[2] = client.getHeightLevel();
			}
			break;

		case ObjectConstants.ALTAR:
		case ObjectConstants.CHAOS_ALTAR:
			if (client.playerLevel[5] == client
					.getLevelForXP(client.playerXP[5])) {
				client.getActionSender().sendMessage(
						"You already have full prayer points.");
			} else {
				Animation.addNewRequest(client, 645, 1);
				client.playerLevel[5] = client
						.getLevelForXP(client.playerXP[5]);
				client.getActionSender().sendQuest(
						"" + client.playerLevel[Constants.PRAYER] + "", 4012);
				client.getActionSender().sendMessage(
						"You recharge your prayer points.");
			}
			break;

		case ObjectConstants.ANCIENT_ALTAR:
			// Animation.addNewRequest(client, 645, 1);
			// client.convertMagic();
			break;

		case ObjectConstants.DEPOSIT_BOX:
			client.getActionSender().sendDepositInterface();
			break;

		case ObjectConstants.KARAMJA_ROPE_DOWN:
			Location.addNewRequest(client, 2856, 9568, 0, 1);
			break;

		case ObjectConstants.KARAMJA_ROPE_UP:
			Location.addNewRequest(client, 2857, 3167, 0, 1);
			break;

		case ObjectConstants.FIRST_DUNGEON_EXIT: // going up ladder
			Location.addNewRequest(client, 3268, 3401, 0, 2);
			Animation.addNewRequest(client, 828, 0);
			break;

		case ObjectConstants.FIRST_DUNGEON_ENTRY: // going down trapdoor.
			Location.addNewRequest(client, 2628, 5072, 0, 3);
			Animation.addNewRequest(client, 770, 1);
			client.getActionSender().sendMessage("You slip and fall in.");
			Animation.addNewRequest(client, 3103, 3);
			Damage.addNewHit(client, client, CombatType.RECOIL, 5, 3);
			break;

		case ObjectConstants.TEA_STALL:
			if (client.getActionAssistant().freeSlots() < 1) {
				client.getActionSender().sendMessage(CommonStrings.INV_FULL);
				break;
			}
			client.setBusyTimer(4);
			Animation.addNewRequest(client, 881, 0);
			client.getActionSender().sendMessage(
					"You take some tea from the stall.");
			client.getActionSender().sendInventoryItem(1978, 1);
			client.getActionAssistant().addSkillXP(
					5 * Constants.SKILL_EXPERIENCE_MULTIPLIER,
					Constants.THIEVING);
			break;

		case ObjectConstants.CHICKEN_DOOR1:
		case ObjectConstants.CHICKEN_DOOR2:
			client.setBusyTimer(2);
			client.resetWalkingQueue();
			client.forceMove = true;
			if (client.getAbsX() == 3264) {
				client.forceMovement[0] = client.getAbsX() - 1;
			} else {
				client.forceMovement[0] = client.getAbsX() + 1;
			}
			client.forceMovement[1] = client.getAbsY();
			client.forceMovement[2] = client.getHeightLevel();
			break;

		default:
			client.getActionSender().sendMessage(
					"OBJECT ID: "
							+ ObjectStorage.getDetail(client,
									ObjectConstants.OBJECT_ID) + "." +
					"OBJECT X: "
							+ ObjectStorage.getDetail(client,
									ObjectConstants.OBJECT_X) + "." +
					"OBJECT Y: "
							+ ObjectStorage.getDetail(client,
									ObjectConstants.OBJECT_Y) + ".");
			break;

		}
	}

	public static void run(Client client, int[] inStream) {

		if (client.isBusy()) {
			return;
		}

		int[] objectData = new int[3];

		for (int i = 0; i < ObjectConstants.objectCommander.length; i++) {
			if (ObjectConstants.objectCommander[i][0] == inStream[0]) {
				for (int i2 = 0; i2 < objectData.length; i2++) {
					objectData[i2] = ObjectConstants.objectCommander[i][i2 + 1];
				}
				break;
			}
		}
		for (int i = 0; i < objectData.length; i++) {
			if (objectData[i] == 0) {
				if (client.privileges > 1) {
					client.getActionSender().sendMessage(
							"OBJECT ID: " + inStream[0] + ".");
				}
				destruct(client);
				return;
			}
		}

		ObjectStorage.addDetails(client, ObjectStorage.compress(inStream[0],
				objectData[1], inStream[1], inStream[2], objectData[0],
				objectData[2]));
		if (atObject(client)) {
			runAction(client);
			return;
		}
		ActionManager.addNewRequest(client, 11, 1);

	}

	public static boolean atObject(Client client) {

		switch (ObjectStorage.getDetail(client, ObjectConstants.OBJECT_SIZE)) {

		case 1:
			return ObjectStorage.getDetail(client, ObjectConstants.OBJECT_X) - 1 == client
					.getAbsX()
					&& ObjectStorage
							.getDetail(client, ObjectConstants.OBJECT_Y) == client
							.getAbsY()
					|| ObjectStorage
							.getDetail(client, ObjectConstants.OBJECT_X) + 1 == client
							.getAbsX()
					&& ObjectStorage
							.getDetail(client, ObjectConstants.OBJECT_Y) == client
							.getAbsY()
					|| ObjectStorage
							.getDetail(client, ObjectConstants.OBJECT_Y) - 1 == client
							.getAbsY()
					&& ObjectStorage
							.getDetail(client, ObjectConstants.OBJECT_X) == client
							.getAbsX()
					|| ObjectStorage
							.getDetail(client, ObjectConstants.OBJECT_Y) + 1 == client
							.getAbsY()
					&& ObjectStorage
							.getDetail(client, ObjectConstants.OBJECT_X) == client
							.getAbsX();
		default:
			return client.getAbsX() >= ObjectStorage.getDetail(client,
					ObjectConstants.OBJECT_X) - 1
					&& client.getAbsX() <= ObjectStorage.getDetail(client,
							ObjectConstants.OBJECT_X)
							+ ObjectStorage.getDetail(client,
									ObjectConstants.OBJECT_SIZE)
					&& client.getAbsY() >= ObjectStorage.getDetail(client,
							ObjectConstants.OBJECT_Y) - 1
					&& client.getAbsY() <= ObjectStorage.getDetail(client,
							ObjectConstants.OBJECT_Y)
							+ ObjectStorage.getDetail(client,
									ObjectConstants.OBJECT_SIZE);
		}
	}

	public int getDetail(Client client, int settingSlot) {
		return ObjectStorage.getDetail(client, settingSlot);
	}

	public static void destruct(Client client) {
		ObjectStorage.destruct(client);

	}

	@Override
	public void execute(Action currentAction) {
		int delay = 0;
		if (currentAction.getClient() == null) {
			if (!currentAction.getClient().isRunning)
				delay = 1;
		}
		currentAction.setCurrentTick(delay);
		currentAction.setActionType(Action.type.LOOPING);
	}

	@Override
	public void loop(Action currentAction) {
		if (currentAction.getClient() == null) {
			stop(currentAction);
			return;
		}
		if (currentAction.getCurrentTick() > 0) {
			currentAction.setCurrentTick(currentAction.getCurrentTick() - 1);
			return;
		}
		for (int i = 0; i < ObjectConstants.objectArraySize; i++) {
			if (ObjectStorage.getDetail(currentAction.getClient(), i) == 0) {
				stop(currentAction);
				break;
			}
		}
		if (atObject(currentAction.getClient())) {
			runAction(currentAction.getClient());
			stop(currentAction);
		}
		currentAction.setCurrentTick(1);
	}

	@Override
	public void stop(Action currentAction) {
		currentAction.setActionType(Action.type.TRASHING);
	}
}