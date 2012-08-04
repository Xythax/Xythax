package org.xythax.model;

import java.util.concurrent.LinkedBlockingDeque;

import org.xythax.core.DelayedEventHandler;
import org.xythax.model.snapshot.Snapshot;

public class World {
	/**
	 * World instance
	 */
	private static World world;
	/**
	 * The delayedeventhandler instance
	 */
	private DelayedEventHandler delayedEventHandler;

	/**
	 * returns the only instance of this world, if there is not already one,
	 * makes it and loads everything
	 */
	public static synchronized World getWorld() {
		if (world == null) {
			world = new World();
		}
		return world;
	};

	/**
	 * Double ended queue to store snapshots into
	 */
	private LinkedBlockingDeque<Snapshot> snapshots = new LinkedBlockingDeque<Snapshot>();

	/**
	 * Returns double-ended queue for snapshots.
	 */
	public synchronized LinkedBlockingDeque<Snapshot> getSnapshots() {
		return snapshots;
	}

	/**
	 * Add entry to snapshots
	 */
	public synchronized void addEntryToSnapshots(Snapshot snapshot) {
		snapshots.offerFirst(snapshot);
	}

	/**
	 * Gets the DelayedEventHandler instance
	 */
	public DelayedEventHandler getDelayedEventHandler() {
		return delayedEventHandler;
	}

	/**
	 * Sets the DelayedEventHandler instance
	 */
	public void setDelayedEventHandler(DelayedEventHandler delayedEventHandler) {
		this.delayedEventHandler = delayedEventHandler;
	}


}
