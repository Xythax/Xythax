package org.xythax.model;

import java.util.Map;

import org.xythax.core.GameEngine;
import org.xythax.model.definition.entity.ItemDefinition;
import org.xythax.utils.Constants;

/**
 * A bunch of methods to assist with containers.
 * 
 * @author Graham
 */
public class ContainerAssistant {

	private Client client;

	public ContainerAssistant(Client client) {
		this.client = client;
	}

	public void moveBankItems(int from, int to, int moveWindow,
			boolean insertMode) {
		if (moveWindow == 3214) {
			int tempI;
			int tempN;
			tempI = client.playerItems[from];
			tempN = client.playerItemsN[from];

			client.playerItems[from] = client.playerItems[to];
			client.playerItemsN[from] = client.playerItemsN[to];
			client.playerItems[to] = tempI;
			client.playerItemsN[to] = tempN;
			client.getActionSender().sendItemReset(3214);
		}
		if (moveWindow == 5382 && from >= 0 && to >= 0
				&& from < client.getPlayerBankSize()
				&& to < client.getPlayerBankSize()) {
			if (insertMode) {
				int tempFrom = from;
				for (int tempTo = to; tempFrom != tempTo;) {
					if (tempFrom > tempTo) {
						swapBankItem(tempFrom, tempFrom - 1);
						tempFrom--;
					} else if (tempFrom < tempTo) {
						swapBankItem(tempFrom, tempFrom + 1);
						tempFrom++;
					}
				}
			} else {
				swapBankItem(from, to);
			}
		}
		if (moveWindow == 5382) {
			client.getActionSender().sendBankReset();
		}
		if (moveWindow == 5064) {
			int tempI;
			int tempN;
			tempI = client.playerItems[from];
			tempN = client.playerItemsN[from];

			client.playerItems[from] = client.playerItems[to];
			client.playerItemsN[from] = client.playerItemsN[to];
			client.playerItems[to] = tempI;
			client.playerItemsN[to] = tempN;
			client.getActionSender().sendItemReset(3214);
		}
	}

	public void swapBankItem(int from, int to) {
		int tempI = client.bankItems[from];
		int tempN = client.bankItemsN[from];
		client.bankItems[from] = client.bankItems[to];
		client.bankItemsN[from] = client.bankItemsN[to];
		client.bankItems[to] = tempI;
		client.bankItemsN[to] = tempN;
	}

