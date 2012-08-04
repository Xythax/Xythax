package org.xythax.model.combat.magic;

import org.xythax.content.controllers.Animation;
import org.xythax.content.controllers.Damage;
import org.xythax.content.controllers.Graphics;
import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.MobileEntity;
import org.xythax.model.NPC;
import org.xythax.model.combat.content.Rings;
import org.xythax.model.map.TileManager;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;

/**
 * 
 * @author killamess
 * 
 */
public class MagicHandler extends Magic {

	public MagicHandler() throws Exception {
		super();
	}

	public static void createProjectile(MobileEntity attacker,
			MobileEntity victim, int spellId) {

		if (Magic.spell(spellId) == null)
			return;

		int projectileId = spell(spellId).getAirGfx();
		int startHeight = spell(spellId).getProjectileStartHeight();
		int endHeight = spell(spellId).getProjectileEndHeight();
		int offsetX = (attacker.getAbsX() - victim.getAbsX()) * -1;
		int offsetY = (attacker.getAbsY() - victim.getAbsY()) * -1;
		int distance = TileManager.calculateDistance(attacker, victim);
		int projectileSpeed = 78;

		if (attacker instanceof Client) {
			((Client) attacker)
					.getActionAssistant()
					.addSkillXP(
							((spell(spellId).getMagicLevel()) * Constants.COMBAT_EXPERIENCE_MULTIPLIER) / 10,
							Constants.MAGIC);
			if (victim instanceof Client && projectileId > 0)
				GameEngine.getGlobalActions().sendProjectile(
						((Client) attacker), attacker.getAbsY(),
						attacker.getAbsX(), offsetY, offsetX, projectileId,
						startHeight, endHeight, projectileSpeed, 17,
						-((Client) victim).getUserID() - 1);

			else if (victim instanceof NPC && projectileId > 0)
				GameEngine.getGlobalActions().sendProjectile(
						((Client) attacker), attacker.getAbsY(),
						attacker.getAbsX(), offsetY, offsetX, projectileId,
						startHeight, endHeight, projectileSpeed, 17,
						((NPC) victim).getNpcId() + 1);

		} else if (attacker instanceof NPC) {

			if (victim instanceof Client && projectileId > 0)
				GameEngine.getGlobalActions()
						.sendProjectile(((Client) victim), attacker.getAbsY(),
								attacker.getAbsX(), offsetY, offsetX,
								projectileId, startHeight, endHeight,
								projectileSpeed, 17,
								-((Client) victim).getUserID() - 1);
		}
		if (victim instanceof Client) {
			if (attacker instanceof NPC) {
				if (((Client) victim).getPrayerHandler().clicked[12]) {
					return;
				}
			}
		}
		/**
		 * is the player is apart of the multiList?
		 */
		boolean playerInMultiList = false;
		boolean hasHit = false;

		for (int i = 0; i < 9; i++) {
			if (attacker.getMultiList(i) == victim) {
				playerInMultiList = true;
			}
		}

		if (playerInMultiList) {
			for (int i = 0; i < 9; i++) {
				if (attacker.getMultiList(i) == victim) {
					hasHit = attacker.getHasHitTarget()[i];
				}
			}
		} else {
			hasHit = attacker.hasHit();
		}

		if (!hasHit) {
			boolean hasChanged = false;
			for (int i = 0; i < 9; i++) {
				if (attacker.getMultiList(i) == victim) {
					attacker.setHasHitTarget(i, false);
					hasChanged = true;
				}
			}
			if (!hasChanged) {
				attacker.setHasHit(false);
			}
			return;
		}

		int totalDamage = 0;
		int extraDamage = 0;
		int damage = spell(spellId).getDamage();
		boolean hasProjectile = spell(spellId).getAirGfx() > 0;

		if (GodSpells.godSpell(attacker, spellId) && attacker.isCharged())
			extraDamage += 10;

		if (attacker instanceof Client) {
			if (((Client) attacker).getPrayerHandler().clicked[23])
				damage *= 1.05;
			else if (((Client) attacker).getPrayerHandler().clicked[24])
				damage *= 1.1;
			else if (((Client) attacker).getPrayerHandler().clicked[25])
				damage *= 1.15;
		}

		totalDamage = Utils.random(damage + extraDamage);

		int hitDelay = (spell(spellId).getEndHitDelay() - (spell(spellId)
				.getProjectileDelay() > 0 ? 1 : 0));

		if (victim instanceof Client) {
			if (attacker instanceof Client) {
				if (((Client) victim).getPrayerHandler().clicked[12]) {
					totalDamage = (int) (totalDamage * 0.60);
				}
			} else if (attacker instanceof NPC) {
				if (((Client) victim).getPrayerHandler().clicked[12]) {
					totalDamage = 0;
				}
			}
		}

		if (hasHit)
			Damage.addNewHit(attacker, victim, attacker.getCombatType(),
					totalDamage, hitDelay + (hasProjectile ? 0 : distance / 4));

		if (attacker instanceof Client) {
			Client client = (Client) attacker;

			if (victim instanceof Client) {
				if (((Client) victim).getEquipment().wearingRecoil())
					Rings.applyRecoil(victim, attacker,
							Rings.pressDetails(totalDamage, 0), hitDelay
									+ (hasProjectile ? 0 : distance / 4));
				if (((Client) attacker).getPrayerHandler().clicked[17])
					((Client) victim).getPrayerHandler().updatePrayer(
							totalDamage / 4);
			}
			if (client.usingMagicDefence) {
				client.getActionAssistant()
						.addSkillXP(
								(totalDamage * Constants.COMBAT_EXPERIENCE_MULTIPLIER) / 2,
								Constants.MAGIC);
				client.getActionAssistant()
						.addSkillXP(
								(totalDamage * Constants.COMBAT_EXPERIENCE_MULTIPLIER) / 2,
								Constants.DEFENCE);
				client.getActionAssistant()
						.addSkillXP(
								(totalDamage * Constants.COMBAT_EXPERIENCE_MULTIPLIER) / 3,
								Constants.HITPOINTS);
			} else {
				client.getActionAssistant().addSkillXP(
						totalDamage * Constants.COMBAT_EXPERIENCE_MULTIPLIER,
						Constants.MAGIC);
				client.getActionAssistant()
						.addSkillXP(
								(totalDamage * Constants.COMBAT_EXPERIENCE_MULTIPLIER) / 3,
								Constants.HITPOINTS);
			}
		}
		if (MagicType.getMagicType(spellId) == MagicType.type.FREEZE
				|| MagicType.getMagicType(spellId) == MagicType.type.BIND)
			Effects.doFreezeEffect(attacker, victim, spellId);
		else if (MagicType.getMagicType(spellId) == MagicType.type.DRAIN)
			Effects.doDrainEffect(attacker, victim, spellId, totalDamage);
	}

