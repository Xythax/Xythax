package org.xythax.content;

import org.xythax.content.actions.Action;
import org.xythax.content.actions.ActionManager;
import org.xythax.content.actions.Task;
import org.xythax.content.controllers.Animation;
import org.xythax.content.controllers.Graphics;
import org.xythax.content.controllers.Location;
import org.xythax.content.skills.Fishing;
import org.xythax.content.skills.MenuTeleports;
import org.xythax.content.skills.RuneCrafting;
import org.xythax.content.skills.Tanning;
import org.xythax.model.Client;
import org.xythax.model.NPC;
import org.xythax.utils.CommonStrings;

	
/**
 * Walk to npc, start action.
 * 
 * @author Killamess
 */

public class WalkToNPC implements Task {


	@Override
	public void execute(Action currentAction) {
		int delay = 0;
		if (currentAction.getClient() == null) {
			if (!currentAction.getClient().isRunning)
				delay = 1;	
		}
		currentAction.setCurrentTick(delay);
		currentAction.setActionType(Action.type.LOOPING);
	}

	@Override
	public void loop(Action currentAction) {
		if (currentAction.getClient() == null) {
			stop(currentAction);
			return;
		}
		if (currentAction.getCurrentTick() > 0) {
			currentAction.setCurrentTick(currentAction.getCurrentTick() - 1);
			return;
		}
		if (badnpc(currentAction.getClient())) {
			stop(currentAction);
			return;
		}
		if (currentAction.getClient().getAbsX() == currentAction.getClient().destX && currentAction.getClient().getAbsY() == currentAction.getClient().destY) {
			currentAction.getClient().moveClient();
		}
		if (atDestination(currentAction.getClient())) {
			startTask(currentAction.getClient());	
			stop(currentAction);
			return;
		}
		currentAction.setCurrentTick(1);
	}


	@Override
	public void stop(Action currentAction) {
		currentAction.setActionType(Action.type.TRASHING);	
	}
	
	/**
	* Task references
	*/
	public final static int SHOP = 1;
	public final static int TALK = 2;
	public final static int EXTRA = 3;
	public final static int SECOND_CLICK_FISHING = 8;
	public final static int BANK = 4;
	public final static int PICKPOCKET = 5;
	public static final int TAN = 6;
	public static final int SHIP = 7;
	public static final int TELEPORT = 9;


	/**
	* Sets the destination of the npc.
	* @Param npc
	* @Param npcInitialTask 
	*/
	public static void setDestination(Client client, final NPC npc, int npcInitialTask) {
		client.npc = npc;
		client.clickedNPCID = npc.getDefinition().getType();
		client.destX = npc.getAbsX();
		client.destY = npc.getAbsY();
		client.npcTask = npcInitialTask;
		client.npcSize = 1;//have not needed to go larger as of yet.

		if (badnpc(client))
			return; //Stops as there is data missing.
		if (client.getAbsX() == client.destX && client.getAbsY() == client.destY) {
			client.moveClient();
		}
		if (atDestination(client)) {
			startTask(client);
			return;
		}
		ActionManager.addNewRequest(client, 12, 1);
	}

	/**
	* Starts the task bound to the npc.
	*/	
	public static void startTask(Client client) {

		switch(client.npcTask) {
		case SHOP:
			Animation.face(client.npc, client);
			client.getActionAssistant().turnTo(client.destX, client.destY);
			client.getActionAssistant().openUpShop(getShopType(client));
			break;
		case TALK:
			Animation.face(client.npc, client);
			break;
		case EXTRA:
			if (client.clickedNPCID == 548) {
				client.getActionSender().sendInterface(3559);
			} else {
				Fishing.loadAction(client, client.clickedNPCID, client.destX, client.destY);	
			}
			break;	
		case SECOND_CLICK_FISHING:
			Fishing.loadAction(client, client.clickedNPCID * 2, client.destX, client.destY);	
			break;
		case BANK:
			Animation.face(client.npc, client);
			client.getActionSender().sendBankInterface();
			break;
		//case PICKPOCKET:
		//	Rob.createSituation(client, client.npc);
		//	break;
		case TAN:
			Animation.face(client.npc, client);
			Tanning.openTanInterface(client);
			break;
		case SHIP:
			Animation.face(client.npc, client);
			MenuTeleports.createTeleportMenu(client, 4);
			break;
		case TELEPORT:
			Animation.face(client.npc, client);
			Animation.addNewRequest(client.npc, 1818, 1);
			Graphics.addNewRequest(client.npc, 343, 100, 1);
			Graphics.addNewRequest(client, 342, 100, 3);
			Animation.addNewRequest(client, 1816, 3);
			Animation.addNewRequest(client, 715, 7);
			if (RuneCrafting.runeCraftArea(client)) {
				Location.addNewRequest(client, 2815, 3450, 0, 6);
			} else {
				Location.addNewRequest(client, 3037, 4843, 0, 6);
			}
			
			break;
		default:
			client.getActionSender().sendMessage(CommonStrings.BAD_NPC);
			break;
		}
		removeTask(client);
	}

