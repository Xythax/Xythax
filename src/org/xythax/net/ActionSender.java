package org.xythax.net;

import org.xythax.model.Client;
import org.xythax.model.Item;
import org.xythax.model.NPC;
import org.xythax.model.Shop;
import org.xythax.utils.CommonStrings;
import org.xythax.utils.Constants;

/**
 * 
 * @author Rodgerwilco
 * 
 *         Sends information to the client.
 */
public class ActionSender {
	private Client client;

	public ActionSender(Client client) {
		this.setClient(client);
	}

	public void sendCookOption(int i) {
		sendFrame164(1743);
		sendFrame246(13716, 250, i);
		sendQuest("How many would you like to cook?", 13721);
		sendQuest("", 13717);
		sendQuest("", 13718);
		sendQuest("", 13719);
		sendQuest("", 13720);
	}

	public void sound(int songid, int vol, int delay) {
		client.outStream.createFrame(174);
		client.outStream.writeWord(songid);
		client.outStream.writeByte(vol);
		client.outStream.writeWord(delay);
	}

	public void selectOption(String question, String s1, String s2, String s3,
			String s4, String s5) {
		sendFrame171(1, 2465);
		sendFrame171(0, 2468);
		sendQuest(question, 2493);
		sendQuest(s1, 2494);
		sendQuest(s2, 2495);
		sendQuest(s3, 2496);
		sendQuest(s4, 2497);
		sendQuest(s5, 2498);
		sendFrame164(2492);
	}

	public void selectOption(String question, String s1, String s2, String s3) {
		sendFrame171(1, 2461);
		sendFrame171(0, 2462);
		sendQuest(question, 2493);
		sendQuest(s1, 2471);
		sendQuest(s2, 2472);
		sendQuest(s3, 2473);
		sendFrame164(2469);
	}

	public void selectOption(String question, String s1, String s2) {
		sendFrame171(1, 2461);
		sendFrame171(0, 2462);
		sendQuest(question, 2493);
		sendQuest(s1, 2461);
		sendQuest(s2, 2462);
		sendFrame164(2459);
	}

	public void sendFrame71(int a, int b) {
		synchronized (client) {
			client.outStream.createFrame(71);
			client.outStream.writeWord(a);
			client.outStream.writeByteA(b);
		}
	}

	public void sendFrame106(int a) {
		synchronized (client) {
			client.outStream.createFrame(106);
			client.outStream.writeByteC(a);
		}
	}

