package org.xythax.model.definition.entity;

import java.util.ArrayList;
import java.util.List;

import org.xythax.model.NPCDrop;

/**
 * An NPC type
 * 
 * @author Graham
 */
public class NPCDefinition {

	/**
	 * The type id.
	 */
	private int type;

	/**
	 * CombatHandler level.
	 */
	private int combat;

	/**
	 * Health.
	 */
	private int health;

	/**
	 * Name.
	 */
	private String name;

	/**
	 * Respawn timer.
	 */
	private int respawn;

	/**
	 * Drops.
	 */
	private List<NPCDrop> drops;

	/**
	 * Construct the NPC definition
	 * 
	 * @param type
	 * @param combat
	 * @param health
	 * @param name
	 * @param respawn
	 */
	public NPCDefinition(int type, int combat, int health, String name,
			int respawn) {
		this.type = type;
		this.combat = combat;
		this.health = health;
		this.name = name;
		this.drops = new ArrayList<NPCDrop>();
		this.respawn = respawn;
	}

	/**
	 * Add a drop.
	 * 
	 * @param drop
	 */
	public void addDrop(NPCDrop drop) {
		drops.add(drop);
	}

	/**
	 * Gets the npc type
	 * 
	 * @return
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * Gets the npc combat
	 * 
	 * @return
	 */
	public int getCombat() {
		return this.combat;
	}

	/**
	 * Gets the npc health
	 * 
	 * @return
	 */
	public int getHealth() {
		return this.health;
	}

	/**
	 * Gets the npc name
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the npc drops
	 * 
	 * @return
	 */
	public List<NPCDrop> getDrops() {
		return this.drops;
	}

	/**
	 * Gets the respawn time.
	 * 
	 * @return
	 */
	public int getRespawn() {
		return this.respawn;
	}

}
