package org.xythax.model.combat.magic;

/**
 * Magic type
 * 
 * @author Killamess
 */

public class MagicType {

	public enum type {
		NOTHING, NORMAL, WEAKEN, BIND, GODSPELL, POISON, BLIND, DRAIN, FREEZE, TELEBLOCK;
	}

	public static type getMagicType(int spell) {

		switch (spell) {

		case 1152:
		case 1154:
		case 1156:
		case 1158:
		case 1160:
		case 1163:
		case 1166:
		case 1169:
		case 1172:
		case 1175:
		case 1177:
		case 1181:
		case 1183:
		case 1185:
		case 1188:
		case 1189:
			return type.NORMAL;

		case 1190:
		case 1191:
		case 1192:
			return type.GODSPELL;

		case 12939:
		case 12963:
		case 12951:
		case 12975:
			return type.POISON;

		case 12987:
		case 13011:
		case 12999:
		case 13023:
			return type.BLIND;

		case 12901:
		case 12919:
		case 12911:
		case 12929:
			return type.DRAIN;

		case 12861:
		case 12881:
		case 12871:
		case 12891:
			return type.FREEZE;

		case 1153:
		case 1157:
		case 1161:
		case 1542:
		case 1543:
		case 1562:
			return type.WEAKEN;

		case 1572:
		case 1582:
		case 1592:
			return type.BIND;

		case 12445:
			return type.TELEBLOCK;

		default:
			return type.NOTHING;
		}
	}
}