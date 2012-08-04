package org.xythax.content.skills;

import java.util.ArrayList;
import java.util.List;

import org.xythax.core.Server;
import org.xythax.content.actions.Action;
import org.xythax.content.actions.Task;
import org.xythax.content.objects.ObjectReplacer;
import org.xythax.content.objects.ObjectStorage;
import org.xythax.model.Client;
import org.xythax.utils.CommonStrings;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;
import org.xythax.world.ObjectManager;


/**
 * 
 * @author killamess
 *
 */

public class Mining implements Task { 
	
	public boolean canMineRock(int[] location) {
		
		if (ObjectManager.objectReplacements.size() == 0)
			return true;
		
		List<ObjectReplacer> newList = new ArrayList<ObjectReplacer>(ObjectManager.objectReplacements);
		
		for (ObjectReplacer r : newList) {
			int[] objectLocation = r.getLocation();
			if (location[0] == objectLocation[0] && location[1] == objectLocation[1] && location[2] == objectLocation[2]) {
				return false;
			}
		}
		newList.clear();
		return true;
	}

	@Override
	public void execute(Action currentAction) {
		
		int[] object = ObjectStorage.getDetails(currentAction.getClient());
		
		currentAction.getClient().objectID = object[0];
		currentAction.getClient().objectX = object[2];			
		currentAction.getClient().objectY = object[3];
		currentAction.getClient().objectSize = object[1];
		currentAction.getClient().setPlayerX = currentAction.getClient().getAbsX();
		currentAction.getClient().setPlayerY = currentAction.getClient().getAbsY();
		
		if (badObject(currentAction.getClient()) || badPlayerSetup(currentAction.getClient())) {
			stop(currentAction);
			return;
		}
		if (currentAction.getClient().getActionAssistant().freeSlots() == 0) {
			currentAction.getClient().getActionSender().sendMessage("There is not enough space in your inventory.");
			stop(currentAction);
			return;
		}
		currentAction.getClient().getActionAssistant().turnTo(currentAction.getClient().objectX, currentAction.getClient().objectY);
		currentAction.getClient().getActionAssistant().startAnimation(getMiningAnimation(currentAction.getClient()), 0);
		currentAction.getClient().getActionSender().sendMessage("You swing your pickaxe at the rock.");
		currentAction.setActionType(Action.type.LOOPING);
	}

	@Override
	public void loop(Action currentAction) {
		
		currentAction.setCurrentTick(currentAction.getCurrentTick() - 1);
		if (currentAction.getCurrentTick() > 1) {
			return;
		}
			
		Client client = currentAction.getClient();
		
		int[] object = ObjectStorage.getDetails(currentAction.getClient());
		
		if (client == null || !canMineRock(new int[] {object[2], object[3], client.getHeightLevel()})) {
			stop(currentAction);
			return;
		}
		if (client.getActionAssistant().freeSlots() == 0) {
			client.getActionSender().sendMessage("There is not enough space in your inventory.");
			stop(currentAction);
			return;
		}
		if (badObject(currentAction.getClient()) || badPlayerSetup(currentAction.getClient())) {
			stop(currentAction);
			return;
		}
		startMiner(currentAction);
		if (canMineRock(new int[] {object[2], object[3], client.getHeightLevel()})) {
			client.getActionAssistant().startAnimation(getMiningAnimation(currentAction.getClient()), 0);
		}
		currentAction.setCurrentTick(4);
		
	}

	@Override
	public void stop(Action currentAction) {
		removeAction(currentAction.getClient());
		if (currentAction.getClient() != null) {
			currentAction.getClient().getActionSender().sendAnimationReset();		
			currentAction.getClient().updateRequired = true;
		}
		currentAction.setActionType(Action.type.TRASHING);
	}
	
	
	public boolean badPlayerSetup(Client client) {

		boolean hasAxe = false;
		for (int axe = 0; axe < validPicaxe.length; axe++) {
			if  (client.playerEquipment[3] == validPicaxe[axe])
				hasAxe = true;
			else if (client.getActionAssistant().isItemInInventory(validPicaxe[axe]) && picaxeReq[axe] <= client.playerLevel[14]) 
				hasAxe = true;
		}
		if (!hasAxe) {
			client.getActionSender().sendMessage("You do not have an pickaxe of which you have a mining level to use.");
			return true;
		}
		for (int i = 0; i < RockID.length; i++) {
			if (client.objectID == RockID[i]) {
				if  (client.playerLevel[14] < levelReq[i]) {
					client.getActionSender().sendMessage("You need an mining level of "+ levelReq[i] +" to mine this rock.");
					return true;
				}
			}
		}
		if (client.objectSize > 1) {
			client.getActionAssistant().turnTo(client.objectX + client.objectSize / 2 , client.objectY + client.objectSize / 2);
		} else {
			client.getActionAssistant().turnTo(client.objectX, client.objectY);
		}
		return false;		
	}

