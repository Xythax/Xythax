package org.xythax.content.actions.tasks;

import org.xythax.content.actions.Action;
import org.xythax.content.actions.ActionManager;
import org.xythax.content.actions.Task;
import org.xythax.model.Client;
import org.xythax.model.Player;

/**
 * 
 * @author Killamess
 * 
 */

public class PrayerDrain implements Task {

	@Override
	public void execute(Action currentAction) {
		currentAction.setActionType(Action.type.LOOPING);
	}

	@Override
	public void loop(Action currentAction) {

		currentAction.setCurrentTick(currentAction.getCurrentTick() - 1);

		if (currentAction.getCurrentTick() == 0) {

			currentAction.setCurrentTick(currentAction.getActionTick());

			for (Player player : ActionManager.players) {
				Client client = (Client) player;
				client.getPrayerHandler().prayerEvent();
			}
		}
	}

	@Override
	public void stop(Action currentAction) {
		currentAction.setActionType(Action.type.TRASHING);
	}
}
