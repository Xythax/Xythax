package org.xythax.content.skills;

import org.xythax.content.actions.Action;
import org.xythax.content.actions.ActionManager;
import org.xythax.content.actions.Task;
import org.xythax.content.controllers.Animation;
import org.xythax.model.Client;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;

public class SmithingSmelt implements Task {

	public Client client;

	public SmithingSmelt(Client c, int timestosmelt, int itemid) {
		readInput(c.playerLevel[13], itemidforstring(itemid, c), c,
				timestosmelt, -1, -1);
	}

	private String itemidforstring(int itemid, Client c) {
		if (itemid == 438 || itemid == 436)
			return "BRONZE";
		else if (itemid == 442)
			return "SILVER";
		else if (itemid == 440)
			return "IRON";
		else if (itemid == 444)
			return "GOLD";
		else if (itemid == 447)
			return "MITH";
		else if (itemid == 449)
			return "ADDY";
		else if (itemid == 451)
			return "RUNE";
		else if (itemid == 443 && c.getActionAssistant().playerHasItem(453, 2)
				&& c.getActionAssistant().playerHasItem(440, 1))
			return "STEEL";
		else if (itemid == 668)
			return "BLUR";
		else if (itemid == 2892)
			return "ELEMENTAL";
		else if (itemid == 453)
			return "COAL";
		return "UNKOWN";
	}

	public SmithingSmelt(Client c) {
		client = c;
	}

	public SmithingSmelt() {
	}

	public boolean doaction(final Client client, final int toadd,
			final int toremove, final int toremove2, final int timestosmelt,
			final int removeam, final int removeam2, final int xp) {

		client.hasfailed2123 = false;

		Animation.createAnimation(client, 899);
		client.getActionSender().sendWindowsRemoval();
		client.amountToMakeBars = timestosmelt;
		client.item = toadd;
		client.toremove = toremove;
		client.barsRemoved = toremove2;
		client.removeam = removeam;
		client.removeam2 = removeam2;
		client.xp = xp;
		ActionManager.addNewRequest(client, 19, 5);

		return false;
	}

	public void out(String s, Client c) {
		// TODO Auto-generated method stub

	}

	public void readInput(int level, String type, Client c, int objectx,
			int objecty, int objectid) {
		c.getActionSender().sendWindowsRemoval();
		synchronized (this) {
			if (type.equals("BRONZE") && level >= 1) {
				if (c.getActionAssistant().playerHasItem(438, 1)
						&& c.getActionAssistant().playerHasItem(436, 1)) {
					c.xp = 6 * Constants.SKILL_EXPERIENCE_MULTIPLIER;
					c.item = 2349;
					c.remove = 438;
					c.remove2 = 436;
					c.removeam = 1;
					c.removeam2 = 1;
				} else if (!c.getActionAssistant().playerHasItem(438, 1)) {
					c.getActionSender().sendWindowsRemoval();
					c.getActionSender().sendMessage(
							"You don't have enough tin to smelt this bar!");
					return;
				} else {
					c.getActionSender().sendWindowsRemoval();
					c.getActionSender().sendMessage(
							"You don't have enough copper to smelt this bar!");
					return;
				}
			} else if (type.equals("STEEL") && level >= 30) {
				c.xp = 17 * Constants.SKILL_EXPERIENCE_MULTIPLIER;
				c.item = 2353;
				c.remove = 440;
				c.removeam = 1;
				c.remove2 = 453;
				c.removeam2 = 2;
			} else if (type.equals("IRON") && level >= 15) {
				c.xp = 17 * Constants.SKILL_EXPERIENCE_MULTIPLIER;
				c.item = 2351;
				c.remove = 440;
				c.removeam = 1;
			} else if (type.equals("BLUR") && level >= 8) {
				c.xp = 8 * Constants.SKILL_EXPERIENCE_MULTIPLIER;
				c.item = -1;
				c.remove = 668;
				c.removeam = 1;
			} else if (type.equals("SILVER") && level >= 20) {
				c.xp = 13 * Constants.SKILL_EXPERIENCE_MULTIPLIER;
				c.item = 2355;
				c.remove = 442;
				c.removeam = 1;
			} else if (type.equals("GOLD") && level >= 40) {
				c.xp = 23 * Constants.SKILL_EXPERIENCE_MULTIPLIER;
				c.item = 2357;
				c.remove = 444;
				c.removeam = 1;
			} else if (type.equals("MITH") && level >= 50) {
				if (c.getActionAssistant().playerHasItem(453, 4)) {
					c.xp = 30 * Constants.SKILL_EXPERIENCE_MULTIPLIER;
					c.item = 2359;
					c.remove = 447;
					c.removeam = 1;
					c.remove2 = 453;
					c.removeam2 = 4;
				} else {
					c.getActionSender().sendWindowsRemoval();
					c.getActionSender().sendMessage(
							"You don't have enough coal to smelt this bar!");
					return;
				}
			} else if (type.equals("ADDY") && level >= 70) {
				if (c.getActionAssistant().playerHasItem(453, 6)) {
					c.xp = 38 * Constants.SKILL_EXPERIENCE_MULTIPLIER;
					c.item = 2361;
					c.remove = 449;
					c.remove2 = 453;
					c.removeam = 1;
					c.removeam2 = 6;
				} else {
					c.getActionSender().sendWindowsRemoval();
					c.getActionSender().sendMessage(
							"You don't have enough coal to smelt this bar!");
					return;
				}
			} else if (type.equals("RUNE") && level >= 85) {
				if (c.getActionAssistant().playerHasItem(453, 8)) {
					c.xp = 50 * Constants.SKILL_EXPERIENCE_MULTIPLIER;
					c.item = 2363;
					c.remove = 451;
					c.removeam = 1;
					c.remove2 = 453;
					c.removeam2 = 8;
				} else {
					c.getActionSender().sendWindowsRemoval();
					c.getActionSender().sendMessage(
							"You don't have enough coal to smelt this bar!");
					return;
				}
			} else if (type.equals("UNKOWN")) {
				c.getActionSender().sendMessage("Unknown id specified.");
				return;
			} else {
				c.getActionSender()
						.sendMessage(
								"You don't have a high enough smithing level to smelt this bar!");
				c.getActionSender().sendWindowsRemoval();
				return;
			}
			c.getActionSender().sendWindowsRemoval();
			doaction(c, c.item, c.remove, c.remove2, objectx, c.removeam,
					c.removeam2, c.xp);
		}
	}

