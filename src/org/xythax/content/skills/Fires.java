package org.xythax.content.skills;

import org.xythax.content.actions.Action;
import org.xythax.content.actions.Task;

/**
 * 
 * @author Killamess A fire on the ground. NOT COMPLETED - xp - delay - level
 *         need to be added.
 */
public class Fires implements Task {

	@Override
	public void execute(Action currentAction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loop(Action currentAction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop(Action currentAction) {
		// TODO Auto-generated method stub

	}

	/*
	 * public Fires(String name, int[] pointer, int delay) { this.owner = name;
	 * this.location = pointer; this.timer = delay; }
	 * 
	 * @Override public void execute(Action currentAction) {
	 * currentAction.setActionType(Action.type.LOOPING); }
	 * 
	 * @Override public void loop(Action currentAction) {
	 * 
	 * ArrayList<Fires> newList = new ArrayList<Fires>(FireMaking.fires);
	 * 
	 * if (newList.size() == 0) return;
	 * 
	 * for (Fires f : newList) {
	 * 
	 * if (f.timer == 0) {
	 * 
	 * WorldObject removalObject = new WorldObject(-1, f.location[0],
	 * f.location[1], f.location[2], Face.NORTH, 10); FloorItem ashes = new
	 * FloorItem(592, 1, f.getOwner(), f.location[0], f.location[1],
	 * f.location[2]);
	 * 
	 * Server.getItemManager().newDrop(ashes, f.getOwner());
	 * Server.getObjectManager().addObject(removalObject);
	 * 
	 * FireMaking.fires.remove(f); } else f.timer--; } newList.clear(); }
	 * 
	 * //should not need to close down this process
	 * 
	 * @Override public void stop(Action currentAction) {
	 * currentAction.setActionType(Action.type.LOOPING); }
	 * 
	 * public Fires() { }
	 * 
	 * String owner; int[] location = new int[3]; int timer;
	 * 
	 * public int getDelay() { return timer; } public String getOwner() { return
	 * owner; } public int[] getLocation() { return location; }
	 */
}
