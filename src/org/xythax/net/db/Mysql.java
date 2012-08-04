package org.xythax.net.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {
	private static ThreadLocal<Connection> con = new ThreadLocalConnection();
	private static String url = "jdbc:mysql://s5.x10premium.com:3306/xythaxor_game";
	private static String user = "xythaxor";
	private static String pass = "Ryan1988";

	public static Connection getConnection() {
		return con.get();
	}

	public static void release() throws SQLException {
		con.get().close();
		con.remove();
	}

	private static class ThreadLocalConnection extends ThreadLocal<Connection> {
		static {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out
						.println("[SQL] Could not locate the JDBC mysql driver.");
			}
		}

		@Override
		protected Connection initialValue() {
			return getConnection();
		}

		private Connection getConnection() {
			DriverManager.setLoginTimeout(15);
			try {
				return DriverManager.getConnection(url, user, pass);
			} catch (SQLException sql) {
				System.out
						.println("[SQL] Error establishing connection. Please make sure you've correctly configured db.properties.");
				return null;
			}
		}

		@Override
		public Connection get() {
			Connection con = super.get();
			try {
				if (con != null)
					if (!con.isClosed())
						return con;
			} catch (SQLException sql) {
			}
			con = getConnection();
			super.set(con);
			return con;
		}
	}
}