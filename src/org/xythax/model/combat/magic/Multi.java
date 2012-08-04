package org.xythax.model.combat.magic;

import java.util.Map;

import org.xythax.core.GameEngine;
import org.xythax.core.Server;
import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.MobileEntity;
import org.xythax.model.NPC;
import org.xythax.model.Player;
import org.xythax.model.combat.content.Hits;
import org.xythax.model.map.TileManager;
import org.xythax.world.PlayerManager;

/**
 * 
 * @author Killamess
 * 
 */
public class Multi {

	public static final int MAX_LIST_SIZE = 9;

	public static boolean canMultiAttack(int spellId) {

		int[] spells = { 12963, 13011, 12919, 12881, 12975, 13023, 12929, 12891 };
		for (int spell : spells) {
			if (spell == spellId) {
				return true;
			}
		}
		return false;
	}

	public static void resetList(Entity ent) {
		for (int i = 0; i < 9; i++) {
			ent.setMultiList(null, i);
			ent.setHasHitTarget(i, false);
		}
	}

	public static void multiAttack(MobileEntity attacker, MobileEntity victim,
			int spellId, int size) {

		if (canMultiAttack(spellId)) {

			makeList(attacker, victim);

			for (int i = 0; i < 9; i++) {
				if (attacker.getMultiList(i) != null) {
					attacker.setHasHitTarget(i, Hits.canHitMagic(attacker,
							attacker.getMultiList(i), spellId));
					MagicHandler.createProjectile(attacker,
							attacker.getMultiList(i), spellId);
					MagicHandler.endGfx(attacker.getMultiList(i), attacker,
							spellId);
				}
			}
			resetList(attacker);
		}
	}

	public static void makeList(MobileEntity attacker, MobileEntity victim) {

		resetList(attacker);

		int addedToListCount = 0;

		for (Player player : PlayerManager.getPlayerManager().getPlayers()) {

			if (player == null || player.isDead() || Server.multiValve == true
					&& !player.multiZone() || player == attacker
					|| player == victim) {
				continue;
			}
			if (TileManager.calculateDistance(victim, player) != 1) {
				continue;
			}
			if (attacker instanceof Client) {

				int lvldiff = Math.abs(((Client) attacker).getCombatLevel()
						- player.getCombatLevel());

				if (lvldiff >= player.getWildernessLevel()) {
					continue;
				}
				if (player.getWildernessLevel() < 1) {
					continue;
				}
			}
			for (int i = 0; i < MAX_LIST_SIZE; i++) {

				if (attacker.getMultiList(i) == player)
					continue;

				if (attacker.getMultiList(i) == null) {
					attacker.setMultiList(player, i);
					addedToListCount++;
					break;
				}
			}
		}
		if (addedToListCount >= MAX_LIST_SIZE) {
			return;
		}
		for (Map.Entry<Integer, NPC> entry : GameEngine.getNpcManager().npcMap
				.entrySet()) {

			NPC n = entry.getValue();

			if (n == null || n.getOwner() != null || n.isDead() || n == victim
					|| TileManager.calculateDistance(victim, n) != 1
					|| Server.multiValve == true && !n.multiZone()) {
				continue;
			}
			for (int i = 0; i < MAX_LIST_SIZE; i++) {

				if (attacker.getMultiList(i) == n)
					continue;

				if (attacker.getMultiList(i) == null) {
					attacker.setMultiList(n, i);
					addedToListCount++;
					break;
				}
			}
		}
	}
}
