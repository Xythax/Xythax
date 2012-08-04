package org.xythax.content;

import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.Item;
import org.xythax.model.Trade;

/**
 * Handles in-game trading
 * 
 * @author Graham
 */
public class TradeHandler {

	private Client client;
	private Trade currentTrade = null;

	public void tradeItem(int itemID, int fromSlot, int amount) {
		if (stage != 1)
			return;
		if (amount > 0 && itemID == (client.playerItems[fromSlot] - 1)) {
			if (amount > client.playerItemsN[fromSlot]) {
				amount = client.playerItemsN[fromSlot];
			}
			boolean isInTrade = false;
			for (int i = 0; i < offer.length; i++) {
				if (offer[i] == client.playerItems[fromSlot]) {
					if (Item.itemStackable[(client.playerItems[fromSlot] - 1)]
							|| Item.itemIsNote[(client.playerItems[fromSlot] - 1)]) {
						offerN[i] += amount;
						isInTrade = true;
						break;
					}
				}
			}
			if (!isInTrade) {
				for (int i = 0; i < offer.length; i++) {
					if (offer[i] <= 0) {
						offer[i] = client.playerItems[fromSlot];
						offerN[i] = amount;
						break;
					}
				}
			}
			if (client.playerItemsN[fromSlot] == amount) {
				client.playerItems[fromSlot] = 0;
			}
			client.playerItemsN[fromSlot] -= amount;
			resetMyItems(3415);
			Client other = getReciever();
			if (other != null) {
				other.getTradeHandler().resetReciversItems(3416);
			}
			if (accepted || other.getTradeHandler().accepted()) {
				accepted = false;
				other.getTradeHandler().setAccepted(false);
				client.getActionSender().sendQuest("", 3431);
				if (other != null) {
					other.getActionSender().sendQuest("", 3431);
				}
			}
			client.getActionSender().sendItemReset(3322);
		}
	}

	public void removeItemFromTrade(int itemID, int fromSlot, int amount) {
		if (stage == 2)
			return;
		if (amount > 0 && (itemID + 1) == offer[fromSlot]) {
			if (amount > offerN[fromSlot]) {
				amount = offerN[fromSlot];
			}
			client.getActionSender().sendInventoryItem(offer[fromSlot] - 1,
					amount);
			if (amount == offerN[fromSlot]) {
				offer[fromSlot] = 0;
			}
			offerN[fromSlot] -= amount;
			resetMyItems(3415);
			Client other = getReciever();
			if (other != null) {
				other.getTradeHandler().resetReciversItems(3416);
			}
			if (accepted || other.getTradeHandler().accepted()) {
				accepted = false;
				other.getTradeHandler().setAccepted(false);
				client.getActionSender().sendQuest("", 3431);
				if (other != null) {
					other.getActionSender().sendQuest("", 3431);
				}
			}
			client.getActionSender().sendItemReset(3322);
		}
	}

	public TradeHandler(Client client) {
		this.client = client;
	}

	public void requestTrade(Client otherClient) {
		if (!client.withinInteractionDistance(otherClient)) {
			return;
		}
		//if (client.getPrivileges() >= 1 && otherClient.getPrivileges() == 0
				//&& !(client.getPrivileges() >= 3)) {
			//client.getActionSender().sendMessage(
					//"You are restricted from trading non-ranked players.");
			//return;
		//}
		//if (client.getPrivileges() == 0 && otherClient.getPrivileges() >= 1
				//&& !(client.getPrivileges() >= 3)) {
			//client.getActionSender().sendMessage(
					//"You are restricted from trading ranked players.");
			//return;
		//}
		if (currentTrade != null) {
			if (currentTrade.getEstablisher() == otherClient) {
				answerTrade(otherClient);
				return;
			} else {
				abortCurrentTrade();
			}
		}
		currentTrade = new Trade(client, otherClient);
		otherClient.getTradeHandler().setCurrentTrade(currentTrade);
		otherClient.getActionSender().sendMessage(
				client.getUsername() + ":tradereq:");
		client.getActionSender().sendMessage("Sending trade request...");
	}

	public int stage = 0;
	public boolean accepted = false;

	public void answerTrade(Client otherClient) {
		if (!client.withinInteractionDistance(otherClient)) {
			return;
		}
		if (currentTrade == null) {
			requestTrade(otherClient);
		} else {
			if (currentTrade.isOpen()) {
				abortCurrentTrade();
				requestTrade(otherClient);
			} else {
				currentTrade.setOpen(true);
				client.getActionSender().sendFrame248(3323, 3321);
				client.getActionSender().sendItemReset(3322);
				resetMyItems(3415);
				resetReciversItems(3416);
				client.getActionSender().sendQuest(
						"Trading With: " + otherClient.getUsername(), 3417);
				client.getActionSender().sendQuest("", 3431);
				otherClient.getActionSender().sendFrame248(3323, 3321);
				otherClient.getActionSender().sendItemReset(3322);
				otherClient.getTradeHandler().resetMyItems(3415);
				otherClient.getTradeHandler().resetReciversItems(3416);
				otherClient.getActionSender().sendQuest(
						"Trading With: " + client.getUsername(), 3417);
				otherClient.getActionSender().sendQuest("", 3431);
				accepted = false;
				// GameEngine.getPlayerManager().saveGame((Client) client,
				// false);
				// GameEngine.getPlayerManager().saveGame((Client) otherClient,
				// false);
				client.getTradeHandler().stage = 1;
				otherClient.getTradeHandler().stage = 1;
				// stage = 1;

			}
		}
	}

