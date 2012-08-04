package org.xythax.content.actions.tasks;

import org.xythax.content.actions.Action;
import org.xythax.content.actions.ActionManager;
import org.xythax.content.actions.Task;
import org.xythax.content.controllers.Animation;
import org.xythax.model.Client;
import org.xythax.model.Player;
import org.xythax.model.map.TileManager;

/**
 * 
 * @author killamess
 * 
 */

public class ItemPickup implements Task {

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

				if (p.pickup[0] < 1)
					continue;
				p.resetFaceDirection();
				if (TileManager.calculateDistance(p.pickup, p) == 0) {
					((Client) p).getActionAssistant().pickUpItem(p.pickup[0],
							p.pickup[1], p.pickup[2]);
					for (int pickupReset = 0; pickupReset < p.pickup.length; pickupReset++) {
						p.pickup[pickupReset] = -1;
					}
				} else if (TileManager.calculateDistance(p.pickup, p) == 1
						&& ((Client) p).getFreezeDelay() > 0) {
					((Client) p).getActionAssistant().pickUpItem(p.pickup[0],
							p.pickup[1], p.pickup[2]);
					Animation.addNewRequest(p, 832, 1);
					for (int pickupReset = 0; pickupReset < p.pickup.length; pickupReset++) {
						p.pickup[pickupReset] = -1;
					}
				}
			}
		}
	}

	@Override
	public void stop(Action currentAction) {
		currentAction.setActionType(Action.type.TRASHING);
	}
}
