package org.xythax.content.skills;

import org.xythax.content.actions.Action;
import org.xythax.content.actions.ActionManager;
import org.xythax.content.actions.Task;
import org.xythax.content.controllers.Animation;
import org.xythax.core.GameEngine;
import org.xythax.model.Client;

public class SmithingMakeItem implements Task {

	public boolean doaction(final Client c, final int toadd, final int barID,
			final int barsRemoved, final int timestomake, final int NOTUSED,
			final int NOTUSED2, final int xp) {
		if (c == null) {
			return false;
		}

		if (!c.getActionAssistant().playerHasItem(barID, barsRemoved)) {
			c.getActionSender().sendMessage(
					"You don't have enough bars to make this item!");
			return false;
		}
		Animation.addNewRequest(c, 898, 1);
		c.amountToMakeBars = timestomake;
		c.getActionSender().sendWindowsRemoval();
		c.barID = barID;
		c.itemToAdd = toadd;
		c.barsRemoved = barsRemoved;
		c.timestomake = timestomake;
		c.NOTUSED = NOTUSED;
		c.NOTUSED2 = NOTUSED2;
		c.xp = xp;
		ActionManager.addNewRequest(c, 18, 4);

		return false;

	}

	@Override
	public void execute(Action currentAction) {
		currentAction.getClient().getActionSender().sendWindowsRemoval();
		currentAction.setActionType(Action.type.LOOPING);
	}

	@Override
	public void loop(Action currentAction) {

		if (currentAction.getCurrentTick() > 0) {
			currentAction.setCurrentTick(currentAction.getCurrentTick() - 1);
			return;
		}

		Client client = currentAction.getClient();

		if (client == null) {
			stop(currentAction);
			return;
		}
		if (!client.getActionAssistant().playerHasItem(client.barID,
				client.barsRemoved)) {
			client.getActionSender().sendMessage(
					"You don't have enough bars to make this item!");
			stop(currentAction);
			return;
		}
		if (client.amountToMakeBars <= 0) {
			stop(currentAction);
			return;
		}
		if (client.amountToMakeBars > 1
				&& client.getActionAssistant().playerHasItem(client.barID,
						client.barsRemoved * 2)) {
			client.getActionSender().sendMessage(
					"You make some "
							+ GameEngine.getItemManager()
									.getItemDefinition((client.itemToAdd))
									.getName() + "s");
		} else {
			client.getActionSender().sendMessage(
					"You make a "
							+ GameEngine.getItemManager()
									.getItemDefinition((client.itemToAdd))
									.getName());
		}
		if (client.getActionAssistant().playerHasItem(client.barID,
				client.barsRemoved)) {
			if (client.barsRemoved == 1) {
				client.getActionAssistant().deleteItem(client.barID,
						client.barsRemoved);
			} else if (client.barsRemoved == 2) {
				client.getActionAssistant().deleteItem(client.barID,
						client.barsRemoved);
				client.getActionAssistant().deleteItem(client.barID,
						client.barsRemoved);
			} else if (client.barsRemoved == 3) {
				client.getActionAssistant().deleteItem(client.barID,
						client.barsRemoved);
				client.getActionAssistant().deleteItem(client.barID,
						client.barsRemoved);
				client.getActionAssistant().deleteItem(client.barID,
						client.barsRemoved);
			} else if (client.barsRemoved == 4) {
				client.getActionAssistant().deleteItem(client.barID,
						client.barsRemoved);
				client.getActionAssistant().deleteItem(client.barID,
						client.barsRemoved);
				client.getActionAssistant().deleteItem(client.barID,
						client.barsRemoved);
				client.getActionAssistant().deleteItem(client.barID,
						client.barsRemoved);
			} else if (client.barsRemoved == 5) {
				client.getActionAssistant().deleteItem(client.barID,
						client.barsRemoved);
				client.getActionAssistant().deleteItem(client.barID,
						client.barsRemoved);
				client.getActionAssistant().deleteItem(client.barID,
						client.barsRemoved);
				client.getActionAssistant().deleteItem(client.barID,
						client.barsRemoved);
				client.getActionAssistant().deleteItem(client.barID,
						client.barsRemoved);
			}
		}
		if (GameEngine.getItemManager().getItemDefinition((client.itemToAdd))
				.getName().contains("dart")) {
			client.getActionSender().sendInventoryItem(client.itemToAdd, 10);
		} else if (GameEngine.getItemManager()
				.getItemDefinition((client.itemToAdd)).getName()
				.contains("nail")) {
			client.getActionSender().sendInventoryItem(client.itemToAdd, 15);
		} else if (GameEngine.getItemManager()
				.getItemDefinition((client.itemToAdd)).getName()
				.contains("arrow")) {
			client.getActionSender().sendInventoryItem(client.itemToAdd, 15);
		} else if (GameEngine.getItemManager()
				.getItemDefinition((client.itemToAdd)).getName()
				.contains("knife")) {
			client.getActionSender().sendInventoryItem(client.itemToAdd, 5);
		} else if (GameEngine.getItemManager()
				.getItemDefinition((client.itemToAdd)).getName()
				.contains("Cannonball")) {
			client.getActionSender().sendInventoryItem(client.itemToAdd, 4);
		} else {
			client.getActionSender().sendInventoryItem(client.itemToAdd, 1);
		}
		client.getActionAssistant().addSkillXP(client.xp, 13);
		if (client.getActionAssistant().playerHasItem(client.barID,
				client.barsRemoved)) {
			Animation.addNewRequest(client, 898, 1);
		}
		client.amountToMakeBars--;
		currentAction.setCurrentTick(4);
	}

