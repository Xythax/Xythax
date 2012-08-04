package org.xythax.model;

import org.xythax.content.controllers.Damage;
import org.xythax.content.controllers.Graphics;
import org.xythax.core.GameEngine;
import org.xythax.model.Entity.CombatType;
import org.xythax.model.combat.content.BarrowsEffects;
import org.xythax.model.combat.content.Rings;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;
import org.xythax.world.PlayerManager;

/**
 * A collection of player actions
 */

public class ActionAssistant {

	private Client client;

	public ActionAssistant(Client client) {
		this.client = client;
	}

	public void setFollowing(int followID, int followType, int followDistance) {
		client.outStream.createFrame(174);
		client.outStream.writeWord(followID);
		client.outStream.writeByte(followType);
		client.outStream.writeWord(followDistance);
	}

	public void setSplitChat(int state) {
		if (state == 0) {
			client.getActionSender().sendFrame36(502, 1);
			client.getActionSender().sendFrame36(287, 1);
			client.SplitChat = true;
		} else {
			client.getActionSender().sendFrame36(502, 0);
			client.getActionSender().sendFrame36(287, 0);
			client.SplitChat = false;
		}
	}

	public void showNewLevel(int skillId) {
		client.getActionSender().sendMessage(
				"Congratulations, you just advanced an "
						+ Constants.SKILL_NAMES[skillId].toLowerCase()
						+ " level!");
		Graphics.addNewRequest(client, 199, 100, 2);
	}

	public void addSkillXP(double amount, int skillId) {

		if (amount + client.playerXP[skillId] < 0
				|| client.playerXP[skillId] > 2080040703) {
			return;
		}

		int oldLevel = client.getLevelForXP(client.playerXP[skillId]);
		client.playerXP[skillId] += amount;
		if (oldLevel < client.getLevelForXP(client.playerXP[skillId])) {
			client.playerLevel[skillId] = client
					.getLevelForXP(client.playerXP[skillId]);
			showNewLevel(skillId);
			if (skillId == 3) {
				addHP(client.getLevelForXP(client.playerXP[skillId]) - oldLevel);
			}
		}
		refreshSkill(skillId);
		client.updateRequired = true;
		client.appearanceUpdateRequired = true;
	}

	public void setFollow(boolean objective, int followID) {
		if (followID > 0 && objective) {
			if (PlayerManager.getPlayerManager().getPlayers()[followID] != null) {
				client.followPlayer = ((Client) PlayerManager.getPlayerManager()
						.getPlayers()[followID]);
				turnPlayerTo(32768 + followID);
				return;
			}
		}
		if (!objective) {
			client.setFollowPlayer(null);
			client.pauseFollow = false;
			turnPlayerTo(32768);
			return;
		}
	}

	public void addHP(int amount) {
		if (client.hitpoints + amount > client.getActionAssistant().client
				.getLevelForXP(client.playerXP[Constants.HITPOINTS])) {
			client.hitpoints = client.getActionAssistant().client
					.getLevelForXP(client.playerXP[Constants.HITPOINTS]);
		} else {
			client.hitpoints += amount;
		}
		client.playerLevel[Constants.HITPOINTS] = client.hitpoints;
		client.hitpoints = client.playerLevel[Constants.HITPOINTS];
		client.getActionAssistant().refreshSkill(3);
	}

	public void restore(final int amount) {
		for (int i = 0; i < Constants.MAX_SKILLS; i++) {
			if (client.playerLevel[i] > client
					.getLevelForXP(client.playerXP[i])) {
				client.playerLevel[i] -= amount;
			}
			refreshSkill(i);
		}
	}

	public void increaseStat(int stat, int increase) {

		int actualLevel = client.getLevelForXP(client.playerXP[stat]);
		double increaseBy = Utils.intToPercentage(increase) * actualLevel;

		if (client.playerLevel[stat] >= actualLevel + increaseBy) {
			return;
		}
		if (stat == 5) {
			if (client.playerLevel[stat] + increaseBy > actualLevel) {
				increaseBy = 0;
				client.playerLevel[stat] = actualLevel;
			}
		}
		client.playerLevel[stat] += increaseBy;

		if (client.playerLevel[stat] >= actualLevel + increaseBy)
			client.playerLevel[stat] = (int) (actualLevel + increaseBy);

		refreshSkill(stat);
	}