	public void sendFrame87(int id, int state) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(87);
				client.outStream.writeWordBigEndian_dup(id);
				client.outStream.writeDWord_v1(state);
				client.flushOutStream();
			}
		}
	}

	public void sendFrame99(int a) {
		synchronized (client) {
			client.outStream.createFrame(99);
			client.outStream.writeByte(a);
		}
	}

	public void sendMultiInterface(int i1) {
		synchronized (client) {
			client.outStream.createFrame(61);// 61
			client.outStream.writeByte(i1);
			client.updateRequired = true;
			client.appearanceUpdateRequired = true;
		}
	}

	public void sendConfig(int id, int state) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(36);
				client.outStream.writeWordBigEndian(id);
				client.outStream.writeByte(state);
				client.flushOutStream();
			}
		}
	}

	public void sendClientConfig(int id, int state) {
		synchronized (client) {
			if (client.outStream == null || client == null) {
				return;
			}
			client.outStream.createFrame(36);
			client.outStream.writeWordBigEndian(id);
			client.outStream.writeByte(state);
			client.flushOutStream();
		}
	}

	public void sendFollowing(int followID, boolean npc, int distance) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(174);
				client.outStream.writeWord(followID);
				client.outStream.writeByte(npc ? 0 : 1); // 0 - NPC's, 1 -
															// Players.
				client.outStream.writeWord(distance);
				client.flushOutStream();
			}
		}
	}

	public void sendConfig2(int id, int state) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(87);
				client.outStream.writeWordBigEndian(id);
				client.outStream.writeDWord_v1(state);
				client.flushOutStream();
			}
		}
	}

	public void sendWalkableInterface(int i) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(208);
				client.outStream.writeWordBigEndian_dup(i);
			}
		}
	}

	public void sendOption(String s, int pos) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrameVarSize(104);
				client.outStream.writeByteC(pos);
				client.outStream.writeByteA(0);
				client.outStream.writeString(s);
				client.outStream.endFrameVarSize();
				client.flushOutStream();
			}
		}
	}

	public void sendMessage(String s) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrameVarSize(253);
				client.outStream.writeString(s);
				client.outStream.endFrameVarSize();
				client.flushOutStream();
			}
		}
	}

	public void sendQuest(String s, int id) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrameVarSizeWord(126);
				client.outStream.writeString(s);
				client.outStream.writeWordA(id);
				client.outStream.endFrameVarSizeWord();
				client.flushOutStream();
			}
		}
	}

	public int[] textID = { 8145, 8147, 8148, 8149, 8150, 8151, 8152, 8153,
			8154, 8155, 8156, 8157, 8158, 8159, 8160, 8161, 8162, 8163, 8164,
			8165, 8166, 8167, 8168, 8169, 8170, 8171, 8172, 8173, 8174, 8175,
			8176, 8177, 8178, 8179, 8180, 8181, 8182, 8183, 8184, 8185, 8186,
			8187, 8188, 8189, 8190, 8191, 8192, 8193, 8194, 8195, 12174, 12175,
			12176, 12177, 12178, 12179, 12180, 12181, 12182, 12183, 12184,
			12185, 12186, 12187, 12188, 12189, 12190, 12191, 12192, 12193,
			12194, 12195, 12196, 12197, 12198, 12199, 12200, 12201, 12202,
			12203, 12204, 12205, 12206, 12207, 12208, 12209, 12210, 12211,
			12212, 12213, 12214, 12215, 12216, 12217, 12218, 12219, 12220,
			12221, 12222, 12223 };

	public void clearQuestInterface() {
		for (int i : textID) {
			sendQuest("", i);
		}
	}

	public void sendAnimationReset() {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(1);
				client.flushOutStream();
			}
		}
	}

	public void sendDialogue(String s) {
		sendQuest(s, 357);
		sendFrame164(356);
	}

	public void sendFrame185(int i) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(185);
				client.outStream.writeWordBigEndianA(i);
				client.flushOutStream();
			}
		}
	}

	public void sendFrame36(int id, int state) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(36);
				client.outStream.writeWordBigEndian(id);
				client.outStream.writeByte(state);
				client.flushOutStream();
			}
		}
	}

	public void sendFrame200(int i, int j) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(200);
				client.outStream.writeWord(i);
				client.outStream.writeWord(j);
				client.flushOutStream();
			}
		}
	}

	public void sendFrame75(int npc, int i) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(75);
				client.outStream.writeWordBigEndianA(npc);
				client.outStream.writeWordBigEndianA(i);
				client.flushOutStream();
			}
		}
	}

	public void sendFrame164(int frame) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(164);
				client.outStream.writeWordBigEndian_dup(frame);
				client.flushOutStream();
			}
		}
	}

	public void sendFrame171(int id, int visible) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(171);
				client.outStream.writeByte(visible);
				client.outStream.writeWord(id);
				client.flushOutStream();
			}
		}
	}

	public void sendSidebar(int menuId, int form) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(71);
				client.outStream.writeWord(form);
				client.outStream.writeByteA(menuId);
				client.flushOutStream();
			}
		}
	}

	public void sendChatOptions(int publicChat, int privateChat, int tradeBlock) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(206);
				client.outStream.writeByte(publicChat);
				client.outStream.writeByte(privateChat);
				client.outStream.writeByte(tradeBlock);
				client.flushOutStream();
			}
		}
	}

	public void sendInterface(int interfaceID) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(97);
				client.outStream.writeWord(interfaceID);
				client.flushOutStream();
			}
		}
	}

	public void sendWindowsRemoval() {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(219);
				client.flushOutStream();
			}
		}
	}

	public void sendFlagRemoval() {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(78);
				client.flushOutStream();
			}
		}
	}

	public void sendBankInterface() {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				// if (!GameEngine.getConsole().valid)
				// return;
				client.outStream.createFrame(248);
				client.outStream.writeWordA(5292);
				client.outStream.writeWord(5063);
				client.flushOutStream();
				sendReplacementOfTempItem();
			}
		}
	}

	public void sendFrame248(int mainFrame, int subFrame) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(248);
				client.outStream.writeWordA(mainFrame);
				client.outStream.writeWord(subFrame);
				client.flushOutStream();
			}
		}
	}

	public void sendFrame34(int id, int slot, int column, int amount) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrameVarSizeWord(34); // init item
																// to smith
																// screen
				client.outStream.writeWord(column); // Column Across Smith
													// Screen
				client.outStream.writeByte(4); // Total Rows?
				client.outStream.writeDWord(slot); // Row Down The Smith
													// Screen
				client.outStream.writeWord(id + 1); // item
				client.outStream.writeByte(amount); // how many there are?
				client.outStream.endFrameVarSizeWord();
			}
		}
	}

	public void sendFrame246(int mainFrame, int subFrame, int subFrame2) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(246);
				client.outStream.writeWordBigEndian(mainFrame);
				client.outStream.writeWord(subFrame);
				client.outStream.writeWord(subFrame2);
				client.flushOutStream();
			}
		}
	}

	public void sendCoords(int x, int y) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(85);
				client.outStream.writeByteC(y - (client.mapRegionY * 8));
				client.outStream.writeByteC(x - (client.mapRegionX * 8));
			}
		}
	}

	public void sendObject(int objectID, int objectX, int objectY,
			int objectHeight, int objectFace, int objectType) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				sendCoords(objectX, objectY);
				client.outStream.createFrame(151);
				client.outStream.writeByteA(0);
				client.outStream.writeWordBigEndian(objectID);
				client.outStream.writeByteS((objectType << 2)
						+ (objectFace & 3));
				client.flushOutStream();
			}
		}
	}

	public void sendObjectAnim(int x, int y, int id, int type, int orientation) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				sendCoords(x, y);
				client.outStream.createFrame(160);
				client.outStream.writeByteS(((x & 7) << 4) + (y & 7));
				client.outStream.writeByteS((type << 2) + (orientation & 3));
				client.outStream.writeWordA(id);
				client.flushOutStream();
			}
		}
	}

	public void sendProjectile(int absY, int absX, int offsetY, int offsetX,
			int proID, int startHeight, int endHeight, int speed, int lockon) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(85);
				client.outStream.writeByteC(absY - client.mapRegionY * 8 - 2);
				client.outStream.writeByteC(absX - client.mapRegionX * 8 - 3);
				client.outStream.createFrame(117);
				client.outStream.writeByte(50);
				client.outStream.writeByte(offsetY);
				client.outStream.writeByte(offsetX);
				client.outStream.writeWord(lockon);
				client.outStream.writeWord(proID);
				client.outStream.writeByte(startHeight);
				client.outStream.writeByte(endHeight);
				client.outStream.writeWord(51);
				client.outStream.writeWord(speed);
				client.outStream.writeByte(16);
				client.outStream.writeByte(64);
				client.flushOutStream();
			}
		}
	}

	/*
	 * This one supports angling for the projectile - killamess.
	 */
	public void sendProjectile(int absY, int absX, int offsetY, int offsetX,
			int proID, int startHeight, int endHeight, int speed, int angle,
			int lockon) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrame(85);
				client.outStream.writeByteC(absY - client.mapRegionY * 8 - 2);
				client.outStream.writeByteC(absX - client.mapRegionX * 8 - 3);
				client.outStream.createFrame(117);
				client.outStream.writeByte(50);
				client.outStream.writeByte(offsetY);
				client.outStream.writeByte(offsetX);
				client.outStream.writeWord(lockon);
				client.outStream.writeWord(proID);
				client.outStream.writeByte(startHeight);
				client.outStream.writeByte(endHeight);
				client.outStream.writeWord(51);
				client.outStream.writeWord(speed);
				client.outStream.writeByte(angle);
				client.outStream.writeByte(64);
				client.flushOutStream();
			}
		}
	}

	public void sendLogout() {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				if (client.logoutDelay > 0) {
					sendMessage(CommonStrings.LOGOUT_COMBAT);
					return;
				}
				NPC.removeMyNPC(client);
				client.setLoggedOut(true);
				client.outStream.createFrame(109);
				client.flushOutStream();
			}
		}
	}

	public void sendItemReset(int frame) {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				client.outStream.createFrameVarSizeWord(53);
				client.outStream.writeWord(frame);
				client.outStream.writeWord(client.playerItems.length);
				for (int i = 0; i < client.playerItems.length; i++) {
					if (client.playerItemsN[i] > 254) {
						client.outStream.writeByte(255);
						client.outStream.writeDWord_v2(client.playerItemsN[i]);
					} else {
						client.outStream.writeByte(client.playerItemsN[i]);
					}
					client.outStream.writeWordBigEndianA(client.playerItems[i]);
				}
				client.outStream.endFrameVarSizeWord();
				client.flushOutStream();
			}
		}
	}

	public void sendItemReset() {
		client.outStream.createFrameVarSizeWord(53);
		client.outStream.writeWord(3214);
		client.outStream.writeWord(client.playerItems.length);
		for (int i = 0; i < client.playerItems.length; i++) {
			if (client.playerItemsN[i] > 254) {
				client.outStream.writeByte(255); // item's stack count. if
				client.outStream.writeDWord_v2(client.playerItemsN[i]); // and
			} else {
				client.outStream.writeByte(client.playerItemsN[i]);
			}
			if (client.playerItems[i] > Constants.MAX_ITEMS
					|| client.playerItems[i] < 0) {
				client.playerItems[i] = Constants.MAX_ITEMS;
			}
			client.outStream.writeWordBigEndianA(client.playerItems[i]); // item
		}
		client.outStream.endFrameVarSizeWord();
		client.flushOutStream();
	}

	public void sendShopReset(Shop shop) {
		client.outStream.createFrameVarSizeWord(53);
		client.outStream.writeWord(3900);
		int count = 0;
		for (int i = 0; i < shop.getContainerSize(); i++) {
			Item item = shop.getItemBySlot(i);
			if (item != null) {
				count++;
			}
		}
		client.outStream.writeWord(count);
		for (int i = 0; i < shop.getContainerSize(); i++) {
			Item item = shop.getItemBySlot(i);
			if (item == null) {
				continue;
			}
			if (item.getAmount() > 254) {
				client.outStream.writeByte(255);
				client.outStream.writeDWord_v2(item.getAmount());
			} else {
				client.outStream.writeByte(item.getAmount());
			}
			client.outStream.writeWordBigEndianA(item.getId() + 1);
		}
		client.outStream.endFrameVarSizeWord();
		client.flushOutStream();
	}

	public void sendBankReset() {
		client.outStream.createFrameVarSizeWord(53);
		client.outStream.writeWord(5382); // bank
		client.outStream.writeWord(client.getPlayerBankSize());
		for (int i = 0; i < client.getPlayerBankSize(); i++) {
			if (client.bankItemsN[i] > 254) {
				client.outStream.writeByte(255);
				client.outStream.writeDWord_v2(client.bankItemsN[i]);
			} else {
				client.outStream.writeByte(client.bankItemsN[i]); // amount
			}
			if (client.bankItemsN[i] < 1)
				client.bankItems[i] = 0;
			if (client.bankItems[i] > Constants.MAX_ITEMS
					|| client.bankItems[i] < 0) {
				client.bankItems[i] = Constants.MAX_ITEMS;
			}
			client.outStream.writeWordBigEndianA(client.bankItems[i]); // itemID
		}
		client.outStream.endFrameVarSizeWord();
		client.flushOutStream();
	}

	public void sendReplacementOfTempItem() {
		int itemCount = 0;
		for (int i = 0; i < client.playerItems.length; i++) {
			if (client.playerItems[i] > -1) {
				itemCount = i;
			}
		}
		client.outStream.createFrameVarSizeWord(53);
		client.outStream.writeWord(5064); // inventory
		client.outStream.writeWord(itemCount + 1); // number of items
		for (int i = 0; i < itemCount + 1; i++) {
			if (client.playerItemsN[i] > 254) {
				client.outStream.writeByte(255);
				client.outStream.writeDWord_v2(client.playerItemsN[i]);
			} else {
				client.outStream.writeByte(client.playerItemsN[i]);
			}
			if (client.playerItems[i] > Constants.MAX_ITEMS
					|| client.playerItems[i] < 0) {
				client.playerItems[i] = Constants.MAX_ITEMS;
			}
			client.outStream.writeWordBigEndianA(client.playerItems[i]); // item
		}

		client.outStream.endFrameVarSizeWord();
		client.flushOutStream();
	}

	public boolean sendInventoryItem(int item, int amount, int slot) {

		if (item < 1 || item > Constants.MAX_ITEM_AMOUNT)
			return false;

		if (!Item.itemStackable[item]
				&& client.getActionAssistant().freeSlots() < 1) {
			sendMessage(CommonStrings.INV_FULL);
			return false;
		}

		/**
		 * Add stackable item.
		 */
		if (Item.itemStackable[item]) {
			if (client.getActionAssistant().freeSlots() > 1
					|| client.getActionAssistant().playerHasItem(item, 1)) {
				for (int i = 0; i < client.playerItems.length; i++) {
					if (client.playerItems[i] == (item + 1)) {
						if ((client.playerItemsN[i] + amount) < Constants.MAX_ITEM_AMOUNT
								&& (client.playerItemsN[i] + amount) > -1) {
							client.playerItemsN[i] += amount;
						} else {
							sendMessage("I don't think i'll be able to carry all that!");
							return false;
						}
						client.outStream.createFrameVarSizeWord(34);
						client.outStream.writeWord(3214);
						client.outStream.writeByte(i);
						client.outStream.writeWord(client.playerItems[i]);
						if (client.playerItemsN[i] > 254) {
							client.outStream.writeByte(255);
							client.outStream.writeDWord(client.playerItemsN[i]);
						} else {
							client.outStream.writeByte(client.playerItemsN[i]); // amount
						}
						client.outStream.endFrameVarSizeWord();
						return true;
					}
				}
			}
		}
		/**
		 * Add single slot item.
		 */
		boolean canAddToSlot = true;

		for (int i = 0; i < client.playerItems.length; i++) {
			if (i == slot) {
				if (client.playerItems[i] > 0) {
					canAddToSlot = false;
				}
			}
		}
		if (canAddToSlot) {
			for (int i = 0; i < client.playerItems.length; i++) {
				if (i == slot) {
					if ((client.playerItemsN[i] + amount) < Constants.MAX_ITEM_AMOUNT
							&& (client.playerItemsN[i] + amount) > -1) {
						client.playerItems[i] = item + 1;
						client.playerItemsN[i] += amount;
					} else {
						sendMessage("I don't think i'll be able to carry all that!");
						return false;
					}
					client.outStream.createFrameVarSizeWord(34);
					client.outStream.writeWord(3214);
					client.outStream.writeByte(i);
					client.outStream.writeWord(client.playerItems[i]);
					if (client.playerItemsN[i] > 254) {
						client.outStream.writeByte(255);
						client.outStream.writeDWord(client.playerItemsN[i]);
					} else {
						client.outStream.writeByte(client.playerItemsN[i]); // amount
																			// }
						client.outStream.endFrameVarSizeWord();
						client.flushOutStream();
						return true;
					}
				}
			}
		} else {
			for (int i = 0; i < client.playerItems.length; i++) {
				if (client.playerItems[i] < 1) {
					if ((client.playerItemsN[i] + amount) < Constants.MAX_ITEM_AMOUNT
							&& (client.playerItemsN[i] + amount) > -1) {
						client.playerItems[i] = item + 1;
						client.playerItemsN[i] += amount;
					} else {
						sendMessage("I don't think i'll be able to carry all that!");
						return false;
					}
					client.outStream.createFrameVarSizeWord(34);
					client.outStream.writeWord(3214);
					client.outStream.writeByte(i);
					client.outStream.writeWord(client.playerItems[i]);
					if (client.playerItemsN[i] > 254) {
						client.outStream.writeByte(255);
						client.outStream.writeDWord(client.playerItemsN[i]);
					} else {
						client.outStream.writeByte(client.playerItemsN[i]); // amount
					}
					client.outStream.endFrameVarSizeWord();
					client.flushOutStream();
					return true;
				}
			}
		}
		sendMessage(CommonStrings.INV_FULL);
		return false;
	}

	public boolean sendInventoryItem2(int item, int amount, int slot) {
		if (item == -1)
			return false;
		if (!Item.itemStackable[item] || amount < 1) {
			amount = 1;
		}
		if ((client.getActionAssistant().freeSlots() >= amount && !Item.itemStackable[item])
				|| client.getActionAssistant().freeSlots() > 0) {
			for (int i = 0; i < client.playerItems.length; i++) {
				if (client.playerItems[i] == (item + 1)
						&& Item.itemStackable[item]
						&& client.playerItems[i] > 0) {
					client.playerItems[i] = (item + 1);
					if ((client.playerItemsN[i] + amount) < Constants.MAX_ITEM_AMOUNT
							&& (client.playerItemsN[i] + amount) > -1) {
						client.playerItemsN[i] += amount;
					} else {
						client.playerItemsN[i] = Constants.MAX_ITEM_AMOUNT;
					}
					client.outStream.createFrameVarSizeWord(34);
					client.outStream.writeWord(3214);
					client.outStream.writeByte(i);
					client.outStream.writeWord(client.playerItems[i]);
					if (client.playerItemsN[i] > 254) {
						client.outStream.writeByte(255);
						client.outStream.writeDWord(client.playerItemsN[i]);
					} else {
						client.outStream.writeByte(client.playerItemsN[i]); // amount
					}
					client.outStream.endFrameVarSizeWord();
					i = 30;
					return true;
				}
			}
			for (int i = 0; i < client.playerItems.length; i++) {
				if (client.playerItems[i] <= 0) {
					client.playerItems[i] = item + 1;
					if (amount < Constants.MAX_ITEM_AMOUNT && amount > -1) {
						client.playerItemsN[i] = amount;
					} else {
						client.playerItemsN[i] = Constants.MAX_ITEM_AMOUNT;
					}
					client.outStream.createFrameVarSizeWord(34);
					client.outStream.writeWord(3214);
					client.outStream.writeByte(i);
					client.outStream.writeWord(client.playerItems[i]);
					if (client.playerItemsN[i] > 254) {
						client.outStream.writeByte(255);
						client.outStream.writeDWord(client.playerItemsN[i]);
					} else {
						client.outStream.writeByte(client.playerItemsN[i]); // amount
					}
					client.outStream.endFrameVarSizeWord();
					client.flushOutStream();
					i = 30;
					return true;
				}
			}
			return false;
		} else {
			sendMessage(CommonStrings.INV_FULL);
			return false;
		}
	}

	public void sendDepositInterface() {
		synchronized (client) {
			if (client.outStream != null && client != null) {
				sendQuest("The Bank of Xythax - Deposit Box", 7421);
				sendFrame248(4465, 197);
				sendItemReset(7423);
			}
		}
	}

	public boolean sendInventoryItem(int item, int amount) {
		if (item == -1)
			return false;
		if (!Item.itemStackable[item] || amount < 1) {
			amount = 1;
		}
		if ((client.getActionAssistant().freeSlots() >= amount && !Item.itemStackable[item])
				|| client.getActionAssistant().freeSlots() > 0) {
			for (int i = 0; i < client.playerItems.length; i++) {
				if (client.playerItems[i] == (item + 1)
						&& Item.itemStackable[item]
						&& client.playerItems[i] > 0) {
					client.playerItems[i] = (item + 1);
					if ((client.playerItemsN[i] + amount) < Constants.MAX_ITEM_AMOUNT
							&& (client.playerItemsN[i] + amount) > -1) {
						client.playerItemsN[i] += amount;
					} else {
						client.playerItemsN[i] = Constants.MAX_ITEM_AMOUNT;
					}
					client.outStream.createFrameVarSizeWord(34);
					client.outStream.writeWord(3214);
					client.outStream.writeByte(i);
					client.outStream.writeWord(client.playerItems[i]);
					if (client.playerItemsN[i] > 254) {
						client.outStream.writeByte(255);
						client.outStream.writeDWord(client.playerItemsN[i]);
					} else {
						client.outStream.writeByte(client.playerItemsN[i]); // amount
					}
					client.outStream.endFrameVarSizeWord();
					i = 30;
					return true;
				}
			}
			for (int i = 0; i < client.playerItems.length; i++) {
				if (client.playerItems[i] <= 0) {
					client.playerItems[i] = item + 1;
					if (amount < Constants.MAX_ITEM_AMOUNT && amount > -1) {
						client.playerItemsN[i] = amount;
					} else {
						client.playerItemsN[i] = Constants.MAX_ITEM_AMOUNT;
					}
					client.outStream.createFrameVarSizeWord(34);
					client.outStream.writeWord(3214);
					client.outStream.writeByte(i);
					client.outStream.writeWord(client.playerItems[i]);
					if (client.playerItemsN[i] > 254) {
						client.outStream.writeByte(255);
						client.outStream.writeDWord(client.playerItemsN[i]);
					} else {
						client.outStream.writeByte(client.playerItemsN[i]); // amount
					}
					client.outStream.endFrameVarSizeWord();
					client.flushOutStream();
					i = 30;
					return true;
				}
			}
			return false;
		} else {
			sendMessage(CommonStrings.INV_FULL);
			return false;
		}
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Client getClient() {
		return client;
	}

}