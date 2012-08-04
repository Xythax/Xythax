package org.xythax.net.phandler.packets;

import org.xythax.content.actions.ActionManager;
import org.xythax.content.objects.ObjectController;
import org.xythax.content.objects.ObjectStorage;
import org.xythax.content.skills.SmithingMakeInterface;
import org.xythax.model.Client;
import org.xythax.model.definition.XMLManager;
import org.xythax.model.definition.skills.Cooking;
import org.xythax.net.phandler.Packet;

public class ItemOnObject implements Packet {

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		int junk = client.inStream.readUnsignedWord();
		int objectId = client.inStream.readSignedWordBigEndian();
		int objectY = client.inStream.readSignedWordBigEndianA();
		int junk2 = client.inStream.readUnsignedWord();
		int objectX = client.inStream.readSignedWordBigEndianA();
		int itemId = client.inStream.readUnsignedWord();

		ActionManager.destructActions(client.getUsername());
		if (client.isBusy())
			return;

		if (objectId == 12102 || objectId == 12269 || objectId == 8712
				|| objectId == 9085 || objectId == 9086 || objectId == 9087
				|| objectId == 2728 || objectId == 2729 || objectId == 2730
				|| objectId == 2731 || objectId == 2859 || objectId == 3039
				|| objectId == 5275 || objectId == 114 || objectId == 8750) {
			for (Cooking food : XMLManager.ingredients) {
				if (food.getRawType() == itemId) {
					client.cooking = itemId;
					ObjectController.run(client,
							ObjectStorage.compress(objectId, objectX, objectY));
					return;
				}
			}
		}
		if (objectId == 12102 || objectId == 2782 || objectId == 2783
				|| objectId == 4306 || objectId == 6150) {
			client.getActionAssistant().turnTo(client.objectX,
					client.objectY + 1);
			if (client.getActionAssistant().playerHasItem(2347, 1)) {
				if (itemId == 2349)
					new SmithingMakeInterface(client, objectId, "BRONZE",
							client.objectX, client.objectY);
				else if (itemId == 2351)
					new SmithingMakeInterface(client, objectId, "IRON",
							client.objectX, client.objectY);
				else if (itemId == 2359)
					new SmithingMakeInterface(client, objectId, "MITH",
							client.objectX, client.objectY);
				else if (itemId == 2353)
					new SmithingMakeInterface(client, objectId, "STEEL",
							client.objectX, client.objectY);
				else if (itemId == 2361)
					new SmithingMakeInterface(client, objectId, "ADDY",
							client.objectX, client.objectY);
				else if (itemId == 2363)
					new SmithingMakeInterface(client, objectId, "RUNE",
							client.objectX, client.objectY);
				else
					client.getActionSender().sendMessage(
							"You can't use this item on an anvil!");
			} else {
				client.getActionSender().sendMessage(
						"You need a hammer to make items!");
			}
		}
		if (objectId == 2732 || objectId == 3038 || objectId == 3769
				|| objectId == 3775 || objectId == 4265 || objectId == 4266
				|| objectId == 5499 || objectId == 5249 || objectId == 5631
				|| objectId == 5632 || objectId == 5981) {
			for (Cooking food : XMLManager.ingredients) {
				if (food.getRawType() == itemId) {
					client.cooking = itemId;
					ObjectController.run(client,
							ObjectStorage.compress(objectId, objectX, objectY));
					return;
				}
			}
		}
		if (itemId >= 434 && itemId <= 454) {
			if (objectId == 12100 || objectId == 2781 || objectId == 2785
					|| objectId == 2966 || objectId == 3044 || objectId == 3294
					|| objectId == 3413 || objectId == 4304 || objectId == 4305
					|| objectId == 6189 || objectId == 6190
					|| objectId == 11666) {
				client.getActionAssistant().turnTo(objectX, objectY + 1);
				for (int fi = 0; fi < client.smelt_frame.length; fi++) {
					client.getActionSender().sendFrame246(
							client.smelt_frame[fi], 150, client.smelt_bars[fi]);
					client.getActionSender().sendFrame164(2400);
				}
			}
		}
		switch (junk) {
		}
		switch (junk2) {
		}

		switch (itemId) {
		case 1074:
			break;

		case 1592:
		case 1595:
		case 1597:
			if (objectId == 11666 || objectId == 2643) { // furnaces
				ObjectController.run(client,
						ObjectStorage.compress(objectId, objectX, objectY));
			}
			break;

		case 444:// gold ore
			if (objectId == 11666 || objectId == 2643) { // furnaces
				client.oreId = itemId;
				ObjectController.run(client,
						ObjectStorage.compress(objectId, objectX, objectY));
			}
			break;

		case 1935:
			switch (objectId) {
			case 873:
			case 874:
			case 879:
			case 880:
			case 4063:
			case 6151:
			case 8699:
			case 9143:
			case 9684:
			case 10175:
			case 13564:
			case 13563:
			case 12974:
			case 12279:
				ObjectController.run(client,
						ObjectStorage.compress(objectId, objectX, objectY));
				break;
			default:
				break;
			}

		default:
			break;
		}

	}

}
