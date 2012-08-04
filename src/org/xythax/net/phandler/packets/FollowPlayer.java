package org.xythax.net.phandler.packets;

import org.xythax.content.actions.ActionManager;
import org.xythax.model.Client;
import org.xythax.model.combat.CombatEngine;
import org.xythax.model.map.FollowEngine;
import org.xythax.model.map.TileManager;
import org.xythax.net.phandler.Packet;
import org.xythax.utils.Utils;
import org.xythax.world.PlayerManager;

public class FollowPlayer implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		CombatEngine.resetAttack(client, false);

		int followId = Utils.HexToInt(client.inStream.buffer, 0, packetSize) / 1000;

		Client follow = (Client) PlayerManager.getPlayerManager().getPlayers()[followId];
		ActionManager.destructActions(client.getUsername());
		if (follow != null) {
			client.hasStartedToFollow = true;
			client.walkToSpot = TileManager.currentLocation(follow);
			client.following = follow;
			FollowEngine.loop(client);
		}
	}
}