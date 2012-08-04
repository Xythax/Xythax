package org.xythax.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.xythax.net.db.Mysql;

/**
 * Bans processing.
 * 
 * @author Blake
 * @author Graham
 * @author Killamess
 * @auther Steven
 */
public class BanProcessor {

	public static boolean ban(String name, int uid, String ip, int type) {
		try {
			Connection con = Mysql.getConnection();
			Statement s;
			s = con.createStatement();
			s.executeQuery("insert into badplayers (username, ip, type) values ('"
					+ name + "', '" + ip + "', '" + type + "')");
			s.close();
			con.close();
			Mysql.release();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public static boolean unban(String name, int type) {
		try {
			Connection con = Mysql.getConnection();
			Statement s = con.createStatement();
			s.executeQuery("DELETE FROM `badplayers` WHERE username=\""+name+"\", type=\""+type+"\";");
			s.close();
			con.close();
			Mysql.release();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean checkUser(String name) {
		try {
			Connection con = Mysql.getConnection();
			Statement s;
			s = con.createStatement();
			s.executeQuery("SELECT null FROM badplayers where username = '"
					+ name + "' and type = 0");
			ResultSet rs = s.getResultSet();
			if (rs.next()) {
				s.close();
				rs.close();
				con.close();
				Mysql.release();
				return true;
			} else {
				s.close();
				rs.close();
				con.close();
				Mysql.release();
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean checkMuted(String name) {
		try {
			Connection con = Mysql.getConnection();
			Statement s;
			s = con.createStatement();
			s.executeQuery("SELECT null FROM badplayers where username = '"
					+ name + "' and type = 2");
			ResultSet rs = s.getResultSet();
			if (rs.next()) {
				s.close();
				rs.close();
				con.close();
				Mysql.release();
				return true;
			} else {
				s.close();
				rs.close();
				con.close();
				Mysql.release();
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean checkIpMuted(String name) {
		try {
			Connection con = Mysql.getConnection();
			Statement s;
			s = con.createStatement();
			s.executeQuery("SELECT null FROM badplayers where username = '"
					+ name + "' and type = 3");
			ResultSet rs = s.getResultSet();
			if (rs.next()) {
				s.close();
				rs.close();
				con.close();
				Mysql.release();
				return true;
			} else {
				s.close();
				rs.close();
				con.close();
				Mysql.release();
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean checkIP(String ip) {
		try {
			Connection con = Mysql.getConnection();
			Statement s;
			s = con.createStatement();
			s.executeQuery("SELECT null FROM badplayers where ip = '" + ip
					+ "' and type = 1");
			ResultSet rs = s.getResultSet();
			if (rs.next()) {
				s.close();
				rs.close();
				con.close();
				Mysql.release();
				return true;
			} else {
				s.close();
				rs.close();
				con.close();
				Mysql.release();
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	public static final int TYPE_BAN = 0;
	public static final int TYPE_IP_BAN = 1;
	public static final int TYPE_MUTE = 2;
	public static final int TYPE_IP_MUTE = 3;
}