	/**
	* Returns the npcs shop id if it has one, Else returns 0.
	*/
	public static int getShopType(Client client) {
		return 
		client.clickedNPCID == 519 ? 1 : 
		client.clickedNPCID == 553 ? 2 : 
		client.clickedNPCID == 530 ? 3	:
		client.clickedNPCID == 522 ? 4	:
		client.clickedNPCID == 528 ? 5	:
		client.clickedNPCID == 538 ? 6	:
		client.clickedNPCID == 577 ? 7	:
		client.clickedNPCID == 583 ? 8	:
		client.clickedNPCID == 559 ? 9	:
		client.clickedNPCID == 550 ? 10 : 
		client.clickedNPCID == 546 ? 11 : 
		client.clickedNPCID == 549 ? 12 : 
		client.clickedNPCID == 520 ? 13 : 
		client.clickedNPCID == 586 ? 14 :
		client.clickedNPCID == 541 ? 15 :
		client.clickedNPCID == 542 ? 16 : 
		client.clickedNPCID == 543 ? 17 : 
		client.clickedNPCID == 544 ? 18 : 
		client.clickedNPCID == 571 ? 19 : 
		client.clickedNPCID == 2622 ? 20 : 
		client.clickedNPCID == 576 ? 21 : 
		client.clickedNPCID == 557 ? 22	: 
		client.clickedNPCID == 558 ? 23	: 
		client.clickedNPCID == 581 ? 24	: 
		client.clickedNPCID == 584 ? 25	: 
		client.clickedNPCID == 580 ? 26	: 
		client.clickedNPCID == 585 ? 27	: 
		client.clickedNPCID == 551 ? 28	: 
		client.clickedNPCID == 545 ? 29	: 
		client.clickedNPCID == 682 ? 30	:
		client.clickedNPCID == 1860 ? 31 :
		client.clickedNPCID == 556 ? 32 :	0;
	}

	/**
	* If data is missing return true, If not return false.
	*/
	public static boolean badnpc(Client client) {
		return client.destX <= 0 || client.destY <= 0 || client.npcSize <= 0 || client.npcTask <= 0 || client.clickedNPCID <= 0;
	}

	/**
	* Gets the distance and returns true if at destination, False if not.
	*/
	public static boolean atDestination(Client client) { //default creates a prefect box around the npc - killamess
		if (client.npcTask == BANK) {
			client.npcSize = 2;
		}
		switch(client.npcSize) {
		case 1: 
			return //Postions the player to not be diagonally.
			client.destX - 1 == client.getAbsX() && client.destY == client.getAbsY() || client.destX + 1 == client.getAbsX() && client.destY == client.getAbsY() ||
			client.destY - 1 == client.getAbsY() && client.destX == client.getAbsX() || client.destY + 1 == client.getAbsY() && client.destX == client.getAbsX();
		default: 	
			return //Creates a box around the npc if it is larger than 1 sqaure.
				client.getAbsX() >= client.destX - 1 && client.getAbsX() <= client.destX + client.npcSize && client.getAbsY() >= client.destY - 1 && client.getAbsY() <= client.destY + client.npcSize;
		}
	}

	/**
	* Resets the players task to nothing so it will not run again.
	*/
	public static void removeTask(Client client) {
		client.destX = client.destY = client.npcSize = client.npcTask = client.clickedNPCID = client.npcSlot = 0;
	}



	public int getActionDelay(Client client) {
		switch(client.npcTask) {
		case SHOP:
			return 0;
		case TALK:
			return 500;
		case EXTRA:
			return 500;
		case PICKPOCKET:
			return 0;
		default:
			return 500;
		}
	}
	
	public static int getNpcWalkType(NPC npc, int clickType) {
		int type = npc.getDefinition().getType();
		
/*		if (Rob.npcs(type) != null && clickType == 2) {
			return 5;
		}*/
		
		/** npc option packets **/
		switch (clickType) {
		
			/** 1st click npc **/
			case 1:
				switch(type) {
					case 519:
					case 539:
					case 571:
					case 570:
					case 573:
					case 572:
					case 574:
					case 569:
					case 554:
					case 805:
					case 2128:
					case 550:
					case 546:
					case 549:
					case 520:
					case 586:
					case 543:
					case 542:
					case 541:
					case 544:
					case 2622:
					case 576:
						return 1; //shops
						
					case 233:
					case 234:
					case 235:
					case 236:
					case 309:
					case 324:
					case 316:
					case 334:
						return 3; //fishing
						
					case 494: 
					case 166:
					case 495:
					case 496:
					case 497:
					case 498:
					case 499:
					case 2619:
						return 4; //banking
						
					case 804:
						return 6; //tanner
					case 1703:
						return 7;//ship travel
						
					case 460:
						return 9;
				}
				break;
				
			/** 2nd click npc **/
			case 2:
			
				switch(type) {
					case 519:
					case 539:
					case 571:
					case 570:
					case 573:
					case 572:
					case 574:
					case 569:
					case 554:
					case 805:
					case 2128:
					case 550:
					case 546:
					case 549:
					case 520:
					case 586:
					case 543:
					case 542:
					case 541:
					case 544:
					case 2622:
					case 576:
						return 1; //shops
						
					case 233:
					case 234:
					case 235:
					case 236:
					case 309:
					case 324:
					case 334:
					case 316:
						return 8; //fishing
						
					case 494: 
					case 166:
					case 495:
					case 496:
					case 497:
					case 498:
					case 499:
					case 2619:
						return 4; //banking
					case 804:
						return 6; //tan
					case 1703:
						return 7;//ship travel
				}
				break;
			
			/** 3rd click npc **/
			case 3:
				System.out.println("3th packet.");
				switch(type) {
				
				case 548:
					return 3;
				}
				break;
			
		}
		return 1;
	}
}
	