package org.xythax.model;

import java.util.HashMap;
import java.util.Map;

/**
 * An item container
 * 
 * @author Graham
 */
public class ItemContainer {

	/**
	 * A map of slot ID to item data.
	 */
	protected Map<Integer, Item> items;

	/**
	 * The number of slots in the container.
	 */
	private int containerSize;

	/**
	 * Initialises the container, reserving memory for a set size.
	 * 
	 * @param size
	 *            The size of the container.
	 */
	public ItemContainer(int size) {
		items = new HashMap<Integer, Item>(size);
		containerSize = size;
	}

	/**
	 * Adds an item to the container.
	 * 
	 * @param item
	 *            The item to add.
	 */
	public void addItem(Item item) {
		int slot = getFreeSlot();
		if (slot != -1) {
			items.put(slot, item);
		} else {
			// TODO inform player that their inventory is full
		}
	}

	public int getFreeSlot() {
		for (int i = 0; i < containerSize; i++) {
			if (!items.containsKey(i))
				return i;
		}
		return -1;
	}

	/**
	 * Removes the item in the specified slot.
	 * 
	 * @param slot
	 *            The slot.
	 */
	public void removeItem(int slot) {
		items.remove(slot);
	}

	/**
	 * Get item by slot.
	 * 
	 * @param slot
	 * @return
	 */
	public Item getItemBySlot(int slot) {
		return items.get(slot);
	}

	/**
	 * Gets the next slot for the specified item.
	 * 
	 * @return The item slot, or -1 if it does not exist.
	 */
	public int getItemSlot(int id) {
		for (Map.Entry<Integer, Item> entry : items.entrySet()) {
			if (entry.getValue().getId() == id) {
				return entry.getKey();
			}
		}
		return -1;
	}

	/**
	 * Gets the nubmer of an item.
	 * 
	 * @param id
	 * @return
	 */
	public int numberOf(int id) {
		int ct = 0;
		for (Map.Entry<Integer, Item> entry : items.entrySet()) {
			if (entry.getValue().getId() == id) {
				ct += entry.getValue().getAmount();
			}
		}
		return ct;
	}

	/**
	 * Gets the size of this container.
	 * 
	 * @return The size of this container.
	 */
	public int getContainerSize() {
		return containerSize;
	}

	public Map<Integer, Item> getMap() {
		return this.items;
	}
}