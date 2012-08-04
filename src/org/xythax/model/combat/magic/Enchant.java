package org.xythax.model.combat.magic;

import org.xythax.content.controllers.Graphics;
import org.xythax.model.Client;
import org.xythax.model.definition.XMLManager;
import org.xythax.model.definition.skills.Enchants;
import org.xythax.utils.CommonStrings;
import org.xythax.utils.Constants;

/**
 * 
 * @author Killamess
 * 
 */
public class Enchant {

	public static Enchants enchant(int id) {
		for (Enchants e : XMLManager.enchants) {
			if (e.getSpellId() == id) {
				return e;
			}
		}
		return null;
	}

	public static void item(Client client, int id, int itemId, int slot) {

		for (Enchants e : XMLManager.enchants) {

			if (e.getSpellId() == id && e.getOriginalId() == itemId) {

				if (client.playerLevel[6] < e.getLevel()) {
					client.getActionSender().sendMessage(
							CommonStrings.MAGE_TOO_LOW);
					return;
				}
				if (!client.getActionAssistant().playerHasItem(e.getRunes()[0],
						e.getAmounts()[0])
						|| !client.getActionAssistant().playerHasItem(
								e.getRunes()[1], e.getAmounts()[1])
						|| !client.getActionAssistant().playerHasItem(
								e.getRunes()[2], e.getAmounts()[2])) {
					client.getActionSender()
							.sendMessage(CommonStrings.NO_RUNES);
					return;
				}
				Graphics.addNewRequest(client, e.getGfx(), 100, 0);
				client.getActionAssistant().deleteItem(e.getRunes()[0],
						e.getAmounts()[0]);
				client.getActionAssistant().deleteItem(e.getRunes()[1],
						e.getAmounts()[1]);
				client.getActionAssistant().deleteItem(e.getRunes()[2],
						e.getAmounts()[2]);
				client.getActionAssistant().deleteItem(e.getOriginalId(), slot,
						1);
				client.getActionSender().sendInventoryItem(e.getNewId(), 1,
						slot);
				client.getActionAssistant().addSkillXP(
						e.getXp() * Constants.SKILL_EXPERIENCE_MULTIPLIER,
						Constants.MAGIC);
			}
		}
	}
}
