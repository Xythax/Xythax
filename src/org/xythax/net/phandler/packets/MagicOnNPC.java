package org.xythax.net.phandler.packets;

import org.xythax.content.actions.ActionManager;
import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.combat.CombatEngine;
import org.xythax.model.map.FollowEngine;
import org.xythax.net.phandler.Packet;

public class MagicOnNPC implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		int npcId = client.inStream.readSignedWordBigEndianA();
		int spellId = client.inStream.readSignedWordA();

		Entity target = null;

		target = GameEngine.getNPCManager().getNPC(npcId);

		ActionManager.destructActions(client.getUsername());
		if (target == null)
			return;
		if (target.getOwner() != null && target.getOwner() != client) {
			client.getActionSender().sendMessage("You cannot attack this npc.");
			return;
		}
		client.spellId = spellId;
		client.turnOffSpell = false;
		client.stopMovement();
		client.setRetaliateDelay(0);
		FollowEngine.resetFollowing(client);
		CombatEngine.addEvent(client, target);

	}
}
