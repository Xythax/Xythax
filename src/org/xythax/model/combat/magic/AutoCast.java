package org.xythax.model.combat.magic;

import org.xythax.model.Client;
import org.xythax.utils.Constants;

/**
 * 
 * @author Killamess
 * 
 */

public class AutoCast {

	public static final int OFF = 0, ON = 1, FRAME_ID = 354, CONFIG_ID = 108;

	public static void turnOff(Client client) {
		client.getActionSender().sendClientConfig(CONFIG_ID, OFF);
		client.setAutoCasting(false);
		client.getActionSender().sendQuest("Choose spell", FRAME_ID);
		client.setAutoCastId(0);
	}

	public static void turnOn(Client client) {
		client.getActionSender().sendClientConfig(CONFIG_ID, ON);
		client.setAutoCasting(true);
	}

	public static boolean checkForCorrectStaff(Client client) {
		if (client.getSpellBook() == 2) {
			if (client.playerEquipment[Constants.WEAPON] != 4675) {
				client.getActionSender()
						.sendMessage(
								"You need an ancient staff to auto-cast ancient magic.");
				return false;
			}
		} else if (client.getSpellBook() == 1) {
			if (!hasRegularStaff(client)) {
				client.getActionSender().sendMessage(
						"You cannot auto-cast with this staff.");
				return false;
			}
		} else if (client.getSpellBook() == 3) {
			client.getActionSender().sendMessage(
					"You cannot auto-cast on lunar magic.");
			return false;
		}
		return true;
	}

	public static void newSpell(Client client, int id) {
		if (!MagicChecker.hasRunes(client, id)
				|| !MagicChecker.hasRequiredLevel(client, id)) {
			turnOff(client);
			return;
		}
		client.setAutoCastId(id);
		client.getActionSender().sendSidebar(0, 328);

		if (id != 0)
			turnOn(client);

		String spellName = Magic.spell(id).getName();
		client.getActionSender().sendQuest(spellName, FRAME_ID);

	}

	public static boolean hasRegularStaff(Client client) {
		int[] regularStaffs = { 1379, 1381, 1383, 1385, 1387, 1389, 1391, 1393,
				1395, 1397, 1399, 1401, 1403, 1405, 1407, 6912, 6910, 6908,
				6914 };
		for (int i : regularStaffs) {
			if (client.playerEquipment[Constants.WEAPON] == i) {
				return true;
			}
		}
		return false;
	}
}
