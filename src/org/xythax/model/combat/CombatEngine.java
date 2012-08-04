package org.xythax.model.combat;

import org.xythax.content.actions.tasks.Skulling;
import org.xythax.content.controllers.Animation;
import org.xythax.content.controllers.Damage;
import org.xythax.core.Server;
import org.xythax.event.BusyEvent;
import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.Entity.CombatType;
import org.xythax.model.Equipment;
import org.xythax.model.MobileEntity;
import org.xythax.model.NPC;
import org.xythax.model.NPCAttacks;
import org.xythax.model.NPCFollow;
import org.xythax.model.combat.content.Distances;
import org.xythax.model.combat.content.Hits;
import org.xythax.model.combat.content.Life;
import org.xythax.model.combat.content.Wilderness;
import org.xythax.model.combat.magic.AutoCast;
import org.xythax.model.combat.magic.GodSpells;
import org.xythax.model.combat.magic.Magic;
import org.xythax.model.combat.magic.MagicChecker;
import org.xythax.model.combat.magic.MagicHandler;
import org.xythax.model.combat.magic.MagicType;
import org.xythax.model.combat.magic.Multi;
import org.xythax.model.combat.ranged.Ranged;
import org.xythax.model.map.FollowEngine;
import org.xythax.model.map.TileManager;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;
import org.xythax.world.NPCManager;

public class CombatEngine {

	public static void addEvent(Entity attacker, Entity target) {
		attacker.setTarget(target);
	}

