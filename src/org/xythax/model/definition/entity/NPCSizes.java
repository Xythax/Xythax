package org.xythax.model.definition.entity;

public class NPCSizes {

	private int type;
	private int size;

	public NPCSizes() {
		this.type = -1;
		this.size = -1;
	}

	public NPCSizes(int type, int size) {
		this.type = type;
		this.size = size;
	}

	public int getType() {
		return type;
	}

	public int getSize() {
		return size;
	}
}
