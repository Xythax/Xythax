package org.xythax.net.phandler.packets;

import org.xythax.content.actions.ActionManager;
import org.xythax.model.Client;
import org.xythax.net.phandler.Packet;
import org.xythax.utils.Constants;

public class CharacterDesign implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {

		int sex = client.inStream.readSignedByte();

		int head = client.inStream.readSignedByte();
		int beard = client.inStream.readSignedByte();
		int body = client.inStream.readSignedByte();
		int arms = client.inStream.readSignedByte();
		int hands = client.inStream.readSignedByte();
		int legs = client.inStream.readSignedByte();
		int feet = client.inStream.readSignedByte();

		int hairColour = client.inStream.readSignedByte();
		int bodyColour = client.inStream.readSignedByte();
		int legColour = client.inStream.readSignedByte();
		int feetColour = client.inStream.readSignedByte();
		int skinColour = client.inStream.readSignedByte();

		client.playerLook[Constants.SEX] = sex;
		client.playerLook[Constants.HAIR_COLOUR] = hairColour;
		client.playerLook[Constants.BODY_COLOUR] = bodyColour;
		client.playerLook[Constants.LEG_COLOUR] = legColour;
		client.playerLook[Constants.FEET_COLOUR] = feetColour;
		client.playerLook[Constants.SKIN_COLOUR] = skinColour;

		client.playerLook[Constants.HEAD] = head;
		client.playerLook[Constants.BODY] = body;
		client.playerLook[Constants.ARMS] = arms;
		client.playerLook[Constants.HANDS] = hands;
		client.playerLook[Constants.LEGS] = legs;
		client.playerLook[Constants.FEET] = feet;
		client.playerLook[Constants.BEARD] = beard;

		client.appearanceSet = true;
		client.updateRequired = true;
		client.appearanceUpdateRequired = true;

		client.getActionSender().sendWindowsRemoval();

		ActionManager.destructActions(client.getUsername());
	}
}