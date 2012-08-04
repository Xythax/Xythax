package org.xythax.net.phandler.packets;

import org.xythax.content.actions.ActionManager;
import org.xythax.model.Client;
import org.xythax.net.phandler.Packet;
import org.xythax.utils.Constants;
import org.xythax.world.PlayerManager;

/**
 * Trade actions
 * 
 * @author Graham
 */
public class TradeAction implements Packet {

	public static final int REQUEST = 73, ANSWER = 139;

	@Override
	public void handlePacket(Client client, int packetType, int packetSize) {
		ActionManager.destructActions(client.getUsername());
		if (packetType == REQUEST) {
			int trade = client.inStream.readSignedWordBigEndian();
			if (trade < 0 || trade >= Constants.MAX_PLAYERS)
				return;
			if (PlayerManager.getPlayerManager().getPlayers()[trade] != null) {
				Client c = (Client) PlayerManager.getPlayerManager().getPlayers()[trade];
				client.getTradeHandler().requestTrade(c);
			}
			client.println_debug("Trade Request to: " + trade);
		} else if (packetType == ANSWER) {
			int trade = client.inStream.readSignedWordBigEndian();
			if (trade < 0 || trade >= Constants.MAX_PLAYERS)
				return;
			if (PlayerManager.getPlayerManager().getPlayers()[trade] != null) {
				Client c = (Client) PlayerManager.getPlayerManager().getPlayers()[trade];
				client.getTradeHandler().answerTrade(c);
			}
			client.println_debug("Trade Answer to: " + trade);
		}
	}

}
