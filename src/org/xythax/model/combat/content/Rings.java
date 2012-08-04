package org.xythax.model.combat.content;

import org.xythax.content.controllers.Damage;
import org.xythax.content.controllers.Location;
import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.Entity.CombatType;
import org.xythax.model.NPC;
import org.xythax.utils.Constants;

/**
 * 
 * @author Killamess Our ring effects.
 * 
 */

public class Rings {

	public static int[] pressDetails(int i, int j) {
		int[] data = new int[2];
		data[0] = i;
		data[1] = j;
		return data;
	}

	public static void applyRecoil(Entity attacker, Entity target,
			int[] damage, int delay) {
		if (damage[0] < 1 && damage[1] < 1)
			return;

		int[] finalDamage = new int[2];
		boolean[] inUse = new boolean[2];

		if (damage[0] > 0)
			inUse[0] = true;
		if (damage[1] > 0)
			inUse[1] = true;

		if (attacker instanceof Client) {
			if (((Client) attacker).getRecoilCount() == 0)
				((Client) attacker).setRecoilCount(250);

			if (((Client) attacker).getRecoilCount() == 1) {
				((Client) attacker).setRecoilCount(0);
				breakRing(attacker);
			}
			if (((Client) attacker).getRecoilCount() > 1) {
				for (int amount : damage) {
					if (((Client) attacker).getRecoilCount() - amount >= 1)
						((Client) attacker).setRecoilCount(((Client) attacker)
								.getRecoilCount() - amount);
					else
						((Client) attacker).setRecoilCount(1);
				}
			}
		} else if (attacker instanceof NPC) {

		}
		for (int i = 0; i < 2; i++)
			finalDamage[i] = (damage[i] / 6) + 1;

		if (inUse[0])
			Damage.addNewHit(attacker, target, CombatType.RECOIL,
					finalDamage[0], delay);
		if (inUse[1])
			Damage.addNewHit(attacker, target, CombatType.RECOIL,
					finalDamage[1], delay);
	}

	public static void applyRingOfLife(Entity entity, int damage) {
		if (!(entity instanceof Client))
			return;

		Client client = (Client) entity;

		if (client != null) {

			if (client.playerEquipment[Constants.RING] != 2570)
				return;

			int totalHealth = client.getLevelForXP(client.playerXP[3]);
			int fifteenPercent = (int) (totalHealth * 0.15);

			if (client.hitpoints <= fifteenPercent && client.hitpoints > 0) {
				Location.teleport(client, 7, "magicbook");
				breakRing(client);
			}
		}
	}

	public static void breakRing(Entity ent) {
		if (ent instanceof Client) {
			((Client) ent).getEquipment().deleteRing();
			((Client) ent).getActionSender().sendMessage(
					"Your ring crumbles to dust.");
		}
	}

}
