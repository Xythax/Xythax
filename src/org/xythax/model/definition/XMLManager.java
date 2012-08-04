package org.xythax.model.definition;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.xythax.model.WorldObject;
import org.xythax.model.definition.entity.NPCSizes;
import org.xythax.model.definition.skills.Axes;
import org.xythax.model.definition.skills.Catches;
import org.xythax.model.definition.skills.Cooking;
import org.xythax.model.definition.skills.Enchants;
import org.xythax.model.definition.skills.Projectile;
import org.xythax.model.definition.skills.Spell;
import org.xythax.model.definition.skills.Teleport;
import org.xythax.model.definition.skills.Trees;

import com.thoughtworks.xstream.XStream;

public class XMLManager {

	public static XStream xstream;

	static {
		xstream = new XStream();
		xstream.alias("object", WorldObject.class);
		xstream.alias("npc", NPCSizes.class);
		xstream.alias("spell", Spell.class);
		xstream.alias("teleport", Teleport.class);
		xstream.alias("projectile", Projectile.class);
		xstream.alias("enchant", Enchants.class);
		xstream.alias("tree", Trees.class);
		xstream.alias("axe", Axes.class);
		xstream.alias("food", Cooking.class);
		xstream.alias("catch", Catches.class);
		// xstream.alias("fire", FireMaking.class);

	}

	public static List<Trees> trees = new ArrayList<Trees>();
	public static List<Axes> axes = new ArrayList<Axes>();
	public static List<Enchants> enchants = new ArrayList<Enchants>();
	public static List<Spell> spells = new ArrayList<Spell>();
	public static List<Teleport> teleports = new ArrayList<Teleport>();
	public static List<Projectile> projectile = new ArrayList<Projectile>();
	public static List<NPCSizes> sizes = new ArrayList<NPCSizes>();
	public static List<WorldObject> objects = new ArrayList<WorldObject>();
	public static List<Cooking> ingredients = new ArrayList<Cooking>();
	public static List<Catches> catches = new ArrayList<Catches>();

	// public static List<FireMaking> fires = new ArrayList<FireMaking>();

	@SuppressWarnings("unchecked")
	public static void load() {
		try {

			int fileCount = 0;
			long start = System.currentTimeMillis();

			System.out.println("[XML] Loading files.");

			trees.addAll((List<Trees>) xstream.fromXML(new FileInputStream(
					"./data/world/xml/woodcutting/Trees.xml")));
			fileCount++;
			axes.addAll((List<Axes>) xstream.fromXML(new FileInputStream(
					"./data/world/xml/woodcutting/Axes.xml")));
			fileCount++;
			enchants.addAll((List<Enchants>) xstream
					.fromXML(new FileInputStream(
							"./data/world/xml/magic/enchants.xml")));
			fileCount++;
			spells.addAll((List<Spell>) xstream.fromXML(new FileInputStream(
					"./data/world/xml/magic/spells.xml")));
			fileCount++;
			teleports.addAll((List<Teleport>) xstream
					.fromXML(new FileInputStream(
							"./data/world/xml/magic/teleports.xml")));
			fileCount++;
			projectile.addAll((List<Projectile>) xstream
					.fromXML(new FileInputStream(
							"./data/world/xml/ranged/ammunition.xml")));
			fileCount++;
			sizes.addAll((List<NPCSizes>) xstream.fromXML(new FileInputStream(
					"./data/world/xml/npc/npcsizes.xml")));
			fileCount++;
			objects.addAll((List<WorldObject>) xstream
					.fromXML(new FileInputStream(
							"./data/world/xml/object/objects.xml")));
			fileCount++;
			ingredients.addAll((List<Cooking>) xstream
					.fromXML(new FileInputStream(
							"./data/world/xml/cooking/food.xml")));
			fileCount++;
			catches.addAll((List<Catches>) xstream.fromXML(new FileInputStream(
					"./data/world/xml/fishing/fish.xml")));
			fileCount++;
			// fires.addAll((List<FireMaking>) xstream.fromXML(new
			// FileInputStream("./data/world/xml/firemaking/fires.xml")));
			// fileCount++;

			long time = System.currentTimeMillis() - start;
			System.out.println("[XML] Loaded " + fileCount + " files in "
					+ time + "ms.");

		} catch (FileNotFoundException file) {
			file.printStackTrace();
		}
	}
}
