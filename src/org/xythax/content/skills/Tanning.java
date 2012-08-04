package org.xythax.content.skills;

import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.Entity;

/**
 * Tanning class
 * 
 * @author killamess
 * 
 */
public class Tanning {

	/**
	 * Opens the tan interface
	 */
	public static void openTanInterface(Entity ent) {

		if (ent instanceof Client) {

			((Client) ent).getActionSender().sendInterface(14670);

			for (int i = 0; i < 6; i++)
				((Client) ent).getActionSender().sendFrame246(
						14700 + tanningInterfaceImage[i][0], 250,
						(i < 3 ? 1700 : 2500) + tanningInterfaceImage[i][1]);
			for (int i = 0; i < 16; i++)
				((Client) ent).getActionSender().sendQuest(writeToInterface[i],
						14777 + i);
		}
	}

	/**
	 * Tan Item
	 */
	public static void tanHide(Entity ent, int originalId, int newItem,
			int amount) {

		if (ent instanceof Client) {

			boolean hasItem = ((Client) ent).getActionAssistant()
					.getItemAmount(originalId) > 0;

			String itemName = GameEngine.getItemManager()
					.getItemDefinition(originalId).getName();
			String itemName2 = GameEngine.getItemManager()
					.getItemDefinition(newItem).getName();

			if (!hasItem) {
				((Client) ent).getActionSender().sendMessage(
						"You need " + itemName.toLowerCase() + " to tan "
								+ itemName2.toLowerCase() + ".");
				return;
			}

			((Client) ent).getActionSender().sendWindowsRemoval();

			for (int i = 0; i < itemPrice.length; i++) {

				if (itemPrice[i][0] == originalId) {

					int count = 0;

					while (((Client) ent).getActionAssistant().getItemAmount(
							originalId) > 0) {

						if (!((Client) ent).getActionAssistant().playerHasItem(
								995, itemPrice[i][1])) {
							((Client) ent).getActionSender().sendMessage(
									"You don't have enough money.");
							return;
						}

						((Client) ent).getActionAssistant().deleteItem(995,
								itemPrice[i][1]);
						((Client) ent).getActionAssistant().deleteItem(
								originalId, 1);
						((Client) ent).getActionSender().sendInventoryItem(
								newItem, 1);
						count++;

						if (count == amount)
							break;
					}
					break;
				}
			}
		}
	}

	private static final int[][] itemPrice = { { 1739, 1 }, { 1739, 15 },
			{ 1753, 200 }, { 1751, 350 }, { 1749, 450 }, { 1747, 600 } };

	private static final int[][] tanningInterfaceImage = { { 69, 41 },
			{ 73, 43 }, { 71, 45 }, { 72, 5 }, { 75, 7 }, { 76, 9 } };

	private static final String[] writeToInterface = { "Leather", "Unvaliable",
			"Green", "Blue", "Hard Leather", "Unvaliable", "Red", "Black",
			"1gp", "N/A", "200gp", "350gp", "5gp", "N/A", "450gp", "600gp" };
}
