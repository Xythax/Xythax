package org.xythax.model;

import org.xythax.world.ShopManager;

/**
 * Represents an item being sold in a shop
 * 
 * @author Graham
 */
public class ShopItem extends Item {

	/**
	 * The normal amount of items that are in the shop (so restocking/player
	 * stock going down can work).
	 */
	private int normalAmount;

	/**
	 * The last 'automatic' stock change.
	 */
	private long lastAutomaticStockChange;

	/**
	 * Is this item always stocked?
	 */
	private boolean isAlwaysStocked;

	/**
	 * Creates this shop item. Shop items created this way are automatically
	 * stocked.
	 * 
	 * @param id
	 *            The id.
	 * @param amount
	 *            The initial amount.
	 * @param normalAmount
	 *            The normal amount.
	 */
	public ShopItem(int id, int amount, int normalAmount) {
		super(id, amount);
		this.isAlwaysStocked = true;
		this.normalAmount = normalAmount;
		this.lastAutomaticStockChange = System.currentTimeMillis();
	}

	/**
	 * Creates this shop item. Shop items created this way are NOT automatically
	 * stocked.
	 * 
	 * @param id
	 *            The id.
	 * @param amount
	 *            The initial amount.
	 * @param normalAmount
	 *            The normal amount.
	 */
	public ShopItem(int id, int amount) {
		super(id, amount);
		this.isAlwaysStocked = false;
		this.lastAutomaticStockChange = System.currentTimeMillis();
	}

	/**
	 * Gets the normal amount.
	 * 
	 * @return
	 */
	public int getNormalAmount() {
		return this.normalAmount;
	}

	/**
	 * Gets the last automatic stock chage.
	 * 
	 * @return
	 */
	public long getLastAutomaticStockChange() {
		return this.lastAutomaticStockChange;
	}

	/**
	 * Updates the stock.
	 * 
	 * @return true if we should be removed, false if not.
	 */
	public boolean updateStock() {
		if (this.lastAutomaticStockChange + ShopManager.RESTOCK_DELAY < System
				.currentTimeMillis()) {
			this.lastAutomaticStockChange = System.currentTimeMillis();
			if (this.isAlwaysStocked) {
				if (this.getAmount() > this.normalAmount
						&& this.normalAmount > 0) {
					this.setAmount(this.getAmount() - 1);
				} else if (this.getAmount() < this.normalAmount) {
					this.setAmount(this.getAmount() + 1);
				}
				return false;
			} else {
				this.setAmount(this.getAmount() - 1);
				if (this.getAmount() <= 0) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * Is this item always automatically stocked?
	 * 
	 * @return
	 */
	public boolean isAlwaysStocked() {
		return this.isAlwaysStocked;
	}

	public void setLastAutomaticStockChange(long currentTimeMillis) {
		this.lastAutomaticStockChange = currentTimeMillis;
	}

}