	private int[] offer = new int[28];
	private int[] offerN = new int[28];

	public void resetMyItems(int frame) {
		client.outStream.createFrameVarSizeWord(53);
		client.outStream.writeWord(frame);
		client.outStream.writeWord(offer.length);
		for (int i = 0; i < offer.length; i++) {
			if (offerN[i] > 254) {
				client.outStream.writeByte(255);
				client.outStream.writeDWord_v2(offerN[i]);
			} else {
				client.outStream.writeByte(offerN[i]);
			}
			client.outStream.writeWordBigEndianA(offer[i]);
		}
		client.outStream.endFrameVarSizeWord();
	}

	public Client getReciever() {
		if (currentTrade == null) {
			return null;
		}
		Client reciever = null;
		if (currentTrade.getReceiver() != client) {
			reciever = currentTrade.getReceiver();
		}
		if (currentTrade.getEstablisher() != client) {
			reciever = currentTrade.getEstablisher();
		}
		return reciever;
	}

	public void resetReciversItems(int frame) {
		Client other = getReciever();
		if (other == null)
			return;
		int[] offer = other.getTradeHandler().getOffer();
		int[] offerN = other.getTradeHandler().getOfferN();
		client.outStream.createFrameVarSizeWord(53);
		client.outStream.writeWord(frame);
		client.outStream.writeWord(offer.length);
		for (int i = 0; i < offer.length; i++) {
			if (offerN[i] > 254) {
				client.outStream.writeByte(255);
				client.outStream.writeDWord_v2(offerN[i]);
			} else {
				client.outStream.writeByte(offerN[i]);
			}
			client.outStream.writeWordBigEndianA(offer[i]); // id
		}
		client.outStream.endFrameVarSizeWord();
	}

	public int[] getOffer() {
		return offer;
	}

	public int[] getOfferN() {
		return offerN;
	}

	public void declineTrade() {
		Client other = getReciever();
		if (other != null) {
			other.getActionSender().sendMessage(
					client.getUsername() + " has declined the trade.");
			other.getTradeHandler().setStage(0);
		}
		client.getActionSender().sendMessage("You decline the trade.");
		client.getTradeHandler().setStage(0);
		abortCurrentTrade();
	}

	public void abortCurrentTrade() {
		if (currentTrade.isOpen()) {
			currentTrade.getReceiver().getActionSender().sendWindowsRemoval();
			currentTrade.getEstablisher().getActionSender()
					.sendWindowsRemoval();
			currentTrade.getReceiver().getTradeHandler()
					.transferOfferToInventory();
			currentTrade.getEstablisher().getTradeHandler()
					.transferOfferToInventory();
		}
		if (currentTrade.getReceiver() != client) {
			currentTrade.getReceiver().getTradeHandler().setCurrentTrade(null);
			currentTrade.getReceiver().getTradeHandler().setAccepted(false);
		}
		if (currentTrade.getEstablisher() != client) {
			currentTrade.getEstablisher().getTradeHandler()
					.setCurrentTrade(null);
			currentTrade.getEstablisher().getTradeHandler().setAccepted(false);
		}
		if (currentTrade.getReceiver().getTradeHandler().client != null) {
			currentTrade.getReceiver().getTradeHandler().setStage(0);
		}
		if (currentTrade.getEstablisher().getTradeHandler().client != null) {
			currentTrade.getEstablisher().getTradeHandler().setStage(0);
		}

		currentTrade = null;
		accepted = false;
		// stage = 0;
	}

	public void transferOfferToInventory() {
		Client other = getReciever();
		if (other == null) {
			return;
		}
		for (int i = 0; i < offer.length; i++) {
			if (offer[i] == 0)
				continue;

			client.getActionSender().sendInventoryItem(offer[i] - 1, offerN[i]);
			offer[i] = 0;
			offerN[i] = 0;
		}
	}

	public Trade getCurrentTrade() {
		return currentTrade;
	}

	public void setCurrentTrade(Trade currentTrade) {
		this.currentTrade = currentTrade;
	}

	public int itemsTraded() {
		int itemsTraded = 0;
		for (int i = 0; i < offer.length; i++) {
			if (offer[i] != 0)
				itemsTraded++;
		}
		return itemsTraded;
	}