	@Override
	public void stop(Action currentAction) {
		Client client = currentAction.getClient();

		if (client != null) {
			client.getActionSender().sendAnimationReset();
			client.setBusy(false);
			client.amountToMakeBars = 0;
		}
		currentAction.setActionType(Action.type.TRASHING);
	}

	public SmithingMakeItem(Client c, int id, int amount) {
		readInput(c.playerLevel[13], Integer.toString(id), c, amount, -1, -1);
	}

	public SmithingMakeItem() {
		// TODO Auto-generated constructor stub
	}

	public void out(String s, Client c) {
		// TODO Auto-generated method stub

	}

	public void readInput(int level, String type, Client c, int amounttomake,
			int NOTUSED, int NOTUSED2) {

		String Name = GameEngine.getItemManager()
				.getItemDefinition(Integer.parseInt(type)).getName();

		if (Name.contains("Bronze")) {
			CheckBronze(c, level, amounttomake, type);
		} else if (Name.contains("Iron")) {
			CheckIron(c, level, amounttomake, type);
		} else if (Name.contains("Steel")) {
			CheckSteel(c, level, amounttomake, type);
		} else if (Name.contains("Cannonball")) {
			CheckSteelCannonBalls(c, level, amounttomake, type);
		} else if (Name.contains("Mith")) {
			CheckMith(c, level, amounttomake, type);
		} else if (Name.contains("Adam") || Name.contains("Addy")) {
			CheckAddy(c, level, amounttomake, type);
		} else if (Name.contains("Rune")) {
			CheckRune(c, level, amounttomake, type);
		} else {
			c.getActionSender().sendMessage("INVALID ARMOUR");
			return;
		}

	}