	public static void createNewAttack(MobileEntity attacker,
			MobileEntity target) {
		if (attacker instanceof Client && !Life.isAlive(attacker)
				|| !Life.isAlive(target)
				|| !Wilderness.checkPlayers(attacker, target)
				|| TileManager.calculateDistance(attacker, target) >= 20) {
			attacker.setInCombatWith(null);
			attacker.setInCombatTimer(0);
			target.setInCombatWith(null);
			target.setInCombatTimer(0);
			resetAttack(attacker, true);
			if (attacker instanceof Client)
				((Client) attacker).resetWalkingQueue();
			return;
		}
		if (target instanceof NPC) {
			if (((NPC) target).getDefinition().getType() == Constants.PET_TYPE) {
				attacker.setInCombatWith(null);
				attacker.setInCombatTimer(0);
				target.setInCombatWith(null);
				target.setInCombatTimer(0);
				resetAttack(attacker, true);
				if (attacker instanceof Client)
					((Client) attacker).resetWalkingQueue();
				return;
			}
		}
		if (attacker instanceof Client)
			((Client) attacker).logoutDelay = 20;
		if (attacker.following == null || attacker.following != target)
			attacker.following = target;

		if (attacker instanceof Client) {
			if (attacker.getCombatType() == CombatType.MELEE) {
				if (!((Client) attacker).usingSpecial())
					Animation.createAnimation(attacker);
				Hits.runHitMelee(attacker, target,
						((Client) attacker).usingSpecial());

			} else if (attacker.getCombatType() == CombatType.RANGE) {
				if (Ranged.hasArrows(((Client) attacker))) {
					if (Ranged
							.projectile(((Client) attacker).playerEquipment[Constants.ARROWS]) != null
							|| Ranged.isUsingCrystalBow(((Client) attacker))) {
						if (Ranged.isUsingCrystalBow(((Client) attacker))) {
							Animation
									.createGraphic(attacker,
											Ranged.projectile(1337)
													.getPullBackGfx(), 0, true);
						} else {
							Animation
									.createGraphic(
											attacker,
											Ranged.projectile(
													((Client) attacker).playerEquipment[Constants.ARROWS])
													.getPullBackGfx(), 0, true);
						}
					}
					Animation.createAnimation(attacker);
					if (((Client) attacker).usingSpecial())
						Ranged.createMSBProjectile(attacker, target);
					else
						Ranged.createProjectile(attacker, target);

					Hits.runHitMelee(attacker, target,
							((Client) attacker).usingSpecial());
				} else {
					resetAttack(attacker, true);
					return;
				}
			} else if (attacker.getCombatType() == CombatType.MAGIC) {
				int theCurrentId = 0;

				if (((Client) attacker).spellId > 0) {
					theCurrentId = ((Client) attacker).spellId;
					((Client) attacker).turnOffSpell = true;
				} else if (((Client) attacker).isAutoCasting()) {
					theCurrentId = ((Client) attacker).getAutoCastId();
				}
				if (theCurrentId > 0) {
					if (MagicChecker.hasRunes(attacker, theCurrentId)) {
						if (MagicChecker.hasRequiredLevel(attacker,
								theCurrentId)) {
							if (GodSpells.godSpell(attacker, theCurrentId)) {
								if (GodSpells.wrongGodCape(attacker,
										theCurrentId)) {
									((Client) attacker)
											.getActionSender()
											.sendMessage(
													"You cannot cast this spell while supporting a different god.");
									resetAttack(attacker, true);
									return;
								}
							}
							if (MagicType.getMagicType(theCurrentId) == MagicType.type.TELEBLOCK) {
								if (target instanceof NPC) {
									((Client) attacker)
											.getActionSender()
											.sendMessage(
													"You cannot cast this spell on npcs.");
									resetAttack(attacker, true);
									return;
								}
							}
							MagicChecker.deleteRunes(attacker, theCurrentId);
							attacker.setHasHit(Hits.canHitMagic(attacker,
									target, theCurrentId));

							/**
							 * check for multi attacking spell.
							 */

							Multi.multiAttack(attacker, target, theCurrentId, 1);
							attacker.setCombatDelay(MagicHandler
									.getSpellDelay(theCurrentId));

							if (Magic.spell(theCurrentId).getProjectileDelay() > 0) {
								MagicHandler.startGfx(attacker, target,
										theCurrentId);
								MagicHandler.startAnimation(attacker,
										theCurrentId);
								MagicHandler.endGfx(target, attacker,
										theCurrentId);
								attacker.setHoldingSpellDelay(Magic.spell(
										theCurrentId).getProjectileDelay());
								attacker.setHoldingSpell(theCurrentId);
								attacker.setHoldingSpellTarget(target);
							} else {
								MagicHandler.startGfx(attacker, target,
										theCurrentId);
								MagicHandler.startAnimation(attacker,
										theCurrentId);
								MagicHandler.createProjectile(attacker, target,
										theCurrentId);
								MagicHandler.endGfx(target, attacker,
										theCurrentId);
							}
						} else {
							AutoCast.turnOff(((Client) attacker));
							resetAttack(attacker, true);
							return;
						}
					} else {
						AutoCast.turnOff(((Client) attacker));
						resetAttack(attacker, true);
						return;
					}
				}
			}
			if (((Client) attacker).playerEquipment[Constants.WEAPON] == Ranged.DARK_BOW)
				Hits.runHitMelee(attacker, target, false);

		} else if (attacker instanceof NPC) {

			int npcId = ((NPC) attacker).getDefinition().getType();

			if (attacker.getCombatType() == CombatType.MAGIC) {

				int spell = 0;

				for (int i = 0; i < NPCAttacks.NPC_SPELLS.length; i++) {
					if (NPCAttacks.NPC_SPELLS[i][0] == npcId) {
						spell = NPCAttacks.NPC_SPELLS[i][1];
						break;
					}
				}
				attacker.setHasHitTarget(0,
						Hits.canHitMagic(attacker, target, spell));
				MagicHandler.startGfx(attacker, target, spell);
				MagicHandler.startAnimation(attacker, spell);
				MagicHandler.createProjectile(attacker, target, spell);
				MagicHandler.endGfx(target, attacker, spell);
				attacker.setCombatDelay(6);

			} else {
				int randomGen = Utils.random(5);
				if (randomGen > 4 && npcId == 941 || npcId == 55) {
					int spell = 0;

					for (int i = 0; i < NPCAttacks.NPC_SPELLS.length; i++) {
						if (NPCAttacks.NPC_SPELLS[i][0] == npcId) {
							spell = NPCAttacks.NPC_SPELLS[i][1];
							break;
						}
					}
					MagicHandler.startGfx(attacker, target, spell);
					MagicHandler.startAnimation(attacker, spell);
					if (target instanceof Client) {
						if (((Client) target).playerEquipment[Constants.SHIELD] != 1540
								&& ((Client) target).playerEquipment[Constants.SHIELD] != 11284) {
							((Client) target).getActionSender().sendMessage(
									"The dragons breath burns you.");
							Damage.addNewHit(attacker, target,
									CombatType.MAGIC, Utils.random(70), 2);
						} else {
							((Client) target).getActionSender().sendMessage(
									"The shield aborbs the dragons breath.");
							if (Utils.random(5) == 1) {
								Damage.addNewHit(attacker, target,
										CombatType.MAGIC, Utils.random(8), 2);
							} else {
								Damage.addNewHit(attacker, target,
										CombatType.MAGIC, 0, 2);
							}
						}
					}
				} else {
					int animation = NPCManager.emotions[npcId][0];
					Animation.createAnimation(attacker, animation);
					Hits.runHitMelee(attacker, target,
							((NPC) attacker).usingSpecial());
				}
			}
		}
		if (!(attacker.getCombatType() == CombatType.MAGIC)) {
			attacker.setCombatDelay((attacker instanceof Client) ? Equipment
					.getWeaponSpeed((Client) attacker) : 6);
		}
		if (attacker instanceof Client && target instanceof Client)
			Skulling.setSkulled((Client) attacker, (Client) target);

	}

