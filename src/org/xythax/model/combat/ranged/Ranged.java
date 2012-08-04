package org.xythax.model.combat.ranged;

import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.FloorItem;
import org.xythax.model.MobileEntity;
import org.xythax.model.NPC;
import org.xythax.model.definition.XMLManager;
import org.xythax.model.definition.skills.Projectile;
import org.xythax.model.map.TileManager;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;

public class Ranged {

	public static int getProjectTileSpeed() {

		return 1;
	}

	public static Projectile projectile(int id) {
		for (Projectile pjt : XMLManager.projectile) {
			if (pjt.getId() == id) {
				return pjt;
			}
		}
		return null;
	}

	public static double getRangedStr(Client client) {

		int itemSearch = client.playerEquipment[Constants.ARROWS];

		for (int object : THROWN_OBJECTS) {
			if (client.playerEquipment[Constants.WEAPON] == object)
				itemSearch = object;
		}

		if (isUsingCrystalBow(client))
			itemSearch = CRYSTAL_BOW;

		if (projectile(itemSearch) != null)
			return projectile(itemSearch).getStrength();

		return 5;
	}

	public static boolean isUsingEnchantedBolt(Client client) {
		for (int[] i : ENCHANTED_BOLTS) {
			if (client.playerEquipment[Constants.ARROWS] == i[0]) {
				return true;
			}
		}
		return false;
	}

	public static int getEnchantedGfx(Client client) {
		for (int[] i : ENCHANTED_BOLTS) {
			if (client.playerEquipment[Constants.ARROWS] == i[0]) {
				return i[1];
			}
		}
		return NO_GFX;
	}

	public static boolean isUsingRange(Client client) {

		int[] BOWS = { 800, 801, 802, 803, 804, 805, 837, 839, 841, 843, 845,
				847, 849, 851, 853, 855, 857, 859, 861, 2883, 4212, 4212, 4216,
				4217, 4218, 4219, 4220, 4221, 4222, 4223, 4236, 4734, 4827,
				4934, 4835, 4936, 4937, 4938, 6818, 884, 863, 865, 869, 866,
				867, 868, 806, 807, 808, 809, 810, 811, 4212, 4214, 6522, 825,
				826, 827, 828, 829, 830, 6724, 9185, 11235 };
		for (int i : BOWS) {
			if (client.playerEquipment[Constants.WEAPON] == i) {
				return true;
			}
		}
		return false;
	}

