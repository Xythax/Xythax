package org.xythax.model;

public class EquipmentReq {

	public static int attack(int itemID) {
		if (itemID == 1203/* Iron_dagger */
				|| itemID == 1205/* Bronze_dagger */
				|| itemID == 1219/* Iron_dagger(p) */
				|| itemID == 1221/* Bronze_dagger(p) */
				|| itemID == 1237/* Bronze_spear */
				|| itemID == 1239/* Iron_spear */
				|| itemID == 1251/* Bronze_spear(p) */
				|| itemID == 1253/* Iron_spear(p) */
				|| itemID == 1265/* Bronze_pickaxe */
				|| itemID == 1267/* Iron_pickaxe */
				|| itemID == 1277/* Bronze_sword */
				|| itemID == 1279/* Iron_sword */
				|| itemID == 1291/* Bronze_longsword */
				|| itemID == 1293/* Iron_longsword */
				|| itemID == 1307/* Bronze_2h_sword */
				|| itemID == 1309/* Iron_2h_sword */
				|| itemID == 1321/* Bronze_scimitar */
				|| itemID == 1323/* Iron_scimitar */
				|| itemID == 1335/* Iron_warhammer */
				|| itemID == 1337/* Bronze_warhammer */
				|| itemID == 1349/* Iron_axe */
				|| itemID == 1351/* Bronze_axe */
				|| itemID == 1363/* Iron_battleaxe */
				|| itemID == 1375/* Bronze_battleaxe */
				|| itemID == 1420/* Iron_mace */
				|| itemID == 1422/* Bronze_mace */
				|| itemID == 3095/* Bronze_claws */
				|| itemID == 3096/* Iron_claws */
				|| itemID == 3170/* Bronze_spear(kp) */
				|| itemID == 3171/* Iron_spear(kp) */
				|| itemID == 3190/* Bronze_halberd */
				|| itemID == 3192/* Iron_halberd */
				|| itemID == 5668/* Iron_dagger(+) */
				|| itemID == 5670/* Bronze_dagger(+) */
				|| itemID == 5686/* Iron_dagger(s) */
				|| itemID == 5688/* Bronze_dagger(s) */
				|| itemID == 5704/* Bronze_spear(+) */
				|| itemID == 5706/* Iron_spear(+) */
				|| itemID == 5718/* Bronze_spear(s) */
				|| itemID == 5720/* Iron_spear(s) */
		) {
			return 1;
		}
		if (itemID == 1207/* Steel_dagger */
				|| itemID == 1223/* Steel_dagger(p) */
				|| itemID == 1241/* Steel_spear */
				|| itemID == 1255/* Steel_spear(p) */
				|| itemID == 1269/* Steel_pickaxe */
				|| itemID == 1281/* Steel_sword */
				|| itemID == 1295/* Steel_longsword */
				|| itemID == 1311/* Steel_2h_sword */
				|| itemID == 1325/* Steel_scimitar */
				|| itemID == 1339/* Steel_warhammer */
				|| itemID == 1353/* Steel_axe */
				|| itemID == 1365/* Steel_battleaxe */
				|| itemID == 1424/* Steel_mace */
				|| itemID == 3097/* Steel_claws */
				|| itemID == 3172/* Steel_spear(kp) */
				|| itemID == 3194/* Steel_halberd */
				|| itemID == 5672/* Steel_dagger(+) */
				|| itemID == 5690/* Steel_dagger(s) */
				|| itemID == 5708/* Steel_spear(+) */
				|| itemID == 5722/* Steel_spear(s) */
		) {
			return 5;
		}
		if (itemID == 1217/* Black_dagger */
				|| itemID == 1233/* Black_dagger(p) */
				|| itemID == 1283/* Black_sword */
				|| itemID == 1297/* Black_longsword */
				|| itemID == 1313/* Black_2h_sword */
				|| itemID == 1327/* Black_scimitar */
				|| itemID == 1341/* Black_warhammer */
				|| itemID == 1361/* Black_axe */
				|| itemID == 1367/* Black_battleaxe */
				|| itemID == 1426/* Black_mace */
				|| itemID == 3098/* Black_claws */
				|| itemID == 3196/* Black_halberd */
				|| itemID == 4580/* Black_spear */
				|| itemID == 4582/* Black_spear(p) */
				|| itemID == 4584/* Black_spear(kp) */
				|| itemID == 5682/* Black_dagger(+) */
				|| itemID == 5700/* Black_dagger(s) */
				|| itemID == 5734/* Black_spear(+) */
				|| itemID == 5736/* Black_spear(s) */
				|| itemID == 6587/* White_claws */
				|| itemID == 6589/* White_battleaxe */
				|| itemID == 6591/* White_dagger */
				|| itemID == 6593/* White_dagger(p) */
				|| itemID == 6595/* White_dagger(+) */
				|| itemID == 6597/* White_dagger(s) */
				|| itemID == 6599/* White_halberd */
				|| itemID == 6601/* White_mace */
				|| itemID == 6605/* White_sword */
				|| itemID == 6607/* White_longsword */
				|| itemID == 6609/* White_2h_sword */
				|| itemID == 6611/* White_scimitar */
				|| itemID == 6613/* White_warhammer */
		) {
			return 10;
		}
		if (itemID == 1209/* Mithril_dagger */
				|| itemID == 1225/* Mithril_dagger(p) */
				|| itemID == 1243/* Mithril_spear */
				|| itemID == 1257/* Mithril_spear(p) */
				|| itemID == 1273/* Mithril_pickaxe */
				|| itemID == 1285/* Mithril_sword */
				|| itemID == 1299/* Mithril_longsword */
				|| itemID == 1315/* Mithril_2h_sword */
				|| itemID == 1329/* Mithril_scimitar */
				|| itemID == 1343/* Mithril_warhammer */
				|| itemID == 1355/* Mithril_axe */
				|| itemID == 1369/* Mithril_battleaxe */
				|| itemID == 1428/* Mithril_mace */
				|| itemID == 3099/* Mithril_claws */
				|| itemID == 3173/* Mithril_spear(kp) */
				|| itemID == 3198/* Mithril_halberd */
				|| itemID == 5674/* Mithril_dagger(+) */
				|| itemID == 5692/* Mithril_dagger(s) */
				|| itemID == 5710/* Mithril_spear(+) */
				|| itemID == 5724/* Mithril_spear(s) */
		) {
			return 20;
		}
		if (itemID == 1211/* Adamant_dagger */
				|| itemID == 1227/* Adamant_dagger(p) */
				|| itemID == 1245/* Adamant_spear */
				|| itemID == 1259/* Adamant_spear(p) */
				|| itemID == 1271/* Adamant_pickaxe */
				|| itemID == 1287/* Adamant_sword */
				|| itemID == 1301/* Adamant_longsword */
				|| itemID == 1317/* Adamant_2h_sword */
				|| itemID == 1331/* Adamant_scimitar */
				|| itemID == 1345/* Adamnt_warhammer */
				|| itemID == 1357/* Adamant_axe */
				|| itemID == 1371/* Adamant_battleaxe */
				|| itemID == 1430/* Adamant_mace */
				|| itemID == 3100/* Adamant_claws */
				|| itemID == 3174/* Adamant_spear(kp) */
				|| itemID == 3200/* Adamant_halberd */
				|| itemID == 3757/* Fremennik_blade */
				|| itemID == 5676/* Adamant_dagger(+) */
				|| itemID == 5694/* Adamant_dagger(s) */
				|| itemID == 5712/* Adamant_spear(+) */
				|| itemID == 5726/* Adamant_spear(s) */
		) {
			return 30;
		}
		if (itemID == 1213/* Rune_dagger */
				|| itemID == 1229/* Rune_dagger(p) */
				|| itemID == 1261/* Rune_spear(p) */
				|| itemID == 1275/* Rune_pickaxe */
				|| itemID == 1289/* Rune_sword */
				|| itemID == 1303/* Rune_longsword */
				|| itemID == 1319/* Rune_2h_sword */
				|| itemID == 1333/* Rune_scimitar */
				|| itemID == 1347/* Rune_warhammer */
				|| itemID == 1359/* Rune_axe */
				|| itemID == 1373/* Rune_battleaxe */
				|| itemID == 1432/* Rune_mace */
				|| itemID == 3101/* Rune_claws */
				|| itemID == 3175/* Rune_spear(kp) */
				|| itemID == 3202/* Rune_halberd */
				|| itemID == 5678/* Rune_dagger(+) */
				|| itemID == 5696/* Rune_dagger(s) */
				|| itemID == 5714/* Rune_spear(+) */
				|| itemID == 5728/* Rune_spear(s) */
		) {
			return 40;
		}
		if (itemID == 4675/* Ancient_staff */
				|| itemID == 4153/* Granite_maul */
				|| itemID == 4158/* Leaf-bladed_spear */
		) {
			return 50;
		}
		if (itemID == 1215/* Dragon_dagger */
				|| itemID == 1231/* Dragon_dagger(p) */
				|| itemID == 1249/* Dragon_spear */
				|| itemID == 1263/* Dragon_spear(p) */
				|| itemID == 1305/* Dragon_longsword */
				|| itemID == 1377/* Dragon_battleaxe */
				|| itemID == 1434/* Dragon_mace */
				|| itemID == 3176/* Dragon_spear(kp) */
				|| itemID == 3204/* Dragon_halberd */
				|| itemID == 4587/* Dragon_scimitar */
				|| itemID == 5680/* Dragon_dagger(+) */
				|| itemID == 5698/* Dragon_dagger(s) */
				|| itemID == 5716/* Dragon_spear(+) */
				|| itemID == 5730/* Dragon_spear(s) */
				|| itemID == 6523/* Toktz-xil-ak */
				|| itemID == 6525/* Toktz-xil-ek */
				|| itemID == 6526/* Toktz-mej-tal */
				|| itemID == 6527/* Tzhaar-ket-em */
				|| itemID == 6739/* Dragon_Axe */
				|| itemID == 7158/* Dragon_2h_Sword */
		) {
			return 60;
		}
		if (itemID == 4151/* Abyssal_whip */
				|| itemID == 4710/* Ahrims_staff */
				|| itemID == 4718/* Dharoks_greataxe */
				|| itemID == 4726/* Guthans_warspear */
				|| itemID == 4747/* Torags_hammers */
				|| itemID == 4755/* Veracs_flail */
				|| itemID == 4862/* Ahrims_staff_100 */
				|| itemID == 4863/* Ahrims_staff_75 */
				|| itemID == 4864/* Ahrims_staff_50 */
				|| itemID == 4865/* Ahrims_staff_25 */
				|| itemID == 4886/* Dharoks_axe_100 */
				|| itemID == 4887/* Dharoks_axe_75 */
				|| itemID == 4888/* Dharoks_axe_50 */
				|| itemID == 4889/* Dharoks_axe_25 */
				|| itemID == 4910/* Guthans_spear_100 */
				|| itemID == 4911/* Guthans_spear_75 */
				|| itemID == 4912/* Guthans_spear_50 */
				|| itemID == 4913/* Guthans_spear_25 */
				|| itemID == 4958/* Torags_hammer_100 */
				|| itemID == 4959/* Torags_hammer_75 */
				|| itemID == 4960/* Torags_hammer_50 */
				|| itemID == 4961/* Torags_hammer_25 */
				|| itemID == 4982/* Veracs_flail_100 */
				|| itemID == 4983/* Veracs_flail_75 */
				|| itemID == 4984/* Veracs_flail_50 */
				|| itemID == 4985/* Veracs_flail_25 */
		) {
			return 70;
		}
		if (itemID == 9747 /* Attack cape non-trimmed */
				|| itemID == 9748 /* Attack cape trimmed */
				|| itemID == 9749 /* Attack hood */
		) {
			return 99;
		}
		return 1;
	}