	public static void resetAttack(Entity ent, boolean stopMovement) {

		ent.setTarget(null);
		FollowEngine.resetFollowing(ent);
		if (ent instanceof Client) {

			((Client) ent).resetFaceDirection();

			if (stopMovement) {
				((Client) ent).stopMovement();
				((Client) ent).getActionSender().sendFlagRemoval();
			}
		}
	}

	public static boolean inCombatWith(Entity entity, Entity victim) {
		return entity.getInCombatWith() == victim;
	}

	public static String canAttack(Entity entity, Entity victim) {

		if (entity.getInCombatWith() != null
				&& entity.getInCombatWith() != victim)
			return "You are already under attack.";
		else if (victim.getInCombatWith() != null
				&& victim.getInCombatWith() != entity)
			return "They are already under attack.";
		else if (victim.getTarget() != null && victim.getTarget() != entity
				&& victim.getTarget().getTarget() != null)
			return "They are already in combat.";
		return "";
	}

	public static CombatType getCombatType(Entity entity) {

		if (entity instanceof Client) {

			Client client = (Client) entity;

			if (client.isAutoCasting() || client.getAutoCastId() > 0
					|| client.spellId > 0)
				return CombatType.MAGIC;

			else if (Ranged.isUsingRange(client))
				return CombatType.RANGE;
		} else if (entity instanceof NPC) {

			NPC npc = (NPC) entity;
			int type = npc.getDefinition().getType();
			int combatType = NPCAttacks.getCombatType(type);

			switch (combatType) {

			case 2:
				return CombatType.RANGE;
			case 3:
				return CombatType.MAGIC;
			case 4:
				if (Utils.random(1) == 0)
					return CombatType.RANGE;
				else
					return CombatType.MAGIC;
			case 5:
				if (Utils.random(1) == 0)
					return CombatType.MELEE;
				else
					return CombatType.RANGE;
			case 6:
				int random = Utils.random(2);
				if (random == 0)
					return CombatType.MELEE;
				else if (random == 1)
					return CombatType.RANGE;
				else
					return CombatType.MAGIC;
			case 7:
				if (Utils.random(1) == 0)
					return CombatType.MELEE;
				else
					return CombatType.MAGIC;
			}
		}
		return CombatType.MELEE;
	}

