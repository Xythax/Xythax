package org.xythax.content.objects;

import org.xythax.utils.Constants;

/**
 * 
 * @author killamess
 * 
 */

public interface ObjectConstants {

	public static int objectArraySize = 6;

	public static final int

	OBJECT_ID = 0, OBJECT_SIZE = 1, OBJECT_X = 2, OBJECT_Y = 3,
			OBJECT_TASK = 4, ACTION_DELAY = 5;
	public static final int

	SHOP = 1, TALK = 2, BANK = 3, DOOR = 999, TREE = Constants.WOODCUTTING,
			FISH = Constants.FISHING, MINE = Constants.MINING,
			GEM_CRAFT = Constants.CRAFTING, ORE_SMELTING = Constants.SMITHING,
			MOULD_CRATE = 9535, JUG = 1935, DEPOSIT_BOX = 9398,
			TELE_KINETIC = 10778, TELE_GRAVEYARD = 10781,
			TELE_ENCHANTERS = 10779, TELE_VARROCK = 13622, CHAOS_ALTAR = 61,
			ALTAR = 409, ANCIENT_ALTAR = 6552, TELE_JAD = 10780,
			MAGIC_DOOR = 10721, MAGIC_STAIR_CASE_1_DOWN = 10773,
			MAGIC_STAIR_CASE_1_UP = 10771, MAGIC_STAIR_CASE_2_DOWN = 10776,
			MAGIC_STAIR_CASE_2_UP = 10775, FIGHT_PIT_WAITING_ROOM = 9369,
			FIGHT_PIT_WAITING_ROOM2 = 9368, VIEWING_ORB = 9391,
			FIRST_DUNGEON_EXIT = 6450, FIRST_DUNGEON_ENTRY = 10321,
			TEA_STALL = 635, STR_DOOR = 1531, HAY_STACK = 299,
			CHICKEN_DOOR1 = 2051, CHICKEN_DOOR2 = 2050, JAIL_EXIT = 6836,
			KARAMJA_ROPE_DOWN = 492, KARAMJA_ROPE_UP = 1764,
			TZ_HAAR_ENTRY = 9358, TZ_HAAR_EXIT = 9359, FLAX = 2646,
			STEAL_CAKE = 2561, STEAL_GEM = 2562, STEAL_SILVER = 2565,
			STEAL_SILK = 2560, STEAL_FUR = 2563, COOKING_FURNACE = 11112,
			LOG_FIRE = 11113, RUNE_CRAFT = 511192, NO_ORE = 450;;

	public static final int REMOVE = 0;

