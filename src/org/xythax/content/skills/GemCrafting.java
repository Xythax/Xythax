package org.xythax.content.skills;

import java.util.HashMap;

import org.xythax.content.actions.Action;
import org.xythax.content.actions.ActionManager;
import org.xythax.content.actions.Task;
import org.xythax.content.controllers.Animation;
import org.xythax.model.Client;
import org.xythax.utils.Constants;

/**
 * Crafting
 * 
 * @author Killamess 100% gem crafting
 * 
 */
public class GemCrafting implements Task {

	public static HashMap<Integer, gemData> data = new HashMap<Integer, gemData>();

	public static gemData getData(int id) {
		return data.get(id);
	}

	public enum gemData {

		gold(2357, -1, -1, 5, 15, 1635, 6, 20, 1654, 8, 30, 1673, 1692), sapphire(
				1607, 20, 50, 20, 40, 1637, 22, 55, 1656, 24, 65, 1675, 1694), emerald(
				1605, 27, 67, 27, 55, 1639, 29, 60, 1658, 31, 70, 1677, 1696), ruby(
				1603, 34, 85, 34, 70, 1641, 40, 75, 1660, 50, 85, 1679, 1698), diamond(
				1601, 43, (int) 107.5, 43, 85, 1643, 56, 90, 1662, 70, 100,
				1681, 1700), dragon_stone(1615, 55, (int) 137.5, 55, 100, 1645,
				72, 105, 1664, 80, 150, 1683, 1702), onyx(6573, 67,
				(int) 167.5, 67, 115, 6575, 82, 120, 6577, 90, 165, 6579, 6581);

		public int[] mapLocation = new int[13];

		gemData(int a, int b, int c, int d, int e, int f, int g, int h, int i,
				int j, int k, int l, int m) {
			mapLocation[0] = a;
			mapLocation[1] = b;
			mapLocation[2] = c;
			mapLocation[3] = d;
			mapLocation[4] = e;
			mapLocation[5] = f;
			mapLocation[6] = g;
			mapLocation[7] = h;
			mapLocation[8] = i;
			mapLocation[9] = j;
			mapLocation[10] = k;
			mapLocation[11] = l;
			mapLocation[12] = m;
		}
	}

	static {
		for (gemData pointer : gemData.values())
			data.put(pointer.mapLocation[0x0], pointer);
	}

	public static void openInterface(Client client) {
		int[] interfaceId = { 1592, 1595, 1597 };

		for (int i : interfaceId)
			showInterface(client, i);

		client.getActionSender().sendInterface(4161);
	}

	public static void showInterface(Client client, int face) {
		int interfaceType = (face == 1592 ? 0 : face == 1595 ? 2
				: face == 1597 ? 1 : -1);
		if (interfaceType < 0)
			return;

		if (client.getActionAssistant().isItemInInventory(face)) {
			for (int i = 0; i < craftInterfaceArray[interfaceType].length; i++)
				customFrame(client, 34, interfaceFrames[interfaceType][1], 7,
						i, craftInterfaceArray[interfaceType][i], 1);

			client.getActionSender().sendFrame246(
					interfaceFrames[interfaceType][0], 0, -1);
			client.getActionSender().sendQuest("Choose an item to make",
					interfaceFrames[interfaceType][1] - 3);
		} else {
			for (int i = 0; i < craftInterfaceArray[interfaceType].length; i++)
				customFrame(client, 34, interfaceFrames[interfaceType][1], 0,
						i, -1, 0);

			client.getActionSender().sendFrame246(
					interfaceFrames[interfaceType][0], 120, face);
			client.getActionSender().sendQuest(interfaceMessage[interfaceType],
					interfaceFrames[interfaceType][1] - 3);
		}
	}

