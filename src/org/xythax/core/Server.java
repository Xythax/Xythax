package org.xythax.core;

import org.xythax.admin.RemoteAdmin;
import org.xythax.content.actions.ActionManager;
import org.xythax.model.definition.XMLManager;
import org.xythax.model.map.WorldMap;
import org.xythax.net.ConnectionListener;
import org.xythax.net.db.Loadable;
import org.xythax.net.phandler.PacketManager;
import org.xythax.net.phandler.SaveAllPlayers;
import org.xythax.net.phandler.commands.CommandManager;
import org.xythax.utils.Constants;
import org.xythax.world.GlobalActions;
import org.xythax.world.ItemManager;
import org.xythax.world.NPCManager;
import org.xythax.world.ObjectManager;
import org.xythax.world.PlayerManager;
import org.xythax.world.ShopManager;

public class Server extends GameEngine {

	public static boolean multiValve = true;
	public static boolean systemValve = false;

	private static Object gameLogicLock = new Object();

	public static Object getGameLogicLock() {
		return gameLogicLock;
	}

	private static ConnectionListener ioThread = null;

	public static ConnectionListener getIOThread() {
		return getIoThread();
	}

	private static int averageProcessTime = -1;

	public static void main(String[] args) {
		try {
			start = System.currentTimeMillis();
			System.out.println("Loading " + Constants.SERVER_NAME);
			setShopManager(new ShopManager());
			setNpcManager(new NPCManager());
			setObjectManager(new ObjectManager());
			setItemManager(new ItemManager());
			setGlobalActions(new GlobalActions());
			GameEngine proc = new GameEngine();
			setPlayerManager(PlayerManager.getPlayerManager());
			PacketManager.loadAllPackets();
			CommandManager.loadAllCommands();
			ActionManager.loadAllActions();
			Loadable.runSQLQuerys();
			XMLManager.load();
			WorldMap.load();
			System.gc();
			new Thread(proc).start();
			setIoThread(new ConnectionListener());
			(new Thread(getIoThread(), "ioThread")).start();
			SaveAllPlayers SaveAllPlayers = new SaveAllPlayers();
			new Thread(SaveAllPlayers).start();
			new Thread(new RemoteAdmin()).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int garbageCollectionCycle = 0;

	public static long start = 0;

	public static String getUptime() {
		long uptime = (System.currentTimeMillis() - start) / 1000;
		int minutes = (int) (uptime / 60);
		if (minutes == 1)
			return minutes + " minute";
		return minutes + " minutes";
	}

	public static int getProcessTime() {
		return averageProcessTime;
	}

	public static int getJVMSize() {
		Runtime runtime = Runtime.getRuntime();
		return (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
	}

	private static boolean debug = false;

	public static boolean isDebugEnabled() {
		return debug;
	}

	private static boolean shutdownServer = false;

	public static boolean isShutdown() {
		return isShutdownServer();
	}

	public static void setShutdown(boolean shutdown) {
		setShutdownServer(shutdown);
	}

	public static void setShutdownServer(boolean shutdownServer) {
		Server.shutdownServer = shutdownServer;
	}

	public static boolean isShutdownServer() {
		return shutdownServer;
	}

	public static void setIoThread(ConnectionListener ioThread) {
		Server.ioThread = ioThread;
	}

	public static ConnectionListener getIoThread() {
		return ioThread;
	}
}