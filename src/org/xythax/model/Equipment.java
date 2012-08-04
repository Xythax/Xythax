package org.xythax.model;

import org.xythax.core.GameEngine;
import org.xythax.model.combat.CombatEngine;
import org.xythax.model.combat.content.CombatMode;
import org.xythax.model.combat.content.Specials;
import org.xythax.model.combat.magic.AutoCast;
import org.xythax.utils.Constants;

public class Equipment {

	private Client client;

	public Equipment(Client client) {
		this.client = client;
	}

	private void sendSpecialBar() {
		int id = client.playerEquipment[Constants.WEAPON];
		switch (id) {

		case 4151: // Whip
			client.getActionSender().sendFrame171(12323, 0);
			break;

		case 3204: // Dragon Hally
			client.getActionSender().sendFrame171(8493, 0);
			break;

		case 861: // Magic Bow
		case 6724: // Seercull
			client.getActionSender().sendFrame171(7549, 0);
			break;

		case 7158: // Dragon 2h
		case 1305: // Dragon Long
		case 4587: // Dragon Scimmy
		case 11694:// ags
		case 11696: // bgs
		case 11698: // sgs
		case 11700: // zgs
		case 11730: // ss
			client.getActionSender().sendFrame171(7599, 0);
			break;

		case 6739: // Dragon Axe
		case 1377: // Dragon Battle
			client.getActionSender().sendFrame171(7499, 0);
			break;

		case 5680:// d dagger (p+)
		case 1231: // d dagger (p)
		case 1215: // d dagger
		case 5698: // Dragon Dagger
			client.getActionSender().sendFrame171(7574, 0);
			break;

		case 1434: // Dragon Mace
			client.getActionSender().sendFrame171(7624, 0);
			break;

		case 4153: // Granite Maul
			client.getActionSender().sendFrame171(7474, 0);
			break;

		case 1249: // Dragon Spear
			client.getActionSender().sendFrame171(7674, 0);
			break;

		default:
			client.getActionSender().sendFrame171(12323, 1);
			client.getActionSender().sendFrame171(8493, 1);
			client.getActionSender().sendFrame171(7549, 1);
			client.getActionSender().sendFrame171(7599, 1);
			client.getActionSender().sendFrame171(7499, 1);
			client.getActionSender().sendFrame171(7574, 1);
			client.getActionSender().sendFrame171(7624, 1);
			client.getActionSender().sendFrame171(7474, 1);
			client.getActionSender().sendFrame171(7674, 1);
			break;
		}
	}

