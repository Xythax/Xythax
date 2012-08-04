package org.xythax.content.actions;

import java.util.ArrayList;
import java.util.List;

import org.xythax.content.WalkToNPC;
import org.xythax.content.actions.tasks.ItemPickup;
import org.xythax.content.actions.tasks.OreSmelter;
import org.xythax.content.actions.tasks.PrayerDrain;
import org.xythax.content.actions.tasks.Skulling;
import org.xythax.content.actions.tasks.SpecialRegain;
import org.xythax.content.actions.tasks.StatRegain;
import org.xythax.content.objects.ObjectController;
import org.xythax.content.skills.Cooker;
import org.xythax.content.skills.CraftHide;
import org.xythax.content.skills.Fires;
import org.xythax.content.skills.Fishing;
import org.xythax.content.skills.Fletching;
import org.xythax.content.skills.GemCrafting;
import org.xythax.content.skills.Mining;
import org.xythax.content.skills.SmithingMakeItem;
import org.xythax.content.skills.SmithingSmelt;
import org.xythax.content.skills.WoodCutting;
import org.xythax.model.Client;
import org.xythax.model.Player;
import org.xythax.model.map.FollowEngine;
import org.xythax.world.ObjectManager;
import org.xythax.world.PlayerManager;

/**
 * 
 * @author killamess Only runs one action per client Background tasks have an
 *         exception.
 */

public class ActionManager {

	public static final int MAX_ACTIONS_AT_ONCE = 100; // keep it to around the
														// amount of players on
														// the server.
	public static Task[] task = new Task[MAX_ACTIONS_AT_ONCE];
	public static List<Action> action = new ArrayList<Action>();
	public static List<Player> players = new ArrayList<Player>();

	public static void addNewRequest(Client client, int skill, int tickRate) {

		ArrayList<Action> actions = new ArrayList<Action>(action);

		if (actions.size() >= MAX_ACTIONS_AT_ONCE) {
			client.getActionSender().sendMessage(
					"Sorry, Our action queue is at maximum capacity.");
			client.getActionSender().sendMessage(
					"Please try again in 5 seconds.");
			return;
		}

		for (Action currentAction : actions) {
			if (currentAction.getClient() == null || client == null)
				continue;
			if (currentAction.getClient().getUsername() == client.getUsername()) {
				action.remove(currentAction);
				continue;
			}
		}
		if (client != null)
			client.getActionSender().sendWindowsRemoval();

		action.add(new Action(client, skill, tickRate));
	}

	public static void processActions() {

		if (action.size() < 1)
			return;

		for (Player player : PlayerManager.getPlayerManager().getPlayers()) {
			if (player != null)
				players.add(player);
		}
		ArrayList<Action> actions = new ArrayList<Action>(action);

		for (Action currentAction : actions) {
			switch (currentAction.getActionType()) {

			case TRASHING:
				task[currentAction.getSkill()].stop(currentAction);
				action.remove(currentAction);
				break;

			case READING:
				task[currentAction.getSkill()].execute(currentAction);
				break;

			case LOOPING:
				task[currentAction.getSkill()].loop(currentAction);
				break;
			}
		}
		actions.clear();
		players.clear();
	}

	public static void removeAction(Action actionToRemove) {
		action.remove(actionToRemove);
	}

	public static void destructActions(String client) {
		if (action.isEmpty())
			return;

		ArrayList<Action> actions = new ArrayList<Action>(action);

		for (Action currentAction : actions) {
			if (currentAction.getUsername() == client) {
				currentAction.setActionType(Action.type.TRASHING);
				if (PlayerManager.getPlayerManager().getPlayerByName(client) != null)
					FollowEngine.resetFollowing(currentAction.getClient());
			}
		}
	}

	public static void loadAllActions() throws Exception {

		task[0] = new WoodCutting();
		task[1] = new SpecialRegain();
		task[2] = new PrayerDrain();
		task[3] = new StatRegain();
		task[5] = new ItemPickup();
		task[6] = new Skulling();
		// task[7] = new AutoTrainer();
		task[8] = new CraftHide();
		task[9] = new OreSmelter();
		task[10] = new Mining();
		task[11] = new ObjectController();
		task[12] = new WalkToNPC();
		// 13 available
		task[14] = new Cooker();
		task[15] = new GemCrafting();
		task[16] = new Fishing();
		task[17] = new Fletching();
		task[18] = new SmithingMakeItem();
		task[19] = new SmithingSmelt();
		task[20] = new Fires();
		task[21] = new ObjectManager();

		int loaded = 0;

		for (int i = 0; i < task.length; i++) {
			if (task[i] != null)
				loaded++;
		}
		loadable();
		System.out.println("Loaded " + loaded + " ActionManager scripts.");
	}

	public static void loadable() {
		addNewRequest(null, 1, 30);
		addNewRequest(null, 2, 6);
		addNewRequest(null, 3, 120);
		addNewRequest(null, 5, 1);
		addNewRequest(null, 6, 1);
		addNewRequest(null, 17, 2);
		addNewRequest(null, 20, 1);
		addNewRequest(null, 21, 1);
	}
}
