package org.xythax.content;

import org.xythax.core.GameEngine;
import org.xythax.model.Client;

public class ItemDestroy {

	public static void option(Client client, int itemId) {
		String itemName = GameEngine.getItemManager().getItemDefinition(itemId)
				.getName();
		String[][] info = {
				{ "Are you sure you want to destroy this item?", "14174" },
				{ "Yes.", "14175" }, { "No.", "14176" }, { "", "14177" },
				{ "", "14182" }, { "", "14183" }, { itemName, "14184" } };
		client.getActionSender().sendFrame34(itemId, 0, 14171, 1);

		for (int i = 0; i < info.length; i++)
			client.getActionSender().sendQuest(info[i][0],
					Integer.parseInt(info[i][1]));

		client.destroyItem = itemId;
		client.getActionSender().sendFrame164(14170);
	}

	public static void destroyItem(Client client) {
		String itemName = GameEngine.getItemManager()
				.getItemDefinition(client.destroyItem).getName();
		client.getActionAssistant().deleteItem(client.destroyItem, 1);
		client.getActionSender().sendMessage(
				"Your " + itemName.toLowerCase()
						+ " vanishes as you drop it on the ground.");
		client.destroyItem = 0;
	}
}