	public void sendWeapon() {
		int weapon = client.playerEquipment[Constants.WEAPON];
		String s = GameEngine.getItemManager().getItemDefinition(weapon)
				.getName();
		if (s.equals("Unarmed")) {
			client.getActionSender().sendSidebar(0, 5855);
			client.getActionSender().sendQuest(s, 5857);
		} else if (s.endsWith("whip")) {
			client.getActionSender().sendSidebar(0, 12290);
			client.getActionSender().sendFrame246(12291, 200, weapon);
			client.getActionSender().sendQuest(s, 12293);
		} else if (s.endsWith("Scythe")) {
			client.getActionSender().sendSidebar(0, 776);
			client.getActionSender().sendFrame246(12291, 200, weapon);
			client.getActionSender().sendQuest(s, 778);
		} else if (s.endsWith("bow") || s.startsWith("Crystal bow")
				|| s.startsWith("Seercull")) {
			client.getActionSender().sendSidebar(0, 1764);
			client.getActionSender().sendFrame246(1765, 200, weapon);
			client.getActionSender().sendQuest(s, 1767);
		} else if (s.startsWith("Staff") || s.endsWith("staff")
				|| s.endsWith("wand")) {
			client.getActionSender().sendSidebar(0, 328);
			client.getActionSender().sendFrame246(329, 200, weapon);
			client.getActionSender().sendQuest(s, 331);
		} else if (s.endsWith("dart") || s.endsWith("knife")
				|| s.endsWith("javelin") || s.contains("thrownaxe")) {
			client.getActionSender().sendSidebar(0, 4446);
			client.getActionSender().sendFrame246(4447, 200, weapon);
			client.getActionSender().sendQuest(s, 4449);
		} else if (s.contains("dagger")) {
			client.getActionSender().sendSidebar(0, 2276);
			client.getActionSender().sendFrame246(2277, 200, weapon);
			client.getActionSender().sendQuest(s, 2279);
		} else if (s.endsWith("pickaxe")) {
			client.getActionSender().sendSidebar(0, 5570);
			client.getActionSender().sendFrame246(5571, 200, weapon);
			client.getActionSender().sendQuest(s, 5573);
		} else if (s.endsWith("axe") || s.endsWith("battleaxe")) {
			client.getActionSender().sendSidebar(0, 1698);
			client.getActionSender().sendFrame246(1699, 200, weapon);
			client.getActionSender().sendQuest(s, 1701);
		} else if (s.endsWith("halberd")) {
			client.getActionSender().sendSidebar(0, 8460);
			client.getActionSender().sendFrame246(8461, 200, weapon);
			client.getActionSender().sendQuest(s, 8463);
		} else if (s.contains("spear")) {
			client.getActionSender().sendSidebar(0, 4679);
			client.getActionSender().sendFrame246(4680, 200, weapon);
			client.getActionSender().sendQuest(s, 4682);
		} else if (s.contains("maul")) {
			client.getActionSender().sendSidebar(0, 425);
			client.getActionSender().sendFrame246(426, 200, weapon);
			client.getActionSender().sendQuest(s, 428);
		} else if (s.endsWith("mace")) {
			client.getActionSender().sendSidebar(0, 3796);
			client.getActionSender().sendFrame246(3797, 200, weapon);
			client.getActionSender().sendQuest(s, 3799);
		} else if (s.endsWith("claws")) {
			client.getActionSender().sendSidebar(0, 7762);
			client.getActionSender().sendFrame246(7763, 200, weapon);
			client.getActionSender().sendQuest(s, 7764);
		} else if (s.endsWith("spear")) {
			client.getActionSender().sendSidebar(0, 7762);
			client.getActionSender().sendFrame246(7763, 200, weapon);
			client.getActionSender().sendQuest(s, 7764);
		} else {
			client.getActionSender().sendSidebar(0, 2423);
			client.getActionSender().sendFrame246(2424, 200, weapon);
			client.getActionSender().sendQuest(s, 2426);
		}
		sendSpecialBar();

		setWeaponEmotes();

		client.getBonuses().calculateBonus();
	}

	public void setWeaponEmotes() {
		client.standEmote = getStandEmote(client);
		client.walkEmote = getWalkEmote(client);
		client.runEmote = getRunEmote(client);
	}

	public void deleteRing() {
		setEquipment(-1, 0, Constants.RING);
	}

	public void setEquipment(int wearID, int amount, int targetSlot) {
		client.outStream.createFrameVarSizeWord(34);
		client.outStream.writeWord(1688);
		client.outStream.writeByte(targetSlot);
		client.outStream.writeWord(wearID + 1);
		if (amount > 254) {
			client.outStream.writeByte(255);
			client.outStream.writeDWord(amount);
		} else {
			client.outStream.writeByte(amount); // amount
		}
		client.outStream.endFrameVarSizeWord();
		client.flushOutStream();

		client.playerEquipment[targetSlot] = wearID;
		client.playerEquipmentN[targetSlot] = amount;
		client.updateRequired = true;
		client.appearanceUpdateRequired = true;
	}

	public int itemType(int item) {
		for (int cape : Item.capes) {
			if (item == cape) {
				return Constants.CAPE;
			}
		}
		for (int hat : Item.hats) {
			if (item == hat) {
				return Constants.HELM;
			}
		}
		for (int boot : Item.boots) {
			if (item == boot) {
				return Constants.BOOTS;
			}
		}
		for (int glove : Item.gloves) {
			if (item == glove) {
				return Constants.GLOVES;
			}
		}
		for (int shield : Item.shields) {
			if (item == shield) {
				return Constants.SHIELD;
			}
		}
		for (int amulet : Item.amulets) {
			if (item == amulet) {
				return Constants.AMULET;
			}
		}
		for (int arrow : Item.arrows) {
			if (item == arrow) {
				return Constants.ARROWS;
			}
		}
		for (int ring : Item.rings) {
			if (item == ring) {
				return Constants.RING;
			}
		}
		for (int element : Item.body) {
			if (item == element) {
				return Constants.CHEST;
			}
		}
		for (int leg : Item.legs) {
			if (item == leg) {
				return Constants.BOTTOMS;
			}
		}
		// Default
		return Constants.WEAPON;
	}