	@Override
	public void execute(Action currentAction) {
		currentAction.getClient().getActionSender().sendWindowsRemoval();
		currentAction.setActionType(Action.type.LOOPING);

	}

	@Override
	public void loop(Action currentAction) {
		if (currentAction.getCurrentTick() > 0) {
			currentAction.setCurrentTick(currentAction.getCurrentTick() - 1);
			return;
		}

		Client client = currentAction.getClient();

		if (client == null) {
			stop(currentAction);
			return;
		}
		if (!client.getActionAssistant().playerHasItem(client.toremove, 1)
				|| !client.getActionAssistant().playerHasItem(
						client.barsRemoved, client.removeam2)
				&& client.barsRemoved > 0) {
			client.getActionSender().sendMessage("You have ran out of bars.");
			stop(currentAction);
			return;
		}
		if (client.amountToMakeBars <= 0) {
			stop(currentAction);
			return;
		}
		if (client.toremove == 447) {
			client.getActionAssistant().deleteItem(453, 1);
			client.getActionAssistant().deleteItem(453, 1);
			client.getActionAssistant().deleteItem(453, 1);
			client.getActionAssistant().deleteItem(453, 1);
		} else if (client.toremove == 438) {
			client.getActionAssistant().deleteItem(436, 1);
		} else if (client.toremove == 440 && client.barsRemoved != 453
				&& Utils.random(2) == 1) {
			client.getActionAssistant().deleteItem(440,
					client.getActionAssistant().getItemSlot(440), 1);
			client.getActionSender().sendMessage(
					"You fail to smelt the iron ore into a bar.");
			Animation.createAnimation(client, 899);
			client.hasfailed2123 = true;
		} else if (client.toremove == 440 && client.barsRemoved == 453) {
			client.getActionAssistant().deleteItem(453, 1);
			client.getActionAssistant().deleteItem(453, 1);
		} else if (client.toremove == 449) {
			client.getActionAssistant().deleteItem(453, 1);
			client.getActionAssistant().deleteItem(453, 1);
			client.getActionAssistant().deleteItem(453, 1);
			client.getActionAssistant().deleteItem(453, 1);
			client.getActionAssistant().deleteItem(453, 1);
			client.getActionAssistant().deleteItem(453, 1);
		} else if (client.toremove == 451) {
			client.getActionAssistant().deleteItem(453, 1);
			client.getActionAssistant().deleteItem(453, 1);
			client.getActionAssistant().deleteItem(453, 1);
			client.getActionAssistant().deleteItem(453, 1);
			client.getActionAssistant().deleteItem(453, 1);
			client.getActionAssistant().deleteItem(453, 1);
			client.getActionAssistant().deleteItem(453, 1);
			client.getActionAssistant().deleteItem(453, 1);
		}
		if (!client.hasfailed2123) {
			client.getActionSender().sendMessage(
					"You smelt the ore into a bar.");
			client.getActionAssistant().addSkillXP(client.xp, 13);
			client.getActionAssistant().replaceItem(client.toremove,
					client.item);
		}

		client.amountToMakeBars--;

		boolean failFlag = false;

		if (client.amountToMakeBars > 0) {
			if (client.toremove > 0) {
				if (!client.getActionAssistant().playerHasItem(client.toremove,
						1)) {
					failFlag = true;
				}
			}
			if (client.barsRemoved > 0) {
				if (client.removeam2 > 0) {
					if (!client.getActionAssistant().playerHasItem(
							client.barsRemoved, client.removeam2)) {
						failFlag = true;
					}
				}
			}
		} else {
			failFlag = true;
		}
		if (!failFlag) {
			Animation.createAnimation(client, 899);
		} else {
			stop(currentAction);
			return;
		}
		client.hasfailed2123 = false;
		currentAction.setCurrentTick(5);
	}

	@Override
	public void stop(Action currentAction) {
		Client client = currentAction.getClient();
		if (client != null) {
			client.getActionSender().sendAnimationReset();
			client.amountToMakeBars = 0;
			Animation.createAnimation(client, -1);
		}
		currentAction.setActionType(Action.type.TRASHING);
	}
}
