package org.xythax.utils;

public class Constants {
	public static int SAVE_TIMER = 30000;
	public static boolean showPlayerOnlineCount = false;
	public static boolean showCpuUsage = false; // will display every 100 cycles
	public static boolean showSaveMessage = false;
	public static boolean showXMLMessage = true; // will display all xml
													// loadings
	public static final int PET_TYPE = 98; // DOG

	/**
	 * On rs when you eat, and get hit at the same time it can delay the hit for
	 * 1 or 2 cycles.
	 */
	public static boolean FOOD_DELAY_HITS = false;

	public static final int cycleTime = 600;
	public static final String SERVER_NAME = "Xythax";
	public static final int LISTENING_PORT = 43594;

	/**
	 * Increase this when needed.
	 */
	public static final int MAX_PLAYERS = 50;

	public static final int INGAME_TIMEOUT = 5;

	public static final int MAX_CONNECTIONS_PER_IP = 1;

	/**
	 * Player shit
	 */
	public static final int SPAWN_X = 3246;
	public static final int SPAWN_Y = 3429;
	public static final int MAX_ITEMS = 16000;
	public static final int MAX_ITEM_AMOUNT = Integer.MAX_VALUE;

	public static final int PROPS_SIZE = 100;
	public static final int WALKING_QUEUE_SIZE = 50;

	public static final int MAX_SKILLS = 25;

	public final static int SEX = 0;
	public final static int HAIR_COLOUR = 1;
	public final static int BODY_COLOUR = 2;
	public final static int LEG_COLOUR = 3;
	public final static int FEET_COLOUR = 4;
	public final static int SKIN_COLOUR = 5;

	public final static int HEAD = 6;
	public final static int BODY = 7;
	public final static int ARMS = 8;
	public final static int HANDS = 9;
	public final static int LEGS = 10;
	public final static int FEET = 11;
	public final static int BEARD = 12;

	public static final int HELM = 0;
	public static final int CAPE = 1;
	public static final int AMULET = 2;
	public static final int WEAPON = 3;
	public static final int CHEST = 4;
	public static final int SHIELD = 5;
	public static final int BOTTOMS = 7;
	public static final int GLOVES = 9;
	public static final int BOOTS = 10;
	public static final int RING = 12;
	public static final int ARROWS = 13;

	public static final int ATTACK = 0;
	public static final int DEFENCE = 1;
	public static final int STRENGTH = 2;
	public static final int HITPOINTS = 3;
	public static final int RANGE = 4;
	public static final int PRAYER = 5;
	public static final int MAGIC = 6;
	public static final int COOKING = 7;
	public static final int WOODCUTTING = 8;
	public static final int FLETCHING = 9;
	public static final int FISHING = 10;
	public static final int FIREMAKING = 11;
	public static final int CRAFTING = 12;
	public static final int SMITHING = 13;
	public static final int MINING = 14;
	public static final int HERBLORE = 15;
	public static final int AGILITY = 16;
	public static final int THIEVING = 17;
	public static final int SLAYER = 18;
	public static final int FARMING = 19;
	public static final int RUNECRAFTING = 20;

	public static final int COMBAT_EXPERIENCE_MULTIPLIER = 20;
	public static final int SKILL_EXPERIENCE_MULTIPLIER = 5;

	public final static String[] SKILL_NAMES = { "Attack", "Defence",
			"Strength", "Hitpoints", "Range", "Prayer", "Magic", "Cooking",
			"Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting",
			"Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer",
			"Farming", "Runecrafting" };

	public static final int STAB_ATT = 0;
	public static final int SLASH_ATT = 1;
	public static final int CRUSH_ATT = 2;
	public static final int MAGIC_ATT = 3;
	public static final int RANGE_ATT = 4;

	public static final int STAB_DEF = 5;
	public static final int SLASH_DEF = 6;
	public static final int CRUSH_DEF = 7;
	public static final int MAGIC_DEF = 8;
	public static final int RANGE_DEF = 9;

	public static final int STR_BONUS = 10;
	public static final int PRAYER_BONUS = 11;

	public static final String BONUS_NAME[] = { "Stab", "Slash", "Crush",
			"Magic", "Range", "Stab", "Slash", "Crush", "Magic", "Range",
			"Strength", "Prayer" };

	public static final int POISON_HIT = 0;
	public static final int RECOIL_HIT = 1;
	public static final int COMBAT_HIT = 2;
	public static final int MAGIC_HIT = 3;

}