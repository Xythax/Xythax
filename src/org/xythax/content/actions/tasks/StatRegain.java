package org.xythax.content.actions.tasks;

import org.xythax.content.actions.Action;
import org.xythax.content.actions.ActionManager;
import org.xythax.content.actions.Task;
import org.xythax.model.Client;
import org.xythax.model.Player;
import org.xythax.utils.Constants;

/**
 * 
 * @author Killamess
 * 
 */

public class StatRegain implements Task {

	@Override
	public void execute(Action currentAction) {
		currentAction.setActionType(Action.type.LOOPING);
	}

	@Override
	public void loop(Action currentAction) {

		currentAction.setCurrentTick(currentAction.getCurrentTick() - 1);

		if (currentAction.getCurrentTick() == 0) {

			currentAction.setCurrentTick(currentAction.getActionTick());

			for (Player p : ActionManager.players) {

				if (p == null)
					continue;

				Client client = (Client) p;

				for (int i = 0; i < Constants.MAX_SKILLS; i++) {
					if (client.playerLevel[i] > client
							.getLevelForXP(client.playerXP[i])) {
						client.playerLevel[i]--;
					}
					if (i == 3) {

						if (client.hitpoints < client
								.getLevelForXP(client.playerXP[i])) {
							if (client.getPrayerHandler().clicked[6]) {
								client.getActionAssistant().addHP(1);
							}
							client.getActionAssistant().addHP(1);
						}

					} else if (client.playerLevel[i] < client
							.getLevelForXP(client.playerXP[i])) {
						if (i == 5) {
							continue;
						}
						client.playerLevel[i]++;
					}
					client.getActionAssistant().refreshSkill(i);
				}
			}
		}
	}

	@Override
	public void stop(Action currentAction) {
		currentAction.setActionType(Action.type.TRASHING);
	}
}