	public void acceptInitialTradeWindow() {
		if (stage != 1)
			return;
		if (currentTrade != null && currentTrade.isOpen()) {
			Client other = getReciever();
			if (other == null)
				return;
			if (itemsTraded() > other.getActionAssistant().freeSlots()) {
				client.getActionSender()
						.sendMessage(
								"The other player doesn't have enough space left in their inventory.");
				return;
			}
			if (other.getTradeHandler().itemsTraded() > client
					.getActionAssistant().freeSlots()) {
				client.getActionSender().sendMessage(
						"There is not enough space in your inventory.");
				return;
			}
			accepted = true;

			if (!other.getTradeHandler().accepted()) {
				client.getActionSender().sendQuest(
						"Waiting for other player...", 3431);
				other.getActionSender().sendQuest("Other player accepted.",
						3431);
			} else {
				client.getActionSender().sendFrame248(3443, 3213);
				client.getActionSender().sendItemReset(3214);
				other.getActionSender().sendFrame248(3443, 3213);
				other.getActionSender().sendItemReset(3214);
				accepted = false;
				client.getTradeHandler().setStage(2);
				other.getTradeHandler().setStage(2);
				other.getTradeHandler().setAccepted(false);
				client.getActionSender().sendQuest(
						"Are you sure you want to accept this trade?", 3535);
				other.getActionSender().sendQuest(
						"Are you sure you want to accept this trade?", 3535);
				sendCurrentPlayersOffer();
				sendOtherPlayersOffer();
				other.getTradeHandler().sendCurrentPlayersOffer();
				other.getTradeHandler().sendOtherPlayersOffer();
			}
		}
	}

	public void sendCurrentPlayersOffer() {
		StringBuilder trade = new StringBuilder();
		boolean empty = true;
		for (int i = 0; i < offer.length; i++) {
			String prefix = "";
			if (offer[i] > 0) {
				empty = false;
				if (offerN[i] >= 100 && offerN[i] < 1000000) {
					prefix = "@cya@" + (offerN[i] / 1000) + "K @whi@("
							+ offerN[i] + ")";
				} else if (offerN[i] >= 1000000) {
					prefix = "@gre@" + (offerN[i] / 1000000)
							+ " million @whi@(" + offerN[i] + ")";
				} else {
					prefix = "" + offerN[i];
				}

				trade.append(GameEngine.getItemManager()
						.getItemDefinition((offer[i] - 1)).getName());
				trade.append(" x ");
				trade.append(prefix);
				trade.append("\\n");
			}
		}
		if (empty) {
			trade.append("Absolutely nothing!");
		}
		client.getActionSender().sendQuest(trade.toString(), 3557);
	}

	public void sendOtherPlayersOffer() {
		Client other = getReciever();
		if (other == null)
			return;
		int[] offer = other.getTradeHandler().getOffer();
		int[] offerN = other.getTradeHandler().getOfferN();
		StringBuilder trade = new StringBuilder();
		boolean empty = true;
		for (int i = 0; i < offer.length; i++) {
			String prefix = "";
			if (offer[i] > 0) {
				empty = false;
				if (offerN[i] >= 100 && offerN[i] < 1000000) {
					prefix = "@cya@" + (offerN[i] / 1000) + "K @whi@("
							+ offerN[i] + ")";
				} else if (offerN[i] >= 1000000) {
					prefix = "@gre@" + (offerN[i] / 1000000)
							+ " million @whi@(" + offerN[i] + ")";
				} else {
					prefix = "" + offerN[i];
				}
				trade.append(GameEngine.getItemManager()
						.getItemDefinition((offer[i] - 1)).getName());
				trade.append(" x ");
				trade.append(prefix);
				trade.append("\\n");
			}
		}
		if (empty) {
			trade.append("Absolutely nothing!");
		}
		client.getActionSender().sendQuest(trade.toString(), 3558);
	}

	public void acceptConfirmationTradeWindow() {
		if (stage != 2)
			return;
		if (currentTrade != null && currentTrade.isOpen()) {
			Client other = getReciever();
			if (other == null)
				return;

			if (!other.getTradeHandler().accepted()) {
				client.getActionSender().sendQuest(
						"Waiting for other player...", 3535);
				other.getActionSender().sendQuest("Other player accepted.",
						3535);
				accepted = true;
			} else {
				for (int i = 0; i < offer.length; i++) {
					if (offer[i] == 0)
						continue;
					other.getActionSender().sendInventoryItem(offer[i] - 1,
							offerN[i]);
					offer[i] = 0;
					offerN[i] = 0;
				}
				int[] offer = other.getTradeHandler().getOffer();
				int[] offerN = other.getTradeHandler().getOfferN();
				for (int i = 0; i < offer.length; i++) {
					if (offer[i] == 0)
						continue;
					client.getActionSender().sendInventoryItem(offer[i] - 1,
							offerN[i]);
					offer[i] = 0;
					offerN[i] = 0;
				}
				abortCurrentTrade();
			}
		}
	}

	public boolean accepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public int getStage() {
		return this.stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

}
