package org.xythax.content;

import org.xythax.content.controllers.Animation;
import org.xythax.content.controllers.Graphics;
import org.xythax.model.Client;
import org.xythax.model.combat.CombatEngine;
import org.xythax.utils.Constants;

/**
 * 
 * @author Killamess
 * 
 */
public class EmotionTablet {
	public static void doEmote(Client client, int actionButton) {
		if (client == null || client.isBusy())
			return;
		CombatEngine.resetAttack(client, true);
		int cape = client.playerEquipment[Constants.CAPE];

		if (actionButton == 74108) {
			if (!skillCapeEquipped(client)) {
				client.getActionSender()
						.sendMessage(
								"You need to be wearing a skillcape to perform this emotion.");
				return;
			}

			for (int search = 0; search < skillCape.length; search++) {
				if (cape == skillCape[search][0]
						|| cape == skillCape[search][1]) {
					Graphics.addNewRequest(client, skillCape[search][2], 0, 2);
					Animation.addNewRequest(client, skillCape[search][3], 2);
					client.setBusyTimer(skillCape[search][4]);
					return;
				}
			}
		}
		for (int i = 0; i < emotions.length; i++) {
			if (emotions[i][0] == actionButton) {
				Animation.addNewRequest(client, emotions[i][1], 1);
				break;
			}
		}
	}

	// still need to add delays to these emotions
	public static final int[][] emotions = { { 168, 855 }, { 169, 856 },
			{ 164, 858 }, { 165, 859 }, { 162, 857 }, { 163, 863 },
			{ 52058, 2113 }, { 171, 862 }, { 167, 864 }, { 170, 861 },
			{ 52054, 2109 }, { 52056, 2111 }, { 166, 866 }, { 52051, 2106 },
			{ 52052, 2107 }, { 52053, 2108 }, { 161, 860 }, { 43092, 1368 },
			{ 52050, 2105 }, { 52055, 2110 }, { 172, 865 }, { 52057, 2112 },
			{ 52071, 2127 }, { 52072, 2128 }, { 2155, 1131 }, { 25103, 1130 },
			{ 25106, 1129 }, { 2154, 1128 }, { 72032, 3544 }, { 72033, 3543 },
			{ 59062, 2836 }, { 72254, 3866 }, };

	public static final int[][] skillCape = { { 9747, 9748, 823, 4959, 8 },
			{ 9753, 9754, 824, 4961, 13 }, { 9750, 9751, 828, 4981, 21 },
			{ 9768, 9769, 833, 4971, 9 }, { 9756, 9757, 832, 4973, 12 },
			{ 9759, 9760, 829, 4979, 13 }, { 9762, 9763, 813, 4939, 8 },
			{ 9801, 9802, 821, 4955, 30 }, { 9807, 9808, 822, 4957, 25 },
			{ 9783, 9784, 812, 4937, 16 }, { 9798, 9799, 819, 4951, 16 },
			{ 9804, 9805, 831, 4975, 12 }, { 9780, 9781, 818, 4949, 18 },
			{ 9795, 9796, 815, 4943, 24 }, { 9792, 9793, 814, 4941, 11 },
			{ 9774, 9775, 835, 4969, 19 }, { 9771, 9772, 830, 4977, 9 },
			{ 9777, 9778, 826, 4965, 8 }, { 9786, 9787, 827, 4967, 7 },
			{ 9810, 9811, 825, 4963, 16 }, { 9765, 9766, 817, 4947, 14 },
			{ 9789, 9790, 820, 4953, 15 }, { 9948, 9949, 907, 5158, 15 },
			{ 9813, 9813, 816, 4945, 18 }, };

	public static boolean skillCapeEquipped(Client client) {
		int cape = client.playerEquipment[Constants.CAPE];
		for (int search = 0; search < skillCape.length; search++) {
			if (cape == skillCape[search][0] || cape == skillCape[search][1])
				return true;
		}
		return false;
	}
}