	private static void customFrame(Client client, int frame, int interfaceId,
			int size, int row, int itemId, int amount) {
		try {
			client.outStream.createFrameVarSizeWord(frame);
			client.outStream.writeWord(interfaceId);
			client.outStream.writeByte(size);
			client.outStream.writeDWord(row);
			client.outStream.writeWord(itemId + 1);
			client.outStream.writeByte(amount);
			client.outStream.endFrameVarSizeWord();
		} catch (Exception e) {
			return;
		}
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
		if (client.craftingAmount == 0) {
			stop(currentAction);
			return;
		}
		if (getData(client.gem) == null
				|| !client.getActionAssistant().isItemInInventory(
						getData(client.gem).mapLocation[0])
				|| !client.getActionAssistant().isItemInInventory(2357)) {
			stop(currentAction);
			return;
		}

		switch (client.craftingType) {

		case 0:

			if (client.playerLevel[Constants.CRAFTING] < getData(client.gem).mapLocation[3]) {
				client.getActionSender().sendMessage(
						"You need a Crafting level of "
								+ getData(client.gem).mapLocation[3]
								+ " to craft this.");
				stop(currentAction);
				return;
			}
			client.getActionAssistant().deleteItem(
					getData(client.gem).mapLocation[0], 1);

			if (getData(client.gem).mapLocation[0] != 2357)
				client.getActionAssistant().deleteItem(2357, 1);

			client.getActionSender().sendInventoryItem(
					getData(client.gem).mapLocation[5], 1);
			client.getActionSender().sendMessage("You craft an ring.");
			client.getActionAssistant().addSkillXP(
					getData(client.gem).mapLocation[4]
							* Constants.SKILL_EXPERIENCE_MULTIPLIER,
					Constants.CRAFTING);
			break;

		case 1:
			if (client.playerLevel[Constants.CRAFTING] < getData(client.gem).mapLocation[6]) {
				client.getActionSender().sendMessage(
						"You need a Crafting level of "
								+ getData(client.gem).mapLocation[6]
								+ " to craft this.");
				stop(currentAction);
				return;
			}
			client.getActionAssistant().deleteItem(
					getData(client.gem).mapLocation[0], 1);

			if (getData(client.gem).mapLocation[0] != 2357)
				client.getActionAssistant().deleteItem(2357, 1);

			client.getActionSender().sendInventoryItem(
					getData(client.gem).mapLocation[8], 1);
			client.getActionSender().sendMessage("You craft an necklace.");
			client.getActionAssistant().addSkillXP(
					getData(client.gem).mapLocation[7]
							* Constants.SKILL_EXPERIENCE_MULTIPLIER,
					Constants.CRAFTING);
			break;

		case 2:
			if (client.playerLevel[Constants.CRAFTING] < getData(client.gem).mapLocation[9]) {
				client.getActionSender().sendMessage(
						"You need a Crafting level of "
								+ getData(client.gem).mapLocation[9]
								+ " to craft this.");
				stop(currentAction);
				return;
			}
			client.getActionAssistant().deleteItem(
					getData(client.gem).mapLocation[0], 1);

			if (getData(client.gem).mapLocation[0] != 2357)
				client.getActionAssistant().deleteItem(2357, 1);

			if (client.getActionAssistant().isItemInInventory(1759)) {
				client.getActionAssistant().deleteItem(1759, 1);
				client.getActionSender().sendInventoryItem(
						getData(client.gem).mapLocation[12], 1);
				client.getActionAssistant().addSkillXP(
						30 * Constants.SKILL_EXPERIENCE_MULTIPLIER,
						Constants.CRAFTING);
				client.getActionSender().sendMessage(
						"You craft an amulet and attach a string to it.");
			} else {
				client.getActionSender().sendInventoryItem(
						getData(client.gem).mapLocation[11], 1);
				client.getActionSender().sendMessage("You craft an amulet.");
			}
			client.getActionAssistant().addSkillXP(
					getData(client.gem).mapLocation[10]
							* Constants.SKILL_EXPERIENCE_MULTIPLIER,
					Constants.CRAFTING);
			break;

		default:
			stop(currentAction);
			return;
		}
		if (client.craftingAmount > 0
				&& client.getActionAssistant().isItemInInventory(
						getData(client.gem).mapLocation[0])
				&& client.getActionAssistant().isItemInInventory(2357))
			client.getActionAssistant().startAnimation(899, 0);
		client.craftingAmount--;
		currentAction.setCurrentTick(4);

	}

	public static void startCrafter(Client client, int gem, int actionAmount,
			int type) {

		if (getData(gem) == null || actionAmount < 1 || client.isBusy())
			return;

		int count = 4;

		switch (type) {

		case 0:
			count--;
		case 1:
			count--;
		case 2:
			count--;
			if (client.playerLevel[Constants.CRAFTING] < getData(gem).mapLocation[count * 3]) {
				client.getActionSender().sendMessage(
						"You need a crafting level of "
								+ getData(gem).mapLocation[count * 3]
								+ " to craft this item.");
				return;
			}
			break;
		}
		if (!client.getActionAssistant().isItemInInventory(
				getData(gem).mapLocation[0x0])
				|| !client.getActionAssistant().isItemInInventory(2357)) {
			client.getActionSender().sendMessage(
					"You do not have the required items.");
			return;
		}
		client.getActionAssistant().startAnimation(899, 0);
		client.setBusy(true);
		client.getActionSender().sendWindowsRemoval();
		client.gem = gem;
		client.craftingType = type;
		client.craftingAmount = actionAmount;
		ActionManager.addNewRequest(client, 15, 4);
	}

	public static void string(Client client, int jemSlot) {
		if (!client.getActionAssistant().isItemInInventory(1759)
				|| !client.getActionAssistant().isItemInInventory(
						mendItems[jemSlot][0])) {
			return;
		}
		client.getActionSender().sendWindowsRemoval();
		client.getActionAssistant().deleteItem(mendItems[jemSlot][0], 1);
		client.getActionAssistant().deleteItem(1759, 1);
		client.getActionSender().sendInventoryItem(mendItems[jemSlot][1], 1);
		client.getActionAssistant().addSkillXP(
				30 * Constants.SKILL_EXPERIENCE_MULTIPLIER, Constants.CRAFTING);
		client.getActionSender().sendMessage(
				"You attach an string to the amulet.");
	}