	public static int strength(int itemID) {

		if (itemID == 3196/* Black_halberd */
		) {
			return 5;
		}
		if (itemID == 3198/* Mithril_halberd */
		) {
			return 10;
		}
		if (itemID == 3200/* Adamant_halberd */
		) {
			return 15;
		}
		if (itemID == 3202/* Rune_halberd */
		) {
			return 20;
		}
		if (itemID == 3204/* Dragon_halberd */
		) {
			return 30;
		}
		if (itemID == 3122/* Granite_shield */
				|| itemID == 4153/* Granite_maul */
				|| itemID == 6809/* Granite_legs */
		) {
			return 50;
		}
		if (itemID == 6528/* Tzhaar-ket-om */
		) {
			return 50;
		}
		if (itemID == 4718/* Dharoks_greataxe */
				|| itemID == 4747/* Torags_hammers */
				|| itemID == 4886/* Dharoks_axe_100 */
				|| itemID == 4887/* Dharoks_axe_75 */
				|| itemID == 4888/* Dharoks_axe_50 */
				|| itemID == 4889/* Dharoks_axe_25 */
				|| itemID == 4958/* Torags_hammer_100 */
				|| itemID == 4959/* Torags_hammer_75 */
				|| itemID == 4960/* Torags_hammer_50 */
				|| itemID == 4961/* Torags_hammer_25 */
		) {
			return 70;
		}
		return 1;
	}

