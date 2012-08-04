package org.xythax.net.phandler.packets;

import org.xythax.model.Client;
import org.xythax.model.combat.magic.Alchemist;
import org.xythax.model.combat.magic.Enchant;
import org.xythax.net.phandler.Packet;

public class MagicOnInventoryItem implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {

		int slot = client.inStream.readSignedWord();
		int itemId = client.inStream.readSignedWordA();
		int junk = client.inStream.readSignedWord();
		int spellId = client.inStream.readSignedWordA();

		if (client.playerItems[slot] - 1 != itemId)
			return;

		// System.out.println(spellId);

		if (spellId == 1162 || spellId == 1178)
			Alchemist.alch(client, itemId, spellId, junk);

		/**
		 * Enchant spells.
		 */
		if (spellId == 1155 || spellId == 1165 || spellId == 1176
				|| spellId == 1180 || spellId == 1187 || spellId == 6003) {
			Enchant.item(client, spellId, itemId, slot);
		}
	}
}