	public static boolean isUsingCrystalBow(Client client) {

		int[] CRYSTAL_BOWS = { 4212, 4212, 4216, 4217, 4218, 4219, 4220, 4221,
				4222, 4223 };

		for (int i : CRYSTAL_BOWS) {
			if (client.playerEquipment[Constants.WEAPON] == i) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasArrows(Client client) {

		if (isUsingCrystalBow(client)) {
			if (client.playerEquipment[Constants.ARROWS] > 0) {
				if (client.getActionAssistant().freeSlots() < 1) {
					client.getActionSender().sendMessage(
							"This bow cannot use ammo.");
					return false;
				} else if (client.getActionAssistant().freeSlots() >= 1) {
					client.getEquipment().removeItem(
							client.playerEquipment[Constants.ARROWS],
							Constants.ARROWS);
					return true;
				}
			} else {
				return true;
			}
		} else if (client.playerEquipment[Constants.WEAPON] == KARILS_CROSSBOW) {
			if (client.playerEquipment[Constants.ARROWS] == KARILS_BOLT
					&& client.playerEquipmentN[Constants.ARROWS] >= 1) {
				return true;
			}
			client.getActionSender().sendMessage(
					"You need bolt racks to use this weapon.");
			return false;
		} else if (client.playerEquipment[Constants.WEAPON] == RUNE_CROSSBOW) {
			for (int i = 0; i < ENCHANTED_BOLTS.length; i++) {
				if (client.playerEquipment[Constants.ARROWS] == ENCHANTED_BOLTS[i][0]
						&& client.playerEquipmentN[Constants.ARROWS] >= 1) {
					return true;
				}
			}
			client.getActionSender().sendMessage(
					"You need bolts to use this weapon.");
			return false;
		}
		for (int object : THROWN_OBJECTS) {
			if (client.playerEquipment[Constants.WEAPON] == object) {
				return true;
			}
		}
		if (client.playerEquipment[Constants.ARROWS] < 1
				|| client.playerEquipmentN[Constants.ARROWS] < 1) {
			client.getActionSender().sendMessage("You have no arrows.");
			return false;
		} else if (client.playerEquipment[Constants.ARROWS] == KARILS_BOLT) {
			client.getActionSender().sendMessage(
					"You cannot use bolts with this bow.");
			return false;
		} else {
			for (int i = 0; i < ENCHANTED_BOLTS.length; i++) {
				if (client.playerEquipment[Constants.ARROWS] == ENCHANTED_BOLTS[i][0]) {
					client.getActionSender().sendMessage(
							"You cannot use bolts with this bow.");
					return false;
				}
			}
		}
		return true;
	}

	public static void createMSBProjectile(MobileEntity attacker,
			MobileEntity victim) {
		int offsetX = (attacker.getAbsX() - victim.getAbsX()) * -1;
		int offsetY = (attacker.getAbsY() - victim.getAbsY()) * -1;
		int startHeight = 43;
		int angle = 15;
		int gfx = 249;
		int endHeight = 27;
		int d = -2;
		if (victim instanceof Client) {
			GameEngine.getGlobalActions().sendProjectile(((Client) attacker),
					attacker.getAbsY(), attacker.getAbsX(), offsetY, offsetX,
					gfx, startHeight, endHeight,
					getProjectileSpeed(attacker, victim) + d, angle,
					-((Client) victim).getUserID() - 1);
			GameEngine.getGlobalActions().sendProjectile(((Client) attacker),
					attacker.getAbsY(), attacker.getAbsX(), offsetY, offsetX,
					gfx, startHeight + 5, endHeight + 2,
					getProjectileSpeed(attacker, victim) + d + 5, angle,
					-((Client) victim).getUserID() - 1);
		} else if (victim instanceof NPC) {
			GameEngine.getGlobalActions().sendProjectile(((Client) attacker),
					attacker.getAbsY(), attacker.getAbsX(), offsetY, offsetX,
					gfx, startHeight, endHeight,
					getProjectileSpeed(attacker, victim) + d, angle,
					((NPC) victim).getNpcId() + 1);
			GameEngine.getGlobalActions().sendProjectile(((Client) attacker),
					attacker.getAbsY(), attacker.getAbsX(), offsetY, offsetX,
					gfx, startHeight + 5, endHeight + 2,
					getProjectileSpeed(attacker, victim) + d + 5, angle,
					((NPC) victim).getNpcId() + 1);
		}
		if (attacker instanceof Client) {
			deleteArrow(((Client) attacker));
			deleteArrow(((Client) attacker));
		}
	}

	public static void createProjectile(MobileEntity attacker,
			MobileEntity victim) {

		if (attacker instanceof Client) {
			boolean noDelete = false;
			boolean deleteThrown = false;
			int item = ((Client) attacker).playerEquipment[Constants.ARROWS];

			for (int object : THROWN_OBJECTS) {
				if (((Client) attacker).playerEquipment[Constants.WEAPON] == object) {
					item = object;
					noDelete = true;
					deleteThrown = true;

				}
			}
			if (isUsingCrystalBow(((Client) attacker))) {
				item = CRYSTAL_BOW;
				noDelete = true;
				deleteThrown = false;
			}
			if (!noDelete) {
				for (int i = 0; i < getArrowUsage(((Client) attacker)); i++) {
					if (Utils.random(5) > 2) {
						dropArrows(attacker, victim);
					}
					deleteArrow(((Client) attacker));
				}
			}
			if (deleteThrown) {
				deleteThrownItems(((Client) attacker));
			}
			if (((Client) attacker).playerEquipment[Constants.WEAPON] == DARK_BOW) {
				int offsetX = (attacker.getAbsX() - victim.getAbsX()) * -1;
				int offsetY = (attacker.getAbsY() - victim.getAbsY()) * -1;
				int startHeight = 43;
				int angle = 15;
				int endHeight = 27;
				int arrowGfx = 0;
				if (item == 1111) {
					arrowGfx = 0;
				} else {
					if (projectile(item) != null)
						arrowGfx = projectile(item).getAirGfx();
				}
				if (victim instanceof Client) {
					GameEngine.getGlobalActions().sendProjectile(
							((Client) attacker), attacker.getAbsY(),
							attacker.getAbsX(), offsetY, offsetX, arrowGfx,
							startHeight, endHeight,
							getProjectileSpeed(attacker, victim) + 10, angle,
							-((Client) victim).getUserID() - 1);
					GameEngine.getGlobalActions().sendProjectile(
							((Client) attacker), attacker.getAbsY(),
							attacker.getAbsX(), offsetY, offsetX, arrowGfx,
							startHeight + 5, endHeight + 5,
							getProjectileSpeed(attacker, victim) + 25,
							angle + 10, -((Client) victim).getUserID() - 1);
				} else if (victim instanceof NPC) {
					GameEngine.getGlobalActions().sendProjectile(
							((Client) attacker), attacker.getAbsY(),
							attacker.getAbsX(), offsetY, offsetX, arrowGfx,
							startHeight, endHeight,
							getProjectileSpeed(attacker, victim) + 10, angle,
							((NPC) victim).getNpcId() + 1);
					GameEngine.getGlobalActions().sendProjectile(
							((Client) attacker), attacker.getAbsY(),
							attacker.getAbsX(), offsetY, offsetX, arrowGfx,
							startHeight + 5, endHeight + 5,
							getProjectileSpeed(attacker, victim) + 25,
							angle + 10, ((NPC) victim).getNpcId() + 1);
				}
				((Client) attacker).getActionAssistant().createPlayerGfx(
						item == 11212 ? 1111 : 1110, 0, true);
				return;
			}
			if (projectile(item) != null) {

				if (projectile(item).getPullBackGfx() != NO_GFX) {
					((Client) attacker).getActionAssistant().createPlayerGfx(
							projectile(item).getPullBackGfx(), 0, true);
				}
				if (projectile(item).getAirGfx() != NO_GFX) {
					int offsetX = (attacker.getAbsX() - victim.getAbsX()) * -1;
					int offsetY = (attacker.getAbsY() - victim.getAbsY()) * -1;
					int startHeight = item == CRYSTAL_BOW ? 44 : 43;
					int angle = ((Client) attacker).playerEquipment[Constants.WEAPON] == 4734
							|| ((Client) attacker).playerEquipment[Constants.WEAPON] == RUNE_CROSSBOW ? 1
							: 15;
					int endHeight = angle == 1 ? 35 : 27;
					if (victim instanceof Client) {
						GameEngine.getGlobalActions().sendProjectile(
								((Client) attacker), attacker.getAbsY(),
								attacker.getAbsX(), offsetY, offsetX,
								projectile(item).getAirGfx(), startHeight,
								endHeight,
								getProjectileSpeed(attacker, victim), angle,
								-((Client) victim).getUserID() - 1);
					} else if (victim instanceof NPC) {
						GameEngine.getGlobalActions().sendProjectile(
								((Client) attacker), attacker.getAbsY(),
								attacker.getAbsX(), offsetY, offsetX,
								projectile(item).getAirGfx(), startHeight,
								endHeight,
								getProjectileSpeed(attacker, victim), angle,
								((NPC) victim).getNpcId() + 1);
					}
				}
			}
		} else if (attacker instanceof NPC) {
		}
	}

	public static int getArrowUsage(Client client) {
		if (isUsingCrystalBow(client))
			return 0;
		if (client.playerEquipment[Constants.WEAPON] == DARK_BOW)
			return 2;
		return 1;
	}

	public static void deleteArrow(Client client) {
		if (client.playerEquipmentN[Constants.ARROWS] > 0) {
			client.outStream.createFrameVarSizeWord(34);
			client.outStream.writeWord(1688);
			client.outStream.writeByte(Constants.ARROWS);
			client.outStream
					.writeWord(client.playerEquipment[Constants.ARROWS] + 1);
			if (client.playerEquipmentN[Constants.ARROWS] - 1 > 254) {
				client.outStream.writeByte(255);
				client.outStream
						.writeDWord(client.playerEquipmentN[Constants.ARROWS] - 1);
			} else {
				client.outStream
						.writeByte(client.playerEquipmentN[Constants.ARROWS] - 1);
			}
			client.outStream.endFrameVarSizeWord();
			client.playerEquipmentN[Constants.ARROWS] -= 1;
		}
		if (client.playerEquipmentN[Constants.ARROWS] == 0) {
			client.playerEquipment[Constants.ARROWS] = -1;
			client.playerEquipmentN[Constants.ARROWS] = 0;
			client.outStream.createFrame(34);
			client.outStream.writeWord(6);
			client.outStream.writeWord(1688);
			client.outStream.writeByte(Constants.ARROWS);
			client.outStream.writeWord(0);
			client.outStream.writeByte(0);
			client.flushOutStream();
		}
		client.updateRequired = true;
		client.appearanceUpdateRequired = true;
	}

	public static void deleteThrownItems(Client client) {
		synchronized (client) {

			if (client.playerEquipmentN[Constants.WEAPON] > 0) {
				client.outStream.createFrameVarSizeWord(34);
				client.outStream.writeWord(1688);
				client.outStream.writeByte(Constants.WEAPON);
				client.outStream
						.writeWord(client.playerEquipment[Constants.WEAPON] + 1);
				if (client.playerEquipmentN[Constants.WEAPON] - 1 > 254) {
					client.outStream.writeByte(255);
					client.outStream
							.writeDWord(client.playerEquipmentN[Constants.WEAPON] - 1);
				} else {
					client.outStream
							.writeByte(client.playerEquipmentN[Constants.WEAPON] - 1);
				}
				client.outStream.endFrameVarSizeWord();
				client.playerEquipmentN[Constants.WEAPON] -= 1;
			}
			if (client.playerEquipmentN[Constants.WEAPON] == 0) {
				client.playerEquipment[Constants.WEAPON] = -1;
				client.playerEquipmentN[Constants.WEAPON] = 0;
				client.outStream.createFrame(34);
				client.outStream.writeWord(6);
				client.outStream.writeWord(1688);
				client.outStream.writeByte(Constants.WEAPON);
				client.outStream.writeWord(0);
				client.outStream.writeByte(0);
				client.flushOutStream();
			}
			client.getEquipment().sendWeapon();
			client.getBonuses().calculateBonus();
			client.updateRequired = true;
			client.appearanceUpdateRequired = true;
		}
	}

	public static void dropArrows(Entity attacker, Entity victim) {
		boolean updated = false;
		if (attacker instanceof Client) {
			Client client = ((Client) attacker);
			for (FloorItem item : GameEngine.getItemManager().getList()) {
				if (item.getId() == client.playerEquipment[Constants.ARROWS]
						&& item.getX() == victim.getAbsX()
						&& item.getY() == victim.getAbsY()
						&& item.getHeight() == victim.getHeightLevel()
						&& item.getUsername() == client.getUsername()) {
					item.setAmount(item.getAmount() + 1);
					updated = true;
				}
			}
			if (!updated) {
				FloorItem arrow = new FloorItem(
						client.playerEquipment[Constants.ARROWS], 1,
						client.getUsername(), victim.getAbsX(),
						victim.getAbsY(), victim.getHeightLevel());
				GameEngine.getItemManager().newDrop(arrow, client);
			}
		}
	}

	public static int getHitDelay(Entity attacker) {
		int count = 0;
		if (attacker instanceof Client) {
			count = 3 + (isUsingCrystalBow(((Client) attacker))
					|| ((Client) attacker).playerEquipment[Constants.WEAPON] == DARK_BOW ? 1
					: 0);
		}
		return count;
	}

	public static int getProjectileSpeed(MobileEntity attacker,
			MobileEntity victim) {
		int speed = 90;
		if (attacker instanceof Client) {

			speed = 65 + (isUsingCrystalBow(((Client) attacker))
					|| ((Client) attacker).playerEquipment[Constants.WEAPON] == DARK_BOW ? 10
					: 0);
			return (speed + TileManager.calculateDistance(victim, attacker)
					* (int) 2.255);
		}
		return speed;
	}

	public static final int MAGIC_SHORT_BOW = 861, YEW_SHORT_BOW = 857,
			MAPLE_SHORT_BOW = 853, WILLOW_SHORT_BOW = 849, OAK_SHORT_BOW = 843,
			SHORT_BOW = 841, BRONZE_ARROW = 882, IRON_ARROW = 884,
			STEEL_ARROW = 886, MITHRIL_ARROW = 888, ADAMANT_ARROW = 890,
			RUNITE_ARROW = 892, BRONZE_KNIFE = 864, IRON_KNIFE = 863,
			STEEL_KNIFE = 865, BLACK_KNIFE = 869, MITHRIL_KNIFE = 866,
			ADAMANT_KNIFE = 867, RUNITE_KNIFE = 868, BRONZE_DART = 806,
			IRON_DART = 807, STEEL_DART = 808, MITHRIL_DART = 809,
			ADAMANT_DART = 810, RUNITE_DART = 811, BRONZE_AXE = 800,
			IRON_AXE = 801, STEEL_AXE = 802, MITHRIL_AXE = 803,
			ADAMANT_AXE = 804, RUNITE_AXE = 805, BRONZE_JAVELIN = 825,
			IRON_JAVELIN = 826, STEEL_JAVELIN = 827, MITHRIL_JAVELIN = 828,
			ADAMANT_JAVELIN = 829, RUNITE_JAVELIN = 830, EMERALD_BOLT_E = 9241,
			RUBY_BOLT_E = 9242, DIAMOND_BOLT_E = 9243, DRAGON_BOLT_E = 9244,
			ONYX_BOLT_E = 9245, DARK_BOW = 11235, KARILS_CROSSBOW = 4734,
			RUNE_CROSSBOW = 9185, KARILS_BOLT = 4740, OBBY_RING = 6724,
			CRYSTAL_BOW = 1337, NO_GFX = -1;

	public static final int[] THROWN_OBJECTS = { 863, 864, 865, 866, 867, 868,
			869, 800, 801, 802, 803, 804, 805, 806, 807, 808, 809, 810, 811,
			825, 826, 827, 828, 829, 830, 6724 };

	public static final int[][] ENCHANTED_BOLTS = { { EMERALD_BOLT_E, 752 },
			{ RUBY_BOLT_E, 754 }, { DIAMOND_BOLT_E, 758 },
			{ DRAGON_BOLT_E, 756 }, { ONYX_BOLT_E, 753 }, };

}