	public static int defence(int itemID) {

		if (itemID == 1067/* Iron_platelegs */
				|| itemID == 1075/* Bronze_platelegs */
				|| itemID == 1081/* Iron_plateskirt */
				|| itemID == 1087/* Bronze_plateskirt */
				|| itemID == 1101/* Iron_chainbody */
				|| itemID == 1103/* Bronze_chainbody */
				|| itemID == 1115/* Iron_platebody */
				|| itemID == 1117/* Bronze_platebody */
				|| itemID == 1137/* Iron_med_helm */
				|| itemID == 1139/* Bronze_med_helm */
				|| itemID == 1153/* Iron_full_helm */
				|| itemID == 1155/* Bronze_full_helm */
				|| itemID == 1173/* Bronze_sq_shield */
				|| itemID == 1175/* Iron_sq_shield */
				|| itemID == 1189/* Bronze_kiteshield */
				|| itemID == 1191/* Iron_kiteshield */
				|| itemID == 4119/* Bronze_boots */
				|| itemID == 4121/* Iron_boots */
		) {
			return 1;
		}
		if (itemID == 1069/* Steel_platelegs */
				|| itemID == 1083/* Steel_plateskirt */
				|| itemID == 1105/* Steel_chainbody */
				|| itemID == 1119/* Steel_platebody */
				|| itemID == 1141/* Steel_med_helm */
				|| itemID == 1157/* Steel_full_helm */
				|| itemID == 1177/* Steel_sq_shield */
				|| itemID == 1193/* Steel_kiteshield */
				|| itemID == 4068/* Decorative_sword */
				|| itemID == 4069/* Decorative_armour */
				|| itemID == 4070/* Decorative_armour */
				|| itemID == 4071/* Decorative_helm */
				|| itemID == 4072/* Decorative_shield */
				|| itemID == 4123/* Steel_boots */
				|| itemID == 4551 /* Spiny_helmet */
		) {
			return 5;
		}
		if (itemID == 1077/* Black_platelegs */
				|| itemID == 1089/* Black_plateskirt */
				|| itemID == 1107/* Black_chainbody */
				|| itemID == 1125/* Black_platebody */
				|| itemID == 1131/* Hardleather_body */
				|| itemID == 1151/* Black_med_helm */
				|| itemID == 1165/* Black_full_helm */
				|| itemID == 1179/* Black_sq_shield */
				|| itemID == 1195/* Black_kiteshield */
				|| itemID == 2583/* Black_platebody_(t) */
				|| itemID == 2585/* Black_platelegs_(t) */
				|| itemID == 2587/* Black_full_helm_(t) */
				|| itemID == 2589/* Black_kiteshield_(t) */
				|| itemID == 2591/* Black_platebody_(g) */
				|| itemID == 2593/* Black_platelegs_(g) */
				|| itemID == 2595/* Black_full_helm_(g) */
				|| itemID == 2597/* Black_kiteshield_(g) */
				|| itemID == 3472/* Black_plateskirt_(t) */
				|| itemID == 3473/* Black_plateskirt_(g) */
				|| itemID == 4125/* Black_boots */
				|| itemID == 4503/* Decorative_sword */
				|| itemID == 4504/* Decorative_armour */
				|| itemID == 4505/* Decorative_armour */
				|| itemID == 4506/* Decorative_helm */
				|| itemID == 4507/* Decorative_shield */
				|| itemID == 6615/* White_chainbody */
				|| itemID == 6617/* White_platebody */
				|| itemID == 6619/* White_boots */
				|| itemID == 6621/* White_med_helm */
				|| itemID == 6623/* White_full_helm */
				|| itemID == 6625/* White_platelegs */
				|| itemID == 6627/* White_plateskirt */
				|| itemID == 6629/* White_gloves */
				|| itemID == 6631/* White_sq_shield */
				|| itemID == 6633/* White_kiteshield */
				|| itemID == 7332/* Black_kiteshield(h) */
				|| itemID == 7338/* Black_kiteshield(h) */
				|| itemID == 7344/* Black_kiteshield(h) */
				|| itemID == 7350/* Black_kiteshield(h) */
				|| itemID == 7356/* Black_kiteshield(h) */
		) {
			return 10;
		}
		if (itemID == 4508/* Decorative_sword */
				|| itemID == 4509/* Decorative_armour */
				|| itemID == 4510/* Decorative_armour */
				|| itemID == 4511/* Decorative_helm */
				|| itemID == 4512/* Decorative_shield */
		) {
			return 15;
		}
		if (itemID == 1071/* Mithril_platelegs */
				|| itemID == 1085/* Mithril_plateskirt */
				|| itemID == 1109/* Mithril_chainbody */
				|| itemID == 1121/* Mithril_platebody */
				|| itemID == 1133/* Studded_body */
				|| itemID == 1143/* Mithril_med_helm */
				|| itemID == 1159/* Mithril_full_helm */
				|| itemID == 1181/* Mithril_sq_shield */
				|| itemID == 1197/* Mithril_kiteshield */
				|| itemID == 4089/* Mystic_hat */
				|| itemID == 4099/* Mystic_hat */
				|| itemID == 4109/* Mystic_hat */
				|| itemID == 4091/* Mystic_robe_top */
				|| itemID == 4101/* Mystic_robe_top */
				|| itemID == 4111/* Mystic_robe_top */
				|| itemID == 4093/* Mystic_robe_bottom */
				|| itemID == 4103/* Mystic_robe_bottom */
				|| itemID == 4113/* Mystic_robe_bottom */
				|| itemID == 4095/* Mystic_gloves */
				|| itemID == 4105/* Mystic_gloves */
				|| itemID == 4115/* Mystic_gloves */
				|| itemID == 4097/* Mystic_boots */
				|| itemID == 4107/* Mystic_boots */
				|| itemID == 4117/* Mystic_boots */
				|| itemID == 4127/* Mithril_boots */
				|| itemID == 4156/* Mirror_shield */
				|| itemID == 5574/* Initiate_helm */
				|| itemID == 5575/* Initiate_platemail */
				|| itemID == 5576/* Initiate_platelegs */
				|| itemID == 7362/* Studded_body_(g) */
				|| itemID == 7364/* Studded_body_(t) */
				|| itemID == 7398/* Enchanted_robe */
				|| itemID == 7399/* Enchanted_top */
				|| itemID == 7400/* Enchanted_hat */
		) {
			return 20;
		}
		if (itemID == 6916/* Infinity_Top */
				|| itemID == 6918/* Infinity_Hat */
				|| itemID == 6920/* Infinity_Boots */
				|| itemID == 6922/* Infinity_Gloves */
				|| itemID == 6924/* Infinity_Bottom */
		) {
			return 25;
		}
		if (itemID == 1073/* Adamant_platelegs */
				|| itemID == 1091/* Adamant_plateskirts */
				|| itemID == 1111/* Adamant_chainbody */
				|| itemID == 1123/* Adamant_platebody */
				|| itemID == 1145/* Adamant_med_helm */
				|| itemID == 1161/* Adamant_full_helm */
				|| itemID == 1183/* Adamant_sq_shield */
				|| itemID == 1199/* Adamant_kiteshield */
				|| itemID == 2599/* Adam_platebody_(t) */
				|| itemID == 2601/* Adam_platelegs_(t) */
				|| itemID == 2603/* Adam_kiteshield_(t) */
				|| itemID == 2605/* Adam_full_helm_(t) */
				|| itemID == 2607/* Adam_platebody_(g) */
				|| itemID == 2609/* Adam_platelegs_(g) */
				|| itemID == 2611/* Adam_kiteshield_(g) */
				|| itemID == 2613/* Adam_full_helm_(g) */
				|| itemID == 3474/* Adam_plateskirt_(t) */
				|| itemID == 3475/* Adam_plateskirt_(g) */
				|| itemID == 4129/* Adamant_boots */
				|| itemID == 6322/* Snakeskin_body */
				|| itemID == 6324/* Snakeskin_chaps */
				|| itemID == 6326/* Snakeskin_bandana */
				|| itemID == 6328/* Snakeskin_boots */
				|| itemID == 6330/* Snakeskin_v'brace */
				|| itemID == 7334/* Adam_kiteshield(h) */
				|| itemID == 7340/* Adam_kiteshield(h) */
				|| itemID == 7346/* Adam_kiteshield(h) */
				|| itemID == 7352/* Adam_kiteshield(h) */
				|| itemID == 7358/* Adam_kiteshield(h) */
		) {
			return 30;
		}
		if (itemID == 1079/* Rune_platelegs */
				|| itemID == 1093/* Rune_plateskirt */
				|| itemID == 1113/* Rune_chainbody */
				|| itemID == 1127/* Rune_platebody */
				|| itemID == 1135/* Dragonhide_body */
				|| itemID == 1147/* Rune_med_helm */
				|| itemID == 1163/* Rune_full_helm */
				|| itemID == 1185/* Rune_sq_shield */
				|| itemID == 1201/* Rune_kiteshield */
				|| itemID == 2499/* Dragonhide_body */
				|| itemID == 2501/* Dragonhide_body */
				|| itemID == 2503/* Dragonhide_body */
				|| itemID == 2615/* Rune_platebody_(g) */
				|| itemID == 2617/* Rune_platelegs_(g) */
				|| itemID == 2619/* Rune_full_helm_(g) */
				|| itemID == 2621/* Rune_kiteshield_(g) */
				|| itemID == 2623/* Rune_platebody_(t) */
				|| itemID == 2625/* Rune_platelegs_(t) */
				|| itemID == 2627/* Rune_full_helm_(t) */
				|| itemID == 2629/* Rune_kiteshield_(t) */
				|| itemID == 2653/* Zamorak_platebody */
				|| itemID == 2655/* Zamorak_platelegs */
				|| itemID == 2657/* Zamorak_full_helm */
				|| itemID == 2659/* Zamorak_kiteshield */
				|| itemID == 2661/* Saradomin_plate */
				|| itemID == 2663/* Saradomin_legs */
				|| itemID == 2665/* Saradomin_full */
				|| itemID == 2667/* Saradomin_kite */
				|| itemID == 2669/* Guthix_platebody */
				|| itemID == 2671/* Guthix_platelegs */
				|| itemID == 2673/* Guthix_full_helm */
				|| itemID == 2675/* Guthix_kiteshield */
				|| itemID == 3385/* Splitbark_helm */
				|| itemID == 3387/* Splitbark_body */
				|| itemID == 3389/* Splitbark_legs */
				|| itemID == 3391/* Splitbark_gauntlets */
				|| itemID == 3393/* Splitbark_greaves */
				|| itemID == 3476/* Rune_plateskirt_(g) */
				|| itemID == 3477/* Rune_plateskirt_(t) */
				|| itemID == 3478/* Zamorak_plateskirt */
				|| itemID == 3479/* Saradomin_skirt */
				|| itemID == 3480/* Guthix_plateskirt */
				|| itemID == 3481/* Gilded_platebody */
				|| itemID == 3483/* Gilded_platelegs */
				|| itemID == 3485/* Gilded_plateskirt */
				|| itemID == 3486/* Gilded_full_helm */
				|| itemID == 3488/* Gilded_kiteshield */
				|| itemID == 4131/* Rune_boots */
				|| itemID == 6128/* Rock-shell_helm */
				|| itemID == 6129/* Rock-shell_plate */
				|| itemID == 6130/* Rock-shell_legs */
				|| itemID == 6131/* Spined_helm */
				|| itemID == 6133/* Spined_body */
				|| itemID == 6135/* Spined_chaps */
				|| itemID == 6137/* Skeletal_helm */
				|| itemID == 6139/* Skeletal_top */
				|| itemID == 6141/* Skeletal_bottoms */
				|| itemID == 6143/* Spined_boots */
				|| itemID == 6145/* Rock-shell_boots */
				|| itemID == 6147/* Skeletal_boots */
				|| itemID == 6149/* Spined_gloves */
				|| itemID == 6151/* Rock-shell_gloves */
				|| itemID == 6153/* Skeletal_gloves */
				|| itemID == 7336/* Rune_kiteshield(h) */
				|| itemID == 7342/* Rune_kiteshield(h) */
				|| itemID == 7348/* Rune_kiteshield(h) */
				|| itemID == 7354/* Rune_kiteshield(h) */
				|| itemID == 7360/* Rune_kiteshield(h) */
				|| itemID == 7370/* D-hide_body(g) */
				|| itemID == 7372/* D-hide_body_(t) */
				|| itemID == 7374/* D-hide_body_(g) */
				|| itemID == 7376/* D-hide_body_(t) */
				|| itemID == 7462/* barrows_gloves */
		) {
			return 40;
		}
		if (itemID == 3749/* Archer_helm */
				|| itemID == 3751/* Berserker_helm */
				|| itemID == 3753/* Warrior_helm */
				|| itemID == 3755/* Farseer_helm */
		) {
			return 45;
		}
		if (itemID == 3122/* Granite_shield */
				|| itemID == 6809/* Granite_legs */
		) {
			return 50;
		}
		if (itemID == 10828) {
			return 55;
		}
		if (itemID == 1149/* Dragon_med_helm */
				|| itemID == 1187/* Dragon_sq_shield */
				|| itemID == 3140/* Dragon_chainbody */
				|| itemID == 4087/* Dragon_platelegs */
				|| itemID == 4585/* Dragon_plateskirt */
				|| itemID == 6524/* Toktz-ket-xil */
				|| itemID == 11732/* dragon_boots */
		) {
			return 60;
		}
		if (itemID == 4224/* New_crystal_shield */
				|| itemID == 4225/* Crystal_shield_full */
				|| itemID == 4226/* Crystal_shield_9/10 */
				|| itemID == 4227/* Crystal_shield_8/10 */
				|| itemID == 4228/* Crystal_shield_7/10 */
				|| itemID == 4229/* Crystal_shield_6/10 */
				|| itemID == 4230/* Crystal_shield_5/10 */
				|| itemID == 4231/* Crystal_shield_4/10 */
				|| itemID == 4232/* Crystal_shield_3/10 */
				|| itemID == 4233/* Crystal_shield_2/10 */
				|| itemID == 4234/* Crystal_shield_1/10 */
				|| itemID == 4708/* Ahrims_hood */
				|| itemID == 4712/* Ahrims_robetop */
				|| itemID == 4714/* Ahrims_robeskirt */
				|| itemID == 4716/* Dharoks_helm */
				|| itemID == 4720/* Dharoks_platebody */
				|| itemID == 4722/* Dharoks_platelegs */
				|| itemID == 4724/* Guthans_helm */
				|| itemID == 4728/* Guthans_platebody */
				|| itemID == 4730/* Guthans_chainskirt */
				|| itemID == 4732/* Karils_coif */
				|| itemID == 4736/* Karils_leathertop */
				|| itemID == 4738/* Karils_leatherskirt */
				|| itemID == 4745/* Torags_helm */
				|| itemID == 4749/* Torags_platebody */
				|| itemID == 4751/* Torags_platelegs */
				|| itemID == 4753/* Veracs_helm */
				|| itemID == 4757/* Veracs_brassard */
				|| itemID == 4759/* Veracs_plateskirt */
				|| itemID == 4856/* Ahrims_hood_100 */
				|| itemID == 4857/* Ahrims_hood_75 */
				|| itemID == 4858/* Ahrims_hood_50 */
				|| itemID == 4859/* Ahrims_hood_25 */
				|| itemID == 4868/* Ahrims_top_100 */
				|| itemID == 4869/* Ahrims_top_75 */
				|| itemID == 4870/* Ahrims_top_50 */
				|| itemID == 4871/* Ahrims_top_25 */
				|| itemID == 4874/* Ahrims_skirt_100 */
				|| itemID == 4875/* Ahrims_skirt_75 */
				|| itemID == 4876/* Ahrims_skirt_50 */
				|| itemID == 4877/* Ahrims_skirt_25 */
				|| itemID == 4880/* Dharoks_helm_100 */
				|| itemID == 4881/* Dharoks_helm_75 */
				|| itemID == 4882/* Dharoks_helm_50 */
				|| itemID == 4883/* Dharoks_helm_25 */
				|| itemID == 4892/* Dharoks_body_100 */
				|| itemID == 4893/* Dharoks_body_75 */
				|| itemID == 4894/* Dharoks_body_50 */
				|| itemID == 4895/* Dharoks_body_25 */
				|| itemID == 4898/* Dharoks_legs_100 */
				|| itemID == 4899/* Dharoks_legs_75 */
				|| itemID == 4900/* Dharoks_legs_50 */
				|| itemID == 4901/* Dharoks_legs_25 */
				|| itemID == 4904/* Guthans_helm_100 */
				|| itemID == 4905/* Guthans_helm_75 */
				|| itemID == 4906/* Guthans_helm_50 */
				|| itemID == 4907/* Guthans_helm_25 */
				|| itemID == 4916/* Guthans_body_100 */
				|| itemID == 4917/* Guthans_body_75 */
				|| itemID == 4918/* Guthans_body_50 */
				|| itemID == 4919/* Guthans_body_25 */
				|| itemID == 4922/* Guthans_skirt_100 */
				|| itemID == 4923/* Guthans_skirt_75 */
				|| itemID == 4924/* Guthans_skirt_50 */
				|| itemID == 4925/* Guthans_skirt_25 */
				|| itemID == 4928/* Karils_coif_100 */
				|| itemID == 4929/* Karils_coif_75 */
				|| itemID == 4930/* Karils_coif_50 */
				|| itemID == 4931/* Karils_coif_25 */
				|| itemID == 4940/* Karils_top_100 */
				|| itemID == 4941/* Karils_top_75 */
				|| itemID == 4942/* Karils_top_50 */
				|| itemID == 4943/* Karils_top_25 */
				|| itemID == 4946/* Karils_skirt_100 */
				|| itemID == 4947/* Karils_skirt_75 */
				|| itemID == 4948/* Karils_skirt_50 */
				|| itemID == 4949/* Karils_skirt_25 */
				|| itemID == 4952/* Torags_helm_100 */
				|| itemID == 4953/* Torags_helm_75 */
				|| itemID == 4954/* Torags_helm_50 */
				|| itemID == 4955/* Torags_helm_25 */
				|| itemID == 4964/* Torags_body_100 */
				|| itemID == 4965/* Torags_body_75 */
				|| itemID == 4966/* Torags_body_50 */
				|| itemID == 4967/* Torags_body_25 */
				|| itemID == 4970/* Torags_legs_100 */
				|| itemID == 4971/* Torags_legs_75 */
				|| itemID == 4972/* Torags_legs_50 */
				|| itemID == 4973/* Torags_legs_25 */
				|| itemID == 4976/* Veracs_helm_100 */
				|| itemID == 4977/* Veracs_helm_75 */
				|| itemID == 4978/* Veracs_helm_50 */
				|| itemID == 4979/* Veracs_helm_25 */
				|| itemID == 4988/* Veracs_top_100 */
				|| itemID == 4989/* Veracs_top_75 */
				|| itemID == 4990/* Veracs_top_50 */
				|| itemID == 4991/* Veracs_top_25 */
				|| itemID == 4994/* Veracs_skirt_100 */
				|| itemID == 4995/* Veracs_skirt_75 */
				|| itemID == 4996/* Veracs_skirt_50 */
				|| itemID == 4997/* Veracs_skirt_25 */
		) {
			return 70;
		}
		if (itemID == 11284 /* dragon fire shield dfs */
				|| itemID == 11728 /* bandos boots */
				|| itemID == 11724 /* bandos plate */
				|| itemID == 11720 /* ardrmyl plate */
				|| itemID == 11722 /* ardrmyl legs */
				|| itemID == 11726 /* bandos legs */
		) {
			return 75;
		}
		return 1;
	}

