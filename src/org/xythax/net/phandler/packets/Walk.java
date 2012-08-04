package org.xythax.net.phandler.packets;

import org.xythax.content.actions.ActionManager;
import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.combat.CombatEngine;
import org.xythax.model.map.FollowEngine;
import org.xythax.net.phandler.Packet;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;

public class Walk implements Packet {

	public static final int minimap = 248, map = 164, playerClick = 98;

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		if (!client.canWalk() || client.getStunnedTimer() > 0)
			return;

		if (client.forceMove)
			return;
		Entity ent = null;

		ent = client;

		if (ent != null) {
			if (ent.isBusy())
				return;
		}

		if (packetType == minimap) {
			packetSize -= 14;
		}
		if (packetType == map || packetType == minimap) {
			for (int pickupReset = 0; pickupReset < client.pickup.length; pickupReset++) {
				client.pickup[pickupReset] = -1;
			}
			FollowEngine.resetFollowing(client);
			client.resetFaceDirection();
			CombatEngine.resetAttack(client, false);
			if (ent.getFreezeDelay() > 0) {
				client.getActionSender().sendMessage(
						"A magical force holds you from moving!");
				return;
			}

		}
		ActionManager.destructActions(client.getUsername());
		client.cancelTasks();
		client.newWalkCmdSteps = packetSize - 5;

		if (client.newWalkCmdSteps % 2 != 0)
			client.println_debug("Warning: walkTo(" + packetType
					+ ") command malformed: "
					+ Utils.hex(client.inStream.buffer, 0, packetSize));
		client.newWalkCmdSteps /= 2;
		if (++client.newWalkCmdSteps > Constants.WALKING_QUEUE_SIZE) {
			client.newWalkCmdSteps = 0;

			return;
		}
		int firstStepX = client.inStream.readSignedWordBigEndianA()
				- client.mapRegionX * 8;
		for (int i = 1; i < client.newWalkCmdSteps; i++) {
			client.newWalkCmdX[i] = client.inStream.readSignedByte();
			client.newWalkCmdY[i] = client.inStream.readSignedByte();
		}

		client.newWalkCmdX[0] = client.newWalkCmdY[0] = 0;
		int firstStepY = client.inStream.readSignedWordBigEndian()
				- client.mapRegionY * 8;

		client.newWalkCmdIsRunning = client.inStream.readSignedByteC() == 1;
		for (int i = 0; i < client.newWalkCmdSteps; i++) {

			client.newWalkCmdX[i] += firstStepX;
			client.newWalkCmdY[i] += firstStepY;

		}
	}
}