	public static void mainProcessor(MobileEntity mobileEntity) {

		if (mobileEntity == null)
			return;

		/*
		 * if (entity.getTarget() != null) { if (entity.getTarget() instanceof
		 * Client) { if (((Client)entity.getTarget()).disconnected) {
		 * CombatEngine.resetAttack(entity, true); } } }
		 */

		if (mobileEntity.getFreezeDelay() > 0) {
			mobileEntity.setFreezeDelay(mobileEntity.getFreezeDelay() - 1);
			if (mobileEntity.getFreezeDelay() == 0) {
				if (mobileEntity instanceof Client)
					((Client) mobileEntity).setCanWalk(true);
			}
		}
		if (mobileEntity.getInCombatWithTimer() > 0)
			mobileEntity.deductInCombatWithTimer();

		if (mobileEntity.getInCombatWithTimer() <= 0)
			mobileEntity.setInCombatWith(null);

		if (mobileEntity.getCombatDelay() > 0)
			mobileEntity.setCombatDelay(mobileEntity.getCombatDelay() - 1);

		if (mobileEntity.getVengTimer() > 0)
			mobileEntity.deductVengTimer();

		if (mobileEntity.getHoldingSpellDelay() > 0)
			mobileEntity.setHoldingSpellDelay(mobileEntity
					.getHoldingSpellDelay() - 1);

		BusyEvent.busyEvent(mobileEntity);
		Wilderness.wildernessEvent(mobileEntity);

		if (mobileEntity.getHoldingSpell() > 0
				&& mobileEntity.getHoldingSpellDelay() == 0
				&& mobileEntity.getHoldingSpellTarget() != null) {
			MagicHandler.createProjectile(mobileEntity,
					mobileEntity.getHoldingSpellTarget(),
					mobileEntity.getHoldingSpell());
			mobileEntity.setHoldingSpellDelay(0);
			mobileEntity.setHoldingSpell(0);
			mobileEntity.setHoldingSpellTarget(null);
		}
		mobileEntity.setCombatType(getCombatType(mobileEntity));

		if (mobileEntity.getTarget() == null) {
			return;
		}
		if (checkForSpellReset(mobileEntity)) {
			FollowEngine.resetFollowing(mobileEntity);
			return;
		}

		Animation.face(mobileEntity, mobileEntity.getTarget());

		if (mobileEntity.getTarget() != null) {
			if (!Life.isAlive(mobileEntity.getTarget())) {
				mobileEntity.setInCombatWith(null);
				mobileEntity.setInCombatTimer(0);
			}
		}
		if (!Wilderness.checkPlayers(mobileEntity, mobileEntity.getTarget())) {
			resetAttack(mobileEntity, true);
			return;
		}
		if (mobileEntity.getRetaliateDelay() > 0) {
			if (mobileEntity.getRetaliateDelay() == 1) {
				mobileEntity.setRetaliateDelay(0);
			} else {
				mobileEntity
						.setRetaliateDelay(mobileEntity.getRetaliateDelay() - 1);
			}
			return;
		}
		if (mobileEntity.isBusy())
			return;

		if (TileManager.calculateDistance(mobileEntity.getTarget(),
				mobileEntity) > 32) {
			resetAttack(mobileEntity, true);
			return;
		}

		if (mobileEntity instanceof NPC) {
			if (NPCFollow.validSidewardsWalker((NPC) mobileEntity)
					&& !NPCFollow.inMeleeDistance(mobileEntity,
							mobileEntity.getTarget())) {
				if (((NPC) mobileEntity).getCombatType() == CombatType.MELEE)
					return;
			}
		} else {
			if (!TileManager.inAttackablePosition(mobileEntity,
					mobileEntity.getTarget())
					&& mobileEntity.combatType == CombatType.MELEE
					&& TileManager.calculateDistance(mobileEntity,
							mobileEntity.getTarget()) == 1) {
				return;
			}
		}

		if (mobileEntity.getCombatDelay() > 0
				|| !Distances.inAttackableDistance(mobileEntity,
						mobileEntity.getTarget()))
			return;

		if (canAttack(mobileEntity, mobileEntity.getTarget()) != "") {
			if (Server.multiValve && !inMultiZone(mobileEntity.getTarget())
					&& !inMultiZone(mobileEntity)) {
				if (mobileEntity instanceof Client) {
					((Client) mobileEntity).getActionSender().sendMessage(
							canAttack(mobileEntity, mobileEntity.getTarget())
									+ "");
				}
				resetAttack(mobileEntity, true);
				return;
			}
		} else {
			mobileEntity.getTarget().setInCombatWith(mobileEntity);
			mobileEntity.getTarget().setInCombatWithTimer();
			if (mobileEntity.getTarget() != null
					&& mobileEntity.getTarget().getTarget() != mobileEntity) {
				mobileEntity.getTarget().setRetaliateDelay(2);
			}
		}
		createNewAttack(mobileEntity, mobileEntity.getTarget());
	}

	public static boolean inMultiZone(Entity entity) {
		return entity.multiZone();
	}

	public static boolean checkForSpellReset(Entity entity) {
		if (!(entity instanceof Client))
			return false;

		Client client = (Client) entity;

		if (client.spellId > 0 && client.turnOffSpell) {
			client.turnOffSpell = false;
			client.spellId = 0;
			entity.setTarget(null);
			return true;
		}
		return false;
	}
}