package org.xythax.model.combat.magic;

import org.xythax.content.JailSystem;
import org.xythax.content.controllers.Animation;
import org.xythax.content.controllers.Graphics;
import org.xythax.content.controllers.Location;
import org.xythax.model.Client;
import org.xythax.model.map.TileManager;
import org.xythax.utils.CommonStrings;
import org.xythax.utils.Constants;

public class TeleOther {

	public static final int[][] SPELLS = {
			{ 12425, 74, 566, 1, 563, 1, 557, 1, 3245, 3198 }, // lumbridge
			{ 12435, 82, 563, 1, 562, 1, 560, 1, 2966, 3379 }, // falador
			{ 12455, 90, 566, 2, 563, 1, -1, -1, 2757, 3478 } // camelot
	};

	public static void castSpell(Client castingPlayer, Client effectedPlayer,
			int spell) {

		if (TileManager.calculateDistance(castingPlayer, effectedPlayer) > 16) {
			castingPlayer.getActionSender().sendMessage(
					"This " + effectedPlayer.getUsername()
							+ " is too far away.");
			return;
		}

		for (int[] s : SPELLS) {
			if (s[0] == spell) {

				if (castingPlayer.playerLevel[Constants.MAGIC] < s[1]) {
					castingPlayer.getActionSender().sendMessage(
							CommonStrings.MAGE_TOO_LOW);
					return;
				}
				if (!castingPlayer.getActionAssistant().playerHasItem(s[2],
						s[3])
						&& !castingPlayer.getActionAssistant().staffType(s[2])
						|| !castingPlayer.getActionAssistant().playerHasItem(
								s[4], s[5])
						&& !castingPlayer.getActionAssistant().staffType(s[4])
						|| !castingPlayer.getActionAssistant().playerHasItem(
								s[6], s[7])
						&& !castingPlayer.getActionAssistant().staffType(s[6])) {
					castingPlayer.getActionSender().sendMessage(
							CommonStrings.NO_RUNES);
					return;
				}
				if (!castingPlayer.getActionAssistant().staffType(s[2])) {
					castingPlayer.getActionAssistant().deleteItem(s[2], s[3]);
				}
				if (!castingPlayer.getActionAssistant().staffType(s[4])) {
					castingPlayer.getActionAssistant().deleteItem(s[4], s[5]);
				}
				if (!castingPlayer.getActionAssistant().staffType(s[6])) {
					castingPlayer.getActionAssistant().deleteItem(s[6], s[7]);
				}
				Animation.face(castingPlayer, effectedPlayer);
				effectedPlayer.teleToSpell = spell;
				effectedPlayer.teleOther = castingPlayer.getUsername();
				effectedPlayer.getActionSender().sendInterface(12468);
				effectedPlayer.getActionSender().sendQuest(
						castingPlayer.getUsername(), 12558);
				effectedPlayer.getActionSender().sendQuest(getTeleName(s[0]),
						12560);
			}
		}
	}

	public static void acceptTeleport(Client toMove, Client toCast, int spell) {

		if (TileManager.calculateDistance(toMove, toCast) > 16) {
			toMove.getActionSender().sendMessage(
					"You are too far away from " + toCast.getUsername() + ".");
			return;
		}
		if (JailSystem.inJail(toMove)) {
			toMove.getActionSender().sendMessage(
					"You cannot teleport out of jail!");
			return;
		}
		Animation.face(toCast, toMove);
		Animation.addNewRequest(toCast, 1818, 1);
		Graphics.addNewRequest(toCast, 343, 100, 1);
		Graphics.addNewRequest(toMove, 342, 100, 3);
		Animation.addNewRequest(toMove, 1816, 3);
		Animation.addNewRequest(toMove, 715, 7);
		for (int[] s : SPELLS) {
			if (s[0] == spell) {
				Location.addNewRequest(toMove, s[8], s[9], 0, 6);
				toMove.stopMovement();
				return;
			}
		}
	}

	public static String getTeleName(int spellId) {
		if (spellId == 12425) {
			return "Lumbridge";
		} else if (spellId == 12435) {
			return "Falador";
		}
		return "Camelot";
	}

}