	public void checkEquipmentLevel() {
		for (int i = 0; i < client.playerEquipment.length; i++) {
			int j = client.playerEquipment[i];
			int attackReq = EquipmentReq.attack(j);
			int strenghtReq = EquipmentReq.strength(j);
			int defenceReq = EquipmentReq.defence(j);
			int magicReq = EquipmentReq.magic(j);
			int rangedReq = EquipmentReq.ranged(j);
			if (attackReq > client
					.getLevelForXP(client.playerXP[Constants.ATTACK]))
				removeItem(client.playerEquipment[i], i);
			else if (strenghtReq > client
					.getLevelForXP(client.playerXP[Constants.STRENGTH]))
				removeItem(client.playerEquipment[i], i);
			else if (defenceReq > client
					.getLevelForXP(client.playerXP[Constants.DEFENCE]))
				removeItem(client.playerEquipment[i], i);
			else if (magicReq > client
					.getLevelForXP(client.playerXP[Constants.MAGIC]))
				removeItem(client.playerEquipment[i], i);
			else if (rangedReq > client
					.getLevelForXP(client.playerXP[Constants.RANGE_ATT]))
				removeItem(client.playerEquipment[i], i);
		}
	}

	public void wearItem(int wearID, int slot) {
		if (client.hitpoints == 0 && client.isBusy()) {
			return;
		}
		CombatEngine.resetAttack(client, false);

		int attackReq = EquipmentReq.attack(wearID);
		int strenghtReq = EquipmentReq.strength(wearID);
		int defenceReq = EquipmentReq.defence(wearID);
		int magicReq = EquipmentReq.magic(wearID);
		int rangedReq = EquipmentReq.ranged(wearID);
		if (attackReq > client.getLevelForXP(client.playerXP[Constants.ATTACK])) {
			client.getActionSender().sendMessage(
					(new StringBuilder()).append("You need ").append(attackReq)
							.append(" Attack to equip this item.").toString());
			return;
		}
		if (strenghtReq > client
				.getLevelForXP(client.playerXP[Constants.STRENGTH])) {
			client.getActionSender()
					.sendMessage(
							(new StringBuilder()).append("You need ")
									.append(strenghtReq)
									.append(" Strength to equip this item.")
									.toString());
			return;
		}
		if (defenceReq > client
				.getLevelForXP(client.playerXP[Constants.DEFENCE])) {
			client.getActionSender().sendMessage(
					(new StringBuilder()).append("You need ")
							.append(defenceReq)
							.append(" Defence to equip this item.").toString());
			return;
		}
		if (magicReq > client.getLevelForXP(client.playerXP[Constants.MAGIC])) {
			client.getActionSender().sendMessage(
					(new StringBuilder()).append("You need ").append(magicReq)
							.append(" Magic to equip this item.").toString());
			return;
		}
		if (rangedReq > client
				.getLevelForXP(client.playerXP[Constants.RANGE_ATT])) {
			client.getActionSender().sendMessage(
					(new StringBuilder()).append("You need ").append(rangedReq)
							.append(" Ranged to equip this item.").toString());
			return;
		}
		if (twoHanded(wearID) && client.getActionAssistant().freeSlots() < 1
				&& client.playerEquipment[Constants.SHIELD] > 0) {
			client.getActionSender().sendMessage(
					"Not enough space in your inventory.");
			return;
		}
		if (slot > -1 && slot < 28 && client.playerItems[slot] == wearID + 1) {
			client.getActionSender().sendItemReset(3214);
			int j = itemType(wearID);
			int k3 = client.playerItemsN[slot];
			if (k3 < 1)
				return;
			if (slot >= 0 && wearID >= 0) {
				client.getActionAssistant().deleteItem(wearID, slot, k3);
				if (client.playerEquipment[j] != wearID
						&& client.playerEquipment[j] >= 0) {
					client.getActionSender().sendInventoryItem(
							client.playerEquipment[j],
							client.playerEquipmentN[j], slot);
					client.getActionSender().sendItemReset(3214);
				} else if (Item.itemStackable[wearID]
						&& client.playerEquipment[j] == wearID)
					k3 = client.playerEquipmentN[j] + k3;
				else if (client.playerEquipment[j] >= 0) {
					client.getActionSender().sendInventoryItem(
							client.playerEquipment[j],
							client.playerEquipmentN[j], slot);
					client.getActionSender().sendItemReset(3214);
				}
			}
			setEquipment(wearID, k3, j);
			client.playerEquipment[j] = wearID;
			client.playerEquipmentN[j] = k3;

			if (j == Constants.WEAPON && twoHanded(wearID))
				removeItem(client.playerEquipment[Constants.SHIELD],
						Constants.SHIELD);

			if (j == Constants.SHIELD
					&& twoHanded(client.playerEquipment[Constants.WEAPON]))
				removeItem(client.playerEquipment[Constants.WEAPON],
						Constants.WEAPON);

			if (j == Constants.WEAPON) {
				Specials.turnOff(client);
				AutoCast.turnOff(client);
				sendWeapon();
				client.combatMode = CombatMode.setCombatMode(client,
						client.combatMode);
			}
			client.getBonuses().calculateBonus();
			client.updateRequired = true;
			client.appearanceUpdateRequired = true;
		}
	}

