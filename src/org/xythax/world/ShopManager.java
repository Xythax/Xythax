package org.xythax.world;

import java.util.HashMap;
import java.util.Map;

import org.xythax.model.Shop;

/**
 * Manages shops
 * 
 * @author Graham/Killamess/Steven
 */
public class ShopManager {

	public Map<Integer, Shop> shops;

	public static final int RESTOCK_DELAY = 30000;

	public ShopManager() {
		this.shops = new HashMap<Integer, Shop>();

	}

	/**
	 * Gets the shops.
	 * 
	 * @return
	 */
	public Map<Integer, Shop> getShops() {
		return shops;
	}

	/**
	 * Processes shops.
	 */
	public void process() {
		for (Map.Entry<Integer, Shop> entry : shops.entrySet()) {
			entry.getValue().process();
		}
	}

}
