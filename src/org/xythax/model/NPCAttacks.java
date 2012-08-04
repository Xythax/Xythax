package org.xythax.model;

/**
 * 
 * @author killamess
 * 
 */

public class NPCAttacks {

	public static int[][] npcArray = new int[3851][8];

	public static int getCombatType(int id) {
		if (npcArray[id][0] > 1) {
			return npcArray[id][0];
		}
		return 1;
	}

	public static int[][] NPC_SPELLS = { { 172, 1156 }, { 174, 1154 },
			{ 2025, 1189 }, { 173, 1158 }, { 941, 1337 }, { 55, 1337 }, };

}
