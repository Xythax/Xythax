package org.xythax.content.objects;

import org.xythax.model.Client;

public class ObjectStorage implements ObjectConstants {

	public static int getDetail(Client client, int settingValue) {
		return client.objectStorage[settingValue];
	}

	public static void addDetails(Client client, int[] settingValue) {
		for (int i = 0; i < client.objectStorage.length; i++)
			client.objectStorage[i] = settingValue[i];
	}

	public static int[] getDetails(Client client) {
		return client.objectStorage;
	}

	public static int[] compress(int i, int j, int k, int l, int h, int g) {
		int[] array = new int[objectArraySize];

		array[0] = i;
		array[1] = j;
		array[2] = k;
		array[3] = l;
		array[4] = h;
		array[5] = g;

		return array;
	}

	public static int[] compress(int i, int j, int k) {
		int[] array = new int[3];

		array[0] = i;
		array[1] = j;
		array[2] = k;

		return array;
	}

	public static void destruct(Client client) {
		for (int i = 0; i < client.objectStorage.length; i++) {
			client.objectStorage[i] = REMOVE;
		}
	}

}