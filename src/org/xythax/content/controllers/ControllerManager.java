package org.xythax.content.controllers;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Killamess I like to keep similar Lists together
 * 
 */
public class ControllerManager {

	protected static List<Damage> hits = new ArrayList<Damage>();
	protected static List<Graphics> gfx = new ArrayList<Graphics>();
	protected static List<Animation> animate = new ArrayList<Animation>();
	protected static List<Location> location = new ArrayList<Location>();

	public static void processRequests() {
		Damage.process();
		Graphics.process();
		Animation.process();
		Location.process();
	}
}