	public boolean twoHanded(int i) {
		String s = GameEngine.getItemManager().getItemDefinition(i).getName();

		s.replaceAll("_", " ");
		s.trim();

		if (s.contains("2h")) {
			return true;
		}
		if (s.contains("book")) {
			return true;
		}
		if (s.contains("great")) {
			return true;
		}
		if (s.endsWith("longbow")) {
			return true;
		}
		if (s.equals("Seercull")) {
			return true;
		}
		if (s.endsWith("shortbow")) {
			return true;
		}
		if (s.endsWith("Longbow")) {
			return true;
		}
		if (s.endsWith("Shortbow")) {
			return true;
		}
		if (s.endsWith("bow") && !s.startsWith("Rune")) {
			return true;
		}
		if (s.endsWith("godsword")) {
			return true;
		}
		if (s.endsWith("Dark bow")) {
			return true;
		}
		if (s.endsWith("halberd")) {
			return true;
		}
		if (s.equals("Granite maul")) {
			return true;
		}
		if (s.equals("Karils crossbow")) {
			return true;
		}
		if (s.equals("Torags hammers")) {
			return true;
		}
		if (s.equals("Veracs flail")) {
			return true;
		}
		if (s.equals("Dharoks greataxe")) {
			return true;
		}
		if (s.equals("Guthans warspear")) {
			return true;
		}
		if (s.equals("Saradomin sword")) {
			return true;
		}
		return s.equals("Tzhaar-ket-om");
	}

	public void deleteEquipment(int id) {
		client.playerEquipment[id] = -1;
		client.playerEquipmentN[id] = 0;
		client.outStream.createFrame(34);
		client.outStream.writeWord(6);
		client.outStream.writeWord(1688);
		client.outStream.writeByte(id);
		client.outStream.writeWord(0);
		client.outStream.writeByte(0);
		client.getBonuses().calculateBonus();
		if (id == Constants.WEAPON) {
			sendWeapon();
		}
		client.updateRequired = true;
		client.appearanceUpdateRequired = true;
	}

	public void removeItem(int wearID, int slot) {
		if (client.hitpoints == 0 && client.isBusy()) {
			return;
		}

		CombatEngine.resetAttack(client, false);

		if (client.getActionSender().sendInventoryItem(
				client.playerEquipment[slot], client.playerEquipmentN[slot])) {
			client.playerEquipment[slot] = -1;
			client.playerEquipmentN[slot] = 0;
			client.outStream.createFrame(34);
			client.outStream.writeWord(6);
			client.outStream.writeWord(1688);
			client.outStream.writeByte(slot);
			client.outStream.writeWord(0);
			client.outStream.writeByte(0);
			client.flushOutStream();
			client.updateRequired = true;
			client.appearanceUpdateRequired = true;

			client.getBonuses().calculateBonus();

			if (slot == Constants.WEAPON) {
				Specials.turnOff(client);
				AutoCast.turnOff(client);
				CombatMode.setCombatMode(client, client.combatMode);
				sendWeapon();
			}
			setWeaponEmotes();

		}
	}

