package org.xythax.event;

import org.xythax.model.Client;

public abstract class SingleEvent extends DelayedEvent {

    public SingleEvent(Client owner, int delay) {
    	super(owner, delay);
    }

    public abstract void action();

    public void run() {
		action();
		super.matchRunning = false;
    }

}