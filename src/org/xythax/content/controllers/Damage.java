package org.xythax.content.controllers;

import java.util.ArrayList;

import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.Entity.CombatType;
import org.xythax.model.NPC;
import org.xythax.model.combat.content.Life;
import org.xythax.model.combat.content.Poison;
import org.xythax.model.combat.content.Rings;
import org.xythax.model.combat.magic.Lunar;
import org.xythax.utils.Constants;

/**
 * 
 * @author killamess
 * 
 */
public class Damage extends ControllerManager {

	private Entity attacker = null;
	private Entity victim = null;
	private CombatType type = null;
	private int damage;
	private int hitDelay;

	public Damage(Entity attacker, Entity ent, CombatType type, int damage,
			int delay) {
		this.setAttacker(attacker);
		this.setVictim(ent);
		this.setCombatType(type);
		this.setDamage(damage);
		this.setHitDelay(delay);
	}

	public static void addNewHit(Entity attacker, Entity victim,
			CombatType type, int damage, int delay) {
		hits.add(new Damage(attacker, victim, type, damage, delay));
	}

	public static void process() {

		if (hits.isEmpty())
			return;

		ArrayList<Damage> newList = new ArrayList<Damage>(hits);

		for (Damage newHit : newList) {

			if (newHit == null)
				continue;

			if (newHit.getHitDelay() > 0)
				newHit.deductHitDelay();

			if (newHit.getHitDelay() == 0) {

				if (newHit.getVictim().isBusy()
						&& newHit.getVictim().getBusyTimer() > 1) {
					newHit.setHitDelay(1);
					continue;
				}
				if (Constants.FOOD_DELAY_HITS) {
					if (newHit.getVictim() instanceof Client) {
						if (((Client) newHit.getVictim()).foodDelay > 2
								|| ((Client) newHit.getVictim()).potionDelay > 1) {
							newHit.setHitDelay(1);
							continue;
						}
					}
				}

				if (!Life.isAlive(newHit.getAttacker(), newHit.getVictim()))
					newHit.setDamage(0);

				if (newHit.getCombatType() != CombatType.THIEF
						&& newHit.getCombatType() != CombatType.RECOIL
						&& newHit.getCombatType() != CombatType.POISON) {

					if (newHit.getVictim() instanceof Client) {

						Client client = (Client) newHit.getVictim();

						if (Life.isAlive(newHit.getVictim())
								&& client.autoRetaliate
								&& newHit.getVictim().getTarget() == null)
							newHit.getVictim().setTarget(newHit.getAttacker());

					} else {

						if (Life.isAlive(newHit.getVictim())) {
							newHit.getVictim().setTarget(newHit.getAttacker());
						}
					}

				}
				if (newHit.getVictim() instanceof Client) {

					Client client = (Client) newHit.getVictim();

					if (newHit.getAttacker() instanceof Client) {
						Client client2 = (Client) newHit.getAttacker();

						if (Life.isAlive(newHit.getAttacker())) {
							if (client != client2) {
								client.whoKilledYa = client2;
								client.logoutDelay = 20;
							}
						}
						if (newHit.getDamage() > 0) {
							if (newHit.getAttacker() instanceof Client) {
								if (Poison
										.canPoison(client2.playerEquipment[Constants.WEAPON])
										&& !Poison.isPoisoned(client)) {
									Poison.applyPoisonDesease(
											client,
											Poison.getPoisonSeverity(client2.playerEquipment[Constants.WEAPON]));
								}
							}
						}
					}
					client.getActionAssistant().appendHit(
							newHit.getCombatType(), newHit.getDamage());

					if (client.isVengOn()
							&& newHit.getCombatType() != CombatType.RECOIL) {
						if (newHit.getDamage() > 0)
							Lunar.vengenceEffect(client, newHit.getAttacker(),
									newHit.getDamage());
					}
					if (((Client) newHit.getVictim()).getEquipment()
							.wearingRecoil()
							&& newHit.getCombatType() != CombatType.RECOIL) {
						Rings.applyRecoil(newHit.getVictim(),
								newHit.getAttacker(),
								Rings.pressDetails(newHit.getDamage(), 0), 0);
					}

				} else if (newHit.getVictim() instanceof NPC) {

					NPC npc = (NPC) newHit.getVictim();

					if (Life.isAlive(newHit.getAttacker()))
						npc.hit(newHit.getAttacker(), npc, newHit.getDamage());

				}
				hits.remove(newHit);
			}
		}
		newList.clear();
	}

	/**
	 * @param victim
	 *            the victim to set
	 */
	public void setAttacker(Entity attacker) {
		this.attacker = attacker;
	}

	/**
	 * @return the victim
	 */
	public Entity getAttacker() {
		return attacker;
	}

	/**
	 * @param victim
	 *            the victim to set
	 */
	public void setVictim(Entity victim) {
		this.victim = victim;
	}

	/**
	 * @return the victim
	 */
	public Entity getVictim() {
		return victim;
	}

	/**
	 * @param damage
	 *            the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @param hitType
	 *            the hitType to set
	 */
	public void setCombatType(CombatType type) {
		this.type = type;
	}

	/**
	 * @return the hitType
	 */
	public CombatType getCombatType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(CombatType type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public CombatType getType() {
		return type;
	}

	public void setHitDelay(int newDelay) {
		this.hitDelay = newDelay;
	}

	public int getHitDelay() {
		return hitDelay;
	}

	public void deductHitDelay() {
		this.hitDelay--;
	}

}
