package org.xythax.net.phandler.packets;

import org.xythax.content.EmotionTablet;
import org.xythax.content.ItemDestroy;
import org.xythax.content.actions.ActionManager;
import org.xythax.content.controllers.Location;
import org.xythax.content.skills.MenuTeleports;
import org.xythax.content.skills.SmithingSmelt;
import org.xythax.content.skills.Tanning;
import org.xythax.model.Client;
import org.xythax.model.combat.content.CombatMode;
import org.xythax.model.combat.content.Specials;
import org.xythax.model.combat.magic.AutoCast;
import org.xythax.model.combat.magic.TeleOther;
import org.xythax.net.phandler.Packet;
import org.xythax.utils.Utils;
import org.xythax.world.PlayerManager;

public class ActionButtons implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {

		int actionButtonId = Utils.HexToInt(client.inStream.buffer, 0,
				packetSize);

		if (client.isDead())
			return;

		switch (actionButtonId) {
		case 49022: // teleother accept;
			client.getActionSender().sendWindowsRemoval();
			TeleOther.acceptTeleport(client, (Client) PlayerManager
					.getPlayerManager().getPlayerByName(client.teleOther),
					client.teleToSpell);
			break;

		case 49024: // teleOther decline
			client.getActionSender().sendWindowsRemoval();
			break;

		case 15147:// Smelt Bronze 1x
			if (client.getActionAssistant().playerHasItem(436, 1)
					&& client.getActionAssistant().playerHasItem(438, 1)) {
				new SmithingSmelt(client, 1, 436);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 15146:// Smelt Bronze 5x
			if (client.getActionAssistant().playerHasItem(436, 1)
					&& client.getActionAssistant().playerHasItem(438, 1)) {
				new SmithingSmelt(client, 5, 436);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 10247:// Smelt Bronze 10x
			if (client.getActionAssistant().playerHasItem(436, 1)
					&& client.getActionAssistant().playerHasItem(438, 1)) {
				new SmithingSmelt(client, 10, 436);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 9110:// Smelt Bronze x
			if (client.getActionAssistant().playerHasItem(436, 1)
					&& client.getActionAssistant().playerHasItem(438, 1)) {
				new SmithingSmelt(client, 20, 436);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 15151: // Iron
			if (client.getActionAssistant().playerHasItem(440, 1)) {
				new SmithingSmelt(client, 1, 440);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 15150:
			if (client.getActionAssistant().playerHasItem(440, 1)) {
				new SmithingSmelt(client, 5, 440);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 15149:
			if (client.getActionAssistant().playerHasItem(440, 1)) {
				new SmithingSmelt(client, 10, 440);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 15148:
			if (client.getActionAssistant().playerHasItem(440, 1)) {
				new SmithingSmelt(client, 20, 440);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 15155: // Silver
			if (client.getActionAssistant().playerHasItem(442, 1)) {
				new SmithingSmelt(client, 1, 442);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 15154:
			if (client.getActionAssistant().playerHasItem(442, 1)) {
				new SmithingSmelt(client, 5, 442);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 15153:
			if (client.getActionAssistant().playerHasItem(442, 1)) {
				new SmithingSmelt(client, 10, 442);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 15152:
			if (client.getActionAssistant().playerHasItem(442, 1)) {
				new SmithingSmelt(client, 20, 442);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 15163: // Gold
			if (client.getActionAssistant().playerHasItem(444, 1)) {
				new SmithingSmelt(client, 1, 444);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 15162:
			if (client.getActionAssistant().playerHasItem(444, 1)) {
				new SmithingSmelt(client, 5, 444);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 15161:
			if (client.getActionAssistant().playerHasItem(444, 1)) {
				new SmithingSmelt(client, 10, 444);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 15160:
			if (client.getActionAssistant().playerHasItem(444, 1)) {
				new SmithingSmelt(client, 20, 444);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 15159: // Steel
			if (client.getActionAssistant().playerHasItem(440, 1)
					&& client.getActionAssistant().playerHasItem(453, 1)) {
				new SmithingSmelt(client, 1, 443);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 15158:
			if (client.getActionAssistant().playerHasItem(440, 1)
					&& client.getActionAssistant().playerHasItem(453, 1)) {
				new SmithingSmelt(client, 5, 443);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 15157:
			if (client.getActionAssistant().playerHasItem(440, 1)
					&& client.getActionAssistant().playerHasItem(453, 1)) {
				new SmithingSmelt(client, 10, 443);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 15156:
			if (client.getActionAssistant().playerHasItem(440, 1)
					&& client.getActionAssistant().playerHasItem(453, 1)) {
				new SmithingSmelt(client, 20, 443);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 29017: // Mithril
			if (client.getActionAssistant().playerHasItem(447, 1)
					&& client.getActionAssistant().playerHasItem(453, 1)) {
				new SmithingSmelt(client, 1, 447);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 29016:
			if (client.getActionAssistant().playerHasItem(447, 1)
					&& client.getActionAssistant().playerHasItem(453, 1)) {
				new SmithingSmelt(client, 5, 447);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 24253:
			if (client.getActionAssistant().playerHasItem(447, 1)
					&& client.getActionAssistant().playerHasItem(453, 1)) {
				new SmithingSmelt(client, 10, 447);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 16062:
			if (client.getActionAssistant().playerHasItem(447, 1)
					&& client.getActionAssistant().playerHasItem(453, 1)) {
				new SmithingSmelt(client, 20, 447);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 29022: // Addy
			if (client.getActionAssistant().playerHasItem(449, 1)
					&& client.getActionAssistant().playerHasItem(453, 1)) {
				new SmithingSmelt(client, 1, 449);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 29020:
			if (client.getActionAssistant().playerHasItem(449, 1)
					&& client.getActionAssistant().playerHasItem(453, 1)) {
				new SmithingSmelt(client, 5, 449);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 29019:
			if (client.getActionAssistant().playerHasItem(449, 1)
					&& client.getActionAssistant().playerHasItem(453, 1)) {
				new SmithingSmelt(client, 10, 449);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 29018:
			if (client.getActionAssistant().playerHasItem(449, 1)
					&& client.getActionAssistant().playerHasItem(453, 1)) {
				new SmithingSmelt(client, 20, 449);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 29026: // Rune
			if (client.getActionAssistant().playerHasItem(451, 1)
					&& client.getActionAssistant().playerHasItem(453, 1)) {
				new SmithingSmelt(client, 1, 451);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 29025:
			if (client.getActionAssistant().playerHasItem(451, 1)
					&& client.getActionAssistant().playerHasItem(453, 1)) {
				new SmithingSmelt(client, 5, 451);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 29024:
			if (client.getActionAssistant().playerHasItem(451, 1)
					&& client.getActionAssistant().playerHasItem(453, 1)) {
				new SmithingSmelt(client, 10, 451);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 29023:
			if (client.getActionAssistant().playerHasItem(451, 1)
					&& client.getActionAssistant().playerHasItem(453, 1)) {
				new SmithingSmelt(client, 20, 451);
			} else {
				client.getActionSender().sendMessage(
						"You don't have enough ores.");
				client.getActionSender().sendWindowsRemoval();
			}
			break;
		case 55095:
			ItemDestroy.destroyItem(client);
		case 55096:
			client.getActionSender().sendWindowsRemoval();
			break;
		case 53152: // cook 1
			client.cookingAmount = 1;
			ActionManager.addNewRequest(client, 14, 4);
			break;
		case 53151: // cook 5
			client.cookingAmount = 5;
			ActionManager.addNewRequest(client, 14, 4);
			break;
		case 53150: // cook x
			client.cookingAmount = 28;
			ActionManager.addNewRequest(client, 14, 4);
			break;
		case 53149: // cook all
			client.cookingAmount = 28;
			ActionManager.addNewRequest(client, 14, 4);
			break;

		/**
		 * Attack mode selections.
		 */
		case 1080:
		case 1177:
		case 6168:
		case 8234:
		case 9125:
		case 14218:
		case 18077:
		case 21200:
		case 33020:
		case 48010:
		case 22230:
			client.combatMode = CombatMode.setCombatMode(client, 0);
			break;

		/**
		 * Defence mode selections.
		 */
		case 1078:
		case 6169:
		case 8235:
		case 9126:
		case 14219:
		case 21201:
		case 33018:
		case 48008:
		case 18078:
		case 1175:
		case 22228:
			client.combatMode = CombatMode.setCombatMode(client, 1);
			break;

		/**
		 * Strength mode selections.
		 */
		case 8236:
		case 1079:
		case 1176:
		case 6170:
		case 6171:
		case 8237:
		case 9128:
		case 14221:
		case 21203:
		case 22229:
			client.combatMode = CombatMode.setCombatMode(client, 2);
			break;

		/**
		 * controlled mode.
		 */
		case 9127:
		case 14220:
		case 21202:
		case 33019:
		case 48009:
		case 18079:
		case 18080:
			client.combatMode = CombatMode.setCombatMode(client, 3);
			break;

		/**
		 * Accurate ranged.
		 */
		case 6236:
		case 17102:
			client.combatMode = CombatMode.setCombatMode(client, 4);
			break;

		/**
		 * Rapid ranged.
		 */
		case 6235:
		case 17101:
			client.combatMode = CombatMode.setCombatMode(client, 5);
			break;

		/**
		 * longranged.
		 */
		case 6234:
		case 17100:
			client.combatMode = CombatMode.setCombatMode(client, 6);
			break;

		case 150:
			client.autoRetaliate = !client.autoRetaliate;

			break;

		/**
		 * Prayer
		 */
		case 97168:
		case 21233: // Thick Skin
			client.getPrayerHandler().activatePrayer(0);
			break;
		case 97170:
		case 21234: // Burst of Strength
			client.getPrayerHandler().activatePrayer(1);
			break;
		case 97172:
		case 21235: // Clarity of though
			client.getPrayerHandler().activatePrayer(2);
			break;
		case 97178:
		case 21236: // Rock skin
			client.getPrayerHandler().activatePrayer(3);
			break;
		case 97180:
		case 21237: // Superhuman Strength
			client.getPrayerHandler().activatePrayer(4);
			break;
		case 97182:
		case 21238: // Improved reflexes
			client.getPrayerHandler().activatePrayer(5);
			break;
		case 97184:
		case 21239: // Rapid Restore
			client.getPrayerHandler().activatePrayer(6);
			break;
		case 97186:
		case 21240: // Rapid Heal
			client.getPrayerHandler().activatePrayer(7);
			break;
		case 97188:
		case 21241: // Protect Item
			client.getPrayerHandler().activatePrayer(8);
			break;
		case 97194:
		case 21242: // Steel Skin
			client.getPrayerHandler().activatePrayer(9);
			break;
		case 97196:
		case 21243: // Ultimate Strength
			client.getPrayerHandler().activatePrayer(10);
			break;
		case 97198:
		case 21244: // Incredible Reflexes
			client.getPrayerHandler().activatePrayer(11);
			break;
		case 97200:
		case 21245: // Protect from Magic
			client.getPrayerHandler().activatePrayer(12);
			break;
		case 97202:
		case 21246: // Protect from Missiles
			client.getPrayerHandler().activatePrayer(13);
			break;
		case 97204:
		case 21247: // Protect from Melee
			client.getPrayerHandler().activatePrayer(14);
			break;
		case 97210:
		case 2171: // Retribution
			client.getPrayerHandler().activatePrayer(15);
			break;
		case 97212:
		case 2172: // Redemption
			client.getPrayerHandler().activatePrayer(16);
			break;
		case 97214:
		case 2173: // Smite
			client.getPrayerHandler().activatePrayer(17);
			break;
		case 77113:
		case 70092:// chivalry
			client.getPrayerHandler().activatePrayer(18);
			break;
		case 77115:
		case 70094:// piety
			client.getPrayerHandler().activatePrayer(19);
			break;
		case 77100: // sharp eye
			client.getPrayerHandler().activatePrayer(20);
			break;
		case 77104: // hawk eye
			client.getPrayerHandler().activatePrayer(21);
			break;
		case 77109: // Eagle eye
			client.getPrayerHandler().activatePrayer(22);
			break;
		case 77102: // Mystic will
			client.getPrayerHandler().activatePrayer(23);
			break;
		case 77106: // Mystic lore
			client.getPrayerHandler().activatePrayer(24);
			break;
		case 77111: // Mystic might
			client.getPrayerHandler().activatePrayer(25);
			break;
		/**
		 * AutoCasting selections
		 */
		case 7038: // wind strike
			AutoCast.newSpell(client, 1152);
			break;
		case 7039: // water strike
			AutoCast.newSpell(client, 1154);
			break;
		case 7040: // earth strike
			AutoCast.newSpell(client, 1156);
			break;
		case 7041: // fire strike
			AutoCast.newSpell(client, 1158);
			break;
		case 7042: // wind bolt
			AutoCast.newSpell(client, 1160);
			break;
		case 7043: // water bolt
			AutoCast.newSpell(client, 1163);
			break;
		case 7044: // earth bolt
			AutoCast.newSpell(client, 1166);
			break;
		case 7045: // fire bolt
			AutoCast.newSpell(client, 1169);
			break;
		case 7046: // wind blast
			AutoCast.newSpell(client, 1172);
			break;
		case 7047: // water blast
			AutoCast.newSpell(client, 1175);
			break;
		case 7048: // earth blast
			AutoCast.newSpell(client, 1177);
			break;
		case 7049: // fire blast
			AutoCast.newSpell(client, 1181);
			break;
		case 7050: // wind wave
			AutoCast.newSpell(client, 1183);
			break;
		case 7051: // water wave
			AutoCast.newSpell(client, 1185);
			break;
		case 7052: // earth blast
			AutoCast.newSpell(client, 1188);
			break;
		case 7053: // fire blast
			AutoCast.newSpell(client, 1189);
			break;
		case 51133: // smoke rush
			AutoCast.newSpell(client, 12939);
			break;
		case 51185: // shadow rush
			AutoCast.newSpell(client, 12987);
			break;
		case 51091: // blood rush
			AutoCast.newSpell(client, 12901);
			break;
		case 24018: // ice rush
			AutoCast.newSpell(client, 12861);
			break;
		case 51159: // smoke burst
			AutoCast.newSpell(client, 12963);
			break;
		case 51211: // shadow burst
			AutoCast.newSpell(client, 13011);
			break;
		case 51111: // blood burst
			AutoCast.newSpell(client, 12919);
			break;
		case 51069: // ice burst
			AutoCast.newSpell(client, 12881);
			break;
		case 51146: // smoke blitz
			AutoCast.newSpell(client, 12951);
			break;
		case 51198: // shadow blitz
			AutoCast.newSpell(client, 12999);
			break;
		case 51102: // blood blitz
			AutoCast.newSpell(client, 12911);
			break;
		case 51058: // ice blitz
			AutoCast.newSpell(client, 12871);
			break;
		case 51172: // smoke barrage
			AutoCast.newSpell(client, 12975);
			break;
		case 51224: // shadow barrage
			AutoCast.newSpell(client, 13023);
			break;
		case 51122: // blood barrage
			AutoCast.newSpell(client, 12929);
			break;
		case 51080: // ice barrage
			AutoCast.newSpell(client, 12891);
			break;

		/**
		 * AutoCasting toggle button.
		 */
		case 1093:
			client.usingMagicDefence = !client.usingMagicDefence;
			break;

		case 1094:
		case 1097:
			if (!AutoCast.checkForCorrectStaff(client)) {
				AutoCast.turnOff(client);
				break;
			}
			if (client.getSpellBook() == 2)
				client.getActionSender().sendSidebar(0, 1689);
			else if (client.getSpellBook() == 1)
				client.getActionSender().sendSidebar(0, 1829);
			break;

		/**
		 * AutoCast cancel spell selection.
		 */
		case 7212:
		case 24017:
			client.setAutoCastId(0);
			client.getActionSender().sendSidebar(0, 328);
			AutoCast.turnOff(client);
			break;

		/**
		 * Special attack bars.
		 */
		case 30108:
		case 33033:
		case 29038:
		case 29063:
		case 29188:
		case 29138:
		case 48023:
		case 29238:
		case 29113:
		case 29163:
			Specials.activateSpecial(client);
			break;

		/**
		 * trading
		 */
		case 13092:
			client.getTradeHandler().acceptInitialTradeWindow();
			break;

		case 13218:
			client.getTradeHandler().acceptConfirmationTradeWindow();
			break;

		case 21011:
			client.takeAsNote = false;
			break;

		case 21010:
			client.takeAsNote = true;
			break;

		/**
		 * Teleports
		 */
		case 75010: // home tele
			Location.teleport(client, 0, "magicbook");
			break;
		case 4140: // varrock teleport
			Location.teleport(client, 1, "magicbook");
			break;
		case 4143: // lumbridge teleport
			Location.teleport(client, 2, "magicbook");
			break;
		case 4146: // Falador
			Location.teleport(client, 3, "magicbook");
			break;
		case 4150: // city teleports
			// MenuTeleports.createTeleportMenu(client, 2);
			break;
		case 6004: // skill teleports
			// MenuTeleports.createTeleportMenu(client, 3);
			break;
		case 6005: // wilderness teleports
			// MenuTeleports.createTeleportMenu(client, 0);
			break;
		case 29031: // training Teleports
			// MenuTeleports.createTeleportMenu(client, 1);
			break;
		case 50235: // paddewwa teleport
			// MenuTeleports.createTeleportMenu(client, 0);//pking
			break;
		case 50245: // senntisten teleport
			// MenuTeleports.createTeleportMenu(client, 3);//skilling
			break;
		case 50253: // kharyrll teleport
			// MenuTeleports.createTeleportMenu(client, 1);//training
			break;
		case 51005: // lassar teleport
			// Location.to(client, 11, "magicbook");
			break;
		case 51013: // dareeyak teleport
			// Location.to(client, 12, "magicbook");
			break;
		case 51023: // carrallangar teleport
			// MenuTeleports.createTeleportMenu(client, 2);//city
			break;
		case 51031: // duel arena
			// Location.to(client, 14, "magicbook");
			break;
		case 51039: // home
			Location.teleport(client, 0, "magicbook");
			break;

		/**
		 * Run/Walk button toggling.
		 */
		case 152:
		case 74214:
			client.setRunning(!client.isRunning);
			client.getActionSender().sendConfig(429, client.isRunning ? 1 : 0);
			break;

		/**
		 * Logout button.
		 */
		case 9154:
			client.getActionSender().sendLogout();
			break;

		/**
		 * Close window button.
		 */
		case 130:
			client.getActionSender().sendWindowsRemoval();
			break;

		case 58253:
			client.getActionSender().sendInterface(15106);
			break;

		/**
		 * Cow hide to leather tanning
		 */
		case 57225:
			Tanning.tanHide(client, 1739, 1741, 1);
			break;
		case 57217:
			Tanning.tanHide(client, 1739, 1741, 5);
			break;
		case 57209:
			break;
		case 57201:
			Tanning.tanHide(client, 1739, 1741, 28);
			break;

		/**
		 * cow hide to hard leather tanning
		 */
		case 57229:
			Tanning.tanHide(client, 1739, 1743, 1);
			break;
		case 57221:
			Tanning.tanHide(client, 1739, 1743, 5);
			break;
		case 57213:
			break;
		case 57205:
			Tanning.tanHide(client, 1739, 1743, 28);
			break;

		/**
		 * All of our emotions
		 */
		case 168:
		case 169:
		case 164:
		case 165:
		case 162:
		case 163:
		case 52058:
		case 171:
		case 167:
		case 170:
		case 52054:
		case 52056:
		case 166:
		case 52051:
		case 52052:
		case 52053:
		case 161:
		case 43092:
		case 52050:
		case 52055:
		case 172:
		case 52057:
		case 52071:
		case 52072:
		case 2155:
		case 25103:
		case 25106:
		case 2154:
		case 72032:
		case 72033:
		case 59062:
		case 72254:
		case 74108:
			EmotionTablet.doEmote(client, actionButtonId);
			break;
		case 9190:
		case 9191:
		case 9192:
		case 9193:
		case 9194:
			if (client.teleportConfig >= 0) {
				MenuTeleports.runTeleport(client, actionButtonId);
				break;
			}
			client.getActionSender().sendWindowsRemoval();
			break;

		case 9168:
		case 9169:
			client.getActionSender().sendWindowsRemoval();
			break;

		default:
			client.println_debug("Unhandled Action Button: " + actionButtonId);
			break;
		}
	}
}