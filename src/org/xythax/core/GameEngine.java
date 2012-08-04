package org.xythax.core;

import org.xythax.content.actions.ActionManager;
import org.xythax.content.controllers.ControllerManager;
import org.xythax.utils.Constants;
import org.xythax.world.GlobalActions;
import org.xythax.world.ItemManager;
import org.xythax.world.NPCManager;
import org.xythax.world.ObjectManager;
import org.xythax.world.PlayerManager;
import org.xythax.world.ShopManager;

public class GameEngine extends Thread {

    /**
     * Handles delayed events rather than events to be ran every iteration
     */
    private DelayedEventHandler eventHandler = new DelayedEventHandler();
    
	private static PlayerManager playerManager = null;

	public static PlayerManager getPlayerManager() {
		return playerManager;
	}

	private static NPCManager npcManager = null;

	public static NPCManager getNPCManager() {
		return getNpcManager();
	}

	private static ObjectManager objectManager = null;

	public static ObjectManager getObjectManager() {
		return objectManager;
	}

	private static ItemManager itemManager = null;

	public static ItemManager getItemManager() {
		return itemManager;
	}

	private static ShopManager shopManager = null;

	public static ShopManager getShopManager() {
		return shopManager;
	}

	private static GlobalActions globalActions = null;

	public static GlobalActions getGlobalActions() {
		return globalActions;
	}

	@Override
	public void run() {

		long lastTicks = System.currentTimeMillis();
		long totalTimeSpentProcessing = 0;
		int cycle = 0;

		while (!Server.isShutdown()) {
			try {
				ControllerManager.processRequests();
				getItemManager().process();
				getNpcManager().process();
				processEvents();
				getPlayerManager().process();
				ActionManager.processActions();
				getShopManager().process();

				long timeSpent = System.currentTimeMillis() - lastTicks;

				totalTimeSpentProcessing += timeSpent;

				if (timeSpent >= Constants.cycleTime)
					timeSpent = Constants.cycleTime;

				try {
					Thread.sleep(Constants.cycleTime - timeSpent);
				} catch (java.lang.Exception _ex) {
				}

				lastTicks = System.currentTimeMillis();
				cycle++;

				if (cycle % 100 == 0) {

					float time = ((float) totalTimeSpentProcessing) / cycle;

					if (Constants.showCpuUsage)
						System.out.println("[CPU-USAGE]: "
								+ (time * 100 / Constants.cycleTime)
								+ "%, Running garbage cleaner.");

					System.gc();
					if (Constants.showPlayerOnlineCount)
						System.out.println("There are currently "
								+ GameEngine.getPlayerManager()
										.getPlayerCount() + " players online.");
				}
				if (Server.isShutdown()) {
					Server.getIoThread().shutdown();
					getPlayerManager().shutdown();
					System.exit(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
    private void processEvents() {
        eventHandler.doEvents();
    }

	public static void setPlayerManager(PlayerManager playerManager) {
		GameEngine.playerManager = playerManager;
	}

	public static void setNpcManager(NPCManager npcManager) {
		GameEngine.npcManager = npcManager;
	}

	public static NPCManager getNpcManager() {
		return npcManager;
	}

	public static void setObjectManager(ObjectManager objectManager) {
		GameEngine.objectManager = objectManager;
	}

	public static void setShopManager(ShopManager shopManager) {
		GameEngine.shopManager = shopManager;
	}

	public static void setGlobalActions(GlobalActions globalActions) {
		GameEngine.globalActions = globalActions;
	}

	public static void setItemManager(ItemManager itemManager) {
		GameEngine.itemManager = itemManager;
	}
}