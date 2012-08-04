package org.xythax.model.combat.content;

import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.FloorItem;

/**
 * 
 * @author killamess
 * 
 */
public class ItemProtect {

	private static final int ITEM = 0, VALUE = 1, AMOUNT = 2;

	public static final int[] protectedList = { 6570, 8844, 8845, 8846, 8847,
			8848, 8849, 8850, 10551, 7455, 7456, 7457, 7458, 7459, 7460, 7461,
			7462, 10498, 10499, 11665, 8839, 8840, 8842, 11664, 9747, 9748,
			9753, 9754, 9750, 9751, 9768, 9769, 9756, 9757, 9759, 9760, 9762,
			9763, 9801, 9802, 9807, 9808, 9783, 9784, 9798, 9799, 9804, 9805,
			9780, 9781, 9795, 9796, 9792, 9793, 9774, 9775, 9771, 9772, 9777,
			9778, 9786, 9787, 9810, 9811, 9765, 9766, 9789, 9790, 9948, 9949,
			9813, 9813, };

	public static boolean onProtectedList(int itemId) {
		for (int list : protectedList)
			if (list == itemId)
				return true;
		return false;
	}

	public static void add(Client client, int loopCount, int item, int value,
			int amount) {
		client.array[loopCount][ITEM] = item;
		client.array[loopCount][VALUE] = value;
		client.array[loopCount][AMOUNT] = amount;
	}

	public static int[] dropItems(Client client, boolean dropItems) {

		client.array = new int[42][3];

		for (int i = 0; i < 28; i++)
			add(client,
					i,
					client.playerItems[i] - 1,
					(int) GameEngine.getItemManager()
							.getItemDefinition(client.playerItems[i] - 1)
							.getShopValue(), client.playerItemsN[i]);

		for (int i = 0; i < 14; i++)
			add(client,
					i + 28,
					client.playerEquipment[i],
					(int) GameEngine.getItemManager()
							.getItemDefinition(client.playerEquipment[i])
							.getShopValue(), client.playerEquipmentN[i]);

		for (int i = 0; i < 42; i++)
			if (client.array[i][ITEM] > 0)
				client.array[i][VALUE] += 1;

		int r, l;

		for (r = client.array.length - 1; r > 0; r--) {
			for (l = 0; l < r; l++) {
				if (client.array[l][VALUE] < client.array[l + 1][VALUE]) {
					int[] temp = client.array[l];
					client.array[l] = client.array[l + 1];
					client.array[l + 1] = temp;
				}
			}
		}
		int PROTECTION_SIZE = 3;

		if (client.getSkullTimer() > 0)
			PROTECTION_SIZE = 0;

		if (client.getPrayerHandler().clicked[8])
			PROTECTION_SIZE += 1;

		int[] protectedItems = new int[PROTECTION_SIZE];

		for (int i = 0; i < PROTECTION_SIZE; i++)
			protectedItems[i] = client.array[i][ITEM];

		if (dropItems) {

			removeAllItems(client);

			int[][] dropsToAdd = new int[client.array.length - PROTECTION_SIZE][2];

			for (int i = 0; i < dropsToAdd.length; i++) {

				dropsToAdd[i][0] = client.array[i + PROTECTION_SIZE][ITEM];
				dropsToAdd[i][1] = client.array[i + PROTECTION_SIZE][AMOUNT];

				for (int protect : protectedList) {
					if (dropsToAdd[i][0] == protect) {
						GameEngine.getItemManager().newDrop(
								new FloorItem(dropsToAdd[i][0],
										dropsToAdd[i][1], client.getUsername(),
										client.getAbsX(), client.getAbsY(),
										client.getHeightLevel()), client);
						dropsToAdd[i][0] = 0;
						dropsToAdd[i][1] = 0;
					}
				}
			}
			for (int i = 0; i < dropsToAdd.length; i++) {
				if (dropsToAdd[i][0] > 0) {
					GameEngine.getItemManager().newDrop(
							new FloorItem(dropsToAdd[i][0], dropsToAdd[i][1],
									client.whoKilledYa == null ? client
											.getUsername() : client.whoKilledYa
											.getUsername(), client.getAbsX(),
									client.getAbsY(), client.getHeightLevel()),
							client.whoKilledYa == null ? client
									: client.whoKilledYa);
				}
			}
			GameEngine.getItemManager().newDrop(
					new FloorItem(526, 1,
							client.whoKilledYa == null ? client.getUsername()
									: client.whoKilledYa.getUsername(),
							client.getAbsX(), client.getAbsY(),
							client.getHeightLevel()),
					client.whoKilledYa == null ? client : client.whoKilledYa);

			for (int item : protectedItems)
				client.getActionSender().sendInventoryItem(item, 1);
		}

		return protectedItems;
	}

	public static void removeAllItems(Client client) {
		for (int i = 0; i < 28; i++) {
			client.playerItemsN[i] = 0;
			client.playerItems[i] = 0;
		}
		client.getActionSender().sendItemReset(3214);

		for (int i = 0; i < 14; i++) {
			client.playerEquipment[i] = -1;
			client.playerEquipmentN[i] = 0;
			client.outStream.createFrame(34);
			client.outStream.writeWord(6);
			client.outStream.writeWord(1688);
			client.outStream.writeByte(i);
			client.outStream.writeWord(0);
			client.outStream.writeByte(0);
			client.flushOutStream();
		}
		client.updateRequired = true;
		client.appearanceUpdateRequired = true;
		client.getBonuses().calculateBonus();
		client.getEquipment().sendWeapon();
	}
}
