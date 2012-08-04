package org.xythax.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xythax.core.GameEngine;

/**
 * Represents a single shop.
 * 
 * @author Graham
 */
public class Shop extends ItemContainer {

	public static enum Type {
		GENERAL, SPECIALIST
	}

	private int id;
	private String name;
	private Type type;
	private int currency;

	private static final int MAX_SHOP_ITEMS = 50;

	/**
	 * Create a shop.
	 * 
	 * @param id
	 * @param name
	 * @param type
	 * @param currency
	 */
	public Shop(int id, String name, Type type, int currency) {
		super(MAX_SHOP_ITEMS);
		this.id = id;
		this.name = name;
		this.type = type;
		this.currency = currency;
	}

	/**
	 * Gets this shops id.
	 * 
	 * @return
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Gets this shops name.
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets this shops type.
	 * 
	 * @return
	 */
	public Type getType() {
		return this.type;
	}

	/**
	 * Updates stock etc.
	 */
	public void process() {
		List<Integer> remove = new ArrayList<Integer>();
		for (Map.Entry<Integer, Item> entry : items.entrySet()) {
			if (entry.getValue() instanceof ShopItem) {
				ShopItem si = (ShopItem) entry.getValue();
				if (si.updateStock()) {
					remove.add(entry.getKey());
				}
			}
		}
		for (int slot : remove) {
			this.removeItem(slot);
		}
		updated();
	}

	/**
	 * Gets the current shop currency.
	 * 
	 * @return
	 */
	public int getCurrency() {
		return this.currency;
	}

	/**
	 * Is an item sellable?
	 * 
	 * @param id
	 * @return
	 */
	public boolean isItemSellable(int id) {
		if (type == Type.GENERAL)
			return true;
		for (Map.Entry<Integer, Item> entry : items.entrySet()) {
			if (entry.getValue() instanceof ShopItem) {
				ShopItem si = (ShopItem) entry.getValue();
				if (si.getId() == id)
					return true;
			}
		}
		return false;
	}

	/**
	 * Gets the normal number of an item.
	 * 
	 * @param id
	 * @return
	 */
	public int normalNumberOf(int id) {
		int ct = 0;
		for (Map.Entry<Integer, Item> entry : items.entrySet()) {
			if (!(entry.getValue() instanceof ShopItem))
				continue;
			ShopItem si = (ShopItem) entry.getValue();
			if (si.getId() == id) {
				ct += si.getNormalAmount();
			}
		}
		return ct;
	}

	/**
	 * Gets an item buy value.
	 * 
	 * @param removeID
	 * @return
	 */
	public int getItemBuyValue(int id) {
		double shopValue = GameEngine.getItemManager().getItemDefinition(id)
				.getShopValue();
		double totalPrice = shopValue * 1.26875;
		if (type == Type.GENERAL) {
			totalPrice *= 1.25;
			totalPrice *= 0.4;
		} else {
			totalPrice *= 0.6;
		}
		return (int) Math.ceil(totalPrice);
	}

	/**
	 * Gets an item buy value.
	 * 
	 * @param removeID
	 * @return
	 */
	public int getItemSellValue(int id) {
		double shopValue = GameEngine.getItemManager().getItemDefinition(id)
				.getShopValue();
		double totalPrice = shopValue * 1.26875;
		if (type == Type.GENERAL) {
			totalPrice *= 1.25;
		} else {
			totalPrice *= 0.6;
		}
		return (int) Math.floor(totalPrice);
	}

	/**
	 * We've updated.
	 */
	public void updated() {
		for (Player p : GameEngine.getPlayerManager().getPlayers()) {
			if (p == null)
				continue;
			if (p.isActive && !p.disconnected) {
				Client c = (Client) p;
				if (c.getExtraData().containsKey("shop")) {
					if (((Integer) c.getExtraData().get("shop")) == id) {
						c.getActionSender().sendShopReset(this);
					}
				}
			}
		}
	}

}