	public void fromBank(int itemID, int fromSlot, int amount) {
		if (amount > 0) {
			if (client.bankItems[fromSlot] > 0) {
				if (!client.takeAsNote) {
					if (Item.itemStackable[client.bankItems[fromSlot] - 1]) {
						if (client.bankItemsN[fromSlot] > amount) {
							if (client.getActionSender().sendInventoryItem(
									(client.bankItems[fromSlot] - 1), amount)) {
								client.bankItemsN[fromSlot] -= amount;
								client.getActionSender().sendBankReset();
								client.getActionSender().sendItemReset(5064);
							}
						} else {
							if (client.getActionSender().sendInventoryItem(
									(client.bankItems[fromSlot] - 1),
									client.bankItemsN[fromSlot])) {
								client.bankItems[fromSlot] = 0;
								client.bankItemsN[fromSlot] = 0;
								client.getActionSender().sendBankReset();
								client.getActionSender().sendItemReset(5064);
							}
						}
					} else {
						while (amount > 0) {
							if (client.bankItemsN[fromSlot] > 0) {
								if (client.getActionSender().sendInventoryItem(
										(client.bankItems[fromSlot] - 1), 1)) {
									client.bankItemsN[fromSlot] += -1;
									amount--;
								} else {
									amount = 0;
								}
							} else {
								amount = 0;
							}
						}
						client.getActionSender().sendBankReset();
						client.getActionSender().sendItemReset(5064);
					}
				} else if (client.takeAsNote
						&& Item.itemIsNote[client.bankItems[fromSlot]]) {
					if (client.bankItemsN[fromSlot] > amount) {
						if (client.getActionSender().sendInventoryItem(
								client.bankItems[fromSlot], amount)) {
							client.bankItemsN[fromSlot] -= amount;
							client.getActionSender().sendBankReset();
							client.getActionSender().sendItemReset(5064);
						}
					} else {
						if (client.getActionSender().sendInventoryItem(
								client.bankItems[fromSlot],
								client.bankItemsN[fromSlot])) {
							client.bankItems[fromSlot] = 0;
							client.bankItemsN[fromSlot] = 0;
							client.getActionSender().sendBankReset();
							client.getActionSender().sendItemReset(5064);
						}
					}
				} else {
					client.getActionSender().sendMessage(
							"Item can't be drawn as note.");
					if (Item.itemStackable[client.bankItems[fromSlot] - 1]) {
						if (client.bankItemsN[fromSlot] > amount) {
							if (client.getActionSender().sendInventoryItem(
									(client.bankItems[fromSlot] - 1), amount)) {
								client.bankItemsN[fromSlot] -= amount;
								client.getActionSender().sendBankReset();
								client.getActionSender().sendItemReset(5064);
							}
						} else {
							if (client.getActionSender().sendInventoryItem(
									(client.bankItems[fromSlot] - 1),
									client.bankItemsN[fromSlot])) {
								client.bankItems[fromSlot] = 0;
								client.bankItemsN[fromSlot] = 0;
								client.getActionSender().sendBankReset();
								client.getActionSender().sendItemReset(5064);
							}
						}
					} else {
						while (amount > 0) {
							if (client.bankItemsN[fromSlot] > 0) {
								if (client.getActionSender().sendInventoryItem(
										(client.bankItems[fromSlot] - 1), 1)) {
									client.bankItemsN[fromSlot] += -1;
									amount--;
								} else {
									amount = 0;
								}
							} else {
								amount = 0;
							}
						}
						client.getActionSender().sendBankReset();
						client.getActionSender().sendItemReset(5064);
					}
				}
			}
		}
	}

