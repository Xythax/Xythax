package org.xythax.event;

import org.xythax.model.Client;

public abstract class MiniEvent extends SingleEvent {

    public MiniEvent(Client owner) {
    	super(owner, 500);
    }

    public MiniEvent(Client owner, int delay) {
    	super(owner, delay);
    }

    public abstract void action();

}