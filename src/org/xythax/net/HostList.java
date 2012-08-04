package org.xythax.net;

import java.util.Hashtable;
import java.util.Map;

/**
 * A map of hosts and the number of connections from that host. This helps to
 * stop SYIpkpker.
 * 
 * @author Graham
 */
public class HostList {

	public static Map<String, Integer> socketList = new Hashtable<String, Integer>();

	public static void add(String remoteAddress) {
		Integer ct = socketList.get(remoteAddress);
		if (ct == null) {
			ct = 1;
		} else {
			ct++;
		}
		socketList.put(remoteAddress, ct);
	}

	public static void remove(String remoteAddress) {
		if (socketList.containsKey(remoteAddress)) {
			int ct = socketList.get(remoteAddress);
			ct--;
			if (ct == 0) {
				socketList.remove(remoteAddress);
			} else {
				socketList.put(remoteAddress, ct);
			}
		}
	}

	public static boolean has(String remoteAddress, int checkCount) {
		Integer count = socketList.get(remoteAddress);
		if (count == null) {
			return false;
		}
		if (count >= checkCount) {
			return true;
		}
		return false;
	}
}