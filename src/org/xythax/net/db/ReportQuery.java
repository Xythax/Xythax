package org.xythax.net.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

import org.xythax.model.Player;
import org.xythax.model.World;
import org.xythax.model.snapshot.Activity;
import org.xythax.model.snapshot.ChatLog;
import org.xythax.model.snapshot.Snapshot;
import org.xythax.world.PlayerManager;

public class ReportQuery extends Mysql {

	/**
	 * Stores a ReportQuery to the Database.
	 */
	public synchronized void storeReportQuery(long from, long about,
			byte reason, Player from2) {
		PreparedStatement insertNewReportRow = null;
		// Statement s = getConnection().createStatement();
		try {
			insertNewReportRow = getConnection()
					.prepareStatement(
							"INSERT INTO `reports` (`from`, `about`, `time`, `reason`, `snapshot_from`,`snapshot_about`,`chatlogs`, `from_x`, `from_y`, `about_x`, `about_y`) VALUES(?,?,?,?,?,?,?,?,?,?,?)");

		} catch (SQLException e) {
			e.printStackTrace();
			// Logger.error(e);
		}
		long time = System.currentTimeMillis() / 1000;

		// String f = hashToUsername(from);
		// String a = hashToUsername(about);

		Player about2 = PlayerManager.getPlayerManager().getPlayerByNameLong(about);
		int player2X;
		int player2Y;
		if (about2 == null) {
			player2X = 0;
			player2Y = 0;
		} else {
			player2X = about2.getAbsX();
			player2Y = about2.getAbsY();
		}
		StringBuilder snapshot_from = new StringBuilder();
		StringBuilder snapshot_about = new StringBuilder();

		StringBuilder chatlog = new StringBuilder();
		Iterator<Snapshot> i = World.getWorld().getSnapshots()
				.descendingIterator();
		while (i.hasNext()) {
			Snapshot s = i.next();
			if (s instanceof ChatLog) {
				ChatLog cl = (ChatLog) s;
				if (cl.getRecievers().contains(about)
						|| cl.getOwner().equals(about)) {
					chatlog.append((cl.getTimestamp() / 1000) + " <"
							+ cl.getOwner() + "> " + cl.getMessage() + "\n");
				}
			} else if (s instanceof Activity) {
				Activity ac = (Activity) s;
				if (ac.getOwner().equals(from)) {
					snapshot_from.append((ac.getTimestamp() / 1000) + " "
							+ ac.getActivity() + "\n");
				} else if (ac.getOwner().equals(about)) {
					snapshot_about.append((ac.getTimestamp() / 1000) + " "
							+ ac.getActivity() + "\n");
				}
			}
		}
		try {
			insertNewReportRow.setLong(1, from);
			insertNewReportRow.setLong(2, about);
			insertNewReportRow.setLong(3, time);
			insertNewReportRow.setInt(4, reason);
			insertNewReportRow.setString(5, snapshot_from.toString());
			insertNewReportRow.setString(6, snapshot_about.toString());
			insertNewReportRow.setString(7, chatlog.toString());
			insertNewReportRow.setInt(8, from2.getAbsX());
			insertNewReportRow.setInt(9, from2.getAbsY());
			insertNewReportRow.setInt(10, player2X);
			insertNewReportRow.setInt(11, player2Y);
			insertNewReportRow.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}