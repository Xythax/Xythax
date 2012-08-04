package org.xythax.net.phandler;

import org.xythax.model.Client;
import org.xythax.model.Player;
import org.xythax.utils.Constants;
import org.xythax.world.PlayerManager;

public class SaveAllPlayers extends Thread {
	public SaveAllPlayers() {
		while (true) {
			try {
				if (PlayerManager.getPlayerManager().getPlayerCount() > 0) {
					int saved = 0;

					if (Constants.showSaveMessage)
						System.out.println("Saving All Players...");

					for (Player p : PlayerManager.getPlayerManager().getPlayers()) {
						if (p == null)
							continue;

						if (((Client) p).getTradeHandler().stage > 0)
							continue;

						saved++;
						PlayerManager.getPlayerManager().saveGame(p, false);
					}
					if (Constants.showSaveMessage)
						System.out.println("Saved " + saved + " Player(s)");
				}
				Thread.sleep(Constants.SAVE_TIMER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