	public static int getStandEmote(Client client) {
		int i = client.playerEquipment[Constants.WEAPON];
		String s = GameEngine.getItemManager().getItemDefinition(i).getName();
		s = s.replaceAll("_", " ");
		s = s.trim();
		if (i == -1)
			return 808;
		if (i == 4718)
			return 2065;
		if (i == 4755)
			return 2061;
		if (i == 4734)
			return 2074;
		if (i == 4747)
			return 808;
		if (i == 4726)
			return 809;
		if (s.contains("bow"))
			return 808;
		if (s.contains("2h") || s.endsWith("godsword")
				|| s.equals("Saradomin sword"))
			return 7047;
		if (s.contains("whip"))
			return 1832;
		if (s.endsWith("spear"))
			return 809;
		if (s.contains("halberd"))
			return 809;
		if (s.contains("Staff") || s.contains("staff") || s.endsWith("wand"))
			return 809;

		if (i == 4153)
			return 1662;
		if (i == 1305)
			return 809;
		if (s.contains("Saradomin staff") || s.contains("Guthix staff")
				|| s.contains("Zamorak staff"))
			return 808;

		else
			return i == 6528 || i == 1319 ? '\u0811' : 808;
	}

	public static int getWalkEmote(Client client) {
		int i = client.playerEquipment[Constants.WEAPON];
		String s = GameEngine.getItemManager().getItemDefinition(i).getName();
		s = s.replaceAll("_", " ");
		s = s.trim();
		if (i == -1)
			return 819;
		if (i == 4718)
			return 2064;
		if (i == 4755)
			return 2060;
		if (i == 4734)
			return 2076;
		if (i == 4747)
			return 819;
		if (i == 4726)
			return 1146;
		if (s.contains("bow"))
			return 819;
		if (s.contains("2h") || s.endsWith("godsword")
				|| s.equals("Saradomin sword"))
			return 7046;
		if (s.contains("whip"))
			return 1660;
		if (s.contains("Spear"))
			return 1146;
		if (s.contains("halberd"))
			return 1146;
		if (i == 4153)
			return 1663;
		if (s.contains("Saradomin staff") || s.contains("Guthix staff")
				|| s.contains("Zamorak staff"))
			return 819;
		if (s.contains("Staff") || s.contains("staff"))
			return 1146;
		else
			return i == 6528 || i == 1319 ? '\u0810' : 819;

	}

	public static int getRunEmote(Client client) {
		int i = client.playerEquipment[Constants.WEAPON];
		String s = GameEngine.getItemManager().getItemDefinition(i).getName();
		s = s.replaceAll("_", " ");
		s = s.trim();
		if (i == -1)
			return 824;
		if (i == 4718 || s.contains("great"))
			return 2563;
		if (i == 4755)
			return 1831;
		if (i == 4734)
			return 2077;
		if (i == 4747)
			return 824;
		if (i == 4726)
			return 1210;
		if (s.contains("bow"))
			return 824;
		if (s.contains("2h") || s.endsWith("godsword")
				|| s.equals("Saradomin sword"))
			return 7039;
		if (s.contains("whip"))
			return 1661;
		if (s.contains("Spear"))
			return 1210;
		if (s.contains("halberd"))
			return 1210;
		if (i == 4153 || i == 8103)
			return 1664;
		if (s.contains("Saradomin staff") || s.contains("Guthix staff")
				|| s.contains("Zamorak staff"))
			return 824;
		if (s.contains("Staff") || s.contains("staff"))
			return 1210;
		else
			return i == 6528 ? '\u0A03' : 824;
	}

