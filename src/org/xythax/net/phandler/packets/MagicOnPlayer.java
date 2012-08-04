package org.xythax.net.phandler.packets;

import org.xythax.content.actions.ActionManager;
import org.xythax.model.Client;
import org.xythax.model.combat.CombatEngine;
import org.xythax.model.combat.magic.TeleOther;
import org.xythax.net.phandler.Packet;
import org.xythax.world.PlayerManager;

/**
 * Magic on player
 * 
 * @author Killamess
 */

public class MagicOnPlayer implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {

		int magicOn = client.inStream.readSignedWordA();
		int spellId = client.inStream.readSignedWordBigEndian();

		Client enemy = (Client) PlayerManager.getPlayerManager().getPlayers()[magicOn];

		ActionManager.destructActions(client.getUsername());

		// System.out.println(spellId);

		if (enemy == null)
			return;

		for (int[] s : TeleOther.SPELLS) {
			if (s[0] == spellId) {
				TeleOther.castSpell(client, enemy, spellId);
				client.stopMovement();
				return;
			}
		}

		client.setRetaliateDelay(0);
		client.spellId = spellId;
		client.turnOffSpell = false;
		CombatEngine.addEvent(client, enemy);
		client.stopMovement();
	}
}
