package org.xythax.model.combat.magic;

import java.util.ArrayList;
import java.util.List;

import org.xythax.model.Client;
import org.xythax.model.definition.XMLManager;
import org.xythax.model.definition.skills.Spell;
import org.xythax.model.definition.skills.Teleport;

/**
 * 
 * @author Killamess
 * 
 */

public class Magic {

	public static Teleport teleport(int id) {
		for (Teleport t : XMLManager.teleports) {
			if (t.getId() == id)
				return t;
		}
		return null;
	}

	public static Spell spell(int id) {
		for (Spell s : XMLManager.spells) {
			if (s.getId() == id)
				return s;
		}
		return null;
	}

	public static List<Client> playersProcessing = new ArrayList<Client>();

	public static boolean inQueue(Client client) {
		return playersProcessing.contains(client);
	}
}