	public boolean bankItem(int itemID, int fromSlot, int amount) {
		if (client.playerItemsN[fromSlot] <= 0) {
			return false;
		}
		if (!Item.itemIsNote[client.playerItems[fromSlot] - 1]) {
			if (client.playerItems[fromSlot] <= 0) {
				return false;
			}
			if (Item.itemStackable[client.playerItems[fromSlot] - 1]
					|| client.playerItemsN[fromSlot] > 1) {
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < client.getPlayerBankSize(); i++) {
					if (client.bankItems[i] == client.playerItems[fromSlot]) {
						if (client.playerItemsN[fromSlot] < amount)
							amount = client.playerItemsN[fromSlot];
						alreadyInBank = true;
						toBankSlot = i;
						i = client.getPlayerBankSize() + 1;
					}
				}

				if (!alreadyInBank
						&& client.getActionAssistant().freeBankSlots() > 0) {
					for (int i = 0; i < client.getPlayerBankSize(); i++) {
						if (client.bankItems[i] <= 0) {
							toBankSlot = i;
							i = client.getPlayerBankSize() + 1;
						}
					}
					client.bankItems[toBankSlot] = client.playerItems[fromSlot];
					if (client.playerItemsN[fromSlot] < amount) {
						amount = client.playerItemsN[fromSlot];
					}
					if ((client.bankItemsN[toBankSlot] + amount) <= Constants.MAX_ITEM_AMOUNT
							&& (client.bankItemsN[toBankSlot] + amount) > -1) {
						client.bankItemsN[toBankSlot] += amount;
					} else {
						client.getActionSender().sendMessage("Bank full!");
						return false;
					}
					client.getActionAssistant().deleteItem(
							(client.playerItems[fromSlot] - 1), fromSlot,
							amount);
					client.getActionSender().sendReplacementOfTempItem();
					client.getActionSender().sendBankReset();
					return true;
				} else if (alreadyInBank) {
					if ((client.bankItemsN[toBankSlot] + amount) <= Constants.MAX_ITEM_AMOUNT
							&& (client.bankItemsN[toBankSlot] + amount) > -1) {
						client.bankItemsN[toBankSlot] += amount;
					} else {
						client.getActionSender().sendMessage("Bank full!");
						return false;
					}
					client.getActionAssistant().deleteItem(
							(client.playerItems[fromSlot] - 1), fromSlot,
							amount);
					client.getActionSender().sendReplacementOfTempItem();
					client.getActionSender().sendBankReset();
					return true;
				} else {
					client.getActionSender().sendMessage("Bank full!");
					return false;
				}
			} else {
				itemID = client.playerItems[fromSlot];
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < client.getPlayerBankSize(); i++) {
					if (client.bankItems[i] == client.playerItems[fromSlot]) {
						alreadyInBank = true;
						toBankSlot = i;
						i = client.getPlayerBankSize() + 1;
					}
				}
				if (!alreadyInBank
						&& client.getActionAssistant().freeBankSlots() > 0) {
					for (int i = 0; i < client.getPlayerBankSize(); i++) {
						if (client.bankItems[i] <= 0) {
							toBankSlot = i;
							i = client.getPlayerBankSize() + 1;
						}
					}
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < client.playerItems.length; i++) {
							if ((client.playerItems[i]) == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							client.bankItems[toBankSlot] = client.playerItems[firstPossibleSlot];
							client.bankItemsN[toBankSlot] += 1;
							client.getActionAssistant()
									.deleteItem(
											(client.playerItems[firstPossibleSlot] - 1),
											firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					client.getActionSender().sendReplacementOfTempItem();
					client.getActionSender().sendBankReset();
					return true;
				} else if (alreadyInBank) {
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < client.playerItems.length; i++) {
							if ((client.playerItems[i]) == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							client.bankItemsN[toBankSlot] += 1;
							client.getActionAssistant()
									.deleteItem(
											(client.playerItems[firstPossibleSlot] - 1),
											firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					client.getActionSender().sendReplacementOfTempItem();
					client.getActionSender().sendBankReset();
					return true;
				} else {
					client.getActionSender().sendMessage("Bank full!");
					return false;
				}
			}
		} else if (Item.itemIsNote[client.playerItems[fromSlot] - 1]
				&& !Item.itemIsNote[client.playerItems[fromSlot] - 2]) {
			if (client.playerItems[fromSlot] <= 0) {
				return false;
			}
			if (Item.itemStackable[client.playerItems[fromSlot] - 1]
					|| client.playerItemsN[fromSlot] > 1) {
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < client.getPlayerBankSize(); i++) {
					if (client.bankItems[i] == (client.playerItems[fromSlot] - 1)) {
						if (client.playerItemsN[fromSlot] < amount)
							amount = client.playerItemsN[fromSlot];
						alreadyInBank = true;
						toBankSlot = i;
						i = client.getPlayerBankSize() + 1;
					}
				}

				if (!alreadyInBank
						&& client.getActionAssistant().freeBankSlots() > 0) {
					for (int i = 0; i < client.getPlayerBankSize(); i++) {
						if (client.bankItems[i] <= 0) {
							toBankSlot = i;
							i = client.getPlayerBankSize() + 1;
						}
					}
					client.bankItems[toBankSlot] = (client.playerItems[fromSlot] - 1);
					if (client.playerItemsN[fromSlot] < amount) {
						amount = client.playerItemsN[fromSlot];
					}
					if ((client.bankItemsN[toBankSlot] + amount) <= Constants.MAX_ITEM_AMOUNT
							&& (client.bankItemsN[toBankSlot] + amount) > -1) {
						client.bankItemsN[toBankSlot] += amount;
					} else {
						return false;
					}
					client.getActionAssistant().deleteItem(
							(client.playerItems[fromSlot] - 1), fromSlot,
							amount);
					client.getActionSender().sendReplacementOfTempItem();
					client.getActionSender().sendBankReset();
					return true;
				} else if (alreadyInBank) {
					if ((client.bankItemsN[toBankSlot] + amount) <= Constants.MAX_ITEM_AMOUNT
							&& (client.bankItemsN[toBankSlot] + amount) > -1) {
						client.bankItemsN[toBankSlot] += amount;
					} else {
						return false;
					}
					client.getActionAssistant().deleteItem(
							(client.playerItems[fromSlot] - 1), fromSlot,
							amount);
					client.getActionSender().sendReplacementOfTempItem();
					client.getActionSender().sendBankReset();
					return true;
				} else {
					client.getActionSender().sendMessage("Bank full!");
					return false;
				}
			} else {
				itemID = client.playerItems[fromSlot];
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < client.getPlayerBankSize(); i++) {
					if (client.bankItems[i] == (client.playerItems[fromSlot] - 1)) {
						alreadyInBank = true;
						toBankSlot = i;
						i = client.getPlayerBankSize() + 1;
					}
				}
				if (!alreadyInBank
						&& client.getActionAssistant().freeBankSlots() > 0) {
					for (int i = 0; i < client.getPlayerBankSize(); i++) {
						if (client.bankItems[i] <= 0) {
							toBankSlot = i;
							i = client.getPlayerBankSize() + 1;
						}
					}
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < client.playerItems.length; i++) {
							if ((client.playerItems[i]) == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							client.bankItems[toBankSlot] = (client.playerItems[firstPossibleSlot] - 1);
							client.bankItemsN[toBankSlot] += 1;
							client.getActionAssistant()
									.deleteItem(
											(client.playerItems[firstPossibleSlot] - 1),
											firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					client.getActionSender().sendReplacementOfTempItem();
					client.getActionSender().sendBankReset();
					return true;
				} else if (alreadyInBank) {
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < client.playerItems.length; i++) {
							if ((client.playerItems[i]) == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							client.bankItemsN[toBankSlot] += 1;
							client.getActionAssistant()
									.deleteItem(
											(client.playerItems[firstPossibleSlot] - 1),
											firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					client.getActionSender().sendReplacementOfTempItem();
					client.getActionSender().sendBankReset();
					return true;
				} else {
					client.getActionSender().sendMessage("Bank full!");
					return false;
				}
			}
		} else {
			client.getActionSender().sendMessage(
					"Item not supported " + (client.playerItems[fromSlot] - 1));
			return false;
		}
	}

	public void sellItem(int removeID, int removeSlot, int amount) {
		// TODO deal with notes
		if (client.playerItems[removeSlot] == 0)
			return;
		removeID = client.playerItems[removeSlot] - 1;
		int addToShopID = removeID;
		if (Item.itemIsNote[removeID]) {
			int unnoted = GameEngine.getItemManager().getUnnotedItem(removeID);
			if (unnoted != -1) {
				addToShopID = unnoted;
			}
		}
		if (client.getExtraData().containsKey("shop")) {
			int id = (Integer) client.getExtraData().get("shop");
			Shop shop = GameEngine.getShopManager().getShops().get(id);
			if (shop == null) {
				return;
			}
			ItemDefinition def = GameEngine.getItemManager().getItemDefinition(
					addToShopID);
			if (def == null) {
				return; // could not find item definition.
			}
			if (shop.getType() == Shop.Type.SPECIALIST) {
				boolean ok = false;
				for (Map.Entry<Integer, Item> entry : shop.getMap().entrySet()) {
					if (entry.getValue().getId() == addToShopID) {
						ok = true;
					}
				}
				if (!ok) {
					client.getActionSender().sendMessage(
							"You cannot sell " + def.getName().toLowerCase()
									+ " in this store.");
					return;
				}
			}
			if (!(shop.getFreeSlot() > 0)
					&& shop.getItemSlot(addToShopID) == -1) {
				client.getActionSender().sendMessage(
						"The shop is currently full!");
				return;
			}
			int has = client.getActionAssistant().getItemAmount(removeID);
			if (amount > has) {
				amount = has;
			}
			for (int i = 0; i < amount; i++) {
				int totalPrice = shop.getItemBuyValue(addToShopID);
				if (client.getActionAssistant().freeSlots() > 0
						|| client.getActionAssistant().isItemInInventory(
								shop.getCurrency())) {
					client.getActionAssistant().deleteItem(removeID,
							client.getActionAssistant().getItemSlot(removeID),
							1);
					client.getActionSender().sendInventoryItem(
							shop.getCurrency(), totalPrice);
					int slot = shop.getItemSlot(addToShopID);
					if (slot != -1) {
						shop.getItemBySlot(slot).setAmount(
								shop.getItemBySlot(slot).getAmount() + 1);
						((ShopItem) shop.getItemBySlot(slot))
								.setLastAutomaticStockChange(System
										.currentTimeMillis());
					} else {
						shop.addItem(new ShopItem(addToShopID, 1));
					}
				} else {
					client.getActionSender().sendMessage(
							"Not enough free space in your inventory.");
					break;
				}
			}
			client.getActionSender().sendItemReset(3823);
			shop.updated();
		}
	}

	public void buyItem(int removeID, int removeSlot, int amount) {
		if (client.getExtraData().containsKey("shop")) {
			int id = (Integer) client.getExtraData().get("shop");
			Shop shop = GameEngine.getShopManager().getShops().get(id);
			if (shop == null) {
				return;
			}
			int coinSlot = client.getActionAssistant().getItemSlot(
					shop.getCurrency());
			String coinName = GameEngine.getItemManager()
					.getItemDefinition(shop.getCurrency()).getName();

			if (coinName.toLowerCase() == "coin")
				coinName = "coin";

			if (coinSlot == -1) {
				client.getActionSender().sendMessage(
						"You don't have enough " + coinName.toLowerCase()
								+ "s.");
				return;
			}
			if (shop.getItemBySlot(removeSlot) == null) {
				return;
			}
			removeID = shop.getItemBySlot(removeSlot).getId();
			if (amount > 0
					&& shop.getItemBySlot(removeSlot).getId() == removeID) {
				if (amount > shop.getItemBySlot(removeSlot).getAmount()) {
					amount = shop.getItemBySlot(removeSlot).getAmount();
				}
				for (int i = 0; i < amount; i++) {

					int totalPrice = shop.getItemSellValue(removeID);

					if (client.playerItemsN[coinSlot] >= totalPrice) {
						if (client.getActionAssistant().freeSlots() > 0) {
							client.getActionAssistant().deleteItem(
									shop.getCurrency(), totalPrice);
							client.getActionSender().sendInventoryItem(
									removeID, 1);

							ShopItem item = (ShopItem) shop
									.getItemBySlot(removeSlot);

							item.setAmount(item.getAmount() - 1);
							item.setLastAutomaticStockChange(System
									.currentTimeMillis());
							if (item.getAmount() == 0
									&& !item.isAlwaysStocked()) {
								shop.removeItem(removeSlot);
								shop.addItem(new ShopItem(592, 100));
								// System.out.println("sort the shop");
								// shop.sortShopItems();
								// System.out.println("hopefully it works.");
								break;
							}
						} else {
							client.getActionSender().sendMessage(
									"Not enough space in your inventory.");
							break;
						}
					} else {
						client.getActionSender().sendMessage(
								"You don't have enough "
										+ coinName.toLowerCase() + "s.");
						break;
					}
				}
				client.getActionSender().sendItemReset(3823);
				shop.updated();
			}
		}
	}

}