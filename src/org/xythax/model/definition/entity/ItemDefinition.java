package org.xythax.model.definition.entity;

/**
 * A type of item.
 * 
 * @author Graham
 */
public class ItemDefinition {

	private int id;
	private String name;
	private double shopValue;
	private double lowAlch;
	private double highAlch;
	private int[] bonuses;
	private String description;

	public ItemDefinition(int id, String name, String description,
			double shopValue, double lowAlch, double highAlch, int[] bonuses) {
		this.id = id;
		this.name = name;
		this.shopValue = shopValue;
		this.highAlch = lowAlch;
		this.lowAlch = highAlch;
		this.bonuses = bonuses;
		this.description = description;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		if (getId() == -1)
			return "Unarmed";
		return this.name;
	}

	public double getShopValue() {
		return this.shopValue;
	}

	public double getLowAlch() {
		return this.lowAlch;
	}

	public double getHighAlch() {
		return this.highAlch;
	}

	public int[] getBonuses() {
		return this.bonuses;
	}

	public int getBonus(int i) {
		return this.bonuses[i];
	}

	public String getDescription() {
		return this.description;
	}

}
