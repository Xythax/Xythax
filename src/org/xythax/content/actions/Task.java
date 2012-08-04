package org.xythax.content.actions;

/**
 * 
 * @author killamess
 * 
 */

public interface Task {

	public void execute(Action currentAction);

	public void loop(Action currentAction);

	public void stop(Action currentAction);

}
