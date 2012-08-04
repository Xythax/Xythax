package org.xythax.content.actions;

import org.xythax.model.Client;

/**
 * 
 * @author killamess
 * 
 */
public class Action {

	private Client client;
	private String username;

	/**
	 * There cycle count
	 */
	private int actionTick;

	/**
	 * The actual count;
	 */
	private int currentTick;

	private int skill;

	private type actionType;

	public static enum type {
		READING, LOOPING, TRASHING;
	}

	public Action(Client newClient, int skillRequest, int tickRate) {
		setClient(newClient);
		setUsername(newClient == null ? "BOT" : newClient.getUsername());
		setActionType(type.READING);
		setSkill(skillRequest);
		setActionTick(tickRate);
		setCurrentTick(tickRate);
	}

	public void setSkill(int skill) {
		this.skill = skill;
	}

	public int getSkill() {
		return skill;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Client getClient() {
		return client;
	}

	public void setActionTick(int actionTick) {
		this.actionTick = actionTick;
	}

	public int getActionTick() {
		return actionTick;
	}

	public void setActionType(type actionType) {
		this.actionType = actionType;
	}

	public type getActionType() {
		return actionType;
	}

	public void setCurrentTick(int currentTick) {
		this.currentTick = currentTick;
	}

	public int getCurrentTick() {
		return currentTick;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
}