	public static int ranged(int itemID) {

		if (itemID == 800/* Bronze_thrownaxe */
				|| itemID == 801/* Iron_thrownaxe */
				|| itemID == 806/* Bronze_dart */
				|| itemID == 807/* Iron_dart */
				|| itemID == 812/* Bronze_dart(p) */
				|| itemID == 813/* Iron_dart(p) */
				|| itemID == 825/* Bronze_javelin */
				|| itemID == 826/* Iron_javelin */
				|| itemID == 831/* Bronze_javelin(p) */
				|| itemID == 832/* Iron_javelin(p) */
				|| itemID == 839/* Longbow */
				|| itemID == 841/* Shortbow */
				|| itemID == 863/* Iron_knife */
				|| itemID == 864/* Bronze_knife */
				|| itemID == 870/* Bronze_knife(p) */
				|| itemID == 871/* Iron_knife(p) */
				|| itemID == 882/* Bronze_arrow */
				|| itemID == 883/* Bronze_arrow(p) */
				|| itemID == 884/* Iron_arrow */
				|| itemID == 885/* Iron_arrow(p) */
				|| itemID == 942/* Bronze_fire_arrows */
				|| itemID == 2532/* Iron_fire_arrows */
				|| itemID == 2533/* Iron_fire_arrows */
				|| itemID == 4773/* Bronze_brutal */
				|| itemID == 4778/* Iron_brutal */
				|| itemID == 5616/* Bronze_arrow(+) */
				|| itemID == 5617/* Iron_arrow(+) */
				|| itemID == 5622/* Bronze_arrow(s) */
				|| itemID == 5623/* Iron_arrow(s) */
				|| itemID == 5628/* Bronze_dart(+) */
				|| itemID == 5629/* Iron_dart(+) */
				|| itemID == 5635/* Bronze_dart(s) */
				|| itemID == 5636/* Iron_dart(s) */
				|| itemID == 5642/* Bronze_javelin(+) */
				|| itemID == 5643/* Iron_javelin(+) */
				|| itemID == 5648/* Bronze_javelin(s) */
				|| itemID == 5649/* Iron_javelin(s) */
				|| itemID == 5654/* Bronze_knife(+) */
				|| itemID == 5655/* Iron_knife(+) */
				|| itemID == 5661/* Bronze_knife(s) */
				|| itemID == 5662/* Iron_knife(s) */
		) {
			return 1;
		}
		if (itemID == 802/* Steel_thrownaxe */
				|| itemID == 808/* Steel_dart */
				|| itemID == 814/* Steel_dart(p) */
				|| itemID == 827/* Steel_javelin */
				|| itemID == 833/* Steel_javelin(p) */
				|| itemID == 843/* Oak_shortbow */
				|| itemID == 845/* Oak_longbow */
				|| itemID == 865/* Steel_knife */
				|| itemID == 872/* Steel_knife(p) */
				|| itemID == 886/* Steel_arrow */
				|| itemID == 887/* Steel_arrow(p) */
				|| itemID == 2534/* Steel_fire_arrows */
				|| itemID == 2535/* Steel_fire_arrows */
				|| itemID == 4236/* Oak_longbow */
				|| itemID == 4783/* Steel_brutal */
				|| itemID == 5618/* Steel_arrow(+) */
				|| itemID == 5624/* Steel_arrow(s) */
				|| itemID == 5630/* Steel_dart(+) */
				|| itemID == 5637/* Steel_dart(s) */
				|| itemID == 5644/* Steel_javelin(+) */
				|| itemID == 5650/* Steel_javelin(s) */
				|| itemID == 5656/* Steel_knife(+) */
				|| itemID == 5663/* Steel_knife(s) */
		) {
			return 5;
		}
		if (itemID == 869/* Black_knife */
				|| itemID == 874/* Black_knife(p) */
				|| itemID == 3093/* Black_dart */
				|| itemID == 3094/* Black_dart(p) */
				|| itemID == 4788/* Black_brutal */
				|| itemID == 5631/* Black_dart(+) */
				|| itemID == 5638/* Black_dart(s) */
				|| itemID == 5658/* Black_knife(+) */
				|| itemID == 5665/* Black_knife(s) */
		) {
			return 10;
		}
		if (itemID == 803/* Mithril_thrownaxe */
				|| itemID == 809/* Mithril_dart */
				|| itemID == 815/* Mithril_dart(p) */
				|| itemID == 828/* Mithril_javelin */
				|| itemID == 834/* Mithril_javelin(p) */
				|| itemID == 847/* Willow_longbow */
				|| itemID == 849/* Willow_shortbow */
				|| itemID == 866/* Mithril_knife */
				|| itemID == 873/* Mithril_knife(p) */
				|| itemID == 888/* Mithril_arrow */
				|| itemID == 889/* Mithril_arrow(p) */
				|| itemID == 1133/* Studded_body */
				|| itemID == 1169/* Coif */
				|| itemID == 2536/* Mithril_fire_arrows */
				|| itemID == 2537/* Mithril_fire_arrows */
				|| itemID == 4793/* Mithril_brutal */
				|| itemID == 5619/* Mithril_arrow(+) */
				|| itemID == 5625/* Mithril_arrow(s) */
				|| itemID == 5632/* Mithril_dart(+) */
				|| itemID == 5639/* Mithril_dart(s) */
				|| itemID == 5645/* Mithril_javelin(+) */
				|| itemID == 5651/* Mithril_javelin(s) */
				|| itemID == 5657/* Mithril_knife(+) */
				|| itemID == 5664/* Mithril_knife(s) */
				|| itemID == 7362/* Studded_body_(g) */
				|| itemID == 7364/* Studded_body_(t) */
				|| itemID == 7366/* Studded_chaps_(g) */
				|| itemID == 7368/* Studded_chaps_(t) */
		) {
			return 20;
		}
		if (itemID == 804/* Adamnt_thrownaxe */
				|| itemID == 810/* Adamant_dart */
				|| itemID == 816/* Adamant_dart(p) */
				|| itemID == 829/* Adamant_javelin */
				|| itemID == 835/* Adamant_javelin(p) */
				|| itemID == 851/* Maple_longbow */
				|| itemID == 853/* Maple_shortbow */
				|| itemID == 867/* Adamant_knife */
				|| itemID == 875/* Adamant_knife(p) */
				|| itemID == 890/* Adamant_arrow */
				|| itemID == 891/* Adamant_arrow(p) */
				|| itemID == 2538/* Adamnt_fire_arrows */
				|| itemID == 2539/* Adamnt_fire_arrows */
				|| itemID == 2866/* Ogre_arrow */
				|| itemID == 2883/* Ogre_bow */
				|| itemID == 4798/* Adamant_brutal */
				|| itemID == 4827/* Comp_ogre_bow */
				|| itemID == 5620/* Adamant_arrow(+) */
				|| itemID == 5626/* Adamant_arrow(s) */
				|| itemID == 5633/* Adamant_dart(+) */
				|| itemID == 5640/* Adamant_dart(s) */
				|| itemID == 5646/* Adamant_javelin(+) */
				|| itemID == 5652/* Adamant_javelin(s) */
				|| itemID == 5659/* Adamant_knife(+) */
				|| itemID == 5666/* Adamant_knife(s) */
				|| itemID == 6322/* Snakeskin_body */
				|| itemID == 6324/* Snakeskin_chaps */
				|| itemID == 6326/* Snakeskin_bandana */
				|| itemID == 6328/* Snakeskin_boots */
				|| itemID == 6330/* Snakeskin_v'brace */
		) {
			return 30;
		}
		if (itemID == 805/* Rune_thrownaxe */
				|| itemID == 811/* Rune_dart */
				|| itemID == 817/* Rune_dart(p) */
				|| itemID == 830/* Rune_javelin */
				|| itemID == 836/* Rune_javelin(p) */
				|| itemID == 6131/* Spined_helm */
				|| itemID == 6133/* Spined_body */
				|| itemID == 6135/* Spined_chaps */
				|| itemID == 6143/* Spined_boots */
				|| itemID == 6149/* Spined_gloves */
				|| itemID == 855/* Yew_longbow */
				|| itemID == 857/* Yew_shortbow */
				|| itemID == 868/* Rune_knife */
				|| itemID == 876/* Rune_knife(p) */
				|| itemID == 892/* Rune_arrow */
				|| itemID == 893/* Rune_arrow(p) */
				|| itemID == 1065/* Dragon_vambraces */
				|| itemID == 1099/* Dragonhide_chaps */
				|| itemID == 1135/* Dragonhide_body */
				|| itemID == 2540/* Rune_fire_arrows */
				|| itemID == 2541/* Rune_fire_arrows */
				|| itemID == 2577/* Ranger_boots */
				|| itemID == 2581/* Robin_hood_hat */
				|| itemID == 4803/* Rune_brutal */
				|| itemID == 5621/* Rune_arrow(+) */
				|| itemID == 5627/* Rune_arrow(s) */
				|| itemID == 5634/* Rune_dart(+) */
				|| itemID == 5641/* Rune_dart(s) */
				|| itemID == 5647/* Rune_javelin(+) */
				|| itemID == 5653/* Rune_javelin(s) */
				|| itemID == 5660/* Rune_knife(+) */
				|| itemID == 5667/* Rune_knife(s) */
				|| itemID == 7378/* D-hide_chaps_(g) */
				|| itemID == 7380/* D-hide_chaps_(t) */
				|| itemID == 7382/* D-hide_chaps_(g) */
				|| itemID == 7384/* D-hide_chaps_(t) */
		) {
			return 40;
		}
		if (itemID == 859/* Magic_longbow */
				|| itemID == 861/* Magic_shortbow */
				|| itemID == 2487/* Dragon_vambraces */
				|| itemID == 2493/* Dragonhide_chaps */
				|| itemID == 2499/* Dragonhide_body */
				|| itemID == 4160/* Broad_arrows */
				|| itemID == 6724/* Seercull */
		) {
			return 50;
		}
		if (itemID == 2489/* Dragon_vambraces */
				|| itemID == 2495/* Dragonhide_chaps */
				|| itemID == 2501/* Dragonhide_body */
				|| itemID == 6522/* Toktz-xil-ul */
		) {
			return 60;
		}
		if (itemID == 2491/* Dragon_vambraces */
				|| itemID == 2497/* Dragonhide_chaps */
				|| itemID == 2503/* Dragonhide_body */
				|| itemID == 4212/* New_crystal_bow */
				|| itemID == 4214/* Crystal_bow_full */
				|| itemID == 4215/* Crystal_bow_9/10 */
				|| itemID == 4216/* Crystal_bow_8/10 */
				|| itemID == 4217/* Crystal_bow_7/10 */
				|| itemID == 4218/* Crystal_bow_6/10 */
				|| itemID == 4219/* Crystal_bow_5/10 */
				|| itemID == 4220/* Crystal_bow_4/10 */
				|| itemID == 4221/* Crystal_bow_3/10 */
				|| itemID == 4222/* Crystal_bow_2/10 */
				|| itemID == 4223/* Crystal_bow_1/10 */
				|| itemID == 4734/* Karils_crossbow */
				|| itemID == 4934/* Karils_x-bow_100 */
				|| itemID == 4935/* Karils_x-bow_75 */
				|| itemID == 4936/* Karils_x-bow_50 */
				|| itemID == 4937/* Karils_x-bow_25 */
		) {
			return 70;
		}
		return 1;
	}