	private void CheckIron(Client c, int level, int amounttomake, String type) {
		c.smithremove = 2351;

		if (type.equals("1349") && level >= 16) // Axe
		{
			c.smithxp = 25;
			c.smithitem = 1349;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1203") && level >= 15) // Dagger
		{
			c.smithxp = 25;
			c.smithitem = 1203;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1420") && level >= 17) // Mace
		{
			c.smithxp = 25;
			c.smithitem = 1420;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1137") && level >= 18) // Med helm
		{
			c.smithxp = 25;
			c.smithitem = 1137;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("820") && level >= 19) // Dart tips
		{
			c.smithxp = 25;
			c.smithitem = 820;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1279") && level >= 19) // Sword (s)
		{
			c.smithxp = 25;
			c.smithitem = 1279;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("4820") && level >= 19) // Nails
		{
			c.smithxp = 25;
			c.smithitem = 4820;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("40") && level >= 20) // Arrow tips
		{
			c.smithxp = 25;
			c.smithitem = 40;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1323") && level >= 20)// Scim
		{
			c.smithxp = 50;
			c.smithitem = 1323;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1293") && level >= 21) // Longsword
		{
			c.smithxp = 50;
			c.smithitem = 1293;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("863") && level >= 22) // Knives
		{
			c.smithxp = 25;
			c.smithitem = 863;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1153") && level >= 22) // Full Helm
		{
			c.smithxp = 50;
			c.smithitem = 1153;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1175") && level >= 23) // Square shield
		{
			c.smithxp = 50;
			c.smithitem = 1175;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1335") && level >= 24) // Warhammer
		{
			c.smithxp = 38;
			c.smithitem = 1335;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1363") && level >= 25) // Battle axe
		{
			c.smithxp = 75;
			c.smithitem = 1363;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1101") && level >= 26) // Chain
		{
			c.smithxp = 75;
			c.smithitem = 1101;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1191") && level >= 27) // Kite
		{
			c.smithxp = 75;
			c.smithitem = 1191;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("3096") && level >= 28) // Claws
		{
			c.smithxp = 50;
			c.smithitem = 3096;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1309") && level >= 29) // 2h Sword
		{
			c.smithxp = 75;
			c.smithitem = 1309;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1067") && level >= 31) // Platelegs
		{
			c.smithxp = 75;
			c.smithitem = 1067;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1081") && level >= 31) // PlateSkirt
		{
			c.smithxp = 75;
			c.smithitem = 1081;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1115") && level >= 33) // Platebody
		{
			c.smithxp = 100;
			c.smithitem = 1115;
			c.smithremoveamount = 5;
			c.smithmaketimes = amounttomake;
		} else {
			c.getActionSender().sendMessage(
					"You don't have a high enough level to make this Item!");
			return;
		}
		doaction(c, c.smithitem, c.smithremove, c.smithremoveamount,
				c.smithmaketimes, -1, -1, c.smithxp);

	}

	private void CheckSteelCannonBalls(Client c, int level, int amounttomake,
			String type) {
		c.smithremove = 2353;

		if (type.equals("2") && level >= 31) // cannonballs
		{
			c.smithxp = 15;
			c.smithitem = 2;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		doaction(c, c.smithitem, c.smithremove, c.smithremoveamount,
				c.smithmaketimes, -1, -1, c.smithxp);

	}

	private void CheckSteel(Client c, int level, int amounttomake, String type) {
		c.smithremove = 2353;

		if (type.equals("1353") && level >= 31) // Axe
		{
			c.smithxp = 38;
			c.smithitem = 1353;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1207") && level >= 30) // Dagger
		{
			c.smithxp = 50;
			c.smithitem = 1207;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1424") && level >= 32) // Mace
		{
			c.smithxp = 50;
			c.smithitem = 1424;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1141") && level >= 33) // Med helm
		{
			c.smithxp = 50;
			c.smithitem = 1141;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("821") && level >= 34) // Dart tips
		{
			c.smithxp = 50;
			c.smithitem = 821;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1281") && level >= 34) // Sword (s)
		{
			c.smithxp = 50;
			c.smithitem = 1281;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1539") && level >= 34) // Nails
		{
			c.smithxp = 50;
			c.smithitem = 1539;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("41") && level >= 35) // Arrow tips
		{
			c.smithxp = 50;
			c.smithitem = 41;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1325") && level >= 35)// Scim
		{
			c.smithxp = 75;
			c.smithitem = 1325;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1295") && level >= 36) // Longsword
		{
			c.smithxp = 75;
			c.smithitem = 1295;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("865") && level >= 37) // Knives
		{
			c.smithxp = 50;
			c.smithitem = 865;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1157") && level >= 37) // Full Helm
		{
			c.smithxp = 75;
			c.smithitem = 1157;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1177") && level >= 38) // Square shield
		{
			c.smithxp = 75;
			c.smithitem = 1177;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1339") && level >= 39) // Warhammer
		{
			c.smithxp = 113;
			c.smithitem = 1339;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1365") && level >= 40) // Battle axe
		{
			c.smithxp = 113;
			c.smithitem = 1365;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1105") && level >= 41) // Chain
		{
			c.smithxp = 113;
			c.smithitem = 1105;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1193") && level >= 42) // Kite
		{
			c.smithxp = 113;
			c.smithitem = 1193;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("3097") && level >= 43) // Claws
		{
			c.smithxp = 75;
			c.smithitem = 3097;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1311") && level >= 44) // 2h Sword
		{
			c.smithxp = 113;
			c.smithitem = 1311;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1069") && level >= 46) // Platelegs
		{
			c.smithxp = 113;
			c.smithitem = 1069;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1083") && level >= 46) // PlateSkirt
		{
			c.smithxp = 113;
			c.smithitem = 1083;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1119") && level >= 48) // Platebody
		{
			c.smithxp = 188;
			c.smithitem = 1119;
			c.smithremoveamount = 5;
			c.smithmaketimes = amounttomake;
		} else {
			c.getActionSender().sendMessage(
					"You don't have a high enough level to make this Item!");
			return;
		}

		doaction(c, c.smithitem, c.smithremove, c.smithremoveamount,
				c.smithmaketimes, -1, -1, c.smithxp);

	}

	private void CheckMith(Client c, int level, int amounttomake, String type) {
		c.smithremove = 2359;

		if (type.equals("2") && level >= 31) // cannonballs
		{
			c.smithxp = 15;
			c.smithitem = 2;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}
		if (type.equals("1355") && level >= 51) // Axe
		{
			c.smithxp = 50;
			c.smithitem = 1355;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1209") && level >= 50) // Dagger
		{
			c.smithxp = 50;
			c.smithitem = 1209;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1428") && level >= 52) // Mace
		{
			c.smithxp = 50;
			c.smithitem = 1428;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1143") && level >= 53) // Med helm
		{
			c.smithxp = 50;
			c.smithitem = 1143;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("822") && level >= 54) // Dart tips
		{
			c.smithxp = 50;
			c.smithitem = 822;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1285") && level >= 54) // Sword (s)
		{
			c.smithxp = 50;
			c.smithitem = 1285;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("4822") && level >= 54) // Nails
		{
			c.smithxp = 50;
			c.smithitem = 4822;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("42") && level >= 55) // Arrow tips
		{
			c.smithxp = 50;
			c.smithitem = 42;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1329") && level >= 55)// Scim
		{
			c.smithxp = 100;
			c.smithitem = 1329;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1299") && level >= 56) // Longsword
		{
			c.smithxp = 100;
			c.smithitem = 1299;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("866") && level >= 57) // Knives
		{
			c.smithxp = 50;
			c.smithitem = 866;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1159") && level >= 57) // Full Helm
		{
			c.smithxp = 100;
			c.smithitem = 1159;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1181") && level >= 58) // Square shield
		{
			c.smithxp = 100;
			c.smithitem = 1181;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1343") && level >= 59) // Warhammer
		{
			c.smithxp = 150;
			c.smithitem = 1343;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1369") && level >= 60) // Battle axe
		{
			c.smithxp = 150;
			c.smithitem = 1369;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1109") && level >= 61) // Chain
		{
			c.smithxp = 150;
			c.smithitem = 1109;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1197") && level >= 62) // Kite
		{
			c.smithxp = 150;
			c.smithitem = 1197;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("3099") && level >= 63) // Claws
		{
			c.smithxp = 100;
			c.smithitem = 3099;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1315") && level >= 64) // 2h Sword
		{
			c.smithxp = 150;
			c.smithitem = 1315;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1071") && level >= 66) // Platelegs
		{
			c.smithxp = 150;
			c.smithitem = 1071;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1085") && level >= 66) // PlateSkirt
		{
			c.smithxp = 150;
			c.smithitem = 1085;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1121") && level >= 68) // Platebody
		{
			c.smithxp = 250;
			c.smithitem = 1121;
			c.smithremoveamount = 5;
			c.smithmaketimes = amounttomake;
		} else {
			c.getActionSender().sendMessage(
					"You don't have a high enough level to make this Item!");
			return;
		}

		doaction(c, c.smithitem, c.smithremove, c.smithremoveamount,
				c.smithmaketimes, -1, -1, c.smithxp);

	}

	private void CheckRune(Client c, int level, int amounttomake, String type) {
		c.smithremove = 2363;

		if (type.equals("2") && level >= 31) // cannonballs
		{
			c.smithxp = 15;
			c.smithitem = 2;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}
		if (type.equals("1359") && level >= 86) // Axe
		{
			c.smithxp = 75;
			c.smithitem = 1359;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1213") && level >= 85) // Dagger
		{
			c.smithxp = 75;
			c.smithitem = 1213;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1432") && level >= 87) // Mace
		{
			c.smithxp = 75;
			c.smithitem = 1432;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1147") && level >= 88) // Med helm
		{
			c.smithxp = 75;
			c.smithitem = 1147;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("824") && level >= 89) // Dart tips
		{
			c.smithxp = 75;
			c.smithitem = 824;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1289") && level >= 89) // Sword (s)
		{
			c.smithxp = 75;
			c.smithitem = 1289;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("4824") && level >= 89) // Nails
		{
			c.smithxp = 75;
			c.smithitem = 4824;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("44") && level >= 90) // Arrow tips
		{
			c.smithxp = 75;
			c.smithitem = 44;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1333") && level >= 90)// Scim
		{
			c.smithxp = 150;
			c.smithitem = 1333;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1303") && level >= 91) // Longsword
		{
			c.smithxp = 150;
			c.smithitem = 1303;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("868") && level >= 92) // Knives
		{
			c.smithxp = 75;
			c.smithitem = 868;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1163") && level >= 92) // Full Helm
		{
			c.smithxp = 150;
			c.smithitem = 1163;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1185") && level >= 93) // Square shield
		{
			c.smithxp = 150;
			c.smithitem = 1185;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1347") && level >= 94) // Warhammer
		{
			c.smithxp = 225;
			c.smithitem = 1347;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1373") && level >= 95) // Battle axe
		{
			c.smithxp = 225;
			c.smithitem = 1373;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1113") && level >= 96) // Chain
		{
			c.smithxp = 225;
			c.smithitem = 1113;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1201") && level >= 97) // Kite
		{
			c.smithxp = 225;
			c.smithitem = 1201;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("3101") && level >= 98) // Claws
		{
			c.smithxp = 150;
			c.smithitem = 3101;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1319") && level >= 99) // 2h Sword
		{
			c.smithxp = 225;
			c.smithitem = 1319;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1079") && level >= 99) // Platelegs
		{
			c.smithxp = 225;
			c.smithitem = 1079;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1093") && level >= 99) // PlateSkirt
		{
			c.smithxp = 225;
			c.smithitem = 1093;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1127") && level >= 99) // Platebody
		{
			c.smithxp = 313;
			c.smithitem = 1127;
			c.smithremoveamount = 5;
			c.smithmaketimes = amounttomake;
		} else {
			c.getActionSender().sendMessage(
					"You don't have a high enough level to make this Item!");
			return;
		}

		doaction(c, c.smithitem, c.smithremove, c.smithremoveamount,
				c.smithmaketimes, -1, -1, c.smithxp);

	}

	private void CheckAddy(Client c, int level, int amounttomake, String type) {
		c.smithremove = 2361;

		if (type.equals("2") && level >= 31) // cannonballs
		{
			c.smithxp = 15;
			c.smithitem = 2;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}
		if (type.equals("1357") && level >= 71) // Axe
		{
			c.smithxp = 70;
			c.smithitem = 1357;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1211") && level >= 70) // Dagger
		{
			c.smithxp = 63;
			c.smithitem = 1211;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1430") && level >= 72) // Mace
		{
			c.smithxp = 70;
			c.smithitem = 1430;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1145") && level >= 73) // Med helm
		{
			c.smithxp = 70;
			c.smithitem = 1145;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("823") && level >= 74) // Dart tips
		{
			c.smithxp = 70;
			c.smithitem = 823;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1287") && level >= 74) // Sword (s)
		{
			c.smithxp = 70;
			c.smithitem = 1287;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("4823") && level >= 74) // Nails
		{
			c.smithxp = 70;
			c.smithitem = 4823;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("43") && level >= 75) // Arrow tips
		{
			c.smithxp = 70;
			c.smithitem = 43;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1331") && level >= 75)// Scim
		{
			c.smithxp = 135;
			c.smithitem = 1331;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1301") && level >= 76) // Longsword
		{
			c.smithxp = 135;
			c.smithitem = 1301;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("867") && level >= 77) // Knives
		{
			c.smithxp = 70;
			c.smithitem = 867;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1161") && level >= 77) // Full Helm
		{
			c.smithxp = 135;
			c.smithitem = 1161;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1183") && level >= 78) // Square shield
		{
			c.smithxp = 135;
			c.smithitem = 1183;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1345") && level >= 79) // Warhammer
		{
			c.smithxp = 200;
			c.smithitem = 1345;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1371") && level >= 80) // Battle axe
		{
			c.smithxp = 200;
			c.smithitem = 1371;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1111") && level >= 81) // Chain
		{
			c.smithxp = 200;
			c.smithitem = 1111;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1199") && level >= 82) // Kite
		{
			c.smithxp = 200;
			c.smithitem = 1199;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("3100") && level >= 83) // Claws
		{
			c.smithxp = 135;
			c.smithitem = 3100;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1317") && level >= 84) // 2h Sword
		{
			c.smithxp = 198;
			c.smithitem = 1317;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1073") && level >= 86) // Platelegs
		{
			c.smithxp = 198;
			c.smithitem = 1073;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1091") && level >= 86) // PlateSkirt
		{
			c.smithxp = 198;
			c.smithitem = 1091;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1123") && level >= 88) // Platebody
		{
			c.smithxp = 325;
			c.smithitem = 1123;
			c.smithremoveamount = 5;
			c.smithmaketimes = amounttomake;
		} else {
			c.getActionSender().sendMessage(
					"You don't have a high enough level to make this Item!");
			return;
		}

		doaction(c, c.smithitem, c.smithremove, c.smithremoveamount,
				c.smithmaketimes, -1, -1, c.smithxp);

	}

	private void CheckBronze(Client c, int level, int amounttomake, String type) {

		if (type.equals("2") && level >= 31) // cannonballs
		{
			c.smithxp = 15;
			c.smithitem = 2;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}
		if (type.equals("1351") && level >= 1) {
			c.smithxp = 13;
			c.smithitem = 1351;
			c.smithremove = 2349;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1205") && level >= 1) {
			c.smithxp = 13;
			c.smithitem = 1205;
			c.smithremove = 2349;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1422") && level >= 2) {
			c.smithxp = 13;
			c.smithitem = 1422;
			c.smithremove = 2349;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1139") && level >= 3) {
			c.smithxp = 13;
			c.smithitem = 1139;
			c.smithremove = 2349;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("819") && level >= 4) {
			c.smithxp = 13;
			c.smithitem = 819;
			c.smithremove = 2349;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1277") && level >= 4) {
			c.smithxp = 13;
			c.smithitem = 1277;
			c.smithremove = 2349;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("4819") && level >= 4) {
			c.smithxp = 13;
			c.smithitem = 4819;
			c.smithremove = 2349;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("39") && level >= 5) {
			c.smithxp = 13;
			c.smithitem = 39;
			c.smithremove = 2349;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1321") && level >= 5) {
			c.smithxp = 25;
			c.smithitem = 1321;
			c.smithremove = 2349;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1291") && level >= 6) {
			c.smithxp = 25;
			c.smithitem = 1291;
			c.smithremove = 2349;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("864") && level >= 7) {
			c.smithxp = 25;
			c.smithitem = 864;
			c.smithremove = 2349;
			c.smithremoveamount = 1;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1155") && level >= 7) {
			c.smithxp = 25;
			c.smithitem = 1155;
			c.smithremove = 2349;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1173") && level >= 8) {
			c.smithxp = 25;
			c.smithitem = 1173;
			c.smithremove = 2349;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1337") && level >= 9) {
			c.smithxp = 38;
			c.smithitem = 1337;
			c.smithremove = 2349;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1375") && level >= 10) {
			c.smithxp = 38;
			c.smithitem = 1375;
			c.smithremove = 2349;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1103") && level >= 11) {
			c.smithxp = 38;
			c.smithitem = 1103;
			c.smithremove = 2349;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		}

		else if (type.equals("1189") && level >= 12) {
			c.smithxp = 38;
			c.smithitem = 1189;
			c.smithremove = 2349;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("3095") && level >= 13) {
			c.smithxp = 25;
			c.smithitem = 3095;
			c.smithremove = 2349;
			c.smithremoveamount = 2;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1307") && level >= 14) {
			c.smithxp = 38;
			c.smithitem = 1307;
			c.smithremove = 2349;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1075") && level >= 16) {
			c.smithxp = 38;
			c.smithitem = 1075;
			c.smithremove = 2349;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1087") && level >= 16) {
			c.smithxp = 38;
			c.smithitem = 1087;
			c.smithremove = 2349;
			c.smithremoveamount = 3;
			c.smithmaketimes = amounttomake;
		} else if (type.equals("1117") && level >= 18) {
			c.smithxp = 63;
			c.smithitem = 1117;
			c.smithremove = 2349;
			c.smithremoveamount = 5;
			c.smithmaketimes = amounttomake;
		} else {
			c.getActionSender().sendMessage(
					"You don't have a high enough level to make this Item!");
			return;
		}

		doaction(c, c.smithitem, c.smithremove, c.smithremoveamount,
				c.smithmaketimes, -1, -1, c.smithxp);

	}

}