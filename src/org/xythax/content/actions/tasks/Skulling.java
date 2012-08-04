package org.xythax.content.actions.tasks;

import org.xythax.content.actions.Action;
import org.xythax.content.actions.ActionManager;
import org.xythax.content.actions.Task;
import org.xythax.model.Client;
import org.xythax.model.Player;

/**
 * 
 * @author killamess
 * 
 */
public class Skulling implements Task {

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

				if (player == null)
					continue;

				Client client = (Client) player;
				if (client.getSkullTimer() < 1) {
					client.skullIcon = -1;
				} else {
					client.setSkullTimer(client.getSkullTimer() - 1);
					;
				}
			}
		}
	}

	@Override
	public void stop(Action currentAction) {
		currentAction.setActionType(Action.type.TRASHING);
	}

	public static void setSkulled(Client client, Client attacked) {
		if (attacked.skulledOn != client) {
			client.setSkullTimer(1200);
			client.skulledOn = attacked;
		} else {
			client.skullIcon = 0;
		}
	}

}
