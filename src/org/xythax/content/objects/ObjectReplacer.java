package org.xythax.content.objects;

/**
 * 
 * @author killamess
 * 
 */

public class ObjectReplacer {

	private int oldObject;
	private int newObject;
	public int changeBackDelay;
	private int[] location;

	public ObjectReplacer(int oldO, int newO, int delay, int[] pointer) {
		this.oldObject = oldO;
		this.newObject = newO;
		this.changeBackDelay = delay;
		this.location = pointer;
	}

	public int getOldObject() {
		return oldObject;
	}

	public int getNewObject() {
		return newObject;
	}

	public int getChangeBackDelay() {
		return changeBackDelay;
	}

	public int[] getLocation() {
		return location;
	}

}
