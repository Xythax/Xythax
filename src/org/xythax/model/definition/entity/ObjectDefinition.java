package org.xythax.model.definition.entity;

/**
 * Object definition
 * 
 * @author Graham
 */
public class ObjectDefinition {

	private int type;
	private String name;
	private String description;
	private int xsize;
	private int ysize;

	public ObjectDefinition(int type, String name, String description,
			int xsize, int ysize) {
		this.type = type;
		this.name = name;
		this.description = description;
		this.xsize = xsize;
		this.ysize = ysize;
	}

	public int getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public int getXSize() {
		return this.xsize;
	}

	public int getYSize() {
		return this.ysize;
	}

}
