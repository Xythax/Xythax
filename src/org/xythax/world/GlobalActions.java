package org.xythax.world;

import org.xythax.model.Client;
import org.xythax.model.Player;

public class GlobalActions {

	public void sendAnimationReset(Client client) {
		for (Player p : PlayerManager.getPlayerManager().getPlayers()) {
			if (p == null)
				continue;
			if (!p.isActive || p.disconnected)
				continue;
			Client c = (Client) p;
			if (c.withinDistance(c.getAbsX(), c.getAbsY(), 60)) {
				c.getActionSender().sendAnimationReset();
			}
		}
	}

	public void sendMessage(String message) {
		for (Player p : PlayerManager.getPlayerManager().getPlayers()) {
			if (p == null)
				continue;
			if (!p.isActive || p.disconnected)
				continue;
			Client c = (Client) p;
			c.getActionSender().sendMessage("[SERVER] " + message + ".");
		}
	}

	public void sendObject(int objectID, int objectX, int objectY,
			int objectHeight, int objectFace, int objectType) {
		for (Player p : PlayerManager.getPlayerManager().getPlayers()) {
			if (p == null)
				continue;
			if (!p.isActive || p.disconnected)
				continue;
			if (p.getHeightLevel() != objectHeight)
				continue;
			Client c = (Client) p;
			if (c.withinDistance(objectX, objectY, 60)) {
				c.getActionSender().sendObject(objectID, objectX, objectY,
						objectHeight, objectFace, objectType);
			}
		}
	}

	public void sendProjectile(Client client, int absY, int absX, int offsetY,
			int offsetX, int proID, int startHeight, int endHeight, int speed,
			int lockon) {
		for (Player p : PlayerManager.getPlayerManager().getPlayers()) {
			if (p == null)
				continue;
			if (!p.isActive || p.disconnected)
				continue;
			Client c = (Client) p;
			if (client.withinDistance(c))
				c.getActionSender().sendProjectile(absY, absX, offsetY,
						offsetX, proID, startHeight, endHeight, speed, lockon);
		}
	}

	public void sendProjectile(Client client, int absY, int absX, int offsetY,
			int offsetX, int proID, int startHeight, int endHeight, int speed,
			int angle, int lockon) {
		for (Player p : PlayerManager.getPlayerManager().getPlayers()) {
			if (p == null)
				continue;
			if (!p.isActive || p.disconnected)
				continue;
			Client c = (Client) p;
			if (client.withinDistance(c))
				c.getActionSender().sendProjectile(absY, absX, offsetY,
						offsetX, proID, startHeight, endHeight, speed, angle,
						lockon);
		}
	}
}