package org.xythax.model.combat.magic;

import org.xythax.content.controllers.Animation;
import org.xythax.content.controllers.Graphics;
import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.utils.CommonStrings;
import org.xythax.utils.Constants;

/**
 * 
 * @author killames
 * 
 */
public class Alchemist {

	public static void alch(Client client, int itemId, int spellId, int junk) {
		if (client == null
				|| !client.getActionAssistant().isItemInInventory(itemId)
				|| client.isBusy() || client.isDead())
			return;

		int reqMagicLevel = spellId == 1162 ? 21 : 55;

		if (client.playerLevel[Constants.MAGIC] < reqMagicLevel) {
			client.getActionSender().sendMessage(CommonStrings.MAGE_TOO_LOW);
			return;
		}
		if (!client.getActionAssistant().isItemInInventory(561)
				|| !client.getActionAssistant().playerHasItem(554, 5)) {
			client.getActionSender().sendMessage(CommonStrings.NO_RUNES);
			return;
		}
		if (itemId == 995) {
			client.getActionSender().sendMessage(
					"You cannot turn gold into gold!");
			return;
		}
		boolean hasCoins = false;

		for (int i = 0; i < 28; i++) {
			if (client.playerItems[i] == 995)
				hasCoins = true;
		}
		if (!hasCoins) {
			if (client.getActionAssistant().freeSlots() < 1) {
				client.getActionSender().sendMessage(CommonStrings.INV_FULL);
				return;
			}
		}
		if (itemId == 561) {
			if (!client.getActionAssistant().playerHasItem(561, 2)) {
				return;
			}
		}
		if (itemId == 554) {
			if (!client.getActionAssistant().playerHasItem(554, 6)) {
				return;
			}
		}
		client.setBusyTimer(spellId == 1162 ? 3 : 5);
		client.getActionAssistant().deleteItem(itemId, 1);
		client.getActionAssistant().deleteItem(561, 1);
		client.getActionAssistant().deleteItem(554, 5);
		client.getActionSender().sendInventoryItem(995, alch(itemId, spellId));
		Animation.addNewRequest(client, spellId == 1162 ? 712 : 713, 1);
		Graphics.addNewRequest(client, spellId == 1162 ? 112 : 113, 1, 1);
		client.getActionSender().sendSidebar(6, 1151);

	}

	public static int alch(int itemId, int spellId) {
		return (int) (spellId == 1162 ? (int) GameEngine.getItemManager()
				.getItemDefinition(itemId).getShopValue() / 3
				: (int) GameEngine.getItemManager().getItemDefinition(itemId)
						.getShopValue() * .75);
	}

}