	public static void startGfx(Entity attacker, Entity victim, int spellId) {
		if (spell(spellId) == null)
			return;

		Animation.face(attacker, victim);

		int gfx = spell(spellId).getHandGfx();
		int animationDelay = spell(spellId).getStartGfxDelay();

		if (gfx > 0 && animationDelay < 1) {
			Animation.createGraphic(attacker, gfx, 0, true);
		} else if (gfx > 0 && animationDelay > 0) {
			Graphics.addNewRequest(attacker, gfx, 100, 1);
		}
	}

	public static void endGfx(MobileEntity victim, MobileEntity attacker,
			int spellId) {
		if (spell(spellId) == null)
			return;

		int gfx = spell(spellId).getEndGfx();
		int gfxHeight = spell(spellId).getEndGfxHeight();
		int gfxDelay = spell(spellId).getEndGfxDelay();
		boolean hasProjectile = spell(spellId).getAirGfx() > 0;
		int distance = TileManager.calculateDistance(attacker, victim);
		boolean fail = false;

		if (victim instanceof Client) {
			if (attacker instanceof NPC) {
				if (((Client) victim).getPrayerHandler().clicked[12]) {
					fail = true;
				}
			}
		}

		if (gfx > 0) {
			/**
			 * is the player is apart of the multiList?
			 */
			boolean playerInMultiList = false;
			boolean hasHit = false;

			for (int i = 0; i < 9; i++) {
				if (attacker.getMultiList(i) == victim) {
					playerInMultiList = true;
				}
			}

			if (playerInMultiList) {
				for (int i = 0; i < 9; i++) {
					if (attacker.getMultiList(i) == victim) {
						hasHit = attacker.getHasHitTarget()[i];
					}
				}
			} else {
				hasHit = attacker.hasHit();
			}
			if (hasHit && !fail)
				Graphics.addNewRequest(victim, gfx, gfxHeight, gfxDelay
						+ (hasProjectile ? 0 : distance / 4));
			else
				Graphics.addNewRequest(victim, 85, 100, gfxDelay
						+ (hasProjectile ? 0 : distance / 4));
		}

	}

	public static void startAnimation(Entity attacker, int spellId) {
		if (spell(spellId) == null)
			return;

		int animation = spell(spellId).getAnimationId();
		int animationDelay = spell(spellId).getStartGfxDelay();

		if (animation > 0 && animationDelay < 1) {
			Animation.createAnimation(attacker, animation);
		} else if (animation > 0 && animationDelay > 0) {
			Animation.addNewRequest(attacker, animation, animationDelay);
		}
	}

	public static int getSpellDelay(int spellId) {

		if (spell(spellId) == null)
			return 0;

		int gfxDelay = spell(spellId).getEndGfxDelay();
		int hitDelay = spell(spellId).getEndHitDelay();
		int highest = gfxDelay > hitDelay ? gfxDelay : hitDelay;

		while (highest++ < 6)
			;

		return highest;
	}
}