	public int[][] objectCommander = {
			{ 1530, DOOR, 1, 500 }, // door
			{ 1533, DOOR, 1, 500 },// door
			{ 1516, DOOR, 1, 500 },// door
			{ 1519, DOOR, 1, 500 },// door
			{ 1316, TREE, 3, 500 }, // evergreen big
			{ 1315, TREE, 2, 500 }, // evergreen
			{ 1276, TREE, 2, 500 }, // normal tree.
			{ 1278, TREE, 2, 500 }, // normal tree.
			{ 1281, TREE, 3, 500 }, // oak tree.
			{ 1308, TREE, 2, 500 }, // willow tree.
			{ 1307, TREE, 2, 500 }, // maple tree.
			{ 1309, TREE, 3, 500 }, // yew tree.
			{ 1306, TREE, 2, 500 }, // magic tree.
			{ 1383, TREE, 1, 500 }, // dead tree.
			{ 1286, TREE, 1, 500 }, // dead tree.
			{ 1282, TREE, 2, 500 }, // dead tree.
			{ 2213, BANK, 1, 500 }, // bank booth
			{ 3193, BANK, 1, 500 }, // bank booth
			{ 11758, BANK, 1, 500 }, // bank booth
			{ 2090, MINE, 1, 500 }, // copper ore
			{ 2091, MINE, 1, 500 }, // copper ore
			{ 2092, MINE, 1, 500 }, // iron ore
			{ 2093, MINE, 1, 500 }, // iron ore
			{ 2094, MINE, 1, 500 }, // tin ore
			{ 2095, MINE, 1, 500 }, // tin ore
			{ 2096, MINE, 1, 500 }, // coal
			{ 2097, MINE, 1, 500 }, // coal
			{ 2101, MINE, 1, 500 }, // sliver ore
			{ 2102, MINE, 1, 500 }, // mithril ore
			{ 2103, MINE, 1, 500 }, // mithril ore
			{ 2108, MINE, 1, 500 }, // clay
			{ 2109, MINE, 1, 500 }, // clay
			{ 11932, MINE, 1, 500 }, // coal
			{ 11931, MINE, 1, 500 }, // coal
			{ 11930, MINE, 1, 500 }, // coal
			{ 11933, MINE, 1, 500 }, // tin
			{ 11934, MINE, 1, 500 }, // tin
			{ 11935, MINE, 1, 500 }, // tin
			{ 11189, MINE, 1, 500 }, // clay
			{ 11190, MINE, 1, 500 }, // clay
			{ 11191, MINE, 1, 500 }, // clay
			{ 11186, MINE, 1, 500 }, // sliver ore
			{ 11187, MINE, 1, 500 }, // sliver ore
			{ 11188, MINE, 1, 500 }, // sliver ore
			{ 11183, MINE, 1, 500 }, // gold ore
			{ 11184, MINE, 1, 500 }, // gold ore
			{ 11185, MINE, 1, 500 }, // gold ore
			{ 11666, GEM_CRAFT, 2, 500 }, { 11666, ORE_SMELTING, 2, 500 },
			{ 2643, ORE_SMELTING, 2, 500 }, { 9534, MOULD_CRATE, 1, 500 },
			{ 873, JUG, 2, 500 }, { 874, JUG, 2, 500 }, { 879, JUG, 2, 500 },
			{ 880, JUG, 2, 500 }, { 4063, JUG, 2, 500 }, { 6151, JUG, 2, 500 },
			{ 8699, JUG, 2, 500 }, { 9143, JUG, 2, 500 },
			{ 9684, JUG, 2, 500 }, { 10175, JUG, 2, 500 },
			{ 13564, JUG, 2, 500 }, { 13563, JUG, 2, 500 },
			{ 12974, JUG, 2, 500 }, { 12279, JUG, 2, 500 },
			{ 9398, DEPOSIT_BOX, 1, 500 }, { 10778, TELE_KINETIC, 1, 500 },
			{ 10781, TELE_GRAVEYARD, 1, 500 },
			{ 10779, TELE_ENCHANTERS, 1, 500 },
			{ 13622, TELE_VARROCK, 2, 500 },
			{ 1531, STR_DOOR, 1, 500 }, { 299, HAY_STACK, 2, 500 },
			{ 61, CHAOS_ALTAR, 2, 500 }, { 409, ALTAR, 2, 500 },
			{ 6552, ANCIENT_ALTAR, 3, 500 }, { 10780, TELE_JAD, 1, 500 },
			{ 10721, MAGIC_DOOR, 1, 500 },
			{ 10773, MAGIC_STAIR_CASE_1_DOWN, 2, 500 },
			{ 10771, MAGIC_STAIR_CASE_1_UP, 2, 500 },
			{ 10776, MAGIC_STAIR_CASE_2_DOWN, 2, 500 },
			{ 10775, MAGIC_STAIR_CASE_2_UP, 4, 500 },
			{ 9369, FIGHT_PIT_WAITING_ROOM, 1, 500 },
			{ 9368, FIGHT_PIT_WAITING_ROOM2, 1, 500 },
			{ 9391, VIEWING_ORB, 1, 500 },
			{ 6450, FIRST_DUNGEON_EXIT, 1, 500 },
			{ FIRST_DUNGEON_ENTRY, FIRST_DUNGEON_ENTRY, 1, 500 },
			{ 635, TEA_STALL, 2, 500 }, { 2051, CHICKEN_DOOR1, 1, 500 },
			{ 2050, CHICKEN_DOOR2, 1, 500 }, { 6836, JAIL_EXIT, 1, 500 },
			{ 492, KARAMJA_ROPE_DOWN, 2, 500 },
			{ 1764, KARAMJA_ROPE_UP, 1, 500 },
			{ TZ_HAAR_ENTRY, TZ_HAAR_ENTRY, 2, 500 },
			{ TZ_HAAR_EXIT, TZ_HAAR_EXIT, 2, 500 }, { FLAX, FLAX, 1, 500 },
			{ STEAL_CAKE, STEAL_CAKE, 2, 500 },
			{ STEAL_GEM, STEAL_GEM, 2, 500 },
			{ STEAL_SILVER, STEAL_SILVER, 2, 500 },
			{ STEAL_SILK, STEAL_SILK, 2, 500 },
			{ STEAL_FUR, STEAL_FUR, 2, 500 },
			{ 12102, COOKING_FURNACE, 1, 500 },
			{ 12269, COOKING_FURNACE, 1, 500 },
			{ 8712, COOKING_FURNACE, 2, 500 },
			{ 9085, COOKING_FURNACE, 2, 500 },
			{ 9086, COOKING_FURNACE, 2, 500 },
			{ 9087, COOKING_FURNACE, 2, 500 },
			{ 2728, COOKING_FURNACE, 2, 500 },
			{ 2729, COOKING_FURNACE, 2, 500 },
			{ 2730, COOKING_FURNACE, 2, 500 },
			{ 2731, COOKING_FURNACE, 2, 500 },
			{ 2859, COOKING_FURNACE, 2, 500 },
			{ 3039, COOKING_FURNACE, 2, 500 },
			{ 5275, COOKING_FURNACE, 2, 500 },
			{ 114, COOKING_FURNACE, 2, 500 },
			{ 8750, COOKING_FURNACE, 2, 500 }, { 2732, LOG_FIRE, 1, 500 },
			{ 3038, LOG_FIRE, 1, 500 }, { 3769, LOG_FIRE, 1, 500 },
			{ 3775, LOG_FIRE, 1, 500 }, { 4265, LOG_FIRE, 1, 500 },
			{ 4266, LOG_FIRE, 1, 500 }, { 5499, LOG_FIRE, 1, 500 },
			{ 5249, LOG_FIRE, 1, 500 }, { 5631, LOG_FIRE, 1, 500 },
			{ 5632, LOG_FIRE, 1, 500 }, { 5981, LOG_FIRE, 1, 500 },
			{ 1530, DOOR, 1, 500 },
			{ 7139, RUNE_CRAFT, 2, 500 },// air
			{ 7137, RUNE_CRAFT, 2, 500 },// water
			{ 7130, RUNE_CRAFT, 2, 500 },// earth
			{ 7129, RUNE_CRAFT, 2, 500 },// fire
			{ 7140, RUNE_CRAFT, 2, 500 },// mind
			{ 7134, RUNE_CRAFT, 2, 500 },// chaos
			{ 7133, RUNE_CRAFT, 2, 500 },// nature
			{ 7135, RUNE_CRAFT, 2, 500 },// law
			{ 7136, RUNE_CRAFT, 2, 500 },// death
			{ 7141, RUNE_CRAFT, 2, 500 },// blood
			{ 7138, RUNE_CRAFT, 2, 500 },// soul
			{ 7131, RUNE_CRAFT, 2, 500 },// body
			{ 7132, RUNE_CRAFT, 2, 500 },// cosmic
			{ 450, NO_ORE, 1, 500 }, { 451, NO_ORE, 1, 500 },
			{ 452, NO_ORE, 1, 500 }, { 453, NO_ORE, 1, 500 },

	};
}