	public static int getAttackEmote(Client client) {
		int i = client.playerEquipment[Constants.WEAPON];
		String s = GameEngine.getItemManager().getItemDefinition(i).getName();
		s = s.replaceAll("_", " ");
		s = s.trim();
		if (s.equals("Unarmed")) {
			if (client.combatMode == 0 || client.combatMode == 1) {
				return 422;
			} else if (client.combatMode == 2) {
				return 423;
			}
		}
		if (s.contains("Staff") || s.contains("staff")) {
			if (client.combatMode == 0 || client.combatMode == 1) {
				return 393;
			} else if (client.combatMode == 2) {
				return 406;
			}
		}
		if (s.equals("Sovereign blade"))
			return 390;
		if (s.contains("Guthans warspear"))
			return 2080;
		if (s.contains("Dragon dagger"))
			return 402;
		if (s.contains("Karils crossbow"))
			return 2075;
		if (s.contains("shortbow") || s.contains("Shortbow"))
			return 426;
		if (s.contains("longbow") || s.contains("Longbow"))
			return 426;
		if (s.contains("Seercull"))
			return 426;
		if (s.contains("Toktz-xil-ul"))
			return 2614;
		if (s.contains("thrownaxe"))
			return 806;
		if (s.contains("dart"))
			return 806;
		if (s.contains("javelin"))
			return 806;
		if (s.contains("knife"))
			return 806;
		if (s.contains("c'bow"))
			return 4230;
		if (s.contains("crossbow") || s.contains("Crossbow"))
			return 427;
		if (s.contains("bow") || s.contains("Bow"))
			return 426;
		if (s.contains("dagger") || s.contains("pickaxe")) {
			if (client.combatMode == 0) {
				return 412;
			} else if (client.combatMode == 3) {
				return 390;
			} else if (client.combatMode == 2) {
				return 402;
			}
		}
		if (s.contains("2h") || s.contains("godsword")
				|| s.equals("Saradomin sword")) {
			if (client.combatMode == 0) {
				return 7048;
			} else if (client.combatMode == 1) {
				return 7049;
			} else {
				return 7041;
			}
		}
		if (s.contains("sword") && !s.contains("2h") || s.contains("mace")
				|| s.contains("longsword") && !s.contains("2h")
				|| s.contains("scimitar")) {
			if (client.combatMode == 3) {
				return 412;
			} else {
				return 390;
			}
		}
		if (s.contains("greataxe") || s.contains("arok")) {
			if (client.combatMode == 1) {
				return 2066;
			} else {
				return 2067;
			}
		}
		if (s.contains("axe") && !s.contains("greataxe")
				&& !s.contains("thrownaxe") || s.contains("battleaxe"))
			return 395;
		if (s.contains("halberd") || s.contains("spear")
				&& !s.contains("Guthans"))
			return 440;
		if (s.contains("Tzhaar-ket-om"))
			return 2661;
		if (s.contains("Barrelchest anchor"))
			return 2661;
		if (s.contains("Granite maul"))
			return 1665;
		if (s.contains("flail"))
			return 2062;
		if (s.contains("whip"))
			return 1658;
		else
			return s.contains("hammers") ? '\u0814' : 390;
	}

	public static boolean basicId(int i) {
		String s = GameEngine.getItemManager().getItemDefinition(i).getName();
		s = s.replaceAll("_", " ");
		s = s.trim();
		return s.startsWith("Bronze") || s.startsWith("Iron")
				|| s.startsWith("Steel") || s.startsWith("Black")
				|| s.startsWith("Mithril") || s.startsWith("Adamant")
				|| s.startsWith("Rune") || s.startsWith("Dragon")
				|| s.startsWith("White");
	}

	public static int getWeaponSpeed(Client client) {
		int i = client.playerEquipment[Constants.WEAPON];
		String s = GameEngine.getItemManager().getItemDefinition(i).getName();
		s = s.replaceAll("_", " ");
		s = s.trim();
		if (s.endsWith("greegree"))
			return 5;
		if (s.equals("Sovereign blade"))
			return 5;
		if (s.contains("dagger") && basicId(i))
			return 5;
		if (s.contains("2h") || s.contains("god"))
			return 7;
		if (s.contains("sword") && !s.contains("longsword")
				&& !s.contains("god") && basicId(i))
			return 5;
		if (s.contains("whip") || s.equals("Saradomin sword"))
			return 5;
		if (s.contains("longsword") && basicId(i))
			return 6;
		if (s.contains("scimitar") && basicId(i))
			return 5;
		if (s.contains("mace") && basicId(i))
			return 6;
		if (s.contains("knife") || s.contains("dart") && basicId(i)
				|| s.contains("thrownaxe") && basicId(i)) {
			if (client.combatMode == 4) {
				return 3;
			} else if (client.combatMode == 5) {
				return 2;
			} else if (client.combatMode == 6) {
				return 4;
			}
		}
		if (s.contains("shortbow") || s.contains("Shortbow")
				|| s.equals("Seercull") || s.equals("Karil's crossbow")
				|| s.equals("Toktz-xil-ul")) {
			if (client.combatMode == 4) {
				return 5;
			} else if (client.combatMode == 5) {
				return 4;
			} else if (client.combatMode == 6) {
				return 6;
			}
		}
		if (s.contains("Crossbow") || s.equals("Crystal bow full")
				|| s.equals("New crystal bow") || s.contains("javelin")
				&& basicId(i)) {
			if (client.combatMode == 4) {
				return 7;
			} else if (client.combatMode == 5) {
				return 6;
			} else if (client.combatMode == 6) {
				return 8;
			}
		}
		if (s.contains("longbow") || s.contains("Longbow")
				|| s.contains("c'bow")) {
			if (client.combatMode == 4) {
				return 7;
			} else if (client.combatMode == 5) {
				return 6;
			} else if (client.combatMode == 6) {
				return 8;
			}
		}
		if (s.contains("Dark") || s.contains("Longbow") || s.contains("c'bow")) {
			if (client.combatMode == 4) {
				return 8;
			} else if (client.combatMode == 5) {
				return 7;
			} else if (client.combatMode == 6) {
				return 9;
			}
		}
		if (s.contains("axe") && !s.contains("greataxe")
				&& !s.contains("battleaxe") && !s.contains("pickaxe")
				&& basicId(i))
			return 6;
		if (s.contains("battleaxe") && basicId(i))
			return 7;
		if (s.contains("warhammer") && basicId(i))
			return 7;
		if (s.contains("spear") && basicId(i))
			return 6;
		if (s.contains("halberd") && basicId(i))
			return 8;
		if (s.contains("pickaxe") && basicId(i))
			return 6;
		if (s.equals("Granite maul"))
			return 8;
		if (s.equals("Toktz-xil-ak"))
			return 5;
		if (s.equals("Tzharr-ket-em"))
			return 6;
		if (s.equals("Tzhaar-ket-om"))
			return 8;
		if (s.equals("Toktz-xil-ek"))
			return 5;
		if (s.equals("Dharoks greataxe"))
			return 8;
		if (s.equals("Torags hammers"))
			return 6;
		if (s.equals("Guthans warspear"))
			return 6;
		if (s.equals("Veracs flail"))
			return 6;
		if (s.equals("Ahrims staff"))
			return 7;
		if (s.contains("staff") || s.contains("Staff"))
			return 5;
		if (s.contains("battlestaff") || s.contains("Battletaff"))
			return 6;
		if (s.equals("Toktz-mej-tal"))
			return 6;
		if (s.equals("Tzhaar-ket-em"))
			return 7;

		if (s.equals("Unarmed") || i == -1) {
			return 5;
		} else {
			// System.out.println("Item has no timer: " + s + ".");
			return 5;
		}
	}

