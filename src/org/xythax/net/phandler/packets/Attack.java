package org.xythax.net.phandler.packets;

import org.xythax.content.actions.ActionManager;
import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.MobileEntity;
import org.xythax.model.combat.CombatEngine;
import org.xythax.model.combat.ranged.Ranged;
import org.xythax.model.map.FollowEngine;
import org.xythax.model.map.TileManager;
import org.xythax.net.phandler.Packet;
import org.xythax.world.PlayerManager;

public class Attack implements Packet {

	public static final int NPC = 72;
	public static final int PLAYER = 128;

	@Override
	public void handlePacket(final Client client, int packetType, int packetSize) {

		if (client.getStunnedTimer() > 0 || client.isBusy())
			return;

		MobileEntity target = null;

		if (Ranged.isUsingRange(client)) {
			if (!Ranged.hasArrows(client)) {
				client.stopMovement();
				return;
			}
		}
		if (packetType == NPC) {
			target = GameEngine.getNPCManager().getNPC(
					client.inStream.readUnsignedWordA());
		} else if (packetType == PLAYER) {
			target = PlayerManager.getPlayerManager().getPlayers()[client.inStream
					.readSignedWord()];
		}
		if (target == null)
			return;
		if (Ranged.isUsingRange(client)) {
			client.stopMovement();
		}

		if (target.getOwner() != null && target.getOwner() != client) {
			client.getActionSender().sendMessage("You cannot attack this npc.");
			return;
		}

		ActionManager.destructActions(client.getUsername());
		client.turnOffSpell = false;
		client.spellId = 0;
		Entity ent = client;
		client.setRetaliateDelay(0);
		CombatEngine.addEvent(client, target);
		client.hasStartedToFollow = true;
		client.walkToSpot = TileManager.currentLocation(target);
		ent.following = target;
		FollowEngine.loop(client);

	}

}