	public static void craftGem(Client client, int toCut) {

		if (!client.getActionAssistant().isItemInInventory(1755)
				|| !client.getActionAssistant().isItemInInventory(toCut))
			return;

		for (int i = 0; i < craftGem.length; i++) {
			if (toCut == craftGem[i][0]) {
				if (client.playerLevel[Constants.CRAFTING] < craftGem[i][2]) {
					client.getActionSender().sendMessage(
							"You need a crafting level of " + craftGem[i][2]
									+ " to cut this gem.");
					return;
				}
				client.getActionSender().sendWindowsRemoval();
				Animation.createAnimation(client, craftGem[i][4]);
				client.getActionAssistant().deleteItem(craftGem[i][0], 1);
				client.getActionSender().sendInventoryItem(craftGem[i][1], 1);
				client.getActionAssistant().addSkillXP(
						craftGem[i][3] * Constants.SKILL_EXPERIENCE_MULTIPLIER,
						Constants.CRAFTING);
				break;
			}
		}
	}

	public static final int RING = 0;
	public static final int NECKLACE = 1;
	public static final int AMULET = 2;
	public static final int JEM_SLOT = 0;
	public static final int JEM_CUT_LEVEL = 1;
	public static final int JEM_CUT_EXP = 2;
	public static final int JEM_CRAFT_RING_LEVEL = 3;
	public static final int JEM_CRAFT_RING_EXP = 4;
	public static final int JEM_CRAFT_RING_FINAL_PRODUCT = 5;
	public static final int JEM_CRAFT_NECKLACE_LEVEL = 6;
	public static final int JEM_CRAFT_NECKLACE_EXP = 7;
	public static final int JEM_CRAFT_NECKLACE_FINAL_PRODUCT = 8;
	public static final int JEM_CRAFT_AMULET_LEVEL = 9;
	public static final int JEM_CRAFT_AMULET_EXP = 10;
	public static final int JEM_CRAFT_AMULET_MID_PRODUCT = 11;
	public static final int JEM_CRAFT_AMULET_FINAL_PRODUCT = 12;

	public static String[] interfaceMessage = {
			"You need a ring mould to craft rings.",
			"You need a necklace mould to craft necklaces.",
			"You need a amulet mould to craft amulets." };

	public static int[][] interfaceFrames = { { 4229, 4233 }, { 4235, 4239 },
			{ 4241, 4245 } };

	/**
	 * Crafting silver ItemIds, FrameLine
	 */
	public static final int[][] SILVER = { { 1716, 16 }, { 1724, 17 },
			{ 2961, 18 }, { 5525, 20 }, };

	/**
	 * Amulets used with strings unattachedId, attachedId
	 */
	public static int[][] mendItems = { { 1673, 1692 }, { 1675, 1694 },
			{ 1677, 1696 }, { 1679, 1698 }, { 1681, 1700 }, { 1683, 1702 },
			{ 6579, 6581 } };

	/**
	 * What appears as our rings, necklaces and amulets in the crafting
	 * interface.
	 */
	public static int[][] craftInterfaceArray = {
			{ 1635, 1637, 1639, 1641, 1643, 1645, 6575 },
			{ 1654, 1656, 1658, 1660, 1662, 1664, 6577 },
			{ 1673, 1675, 1677, 1679, 1681, 1683, 6579 } };

	public static final int[][] ITEMS = {
			// itemid, baseid, type.
			{ 1635, 2357, RING }, { 1654, 2357, NECKLACE },
			{ 1673, 2357, AMULET }, { 1637, 1607, RING },
			{ 1656, 1607, NECKLACE }, { 1675, 1607, AMULET },
			{ 1639, 1605, RING }, { 1658, 1605, NECKLACE },
			{ 1677, 1605, AMULET }, { 1641, 1603, RING },
			{ 1660, 1603, NECKLACE }, { 1679, 1603, AMULET },
			{ 1643, 1601, RING }, { 1662, 1601, NECKLACE },
			{ 1681, 1601, AMULET }, { 1645, 1615, RING },
			{ 1664, 1615, NECKLACE }, { 1683, 1615, AMULET },
			{ 6575, 6573, RING }, { 6577, 6573, NECKLACE },
			{ 6579, 6573, AMULET } };

	/**
	 * Used for cutting uncut gems uncutid, cutid, level, exp, animation
	 */
	public static final int[][] craftGem = { { 1623, 1607, 20, 50, 888 },
			{ 1621, 1605, 27, 67, 889 }, { 1619, 1603, 34, 85, 887 },
			{ 1617, 1601, 43, (int) 107.5, 886 },
			{ 1631, 1615, 55, (int) 137.5, 885 }, { 6571, 6573, 67, 168, 2717 }

	};

	@Override
	public void stop(Action currentAction) {
		if (currentAction.getClient() != null) {
			currentAction.getClient().getActionSender().sendAnimationReset();
			currentAction.getClient().gem = 0;
			currentAction.getClient().crafting = 0;
			currentAction.getClient().craftingItem = 0;
			currentAction.getClient().craftingType = 0;
			currentAction.getClient().craftingAmount = 0;
			currentAction.getClient().craftingThreadCount = 0;
		}
		currentAction.setActionType(Action.type.TRASHING);

	}
}