	public static int getDefendAnimation(Client client) {
		int weapon = client.playerEquipment[Constants.WEAPON];
		int shield = client.playerEquipment[Constants.SHIELD];
		int i = client.playerEquipment[Constants.WEAPON];
		String s = GameEngine.getItemManager().getItemDefinition(i).getName();
		s = s.replaceAll("_", " ");
		s = s.trim();

		if (s.contains("2h") || s.contains("god"))
			return 7050;

		switch (shield) {
		case 8850:
		case 8849:
		case 8848:
		case 8847:
		case 8846:
		case 8845:
		case 8844:
			return 4177;
		}
		switch (weapon) {
		case 1307:
		case 1309:
		case 1311:
		case 1313:
		case 1315:
		case 1317:
		case 1319:
			return 410;

		case 4151:
			return 1659;

		case 11694:
		case 11698:
		case 11700:
		case 11696:
		case 11730:
			return 7050;
		}
		if (shield > 0) {
			return 1156;
		} else
			return 404;
	}

	public boolean wearingRecoil() {
		return client.playerEquipment[Constants.RING] == 2550;
	}

	public boolean fullToragEquipped() {
		return client.playerEquipment[Constants.HELM] == 4745
				&& client.playerEquipment[Constants.CHEST] == 4749
				&& client.playerEquipment[Constants.BOTTOMS] == 4751
				&& client.playerEquipment[Constants.WEAPON] == 4718;
	}

	public boolean fullKarilEquipped() {
		return client.playerEquipment[Constants.HELM] == 4732
				&& client.playerEquipment[Constants.CHEST] == 4736
				&& client.playerEquipment[Constants.BOTTOMS] == 4738
				&& client.playerEquipment[Constants.WEAPON] == 4734;
	}

	public boolean fullDharokEquipped() {
		return client.playerEquipment[Constants.HELM] == 4716
				&& client.playerEquipment[Constants.CHEST] == 4720
				&& client.playerEquipment[Constants.BOTTOMS] == 4722
				&& client.playerEquipment[Constants.WEAPON] == 4718;
	}

	public boolean fullGuthanEquipped() {
		return client.playerEquipment[Constants.HELM] == 4724
				&& client.playerEquipment[Constants.CHEST] == 4728
				&& client.playerEquipment[Constants.BOTTOMS] == 4730
				&& client.playerEquipment[Constants.WEAPON] == 4726;
	}

}