	public void decreaseStat(int stat, double decrease) {
		if ((client.playerLevel[stat] - decrease) > 0) {
			client.playerLevel[stat] -= decrease;
		} else {
			client.playerLevel[stat] = 0;
		}
		refreshSkill(stat);
	}

	public void refreshSkill(int skillId) {
		switch (skillId) {

		case 0:
			client.getActionSender()
					.sendQuest(client.playerLevel[0] + "", 4004);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[0]) + "", 4005);
			break;

		case 1:
			client.getActionSender()
					.sendQuest(client.playerLevel[1] + "", 4008);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[1]) + "", 4009);
			break;

		case 2:
			client.getActionSender()
					.sendQuest(client.playerLevel[2] + "", 4006);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[2]) + "", 4007);
			break;

		case 3:
			client.getActionSender().sendQuest(client.hitpoints + "", 4016);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[3]) + "", 4017);
			break;

		case 4:
			client.getActionSender()
					.sendQuest(client.playerLevel[4] + "", 4010);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[4]) + "", 4011);
			break;

		case 5:
			client.getActionSender()
					.sendQuest(client.playerLevel[5] + "", 4012);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[5]) + "", 4013);
			break;

		case 6:
			client.getActionSender()
					.sendQuest(client.playerLevel[6] + "", 4014);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[6]) + "", 4015);
			break;

		case 7:
			client.getActionSender()
					.sendQuest(client.playerLevel[7] + "", 4034);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[7]) + "", 4035);
			break;

		case 8:
			client.getActionSender()
					.sendQuest(client.playerLevel[8] + "", 4038);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[8]) + "", 4039);
			break;

		case 9:
			client.getActionSender()
					.sendQuest(client.playerLevel[9] + "", 4026);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[9]) + "", 4027);
			break;

		case 10:
			client.getActionSender().sendQuest(client.playerLevel[10] + "",
					4032);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[10]) + "", 4033);
			break;

		case 11:
			client.getActionSender().sendQuest(client.playerLevel[11] + "",
					4036);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[11]) + "", 4037);
			break;

		case 12:
			client.getActionSender().sendQuest(client.playerLevel[12] + "",
					4024);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[12]) + "", 4025);
			break;

		case 13:
			client.getActionSender().sendQuest(client.playerLevel[13] + "",
					4030);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[13]) + "", 4031);
			break;

		case 14:
			client.getActionSender().sendQuest(client.playerLevel[14] + "",
					4028);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[14]) + "", 4029);
			break;

		case 15:
			client.getActionSender().sendQuest(client.playerLevel[15] + "",
					4020);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[15]) + "", 4021);
			break;

		case 16:
			client.getActionSender().sendQuest(client.playerLevel[16] + "",
					4018);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[16]) + "", 4019);
			break;

		case 17:
			client.getActionSender().sendQuest(client.playerLevel[17] + "",
					4022);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[17]) + "", 4023);
			break;

		case 18:
			client.getActionSender().sendQuest(client.playerLevel[18] + "",
					12166);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[18]) + "", 12167);
			break;

		case 19:
			client.getActionSender().sendQuest(client.playerLevel[19] + "",
					13926);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[19]) + "", 13927);
			break;

		case 20:
			client.getActionSender().sendQuest(client.playerLevel[20] + "",
					4152);
			client.getActionSender().sendQuest(
					client.getLevelForXP(client.playerXP[20]) + "", 4153);
			break;
		}
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(134);
				client.outStream.writeByte(skillId);
				client.outStream.writeDWord_v1(client.playerXP[skillId]);
				client.outStream.writeByte(client.playerLevel[skillId]);
			}
		}
	}

	public void openUpShop(int shopId) {
		Shop s = GameEngine.getShopManager().getShops().get(shopId);
		if (s == null)
			return;
		client.getActionSender().sendQuest(s.getName(), 3901);
		client.getActionSender().sendFrame248(3824, 3822);
		client.getActionSender().sendItemReset(3823);
		client.getActionSender().sendShopReset(s);
		client.getExtraData().put("shop", shopId);
		client.flushOutStream();
	}

	public void createPlayerGfx(int id, int delay, boolean tallGfx) {
		client.gfxID = id;
		client.gfxDelay = (tallGfx == true) ? delay + 6553600 : delay;
		client.updateRequired = true;
		client.graphicsUpdateRequired = true;
	}

	public void createPlayerGfx2(int id, int delay) {
		client.gfxID = id;
		client.gfxDelay = delay;
		client.updateRequired = true;
		client.graphicsUpdateRequired = true;
	}

	public void gfx100(int id) {
		client.gfxID = id;
		client.gfxDelay = 6553600;
		client.updateRequired = true;
		client.graphicsUpdateRequired = true;
	}

	public void startAni(int anim) {
		client.animationRequest = anim;
		client.animationWaitCycles = 0;
		client.updateRequired = true;
	}

	public void startAnimation(int anim, int delay) {
		client.animationRequest = anim;
		client.animationWaitCycles = delay;
		client.updateRequired = true;
	}

	public void forceText(String s) {
		client.forcedText = s;
		client.updateRequired = true;
		client.forcedTextUpdateRequired = true;
	}

	public void turnPlayerTo(int index) {
		client.turnPlayerTo = index;
		client.updateRequired = true;
		client.turnPlayerToUpdateRequired = true;
	}

	public void turnTo(int x, int y) {
		client.viewToX = 2 * x + 1;
		client.viewToY = 2 * y + 1;
		client.updateRequired = true;
		client.faceUpdateRequired = true;
	}

	public void subtractDamage(int i) {
		for (int j = 1; j < client.killedBy.length; j++) {
			client.killedBy[j] -= i;
			if (client.killedBy[j] < 0)
				client.killedBy[j] = 0;
		}
	}

	public void subtractHp(int i) {
		subtractDamage(i);
		client.hitpoints -= i;
		if (client.hitpoints < 0)
			client.hitpoints = 0;
		client.playerLevel[3] = client.hitpoints;
		client.hitpoints = client.playerLevel[3];
		client.getActionAssistant().refreshSkill(3);
	}

	public void appendHit(CombatType type, int damage) {
		if (damage > client.hitpoints) {
			damage = client.hitpoints;
		}
		if (type == CombatType.POISON) {
			client.poisonHit = true;
			client.hitDiff2 = damage;
			subtractHp(client.hitDiff2);
			client.updateRequired = true;
			client.hitUpdateRequired2 = true;

		} else {
			startAnimation(Equipment.getDefendAnimation(client), 0);

			if (!client.hitUpdateRequired) {
				client.hitDiff = damage;
				subtractHp(client.hitDiff);
				client.updateRequired = true;
				client.hitUpdateRequired = true;
				if (BarrowsEffects.fullDharoks(client) && damage > 0)
					BarrowsEffects.degradeDharoks(client, damage);
				Rings.applyRingOfLife(client, damage);
				client.getActionSender().sendWindowsRemoval();

			} else if (!client.hitUpdateRequired2) {
				client.hitDiff2 = damage;
				subtractHp(client.hitDiff2);
				client.updateRequired = true;
				client.hitUpdateRequired2 = true;
				if (BarrowsEffects.fullDharoks(client) && damage > 0)
					BarrowsEffects.degradeDharoks(client, damage);
				Rings.applyRingOfLife(client, damage);
				client.getActionSender().sendWindowsRemoval();

			} else {
				Damage.addNewHit(client, client, CombatType.MELEE, damage, 1);
			}
		}
	}

	public void dropItem(int id, int slot) {
		if (client.playerItems[slot] == (id + 1)) {
			FloorItem i = new FloorItem(client.playerItems[slot] - 1,
					client.playerItemsN[slot], client.getUsername(),
					client.getAbsX(), client.getAbsY(), client.getHeightLevel());
			GameEngine.getItemManager().newDrop(i, client);
			deleteItem(client.playerItems[slot] - 1, slot,
					client.playerItemsN[slot]);
		}
	}

	public void makeFloorItem(int id, int amount) {
		FloorItem i = new FloorItem(id, amount, client.getUsername(),
				client.getAbsX(), client.getAbsY(), client.getHeightLevel());
		GameEngine.getItemManager().newDrop(i, client);
	}

	public void pickUpItem(int x, int y, int item) {
		GameEngine.getItemManager().pickupDrop(client, x, y,
				client.getHeightLevel(), item);
	}

	public boolean isItemInInventory(int itemID) {
		for (int i = 0; i < client.playerItems.length; i++) {
			if ((client.playerItems[i] - 1) == itemID) {
				return true;
			}
		}
		return false;
	}

	public int getItemCount(int itemId) {
		int total = 0;
		for (int i = 0; i < client.playerItems.length; i++) {
			if ((client.playerItems[i] - 1) == itemId) {
				total++;
			}
		}
		return total;
	}

	public boolean playerEquipContains(int itemID) {
		for (int i = 0; i < client.playerEquipment.length; i++) {
			if (client.playerEquipment[i] == itemID) {
				return true;
			}
		}
		return false;
	}

	public boolean playerHasItem(int itemID, int amt) {
		itemID++;
		int found = 0;
		for (int i = 0; i < client.playerItems.length; i++) {
			if (client.playerItems[i] == itemID) {
				if (client.playerItemsN[i] >= amt)
					return true;
				else
					found++;
			}
		}
		if (found >= amt)
			return true;
		return false;

	}

	// checks if the player has the staff type of the rune. - killamess
	public boolean staffType(int runeType) {

		int staffTypes[][] = {

		{ 554, 1387, 1393, 1401 }, { 555, 1383, 1395, 1403 },
				{ 556, 1381, 1397, 1405 }, { 557, 1385, 1399, 1407 }

		};
		int weapon = client.playerEquipment[Constants.WEAPON];

		for (int i = 0; i < staffTypes.length; i++) {
			if (runeType == staffTypes[i][0])
				for (int j = 1; j < 4; j++)
					if (weapon == staffTypes[i][j])
						return true;
		}
		return false;
	}

	public void deleteItem(int id, int amount) {
		deleteItem(id, getItemSlot(id), amount);
	}

	public void deleteItem(int id, int slot, int amount) {
		if (slot > -1) {
			if (client.playerItems[slot] == (id + 1)) {
				if (client.playerItemsN[slot] > amount)
					client.playerItemsN[slot] -= amount;
				else {
					client.playerItemsN[slot] = 0;
					client.playerItems[slot] = 0;
				}
				client.getActionSender().sendItemReset(3214);
			}
		}
	}

	public void replaceItem(int oldID, int newID) {
		deleteItem(oldID, 1);
		client.getActionSender().sendInventoryItem(newID, 1);
	}

	public int getItemSlot(int itemID) {
		for (int i = 0; i < client.playerItems.length; i++) {
			if ((client.playerItems[i] - 1) == itemID) {
				return i;
			}
		}
		return -1;
	}

	public int getItemAmount(int itemID) {
		int tempAmount = 0;
		for (int i = 0; i < client.playerItems.length; i++) {
			if ((client.playerItems[i] - 1) == itemID) {
				if (Item.itemStackable[itemID] || Item.itemIsNote[itemID]) {
					tempAmount += client.playerItemsN[i];
				} else {
					tempAmount += 1;
				}
			}
		}
		return tempAmount;
	}

	public int freeBankSlots() {
		int freeS = 0;
		for (int i = 0; i < client.getPlayerBankSize(); i++) {
			if (client.bankItems[i] <= 0) {
				freeS++;
			}
		}
		return freeS;
	}

	public int freeSlots() {
		int freeS = 0;
		for (int i = 0; i < client.playerItems.length; i++) {
			if (client.playerItems[i] <= 0) {
				freeS++;
			}
		}
		return freeS;
	}
}