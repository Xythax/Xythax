package org.xythax.content;

import org.xythax.model.Client;
import org.xythax.model.Player;
import org.xythax.utils.Utils;
import org.xythax.world.PlayerManager;

/**
 * Handles the friends/ignores/pm system
 * 
 * @author Graham
 */
public class FriendsHandler {

	private Client client;

	private static int lastChatId = 0;

	public FriendsHandler(Client client) {
		this.client = client;
	}

	public boolean containsFriend(long l) {
		for (int i = 0; i < client.getFriends().length; i++) {
			if (client.getFriends()[i] == l) {
				return true;
			}
		}
		return false;
	}

	public boolean containsIgnore(long l) {
		for (int i = 0; i < client.getIgnores().length; i++) {
			if (client.getIgnores()[i] == l) {
				return true;
			}
		}
		return false;
	}

	public void initialize() {
		if (client.getFriends() == null) {
			client.setFriends(new long[200]);
		}
		if (client.getIgnores() == null) {
			client.setIgnores(new long[100]);
		}
		refreshLists();
		long l = Utils.playerNameToInt64(client.getUsername());
		int world = getWorld(l);
		for (Player p : PlayerManager.getPlayerManager().getPlayers()) {
			if (p == null)
				continue;
			if (!p.isActive || p.disconnected)
				continue;
			Client c = (Client) p;
			if (c.getFriendsHandler().containsFriend(l)) {
				c.getFriendsHandler().sendFriend(l, world);
			}
		}
	}

	public void destruct() {
		long l = Utils.playerNameToInt64(client.getUsername());
		for (Player p : PlayerManager.getPlayerManager().getPlayers()) {
			if (p == null)
				continue;
			if (!p.isActive || p.disconnected)
				continue;
			Client c = (Client) p;
			if (c.getFriendsHandler().containsFriend(l)) {
				c.getFriendsHandler().sendFriend(l, 0);
			}
		}
	}

	public void refreshLists() {
		for (int i = 0; i < client.getFriends().length; i++) {
			long l = client.getFriends()[i];
			if (l == 0)
				continue;
			sendFriend(l, getWorld(l));
		}
		sendStatus(2);
	}

	public void sendFriend(long l, int world) {
		if (world != 0) {
			world += 9;
		}
		client.outStream.createFrame(50);
		client.outStream.writeQWord(l);
		client.outStream.writeByte(world);
		client.flushOutStream();
	}

	public void sendStatus(int status) {
		client.outStream.createFrame(221);
		client.outStream.writeByte(status);
		client.flushOutStream();
	}

	public int getWorld(long who) {
		Player p = PlayerManager.getPlayerManager().getPlayerByNameLong(who);
		int world = 0;
		if (p != null) {
			world = 1;
		}
		return world;
	}

	public void addFriend(long l) {
		if (getAmountOf(client.getFriends()) > client.getFriendsSize()) {
			// TODO is there a message? and is this correct?
			client.getActionSender().sendMessage(
					"Your friends actions is full.");
			return;
		} else {
			if (containsFriend(l)) {
				client.getActionSender().sendMessage(
						Utils.longToPlayerName(l)
								+ " is already in your friendlist.");
				return;
			}
			int slot = getFreeSlot(client.getFriends());
			if (slot == -1)
				return;
			client.getFriends()[slot] = l;
			sendFriend(l, getWorld(l));
		}
	}

	public void removeFriend(long l) {
		for (int i = 0; i < client.getFriends().length; i++) {
			if (client.getFriends()[i] == l) {
				client.getFriends()[i] = 0;
			}
		}
	}

	public void addIgnore(long l) {
		if (getAmountOf(client.getIgnores()) > client.getIgnoresSize()) {
			// TODO is there a message? and is this correct?
			client.getActionSender().sendMessage(
					"Your ignores actions is full.");
			return;
		} else {
			int slot = getFreeSlot(client.getIgnores());
			if (slot == -1)
				return;
			client.getIgnores()[slot] = l;
		}
	}

	public void removeIgnore(long l) {
		for (int i = 0; i < client.getIgnores().length; i++) {
			if (client.getIgnores()[i] == l) {
				client.getIgnores()[i] = 0;
			}
		}
	}

	public int getAmountOf(long[] array) {
		int count = 0;
		for (long l : array) {
			if (l > 0) {
				count++;
			}
		}
		return count;
	}

	public int getFreeSlot(long[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == 0) {
				return i;
			}
		}
		return -1;
	}

	public void sendMessage(long to, byte[] chatText, int chatTextSize) {
		Player p = PlayerManager.getPlayerManager().getPlayerByNameLong(to);
		Client c = (Client) p;
		if (c == null) {
			client.getActionSender().sendMessage(
					Utils.longToPlayerName(to) + " is currently unavailable.");
			return;
		} else {
			c.getFriendsHandler().sendPM(
					Utils.playerNameToInt64(client.getUsername()),
					client.getPrivileges(), chatText, chatTextSize);
		}
	}

	public void sendPM(long from, int playerRights, byte[] chatText,
			int chatTextSize) {
		if (lastChatId == 0) {
			lastChatId++;
		}
		client.outStream.createFrameVarSize(196);
		client.outStream.writeQWord(from);
		client.outStream.writeDWord(++lastChatId);
		client.outStream.writeByte(playerRights);
		client.outStream.writeBytes(chatText, chatTextSize, 0);
		client.outStream.endFrameVarSize();
		client.flushOutStream();
	}

}