	public static int magic(int itemID) {
		if (itemID == 2579/* Wizard_boots */
		) {
			return 20;
		}
		if (itemID == 6215/* Broodoo_shield_(10) */
				|| itemID == 6217/* Broodoo_shield_(9) */
				|| itemID == 6219/* Broodoo_shield_(8) */
				|| itemID == 6221/* Broodoo_shield_(7) */
				|| itemID == 6223/* Broodoo_shield_(6) */
				|| itemID == 6225/* Broodoo_shield_(5) */
				|| itemID == 6227/* Broodoo_shield_(4) */
				|| itemID == 6229/* Broodoo_shield_(3) */
				|| itemID == 6231/* Broodoo_shield_(2) */
				|| itemID == 6233/* Broodoo_shield_(1) */
				|| itemID == 6235/* Broodoo_shield */
				|| itemID == 6237/* Broodoo_shield_(10) */
				|| itemID == 6239/* Broodoo_shield_(9) */
				|| itemID == 6241/* Broodoo_shield_(8) */
				|| itemID == 6243/* Broodoo_shield_(7) */
				|| itemID == 6245/* Broodoo_shield_(6) */
				|| itemID == 6247/* Broodoo_shield_(5) */
				|| itemID == 6249/* Broodoo_shield_(4) */
				|| itemID == 6251/* Broodoo_shield_(3) */
				|| itemID == 6253/* Broodoo_shield_(2) */
				|| itemID == 6255/* Broodoo_shield_(1) */
				|| itemID == 6257/* Broodoo_shield */
				|| itemID == 6259/* Broodoo_shield_(10) */
				|| itemID == 6261/* Broodoo_shield_(9) */
				|| itemID == 6263/* Broodoo_shield_(8) */
				|| itemID == 6265/* Broodoo_shield_(7) */
				|| itemID == 6267/* Broodoo_shield_(6) */
				|| itemID == 6269/* Broodoo_shield_(5) */
				|| itemID == 6271/* Broodoo_shield_(4) */
				|| itemID == 6273/* Broodoo_shield_(3) */
				|| itemID == 6275/* Broodoo_shield_(2) */
				|| itemID == 6277/* Broodoo_shield_(1) */
				|| itemID == 6279/* Broodoo_shield */
		) {
			return 25;
		}
		if (itemID == 4089/* Mystic_hat */
				|| itemID == 4099/* Mystic_hat */
				|| itemID == 4109/* Mystic_hat */
				|| itemID == 4091/* Mystic_robe_top */
				|| itemID == 4101/* Mystic_robe_top */
				|| itemID == 4111/* Mystic_robe_top */
				|| itemID == 4093/* Mystic_robe_bottom */
				|| itemID == 4103/* Mystic_robe_bottom */
				|| itemID == 4113/* Mystic_robe_bottom */
				|| itemID == 4095/* Mystic_gloves */
				|| itemID == 4105/* Mystic_gloves */
				|| itemID == 4115/* Mystic_gloves */
				|| itemID == 4097/* Mystic_boots */
				|| itemID == 4107/* Mystic_boots */
				|| itemID == 4117/* Mystic_boots */
				|| itemID == 6137/* Skeletal_helm */
				|| itemID == 6139/* Skeletal_top */
				|| itemID == 6141/* Skeletal_bottoms */
				|| itemID == 6147/* Skeletal_boots */
				|| itemID == 6153/* Skeletal_gloves */
				|| itemID == 7398/* Enchanted_robe */
				|| itemID == 7399/* Enchanted_top */
				|| itemID == 7400/* Enchanted_hat */
		) {
			return 40;
		}
		if (itemID == 3385/* Splitbark_helm */
				|| itemID == 3387/* Splitbark_body */
				|| itemID == 3389/* Splitbark_legs */
				|| itemID == 3391/* Splitbark_gauntlets */
				|| itemID == 3393/* Splitbark_greaves */
				|| itemID == 6908/* Beginner_wand */
		) {
			return 45;
		}
		if (itemID == 4170/* Slayer's_staff */
				|| itemID == 4675/* Ancient_staff */
				|| itemID == 6910/* Apprentice_wand */
				|| itemID == 6916/* Infinity_Top */
				|| itemID == 6918/* Infinity_Hat */
				|| itemID == 6920/* Infinity_Boots */
				|| itemID == 6922/* Infinity_Gloves */
				|| itemID == 6924/* Infinity_Bottom */
		) {
			return 50;
		}
		if (itemID == 6912/* Teacher_wand */
		) {
			return 55;
		}
		if (itemID == 2412/* Saradomin_cape */
				|| itemID == 2413/* Guthix_cape */
				|| itemID == 2414/* Zamorak_cape */
				|| itemID == 2415/* Saradomin_staff */
				|| itemID == 2416/* Guthix_staff */
				|| itemID == 2417/* Zamorak_staff */
				|| itemID == 6526/* Toktz-mej-tal */
				|| itemID == 6914/* Master_wand */
		) {
			return 60;
		}
		if (itemID == 4708/* Ahrims_hood */
				|| itemID == 4710/* Ahrims_staff */
				|| itemID == 4712/* Ahrims_robetop */
				|| itemID == 4714/* Ahrims_robeskirt */
				|| itemID == 4856/* Ahrims_hood_100 */
				|| itemID == 4857/* Ahrims_hood_75 */
				|| itemID == 4858/* Ahrims_hood_50 */
				|| itemID == 4859/* Ahrims_hood_25 */
				|| itemID == 4862/* Ahrims_staff_100 */
				|| itemID == 4863/* Ahrims_staff_75 */
				|| itemID == 4864/* Ahrims_staff_50 */
				|| itemID == 4865/* Ahrims_staff_25 */
				|| itemID == 4868/* Ahrims_top_100 */
				|| itemID == 4869/* Ahrims_top_75 */
				|| itemID == 4870/* Ahrims_top_50 */
				|| itemID == 4871/* Ahrims_top_25 */
				|| itemID == 4874/* Ahrims_skirt_100 */
				|| itemID == 4875/* Ahrims_skirt_75 */
				|| itemID == 4876/* Ahrims_skirt_50 */
				|| itemID == 4877/* Ahrims_skirt_25 */
		) {
			return 70;
		}
		return 1;
	}

}