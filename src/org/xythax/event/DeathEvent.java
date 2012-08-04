package org.xythax.event;

import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.Player;
import org.xythax.model.combat.CombatEngine;
import org.xythax.model.combat.content.ItemProtect;
import org.xythax.model.combat.content.Specials;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;
import org.xythax.world.PlayerManager;

public class DeathEvent {
	public static void process() {
		for (Player p : PlayerManager.getPlayerManager().getPlayers()) {
			if (p == null)
				continue;

			Client client = (Client) p;
			if (client.hitpoints <= 0 && client.getStunnedTimer() < 0) {
				client.setDead(true);
				client.setCanWalk(false);
				client.stopMovement();
				client.setStunnedTimer(4);
			}
			if (client.isDead()) {
				if (!client.isDeadWaiting() && client.getStunnedTimer() <= 0) {
					client.setDeadWaiting(true);
					client.setDeadTimer(5);
					client.poisonDelay = -1;
					client.poisonDamage = -1;
					client.setBusy(true);
					client.setCanWalk(false);
					client.stopMovement();
					CombatEngine.resetAttack(client, true);
					client.getActionAssistant().startAnimation(2304, 0);
					client.getActionSender().sendMessage("Oh dear, you died!");
				} else {
					if (client.getDeadTimer() == 1) {
						client.teleportToX = Constants.SPAWN_X
								+ Utils.random(1);
						client.teleportToY = Constants.SPAWN_Y
								+ Utils.random(1);
						ItemProtect.dropItems(client, true);
					}
					client.setWildernessLevel(-1);
					client.setFreezeDelay(0);
					client.energy = 100;
					client.updateEnergy();
					client.getEquipment().sendWeapon();
					client.setVeng(false);
					client.setHeightLevel(0);
					client.skullTimer = 0;
					client.skulledOn = null;
					client.getEquipment().setWeaponEmotes();
					client.hitpoints = client
							.getLevelForXP(client.playerXP[Constants.HITPOINTS]);
					client.getPrayerHandler().resetAllPrayers();
					Specials.deathEvent(client);
					for (int i = 0; i < Constants.MAX_SKILLS; i++) {
						client.playerLevel[i] = client
								.getLevelForXP(client.playerXP[i]);
						client.getActionAssistant().refreshSkill(i);
					}
				}
				if (client.getDeadTimer() == 0) {
					client.setDead(false);
					client.setDeadWaiting(false);
					client.logoutDelay = 20;
					client.setBusy(false);
					client.setCanWalk(true);
					GameEngine.getGlobalActions().sendAnimationReset(client);
				}
				client.deductDeadTimer();
			}
			client.deductStunnedTimer();
		}
	}
}