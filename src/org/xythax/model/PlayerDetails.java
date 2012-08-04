package org.xythax.model;

/**
 * The save object
 *
 * @author Ultimate
 *
 */

import java.io.Serializable;

public class PlayerDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	public String username, password, ConnectedFrom;
	public String[] lastKilled;

	public int absX, absY, heightLevel, hitpoints, friendsSize, playerBankSize,
			ignoresSize, specialAmount, poisonDelay, poisonDamage, skullTimer,
			pkPoints, energy, dharokDamage, spellBook, combatMode,
			lastMeleeMode, lastRangeMode, starter, ID, running;

	public int[] playerItem, playerItemN, playerEquipment, playerEquipmentN,
			playerLevel, playerXP, bankItems, bankItemsN, playerLook;

	public long[] ignores, friends;

	public boolean appearanceSet, SplitChat, autoRetaliate, ancients,
			playerIsMember;

	public byte[] bytes;

	public PlayerDetails(Player p) {
		ID = p.getID();
		username = p.getUsername();
		password = p.getPassword();
		absX = p.getAbsX();
		absY = p.getAbsY();
		heightLevel = p.getHeightLevel();
		appearanceSet = p.appearanceSet;
		playerItem = p.playerItems;
		playerItemN = p.playerItemsN;
		playerEquipment = p.playerEquipment;
		playerEquipmentN = p.playerEquipmentN;
		playerLevel = p.playerLevel;
		playerXP = p.playerXP;
		hitpoints = p.hitpoints;
		bankItems = p.bankItems;
		bankItemsN = p.bankItemsN;
		playerLook = p.playerLook;
		playerBankSize = p.getPlayerBankSize();

		if (p.getFriends() != null)
			friends = p.getFriends();
		else
			friends = new long[200];

		friendsSize = p.getFriendsSize();

		if (p.getIgnores() != null)
			ignores = p.getIgnores();
		else
			ignores = new long[100];

		ignoresSize = p.getIgnoresSize();
		specialAmount = p.getSpecialAmount();
		poisonDelay = p.getPoisonDelay();
		poisonDamage = p.poisonDamage;
		skullTimer = p.getSkullTimer();
		energy = p.getEnergy();
		ancients = p.isUsingAncients();
		playerIsMember = p.isPlayerMember();
		dharokDamage = p.getDharokDamage();
		spellBook = p.getSpellBook();
		lastKilled = p.getLastKilled();
		ConnectedFrom = p.connectedFrom;
		SplitChat = p.SplitChat;
		autoRetaliate = p.autoRetaliate;
		combatMode = p.combatMode;
		lastMeleeMode = p.lastMeleeMode;
		lastRangeMode = p.lastRangeMode;
		starter = p.starter;
		running = p.isRunning ? 1 : 0;
	}
}