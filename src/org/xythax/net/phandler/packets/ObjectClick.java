package org.xythax.net.phandler.packets;

import org.xythax.content.actions.ActionManager;
import org.xythax.content.controllers.Location;
import org.xythax.content.objects.ObjectController;
import org.xythax.content.objects.ObjectStorage;
import org.xythax.content.skills.Mining;
import org.xythax.model.Client;
import org.xythax.model.combat.CombatEngine;
import org.xythax.net.phandler.Packet;

public class ObjectClick implements Packet {

	public static final int FIRST_CLICK = 132, SECOND_CLICK = 252;

	public void handlePacket(Client client, int packetType, int packetSize) {
		int objectY = 0;
		int objectID = 0;
		int objectX = 0;

		switch (packetType) {

		case FIRST_CLICK:
			objectX = client.inStream.readSignedWordBigEndianA();
			objectID = client.inStream.readUnsignedWord();
			objectY = client.inStream.readUnsignedWordA();
			if (objectID == 881) {
				client.getActionSender().sendMessage("You enter the hole");
				Location.addNewRequest(client, 3237, 9857, 0, 1);
				break;
			}
			if (objectID == 11666) {
				break;
			}
			if (objectID == 1755 && objectX == 3237 && objectY == 9858) {
				// GameEngine.getObjectManager().addObject(new WorldObject(-1,
				// object[2], object[3], 0, Face.NORTH, 0));
				client.getActionSender().sendMessage("Temp Solution");
				Location.addNewRequest(client, 3238, 9898, 0, 1);
				break;
			}
			if (objectID == 9497 && objectX == 3008 && objectY == 3150) {
				// GameEngine.getObjectManager().addObject(new WorldObject(-1,
				// object[2], object[3], 0, Face.NORTH, 0));
				client.getActionSender().sendMessage("Temp Solution");
				Location.addNewRequest(client, 1298, 4512, 0, 1);
				break;
			}
			if (objectID == 1804 && objectX == 3115 && objectY == 3449) {
				// GameEngine.getObjectManager().addObject(new WorldObject(-1,
				// object[2], object[3], 0, Face.NORTH, 0));
				client.getActionSender().sendMessage("Temp Solution");
				Location.addNewRequest(client, 3115, 3450, 0, 1);
				break;
			}
			if (objectID == 1754 && objectX == 3116 && objectY == 3452) {
				// GameEngine.getObjectManager().addObject(new WorldObject(-1,
				// object[2], object[3], 0, Face.NORTH, 0));
				client.getActionSender().sendMessage("Temp Solution");
				Location.addNewRequest(client, 3117, 9852, 0, 1);
				break;
			}
			if (objectID == 1750 && objectX == 3116 && objectY == 9852) {
				// GameEngine.getObjectManager().addObject(new WorldObject(-1,
				// object[2], object[3], 0, Face.NORTH, 0));
				client.getActionSender().sendMessage("Temp Solution");
				Location.addNewRequest(client, 3116, 3451, 0, 1);
				break;
			}

			if (objectID == 1568 && objectX == 3097 && objectY == 3468) {
				// GameEngine.getObjectManager().addObject(new WorldObject(-1,
				// object[2], object[3], 0, Face.NORTH, 0));
				client.getActionSender().sendMessage("Temp Solution");
				Location.addNewRequest(client, 3097, 9868, 0, 1);
				break;
			}

			if (objectID == 2883 && objectX == 3267 && objectY == 3228) {
				// GameEngine.getObjectManager().addObject(new WorldObject(-1,
				// object[2], object[3], 0, Face.NORTH, 0));
				client.getActionSender().sendMessage(
						"You pass through the gate.");
				Location.addNewRequest(client, 3268, 3228, 0, 1);
				break;
			}

			if (objectID == 1551 && objectX == 3252 && objectY == 3266) {
				// GameEngine.getObjectManager().addObject(new WorldObject(-1,
				// object[2], object[3], 0, Face.NORTH, 0));
				client.getActionSender().sendMessage(
						"You pass through the gate.");
				Location.addNewRequest(client, 3253, 3266, 0, 1);
				break;
			}

			if (objectID == 1553 && objectX == 3237 && objectY == 3295) {
				// GameEngine.getObjectManager().addObject(new WorldObject(-1,
				// object[2], object[3], 0, Face.NORTH, 0));
				client.getActionSender().sendMessage(
						"You pass through the gate.");
				Location.addNewRequest(client, 3236, 3295, 0, 1);
				break;
			}

			ActionManager.destructActions(client.getUsername());
			if (client.getFreezeDelay() > 0) {
				client.getActionSender().sendMessage(
						"A magical force holds you from moving!");
				return;
			}
			CombatEngine.resetAttack(client, false);
			ObjectController.run(client,
					ObjectStorage.compress(objectID, objectX, objectY));
			break;

		case SECOND_CLICK:
			objectID = client.inStream.readUnsignedWordBigEndianA();
			objectY = client.inStream.readSignedWordBigEndian();
			objectX = client.inStream.readUnsignedWordA();
			if (objectID == 11666) {
				break;
			}
			ActionManager.destructActions(client.getUsername());
			if (client.getFreezeDelay() > 0) {
				client.getActionSender().sendMessage(
						"A magical force holds you from moving!");
				return;
			}
			CombatEngine.resetAttack(client, false);
			int c = 0;
			for (int i : Mining.RockID) {
				if (i == objectID) {
					client.getActionSender().sendMessage(
							"This rock contains " + Mining.getName[c] + ".");
					return;
				}
				c++;
			}
			ObjectController.run(client,
					ObjectStorage.compress(objectID, objectX, objectY));
			break;
		}
	}

}