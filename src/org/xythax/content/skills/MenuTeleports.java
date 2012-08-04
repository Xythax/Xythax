package org.xythax.content.skills;

import org.xythax.content.JailSystem;
import org.xythax.content.controllers.Animation;
import org.xythax.content.controllers.Graphics;
import org.xythax.content.controllers.Location;
import org.xythax.model.Client;

/**
 * 
 * @author Killamess Used for quick teleports.
 */
public class MenuTeleports {

	public static void createTeleportMenu(Client client, int config) {
		if (client.isBusy())
			return;
		if (JailSystem.inJail(client)) {
			client.getActionSender().sendMessage(
					"You cannot teleport out of jail!");
			return;
		}
		for (int i = 0; i < client.teleport.length; i++)
			client.teleport[i] = (i == config);
		client.teleportConfig = config;
		client.getActionSender().selectOption(places[config][0],
				places[config][1], places[config][2], places[config][3],
				places[config][4], places[config][5]);
	}

	public static void runTeleport(Client client, int frame) {
		if (frame == 9194) {
			switch (client.teleportConfig) {
			case 1:
				createTeleportMenu(client, 5);
				break;
			case 5:
				createTeleportMenu(client, 1);
				break;
			default:
				client.getActionSender().sendWindowsRemoval();
				break;
			}
			return;
		}
		for (int[] teleports : teleportMenuLocation) {
			if (teleports[0] == frame && teleports[1] == client.teleportConfig) {
				client.resetFaceDirection();
				client.resetWalkingQueue();
				client.setBusyTimer(5);
				Animation.addNewRequest(client, 714, 0);
				Graphics.addNewRequest(client, 308, 100, 3);
				Location.addNewRequest(client, teleports[2], teleports[3], 0, 4);
				Animation.addNewRequest(client, 715, 5);
				resetTeleport(client);
				break;
			}
		}
	}

	public static void resetTeleport(Client client) {

		client.teleportConfig = -1;

		for (int i = 0; i < client.teleport.length; i++)
			client.teleport[i] = false;
	}

	public static final String[][] places = {
			/** 9190 **/
			/** 9191 **/
			/** 9192 **/
			/** 9193 **/
			/** 9194 **/
			// gl
			{ "Pking Areas", "Lumbridge", "Varrock", "Edgeville",
					"Green Dragons", "No where" },
			/** 0 **/
			{ "Training Area A", "Barbarians", "Goblins", "Rock Crabs",
					"Wizards", "Next" },
			/** 1 **/
			{ "City Areas", "Varrock", "Falador", "Camelot", "Edgeville",
					"No where" },
			/** 2 **/
			{ "Skilling Areas", "Rune-Crafting", "Crafting", "Woodcuting",
					"Thieving", "No where" },
			/** 3 **/
			{ "Sail to?", "Port Sarim", "Port Karamja", "Port Khazard",
					"Home Port", "No where" },
			/** 4 **/
			{ "Training Area B", "Lesser Demons", "Moss Giants", "TzHaar",
					"Green Dragons", "Back" }, /** 5 **/
	};

	public static final int[][] teleportMenuLocation = {
			{ 9190, 0, 3246, 3198 }, { 9190, 1, 3078, 3419 },
			{ 9190, 2, 3209, 3425 }, { 9190, 3, 3039, 4834 },
			{ 9190, 4, 3029, 3217 }, { 9190, 5, 2838, 9581 },

			{ 9191, 0, 3238, 3514 }, { 9191, 1, 3293, 3420 },
			{ 9191, 2, 2964, 3378 }, { 9191, 3, 2933, 3288 },
			{ 9191, 4, 2907, 3191 }, { 9191, 5, 3283, 3466 },

			{ 9192, 0, 3088, 3502 }, { 9192, 1, 2629, 5068 },
			{ 9192, 2, 2757, 3478 }, { 9192, 3, 2731, 3485 },
			{ 9192, 4, 2676, 3170 }, { 9192, 5, 2480, 5173 },

			{ 9193, 0, 2981, 3595 }, { 9193, 1, 3225, 3369 },
			{ 9193, 2, 3088, 3502 }, { 9193, 3, 2662, 3305 },
			{ 9193, 4, 2804, 3421 }, { 9193, 5, 2981, 3595 },

			/**
			 * These normally are the No Where options aswel as opening
			 * different menus
			 **/

			{ 9194, 0, 0, 0 }, { 9194, 1, 1, 5 }, { 9194, 2, 0, 0 },
			{ 9194, 3, 0, 0 }, { 9194, 4, 0, 0 }, { 9194, 5, 5, 1 } };

}