	public void startMiner(Action currentAction) {
		for (int i = 0; i < RockID.length; i++) {
			if (currentAction.getClient().objectID == RockID[i])
				if (Utils.random(getDefence[i] * 5) > getMiningBonus(currentAction.getClient()))
					return;
				else
					addOre(currentAction.getClient());					
		}
		if (currentAction.getClient().getActionAssistant().freeSlots() < 1) {
			currentAction.getClient().getActionSender().sendMessage(CommonStrings.INV_FULL);
			stop(currentAction);
			return;
		}
	}
	public void addOre(Client client) {
		for (int i = 0; i < RockID.length; i++) {
			if (client.objectID == RockID[i]) {
				if (Utils.random(50) == 1) {
					int rock = getRandomGem();
					String gem = Server.getItemManager().getItemDefinition(rock).getName();
					client.getActionSender().sendInventoryItem(rock, 1);
					client.getActionSender().sendMessage("You manage to obtain an "+gem.toLowerCase()+".");
					client.getActionAssistant().addSkillXP((rockXP[i] * 3) * Constants.SKILL_EXPERIENCE_MULTIPLIER, 14);
					return;		
				}
				client.getActionSender().sendMessage("You get some "+ getName[i].toLowerCase() +".");
				client.getActionAssistant().addSkillXP(rockXP[i] * Constants.SKILL_EXPERIENCE_MULTIPLIER, 14);
				client.getActionSender().sendInventoryItem(oreID[i], 1);
				ObjectManager.newTempObject(client.objectID, 450, new int[] { client.objectX, client.objectY, client.getHeightLevel()}, 10);
				client.getActionSender().sendAnimationReset();		
				client.updateRequired = true;
				return;
			}
		}
	}
	public int getMiningAnimation(Client client) {
		for (int i = 0; i < pickaxeAnimation.length; i++) {
			if (client.playerEquipment[3] == validPicaxe[i] || client.getActionAssistant().isItemInInventory(validPicaxe[i]) && picaxeReq[i] <= client.playerLevel[14])
				return pickaxeAnimation[i];	
		}
		return 0;
	}
	public int getAxeBonus(Client client) {
		for (int i = 0; i < validPicaxe.length; i++) {
			if (client.playerEquipment[3] == validPicaxe[i] || client.getActionAssistant().isItemInInventory(validPicaxe[i]) && picaxeReq[i] <= client.playerLevel[14])
				return pickaxeBonus[i];
		}
		return 0;
	}
	public static boolean badObject(Client client) {
		return client.objectID <= 0 || client.objectX <= 0 || client.objectY <= 0 || client.objectSize <= 0;
	}
	public int getMiningBonus(Client client) {
		return client.playerLevel[14] + getAxeBonus(client);
	}	
	public int getRandomGem() {
		return gemId[(int) (Math.random() * gemId.length)];
	}
	public void removeAction(Client client) {
		client.getActionSender().sendAnimationReset();		
		client.updateRequired = true;
		client.objectID = client.objectX = client.objectY = client.objectSize = client.setPlayerX = client.setPlayerY = 0;
	}	
	
	public static int RockID[] = { 
		2090,	2091,
		9709,	9708,	9710,
		2092,	2093,	9718,	9717,	9719,
		2094,	2095,	11933,	11934,	11935,	9716,	9714,
		2101,	11186,	11187,	11188, 
		2108,	2109,	11189,	11190,	11191,
		11183,	11184,	11185,	9713,	9711,	9722,	9720,
		2096,	2097,	11930,	11931,	11932,
		2102,	2103,
		2104,	2105,
		2107,	14860,	14859,
	};
	public static int levelReq[] = {
		1,	1,
		1,	1,	1,
		15,	15,	15,	15,	15,	15,
		1,	1,	1,	1,	1,	1,				
		20, 20, 20, 20,
		1, 	1, 	1, 	1, 	1,
		40, 40, 40, 40, 40,
		30, 30, 30, 30, 30,
		55, 55,
		55, 55,
		85, 85, 85,
	};
	public static int getDefence[] = {
		15, 15,
		15, 15, 15,
		35, 35, 35, 35, 35 ,
		15, 15, 15, 15, 15, 15, 15,
		46, 46, 46, 46, 
		15, 15, 15, 15, 15,
		60, 60, 60, 60, 60,
		50, 50,
		70, 70, 70, 70, 70,
	};	
	public static int gemId[] = {
		1623,
		1621,
		1619,
		1617,
	};
	public static int oreID[] = {
		436, 436,
		436, 436, 436,
		440, 440, 440, 440, 440,
		438, 438, 438, 438, 438, 438, 438,
		442, 442, 442, 442, 
		434, 434, 434, 434, 434,
		444, 444, 444, 444, 444,
		453, 453,
		447, 447, 447, 447, 447,
	};
	public static int rockXP[] = {
		(int)	17.5,	(int) 17.5,
		(int)	17.5,	(int) 17.5,	(int) 17.5,
		35,	35,	35,	35,	35,
		(int) 17.5, (int) 17.5, (int) 17.5, (int) 17.5, (int) 17.5, (int) 17.5, (int) 17.5,
		40, 40, 40, 40,
		5, 5, 5, 5, 5,
		65, 65, 65, 65, 65,
		50, 50,
		80, 80, 80, 80, 80,
	};
	public static String getName[] = {
		"Copper ore", "Copper ore", "Iron ore", "Iron ore", 
		"Tin ore", "Tin ore", "Tin ore", "Tin ore", "Tin ore", "Sliver ore", "Sliver ore",
		"Sliver ore","Sliver ore", "Clay", "Clay","Clay", "Clay", "Clay", "Gold ore", 
		"Gold ore", "Gold ore",	"Coal", "Coal", "Coal", "Coal", "Coal", "Mithril ore", "Mithril ore",
	};
	public static int picaxeReq[] = { //level needed to use pic
		1,1,5,21,31,41,
	};	
	public static int validPicaxe[] = { //picaxe ids
		1265, 1267, 1269, 1273, 1271, 1275//bronze - rune pickaxe
	};
	public static int pickaxeAnimation[] = { //animation for picaxes
		625, 626, 627, 629, 628, 624//bronze - rune pickaxe
	};	
	public static int pickaxeBonus[] = {
		5, 10, 15, 30, 40, 50 //bronze - rune pickaxe
	};


}