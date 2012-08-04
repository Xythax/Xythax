package org.xythax.content.skills;

import org.xythax.model.Client;

public class SmithingMakeInterface {

	
	public SmithingMakeInterface(Client c, int object, String type, int x, int y)
	{
		readInput(c.playerLevel[13], type, c, x, y, object);
	}

	public void readInput(int level, String type, Client c, int objectx, int objecty, int objectid) {
		if (type.equals("BRONZE"))
		{
			MakeBronzeInterface(c);
		}
		
		else if (type.equals("IRON"))
		{
			MakeIronInterface(c);
		}
		
		else if (type.equals("STEEL"))
		{
			MakeSteelInterface(c);
		}
		
		else if (type.equals("MITH"))
		{
			MakeMithInterface(c);
		}
		
		else if (type.equals("ADDY"))
		{
			MakeAddyInterface(c);
		}
		else if (type.equals("RUNE"))
		{
			MakeRuneInterface(c);
		}
		
	}

	private void MakeRuneInterface(Client c) {
		String fiveb = GetForBars(2363, 5, c);
		String threeb = GetForBars(2363, 3, c);
		String twob = GetForBars(2363, 2, c);
		String oneb = GetForBars(2363, 1, c);
		c.getActionSender().sendQuest(fiveb+"5 Bars"+fiveb, 1112);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1109);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1110);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1118);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1111);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1095);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1115);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1090);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1113);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1116);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1114);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1089);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 8428);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1124);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1125);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1126);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1127);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1128);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1129);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1130);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1131);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 13357);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 11459);
		c.getActionSender().sendQuest(GetForlvl(88, c)+"Plate Body"+GetForlvl(18, c), 1101);
		c.getActionSender().sendQuest(GetForlvl(99, c)+"Plate Legs"+GetForlvl(16, c), 1099);
		c.getActionSender().sendQuest(GetForlvl(99, c)+"Plate Skirt"+GetForlvl(16, c), 1100);
		c.getActionSender().sendQuest(GetForlvl(99, c)+"2 Hand Sword"+GetForlvl(14, c), 1088);
		c.getActionSender().sendQuest(GetForlvl(98, c)+"Claws"+GetForlvl(13, c), 8429);
		c.getActionSender().sendQuest(GetForlvl(97, c)+"Kite Shield"+GetForlvl(12, c), 1105);
		c.getActionSender().sendQuest(GetForlvl(96, c)+"Chain Body"+GetForlvl(11, c), 1098);
		c.getActionSender().sendQuest(GetForlvl(95, c)+"Battle Axe"+GetForlvl(10, c), 1092);
		c.getActionSender().sendQuest(GetForlvl(94, c)+"Warhammer"+GetForlvl(9, c), 1083);
		c.getActionSender().sendQuest(GetForlvl(93, c)+"Square Shield"+GetForlvl(8, c), 1104);
		c.getActionSender().sendQuest(GetForlvl(92, c)+"Full Helm"+GetForlvl(7, c), 1103);
		//c.getActionSender().sendQuest(GetForlvl(92, c)+"Throwing Knives"+GetForlvl(7, c), 1106);
		c.getActionSender().sendQuest(GetForlvl(91, c)+"Long Sword"+GetForlvl(6, c), 1086);
		c.getActionSender().sendQuest(GetForlvl(90, c)+"Scimitar"+GetForlvl(5, c), 1087);
		c.getActionSender().sendQuest(GetForlvl(90, c)+"Arrowtips"+GetForlvl(5, c), 1108);
		c.getActionSender().sendQuest(GetForlvl(89, c)+"Sword"+GetForlvl(4, c), 1085);
		c.getActionSender().sendQuest(GetForlvl(89, c)+"Dart Tips"+GetForlvl(4, c), 1107);
		c.getActionSender().sendQuest(GetForlvl(89, c)+"Nails"+GetForlvl(4, c), 13358);
		c.getActionSender().sendQuest(GetForlvl(88, c)+"Medium Helm"+GetForlvl(3, c), 1102);
		c.getActionSender().sendQuest(GetForlvl(87, c)+"Mace"+GetForlvl(2, c), 1093);
		c.getActionSender().sendQuest(GetForlvl(85, c)+"Dagger"+GetForlvl(1, c), 1094);
		c.getActionSender().sendQuest(GetForlvl(86, c)+"Axe"+GetForlvl(1, c), 1091);
		c.getActionSender().sendFrame34(1213,0,1119,1); //dagger
		 c.getActionSender().sendFrame34(1359,0,1120,1); //axe
		 c.getActionSender().sendFrame34(1113,0,1121,1); //chain body
		 c.getActionSender().sendFrame34(1147,0,1122,1); //med helm
		 c.getActionSender().sendFrame34(824,0,1123,10); //dart tips
		 c.getActionSender().sendFrame34(1289,1,1119,1); //s-sword
		 c.getActionSender().sendFrame34(1432,1,1120,1); //mace
		 c.getActionSender().sendFrame34(1079,1,1121,1); //platelegs
		 c.getActionSender().sendFrame34(1163,1,1122,1); //full helm
		 c.getActionSender().sendFrame34(44,1,1123,15); //arrowtips
		 c.getActionSender().sendFrame34(1333,2,1119,1); //scimmy
		 c.getActionSender().sendFrame34(1347,2,1120,1); //warhammer
		 c.getActionSender().sendFrame34(1093,2,1121,1); //plateskirt
		 c.getActionSender().sendFrame34(1185,2,1122,1); //Sq. Shield
		 c.getActionSender().sendFrame34(868,2,1123,5); //throwing-knives
		 c.getActionSender().sendFrame34(1303,3,1119,1); //longsword
		 c.getActionSender().sendFrame34(1373,3,1120,1); //battleaxe
		 c.getActionSender().sendFrame34(1127,3,1121,1); //platebody
		 c.getActionSender().sendFrame34(1201,3,1122,1); //kiteshield
		 c.getActionSender().sendFrame34(1319,4,1119,1); //2h sword
		 c.getActionSender().sendFrame34(3101,4,1120,1); //claws
		 c.getActionSender().sendFrame34(4824,4,1122,15); //nails
		 c.getActionSender().sendFrame34(-1,3,1123, 1);
		 c.getActionSender().sendQuest("",1135);
		 c.getActionSender().sendQuest("",1134);
		 c.getActionSender().sendQuest("",11461);
		 c.getActionSender().sendQuest("",11459);
		 c.getActionSender().sendQuest("",1132);
		 c.getActionSender().sendQuest("",1096);
		c.outStream.createFrame(97);
		c.outStream.writeWord(994);
	}

	private void MakeAddyInterface(Client c) {
		String fiveb = GetForBars(2361, 5, c);
		String threeb = GetForBars(2361, 3, c);
		String twob = GetForBars(2361, 2, c);
		String oneb = GetForBars(2361, 1, c);
		c.getActionSender().sendQuest(fiveb+"5 Bars"+fiveb, 1112);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1109);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1110);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1118);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1111);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1095);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1115);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1090);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1113);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1116);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1114);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1089);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 8428);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1124);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1125);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1126);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1127);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1128);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1129);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1130);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1131);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 13357);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 11459);
		c.getActionSender().sendQuest(GetForlvl(88, c)+"Plate Body"+GetForlvl(18, c), 1101);
		c.getActionSender().sendQuest(GetForlvl(86, c)+"Plate Legs"+GetForlvl(16, c), 1099);
		c.getActionSender().sendQuest(GetForlvl(86, c)+"Plate Skirt"+GetForlvl(16, c), 1100);
		c.getActionSender().sendQuest(GetForlvl(84, c)+"2 Hand Sword"+GetForlvl(14, c), 1088);
		c.getActionSender().sendQuest(GetForlvl(83, c)+"Claws"+GetForlvl(13, c), 8429);
		c.getActionSender().sendQuest(GetForlvl(82, c)+"Kite Shield"+GetForlvl(12, c), 1105);
		c.getActionSender().sendQuest(GetForlvl(81, c)+"Chain Body"+GetForlvl(11, c), 1098);
		c.getActionSender().sendQuest(GetForlvl(80, c)+"Battle Axe"+GetForlvl(10, c), 1092);
		c.getActionSender().sendQuest(GetForlvl(79, c)+"Warhammer"+GetForlvl(9, c), 1083);
		c.getActionSender().sendQuest(GetForlvl(78, c)+"Square Shield"+GetForlvl(8, c), 1104);
		c.getActionSender().sendQuest(GetForlvl(77, c)+"Full Helm"+GetForlvl(7, c), 1103);
		//c.getActionSender().sendQuest(GetForlvl(77, c)+"Throwing Knives"+GetForlvl(7, c), 1106);
		c.getActionSender().sendQuest(GetForlvl(76, c)+"Long Sword"+GetForlvl(6, c), 1086);
		c.getActionSender().sendQuest(GetForlvl(75, c)+"Scimitar"+GetForlvl(5, c), 1087);
		c.getActionSender().sendQuest(GetForlvl(75, c)+"Arrowtips"+GetForlvl(5, c), 1108);
		c.getActionSender().sendQuest(GetForlvl(74, c)+"Sword"+GetForlvl(4, c), 1085);
		c.getActionSender().sendQuest(GetForlvl(74, c)+"Dart Tips"+GetForlvl(4, c), 1107);
		c.getActionSender().sendQuest(GetForlvl(74, c)+"Nails"+GetForlvl(4, c), 13358);
		c.getActionSender().sendQuest(GetForlvl(73, c)+"Medium Helm"+GetForlvl(3, c), 1102);
		c.getActionSender().sendQuest(GetForlvl(72, c)+"Mace"+GetForlvl(2, c), 1093);
		c.getActionSender().sendQuest(GetForlvl(70, c)+"Dagger"+GetForlvl(1, c), 1094);
		c.getActionSender().sendQuest(GetForlvl(71, c)+"Axe"+GetForlvl(1, c), 1091);
		c.getActionSender().sendFrame34(1211,0,1119,1); //dagger
		 c.getActionSender().sendFrame34(1357,0,1120,1); //axe
		 c.getActionSender().sendFrame34(1111,0,1121,1); //chain body
		 c.getActionSender().sendFrame34(1145,0,1122,1); //med helm
		 c.getActionSender().sendFrame34(823,0,1123,10); //dart tips
		 c.getActionSender().sendFrame34(1287,1,1119,1); //s-sword
		 c.getActionSender().sendFrame34(1430,1,1120,1); //mace
		 c.getActionSender().sendFrame34(1073,1,1121,1); //platelegs
		 c.getActionSender().sendFrame34(1161,1,1122,1); //full helm
		 c.getActionSender().sendFrame34(43,1,1123,15); //arrowtips
		 c.getActionSender().sendFrame34(1331,2,1119,1); //scimmy
		 c.getActionSender().sendFrame34(1345,2,1120,1); //warhammer
		 c.getActionSender().sendFrame34(1091,2,1121,1); //plateskirt
		 c.getActionSender().sendFrame34(1183,2,1122,1); //Sq. Shield
		 c.getActionSender().sendFrame34(867,2,1123,5); //throwing-knives
		 c.getActionSender().sendFrame34(1301,3,1119,1); //longsword
		 c.getActionSender().sendFrame34(1371,3,1120,1); //battleaxe
		 c.getActionSender().sendFrame34(1123,3,1121,1); //platebody
		 c.getActionSender().sendFrame34(1199,3,1122,1); //kiteshield
		 c.getActionSender().sendFrame34(1317,4,1119,1); //2h sword
		 c.getActionSender().sendFrame34(3100,4,1120,1); //claws
		 c.getActionSender().sendFrame34(4823,4,1122,15); //nails
		 c.getActionSender().sendFrame34(-1,3,1123, 1);
		 c.getActionSender().sendQuest("",1135);
		 c.getActionSender().sendQuest("",1134);
		 c.getActionSender().sendQuest("",11461);
		 c.getActionSender().sendQuest("",11459);
		 c.getActionSender().sendQuest("",1132);
		 c.getActionSender().sendQuest("",1096);
		c.outStream.createFrame(97);
		c.outStream.writeWord(994);
	}

	private void MakeMithInterface(Client c) {
		String fiveb = GetForBars(2359, 5, c);
		String threeb = GetForBars(2359, 3, c);
		String twob = GetForBars(2359, 2, c);
		String oneb = GetForBars(2359, 1, c);
		c.getActionSender().sendQuest(fiveb+"5 Bars"+fiveb, 1112);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1109);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1110);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1118);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1111);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1095);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1115);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1090);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1113);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1116);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1114);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1089);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 8428);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1124);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1125);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1126);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1127);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1128);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1129);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1130);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1131);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 13357);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 11459);
		c.getActionSender().sendQuest(GetForlvl(68, c)+"Plate Body"+GetForlvl(18, c), 1101);
		c.getActionSender().sendQuest(GetForlvl(66, c)+"Plate Legs"+GetForlvl(16, c), 1099);
		c.getActionSender().sendQuest(GetForlvl(66, c)+"Plate Skirt"+GetForlvl(16, c), 1100);
		c.getActionSender().sendQuest(GetForlvl(64, c)+"2 Hand Sword"+GetForlvl(14, c), 1088);
		c.getActionSender().sendQuest(GetForlvl(63, c)+"Claws"+GetForlvl(13, c), 8429);
		c.getActionSender().sendQuest(GetForlvl(62, c)+"Kite Shield"+GetForlvl(12, c), 1105);
		c.getActionSender().sendQuest(GetForlvl(61, c)+"Chain Body"+GetForlvl(11, c), 1098);
		c.getActionSender().sendQuest(GetForlvl(60, c)+"Battle Axe"+GetForlvl(10, c), 1092);
		c.getActionSender().sendQuest(GetForlvl(59, c)+"Warhammer"+GetForlvl(9, c), 1083);
		c.getActionSender().sendQuest(GetForlvl(58, c)+"Square Shield"+GetForlvl(8, c), 1104);
		c.getActionSender().sendQuest(GetForlvl(57, c)+"Full Helm"+GetForlvl(7, c), 1103);
		//c.getActionSender().sendQuest(GetForlvl(57, c)+"Throwing Knives"+GetForlvl(7, c), 1106);
		c.getActionSender().sendQuest(GetForlvl(56, c)+"Long Sword"+GetForlvl(6, c), 1086);
		c.getActionSender().sendQuest(GetForlvl(55, c)+"Scimitar"+GetForlvl(5, c), 1087);
		c.getActionSender().sendQuest(GetForlvl(55, c)+"Arrowtips"+GetForlvl(5, c), 1108);
		c.getActionSender().sendQuest(GetForlvl(54, c)+"Sword"+GetForlvl(4, c), 1085);
		c.getActionSender().sendQuest(GetForlvl(54, c)+"Dart Tips"+GetForlvl(4, c), 1107);
		c.getActionSender().sendQuest(GetForlvl(54, c)+"Nails"+GetForlvl(4, c), 13358);
		c.getActionSender().sendQuest(GetForlvl(53, c)+"Medium Helm"+GetForlvl(3, c), 1102);
		c.getActionSender().sendQuest(GetForlvl(52, c)+"Mace"+GetForlvl(2, c), 1093);
		c.getActionSender().sendQuest(GetForlvl(50, c)+"Dagger"+GetForlvl(1, c), 1094);
		c.getActionSender().sendQuest(GetForlvl(51, c)+"Axe"+GetForlvl(1, c), 1091);
		c.getActionSender().sendFrame34(1209,0,1119,1); //dagger
		 c.getActionSender().sendFrame34(1355,0,1120,1); //axe
		 c.getActionSender().sendFrame34(1109,0,1121,1); //chain body
		 c.getActionSender().sendFrame34(1143,0,1122,1); //med helm
		 c.getActionSender().sendFrame34(822,0,1123,10); //dart tips
		 c.getActionSender().sendFrame34(1285,1,1119,1); //s-sword
		 c.getActionSender().sendFrame34(1428,1,1120,1); //mace
		 c.getActionSender().sendFrame34(1071,1,1121,1); //platelegs
		 c.getActionSender().sendFrame34(1159,1,1122,1); //full helm
		 c.getActionSender().sendFrame34(42,1,1123,15); //arrowtips
		 c.getActionSender().sendFrame34(1329,2,1119,1); //scimmy
		 c.getActionSender().sendFrame34(1343,2,1120,1); //warhammer
		 c.getActionSender().sendFrame34(1085,2,1121,1); //plateskirt
		 c.getActionSender().sendFrame34(1181,2,1122,1); //Sq. Shield
		 c.getActionSender().sendFrame34(866,2,1123,5); //throwing-knives
		 c.getActionSender().sendFrame34(1299,3,1119,1); //longsword
		 c.getActionSender().sendFrame34(1369,3,1120,1); //battleaxe
		 c.getActionSender().sendFrame34(1121,3,1121,1); //platebody
		 c.getActionSender().sendFrame34(1197,3,1122,1); //kiteshield
		 c.getActionSender().sendFrame34(1315,4,1119,1); //2h sword
		 c.getActionSender().sendFrame34(3099,4,1120,1); //claws
		 c.getActionSender().sendFrame34(4822,4,1122,15); //nails
		 c.getActionSender().sendFrame34(-1,3,1123, 1);
		 c.getActionSender().sendQuest("",1135);
		 c.getActionSender().sendQuest("",1134);
		 c.getActionSender().sendQuest("",11461);
		 c.getActionSender().sendQuest("",11459);
		 c.getActionSender().sendQuest("",1132);
		 c.getActionSender().sendQuest("",1096);
		c.outStream.createFrame(97);
		c.outStream.writeWord(994);
	}

	private void MakeSteelInterface(Client c) {
		String fiveb = GetForBars(2353, 5, c);
		String threeb = GetForBars(2353, 3, c);
		String twob = GetForBars(2353, 2, c);
		String oneb = GetForBars(2353, 1, c);
		c.getActionSender().sendQuest(fiveb+"5 Bars"+fiveb, 1112);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1109);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1110);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1118);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1111);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1095);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1115);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1090);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1113);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1116);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1114);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1089);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 8428);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1124);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1125);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1126);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1127);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1128);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1129);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1130);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1131);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 13357);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb,1132);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb,1135);
		c.getActionSender().sendQuest("", 11459);
		c.getActionSender().sendQuest(GetForlvl(48, c)+"Plate Body"+GetForlvl(18, c), 1101);
		c.getActionSender().sendQuest(GetForlvl(46, c)+"Plate Legs"+GetForlvl(16, c), 1099);
		c.getActionSender().sendQuest(GetForlvl(46, c)+"Plate Skirt"+GetForlvl(16, c), 1100);
		c.getActionSender().sendQuest(GetForlvl(44, c)+"2 Hand Sword"+GetForlvl(14, c), 1088);
		c.getActionSender().sendQuest(GetForlvl(43, c)+"Claws"+GetForlvl(13, c), 8429);
		c.getActionSender().sendQuest(GetForlvl(42, c)+"Kite Shield"+GetForlvl(12, c), 1105);
		c.getActionSender().sendQuest(GetForlvl(41, c)+"Chain Body"+GetForlvl(11, c), 1098);
		c.getActionSender().sendQuest("",11461);
		c.getActionSender().sendQuest(GetForlvl(40, c)+"Battle Axe"+GetForlvl(10, c), 1092);
		c.getActionSender().sendQuest(GetForlvl(39, c)+"Warhammer"+GetForlvl(9, c), 1083);
		c.getActionSender().sendQuest(GetForlvl(38, c)+"Square Shield"+GetForlvl(8, c), 1104);
		c.getActionSender().sendQuest(GetForlvl(37, c)+"Full Helm"+GetForlvl(7, c), 1103);
		//c.getActionSender().sendQuest(GetForlvl(37, c)+"Throwing Knives"+GetForlvl(7, c), 1106);
		c.getActionSender().sendQuest(GetForlvl(36, c)+"Long Sword"+GetForlvl(6, c), 1086);
		c.getActionSender().sendQuest(GetForlvl(35, c)+"Scimitar"+GetForlvl(5, c), 1087);
		c.getActionSender().sendQuest(GetForlvl(35, c)+"Arrowtips"+GetForlvl(5, c), 1108);
		c.getActionSender().sendQuest(GetForlvl(34, c)+"Sword"+GetForlvl(4, c), 1085);
		c.getActionSender().sendQuest(GetForlvl(34, c)+"Dart Tips"+GetForlvl(4, c), 1107);
		c.getActionSender().sendQuest(GetForlvl(34, c)+"Nails"+GetForlvl(4, c), 13358);
		c.getActionSender().sendQuest(GetForlvl(33, c)+"Medium Helm"+GetForlvl(3, c), 1102);
		c.getActionSender().sendQuest(GetForlvl(32, c)+"Mace"+GetForlvl(2, c), 1093);
		c.getActionSender().sendQuest(GetForlvl(30, c)+"Dagger"+GetForlvl(1, c), 1094);
		c.getActionSender().sendQuest(GetForlvl(31, c)+"Axe"+GetForlvl(1, c), 1091);
		c.getActionSender().sendQuest(GetForlvl(35, c)+"Cannon Ball"+GetForlvl(35, c),1096);
		c.getActionSender().sendQuest(GetForlvl(36, c)+"Studs"+GetForlvl(36, c),1134);
		c.getActionSender().sendFrame34(1207,0,1119,1);
		 c.getActionSender().sendFrame34(1353,0,1120,1);
		 c.getActionSender().sendFrame34(1105,0,1121,1);
		 c.getActionSender().sendFrame34(1141,0,1122,1);
		 c.getActionSender().sendFrame34(821,0,1123,10);
		 c.getActionSender().sendFrame34(1281,1,1119,1);
		 c.getActionSender().sendFrame34(1424,1,1120,1);
		 c.getActionSender().sendFrame34(1069,1,1121,1);
		 c.getActionSender().sendFrame34(1157,1,1122,1);
		 c.getActionSender().sendFrame34(41,1,1123,15);
		 c.getActionSender().sendFrame34(1325,2,1119,1);
		 c.getActionSender().sendFrame34(1339,2,1120,1);
		 c.getActionSender().sendFrame34(1083,2,1121,1);
		 c.getActionSender().sendFrame34(1177,2,1122,1);
		 c.getActionSender().sendFrame34(865,2,1123,5);
		 c.getActionSender().sendFrame34(1295,3,1119,1);
		 c.getActionSender().sendFrame34(1365,3,1120,1);
		 c.getActionSender().sendFrame34(1119,3,1121,1);
		 c.getActionSender().sendFrame34(1193,3,1122,1);
		 c.getActionSender().sendFrame34(1311,4,1119,1);
		 c.getActionSender().sendFrame34(3097,4,1120,1);
		 c.getActionSender().sendFrame34(1539,4,1122,15);
		 c.getActionSender().sendFrame34(2,3,1123,4);
		 c.getActionSender().sendFrame34(2370,4,1123,1);
		c.outStream.createFrame(97);
		c.outStream.writeWord(994);
	}

	private void MakeIronInterface(Client c) 
	{
		String fiveb = GetForBars(2351, 5, c);
		String threeb = GetForBars(2351, 3, c);
		String twob = GetForBars(2351, 2, c);
		String oneb = GetForBars(2351, 1, c);
		c.getActionSender().sendQuest(fiveb+"5 Bars"+fiveb, 1112);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1109);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1110);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1118);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1111);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1095);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1115);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1090);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1113);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1116);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1114);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1089);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 8428);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1124);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1125);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1126);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1127);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1128);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1129);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1130);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1131);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 13357);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 11459);
		c.getActionSender().sendQuest(GetForlvl(33, c)+"Plate Body"+GetForlvl(18, c), 1101);
		c.getActionSender().sendQuest(GetForlvl(31, c)+"Plate Legs"+GetForlvl(16, c), 1099);
		c.getActionSender().sendQuest(GetForlvl(31, c)+"Plate Skirt"+GetForlvl(16, c), 1100);
		c.getActionSender().sendQuest(GetForlvl(29, c)+"2 Hand Sword"+GetForlvl(14, c), 1088);
		c.getActionSender().sendQuest(GetForlvl(28, c)+"Claws"+GetForlvl(13, c), 8429);
		c.getActionSender().sendQuest(GetForlvl(27, c)+"Kite Shield"+GetForlvl(12, c), 1105);
		c.getActionSender().sendQuest(GetForlvl(26, c)+"Chain Body"+GetForlvl(11, c), 1098);
		c.getActionSender().sendQuest(GetForlvl(26, c)+"Oil Lantern Frame"+GetForlvl(11, c),11461);
		c.getActionSender().sendQuest(GetForlvl(25, c)+"Battle Axe"+GetForlvl(10, c), 1092);
		c.getActionSender().sendQuest(GetForlvl(24, c)+"Warhammer"+GetForlvl(9, c), 1083);
		c.getActionSender().sendQuest(GetForlvl(23, c)+"Square Shield"+GetForlvl(8, c), 1104);
		c.getActionSender().sendQuest(GetForlvl(22, c)+"Full Helm"+GetForlvl(7, c), 1103);
		//c.getActionSender().sendQuest(GetForlvl(21, c)+"Throwing Knives"+GetForlvl(7, c), 1106);
		c.getActionSender().sendQuest(GetForlvl(21, c)+"Long Sword"+GetForlvl(6, c), 1086);
		c.getActionSender().sendQuest(GetForlvl(20, c)+"Scimitar"+GetForlvl(5, c), 1087);
		c.getActionSender().sendQuest(GetForlvl(20, c)+"Arrowtips"+GetForlvl(5, c), 1108);
		c.getActionSender().sendQuest(GetForlvl(19, c)+"Sword"+GetForlvl(4, c), 1085);
		c.getActionSender().sendQuest(GetForlvl(19, c)+"Dart Tips"+GetForlvl(4, c), 1107);
		c.getActionSender().sendQuest(GetForlvl(19, c)+"Nails"+GetForlvl(4, c), 13358);
		c.getActionSender().sendQuest(GetForlvl(18, c)+"Medium Helm"+GetForlvl(3, c), 1102);
		c.getActionSender().sendQuest(GetForlvl(17, c)+"Mace"+GetForlvl(2, c), 1093);
		c.getActionSender().sendQuest(GetForlvl(15, c)+"Dagger"+GetForlvl(1, c), 1094);
		c.getActionSender().sendQuest(GetForlvl(16, c)+"Axe"+GetForlvl(1, c), 1091);
		c.getActionSender().sendFrame34(1203,0,1119,1);
		 c.getActionSender().sendFrame34(1349,0,1120,1);
		 c.getActionSender().sendFrame34(1101,0,1121,1);
		 c.getActionSender().sendFrame34(1137,0,1122,1);
		 c.getActionSender().sendFrame34(820,0,1123,10);
		 c.getActionSender().sendFrame34(1279,1,1119,1);
		 c.getActionSender().sendFrame34(1420,1,1120,1);
		 c.getActionSender().sendFrame34(1067,1,1121,1);
		 c.getActionSender().sendFrame34(1153,1,1122,1);
		 c.getActionSender().sendFrame34(40,1,1123,15);
		 c.getActionSender().sendFrame34(1323,2,1119,1);
		 c.getActionSender().sendFrame34(1335,2,1120,1);
		 c.getActionSender().sendFrame34(1081,2,1121,1);
		 c.getActionSender().sendFrame34(1175,2,1122,1);
		 c.getActionSender().sendFrame34(863,2,1123,5);
		 c.getActionSender().sendFrame34(1293,3,1119,1);
		 c.getActionSender().sendFrame34(1363,3,1120,1);
		 c.getActionSender().sendFrame34(1115,3,1121,1);
		 c.getActionSender().sendFrame34(1191,3,1122,1);
		 c.getActionSender().sendFrame34(1309,4,1119,1);
		 c.getActionSender().sendFrame34(3096,4,1120,1);
		 c.getActionSender().sendFrame34(4820,4,1122,15);
		 c.getActionSender().sendFrame34(4540,4,1121,1);
		 c.getActionSender().sendFrame34(-1,3,1123, 1);
		 c.getActionSender().sendQuest("",1135);
		 c.getActionSender().sendQuest("",1134);
		 c.getActionSender().sendQuest("",1132);
		 c.getActionSender().sendQuest("",1096);
		c.outStream.createFrame(97);
		c.outStream.writeWord(994);
	}

	private void MakeBronzeInterface(Client c) 
	{
		String fiveb = GetForBars(2349, 5, c);
		String threeb = GetForBars(2349, 3, c);
		String twob = GetForBars(2349, 2, c);
		String oneb = GetForBars(2349, 1, c);
		c.getActionSender().sendQuest(fiveb+"5 Bars"+fiveb, 1112);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1109);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1110);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1118);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1111);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1095);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1115);
		c.getActionSender().sendQuest(threeb+"3 Bars"+threeb, 1090);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1113);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1116);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1114);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 1089);
		c.getActionSender().sendQuest(twob+"2 Bars"+twob, 8428);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1124);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1125);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1126);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1127);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1128);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1129);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1130);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 1131);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 13357);
		c.getActionSender().sendQuest(oneb+"1 Bar"+oneb, 11459);
		c.getActionSender().sendQuest(GetForlvl(18, c)+"Plate Body"+GetForlvl(18, c), 1101);
		c.getActionSender().sendQuest(GetForlvl(16, c)+"Plate Legs"+GetForlvl(16, c), 1099);
		c.getActionSender().sendQuest(GetForlvl(16, c)+"Plate Skirt"+GetForlvl(16, c), 1100);
		c.getActionSender().sendQuest(GetForlvl(14, c)+"2 Hand Sword"+GetForlvl(14, c), 1088);
		c.getActionSender().sendQuest(GetForlvl(13, c)+"Claws"+GetForlvl(13, c), 8429);
		c.getActionSender().sendQuest(GetForlvl(12, c)+"Kite Shield"+GetForlvl(12, c), 1105);
		c.getActionSender().sendQuest(GetForlvl(11, c)+"Chain Body"+GetForlvl(11, c), 1098);
		c.getActionSender().sendQuest(GetForlvl(10, c)+"Battle Axe"+GetForlvl(10, c), 1092);
		c.getActionSender().sendQuest(GetForlvl(9, c)+"Warhammer"+GetForlvl(9, c), 1083);
		c.getActionSender().sendQuest(GetForlvl(8, c)+"Square Shield"+GetForlvl(8, c), 1104);
		c.getActionSender().sendQuest(GetForlvl(7, c)+"Full Helm"+GetForlvl(7, c), 1103);
		//c.getActionSender().sendQuest(GetForlvl(7, c)+"Throwing Knives"+GetForlvl(7, c), 1106);
		c.getActionSender().sendQuest(GetForlvl(6, c)+"Long Sword"+GetForlvl(6, c), 1086);
		c.getActionSender().sendQuest(GetForlvl(5, c)+"Scimitar"+GetForlvl(5, c), 1087);
		c.getActionSender().sendQuest(GetForlvl(5, c)+"Arrowtips"+GetForlvl(5, c), 1108);
		c.getActionSender().sendQuest(GetForlvl(4, c)+"Sword"+GetForlvl(4, c), 1085);
		c.getActionSender().sendQuest(GetForlvl(4, c)+"Dart Tips"+GetForlvl(4, c), 1107);
		c.getActionSender().sendQuest(GetForlvl(4, c)+"Nails"+GetForlvl(4, c), 13358);
		c.getActionSender().sendQuest(GetForlvl(3, c)+"Medium Helm"+GetForlvl(3, c), 1102);
		c.getActionSender().sendQuest(GetForlvl(2, c)+"Mace"+GetForlvl(2, c), 1093);
		c.getActionSender().sendQuest(GetForlvl(1, c)+"Dagger"+GetForlvl(1, c), 1094);
		c.getActionSender().sendQuest(GetForlvl(1, c)+"Axe"+GetForlvl(1, c), 1091);
		c.getActionSender().sendFrame34(1205,0,1119,1);
		 c.getActionSender().sendFrame34(1351,0,1120,1);
		 c.getActionSender().sendFrame34(1103,0,1121,1);
		 c.getActionSender().sendFrame34(1139,0,1122,1);
		 c.getActionSender().sendFrame34(819,0,1123,10);
		 c.getActionSender().sendFrame34(1277,1,1119,1);
		 c.getActionSender().sendFrame34(1422,1,1120,1);
		 c.getActionSender().sendFrame34(1075,1,1121,1);
		 c.getActionSender().sendFrame34(1155,1,1122,1);
		 c.getActionSender().sendFrame34(39,1,1123,15);
		 c.getActionSender().sendFrame34(1321,2,1119,1);
		 c.getActionSender().sendFrame34(1337,2,1120,1);
		 c.getActionSender().sendFrame34(1087,2,1121,1);
		 c.getActionSender().sendFrame34(1173,2,1122,1);
		 c.getActionSender().sendFrame34(864,2,1123,5);
		 c.getActionSender().sendFrame34(1291,3,1119,1);
		 c.getActionSender().sendFrame34(1375,3,1120,1);
		 c.getActionSender().sendFrame34(1117,3,1121,1);
		 c.getActionSender().sendFrame34(1189,3,1122,1);
		 c.getActionSender().sendFrame34(1307,4,1119,1);
		 c.getActionSender().sendFrame34(3095,4,1120,1);
		 c.getActionSender().sendFrame34(4819,4,1122,15);
		 c.getActionSender().sendFrame34(-1,3,1123, 1);
		 c.getActionSender().sendQuest("",1135);
		 c.getActionSender().sendQuest("",1134);
		 c.getActionSender().sendQuest("",11461);
		 c.getActionSender().sendQuest("",11459);
		 c.getActionSender().sendQuest("",1132);
		 c.getActionSender().sendQuest("",1096);
		c.outStream.createFrame(97);
		c.outStream.writeWord(994);
		
	}

	private String GetForlvl(int i, Client c) {
		if (c.playerLevel[13] >= i)
			return "@whi@";
		
			return "@bla@";
	}

	private String GetForBars(int i, int j, Client c) {
		if (c.getActionAssistant().playerHasItem(i, j)) 
		return "@gre@";
		
		return "@red@";
	}

}