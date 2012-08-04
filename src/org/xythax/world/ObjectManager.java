package org.xythax.world;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xythax.content.actions.Action;
import org.xythax.content.actions.Action.type;
import org.xythax.content.actions.Task;
import org.xythax.content.objects.ObjectReplacer;
import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.WorldObject;
import org.xythax.model.WorldObject.Face;
import org.xythax.model.definition.XMLManager;
import org.xythax.model.definition.entity.ObjectDefinition;

/**
 * ObjectManager
 * 
 * @author Graham
 * @author Ultimate
 */

public class ObjectManager implements Task {

	private Map<Integer, ObjectDefinition> definitions;

	public ObjectManager() throws Exception {
		definitions = new HashMap<Integer, ObjectDefinition>();
		loadDefinitions("./data/world/objectDefinitions.cfg");
	}

	/**
	 * Loads definitions from a file
	 * 
	 * @param name
	 * @throws IOException
	 */
	private void loadDefinitions(String name) throws IOException {
		BufferedReader file = null;
		try {
			file = new BufferedReader(new FileReader(name));
			while (true) {
				String line = file.readLine();
				if (line == null)
					break;
				int spot = line.indexOf('=');
				if (spot > -1) {
					String values = line.substring(spot + 1);
					values = values.replace("\t\t", "\t");
					values = values.replace("\t\t", "\t");
					values = values.replace("\t\t", "\t");
					values = values.trim();
					String[] valuesArray = values.split("\t");
					int type = Integer.valueOf(valuesArray[0]);
					String objectName = valuesArray[1];
					String size = valuesArray[2];
					int split = size.indexOf('x');
					int xsize = Integer.valueOf(size.substring(0, split));
					int ysize = Integer.valueOf(size.substring(split + 1));
					String description = valuesArray[3].substring(1,
							valuesArray[3].length() - 1);
					ObjectDefinition def = new ObjectDefinition(type,
							objectName, description, xsize, ysize);
					definitions.put(def.getType(), def);
				}
			}

		} finally {
			if (file != null)
				file.close();
		}
	}

	public boolean objectsExists(int id) {
		for (ObjectDefinition o : definitions.values()) {
			int id2 = o.getType();
			if (id == id2)
				return true;
		}
		return false;
	}

	public ObjectDefinition getDefinition(int id) {
		return definitions.get(id);
	}

	public void addObject(WorldObject object) {
		XMLManager.objects.add(object);
		showObject(object);
	}

	public void removeObject(WorldObject object) {
		XMLManager.objects.remove(object);
		showObject(object);
	}

	public WorldObject getObjectAt(int x, int y, int z) {
		for (WorldObject o : XMLManager.objects) {
			if (o.getObjectX() == x && o.getObjectY() == y
					&& o.getObjectHeight() == z) {
				return o;
			}
		}
		return null;
	}

	public void replaceTemporaryObject(final WorldObject oldObject,
			final WorldObject newObject) {
		XMLManager.objects.remove(oldObject);
		showObject(newObject);
	}

	public void replaceObject(final WorldObject oldObject,
			final WorldObject newObject, final int respawnTime) {
		addObject(newObject);
		/*
		 * EventManager.getSingleton().addEvent(null, new Event() {
		 * 
		 * @Override public void execute(EventContainer container) {
		 * removeObject(newObject); addObject(oldObject); container.stop(); }
		 * 
		 * @Override public void stop() { removeObject(oldObject); }
		 * 
		 * }, respawnTime * 1000);
		 */
	}

	public int objectCount() {
		return XMLManager.objects.size();
	}

	public void reload(Client c) {
		for (WorldObject o : XMLManager.objects) {
			c.getActionSender().sendObject(o.getObjectID(), o.getObjectX(),
					o.getObjectY(), o.getObjectHeight(), o.getObjectFace(),
					o.getObjectType());
		}
	}

	public void showObject(WorldObject o) {
		GameEngine.getGlobalActions().sendObject(o.getObjectID(),
				o.getObjectX(), o.getObjectY(), o.getObjectHeight(),
				o.getObjectFace(), o.getObjectType());
	}

	@Override
	public void execute(Action currentAction) {
		currentAction.setActionType(type.LOOPING);
	}

	@Override
	public void loop(Action currentAction) {

		if (objectReplacements.size() == 0)
			return;

		List<ObjectReplacer> newList = new ArrayList<ObjectReplacer>(
				objectReplacements);

		for (ObjectReplacer r : newList) {
			if (r.getChangeBackDelay() == 0) {
				GameEngine.getObjectManager().addObject(
						new WorldObject(r.getOldObject(), r.getLocation()[0], r
								.getLocation()[1], r.getLocation()[2],
								Face.NORTH, 10));
				objectReplacements.remove(r);
			} else {
				r.changeBackDelay--;
			}
		}
		newList.clear();
	}

	@Override
	public void stop(Action currentAction) {
		currentAction.setActionType(Action.type.TRASHING);
	}

	public static List<ObjectReplacer> objectReplacements = new ArrayList<ObjectReplacer>();

	public static void newTempObject(int oldO, int newO, int[] location,
			int delay) {
		GameEngine.getObjectManager().addObject(
				new WorldObject(newO, location[0], location[1], location[2],
						Face.NORTH, 10));
		objectReplacements.add(new ObjectReplacer(oldO, newO, delay, location));
	}
}
