package org.xythax.net.phandler.packets;

import org.xythax.content.WalkToNPC;
import org.xythax.content.actions.ActionManager;
import org.xythax.content.controllers.Animation;
import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.Entity;
import org.xythax.model.NPC;
import org.xythax.net.phandler.Packet;

/**
 * Click NPC packet.
 * 
 * @author Killamess
 */
public class ClickNPC implements Packet {
	// 155 17 14
	public static final int FIRST_CLICK = 155, SECOND_CLICK = 17,
			THIRD_CLICK = 21;

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {

		Entity npc = null;

		int clickType = 0;

		switch (packetType) {

		case FIRST_CLICK:
			npc = GameEngine.getNPCManager().getNPC(
					client.inStream.readSignedWordBigEndian());
			clickType = 1;
			break;

		case SECOND_CLICK:
			npc = GameEngine.getNPCManager().getNPC(
					client.inStream.readUnsignedWordBigEndianA());
			clickType = 2;
			break;

		case THIRD_CLICK:
			npc = GameEngine.getNPCManager().getNPC(
					client.inStream.readSignedWord());// client.inStream.readUnsignedWordA()
			clickType = 3;
			break;

		default:
			break;

		}
		ActionManager.destructActions(client.getUsername());

		if (npc == null)
			return;

		Animation.face(client, npc);
		WalkToNPC.setDestination(client, ((NPC) npc),
				WalkToNPC.getNpcWalkType(((NPC) npc), clickType));
	}

}
