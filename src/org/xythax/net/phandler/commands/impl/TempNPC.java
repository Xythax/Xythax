package org.xythax.net.phandler.commands.impl;

import org.xythax.core.GameEngine;
import org.xythax.model.Client;
import org.xythax.model.NPC;
import org.xythax.model.definition.entity.NPCDefinition;
import org.xythax.net.db.Loadable;
import org.xythax.net.phandler.commands.Command;

public class TempNPC implements Command {
	@Override
	public void execute(Client client, String command) {
		if (client.getPrivileges() >= 2) {
			try {
				int npcId = Integer.valueOf(command.substring(8));
				int slot = GameEngine.getNPCManager().freeSlot();
				NPCDefinition def = GameEngine.getNPCManager().npcDefinitions
						.get(npcId);

				if (def == null)
					return;

				NPC npc = new NPC(slot, def, client.getAbsX(),
						client.getAbsY(), client.getHeightLevel());
				npc.setX1(npc.getAbsX());
				npc.setY1(npc.getAbsY());
				npc.setX2(npc.getAbsX());
				npc.setY2(npc.getAbsY());
				npc.setWalking(false);
				GameEngine.getNPCManager().npcMap.put(npc.getNpcId(), npc);
				client.getActionSender().sendMessage(
						"You spawn a " + def.getName() + ".");
				npc.setOwner(client);
				npc.following = client;

				Loadable.storeNpcToDatabase(npc);

			} catch (Exception e) {
			}
		}

	}
}