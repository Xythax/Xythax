package org.xythax.content.skills;

/**
 * 
 * @author killamess
 * 
 */
public class PotionMixing {

	/*
	 * public static final int VIAL = 227;
	 * 
	 * public static int[][] mixtures = { { 249, 221, 91, 2428, 3, 25}, //
	 * attack potion { 251, 235, 93, 175, 5, (int) 37.5}, // antipoison potion {
	 * 253, 225, 95, 133, 12, 50}, // strength potion { 255, 223, 97, 2430, 22,
	 * (int) 62.5}, // Restore_potion { 257, 231, 99, 2434, 38, (int) 87.5}, //
	 * prayer potion { 259, 221, 101, 2436, 45, 100}, // super attack { 261,
	 * 2970, 103, 3016, 52, (int) 117.5},// Super_energy { 263, 225, 105, 2440,
	 * 55, 125}, //super strength { 265, 239, 107, 2442, 66, 150}, //super def {
	 * 267, 245, 109, 2444, 72, (int) 162.5},//ranging potion { 269, 247, 111,
	 * 2450, 78, 175}, //zamorak brew };
	 * 
	 * public static void makeUnfinished(Client client, int itemUsed, int
	 * onItem) {
	 * 
	 * if (itemUsed == VIAL) { for (int i = 0; i < mixtures.length; i++) { if
	 * (mixtures[i][0] == onItem) { if (client.playerLevel[Constants.HERBLORE] <
	 * mixtures[i][4]) {
	 * client.getActionSender().sendMessage("You need a herblore level of "+
	 * mixtures[i][4] +" to make this potion."); return; } if
	 * (client.getActionAssistant().isItemInBag(mixtures[i][0]) &&
	 * client.getActionAssistant().isItemInBag(VIAL)) {
	 * client.getActionAssistant().deleteItem(mixtures[i][0], 1);
	 * client.getActionAssistant().deleteItem(VIAL, 1);
	 * client.getActionSender().sendInventoryItem(mixtures[i][2], 1); String
	 * herbName =
	 * GameEngine.getItemManager().getItemDefinition(mixtures[i][0]).getName();
	 * client.getActionSender().sendMessage("You mix the "+
	 * herbName.toLowerCase() +" with vial of water."); break; } } } } } public
	 * static void makeFinished(Client client, int item) {
	 * 
	 * for (int i = 0; i < mixtures.length; i++) { if (mixtures[i][2] == item) {
	 * if (client.playerLevel[Constants.HERBLORE] < mixtures[i][4]) {
	 * client.getActionSender().sendMessage("You need a herblore level of "+
	 * mixtures[i][4] +" to make this potion."); return; } if
	 * (client.getActionAssistant().isItemInBag(mixtures[i][1]) &&
	 * client.getActionAssistant().isItemInBag(mixtures[i][2])) {
	 * client.getActionAssistant().deleteItem(mixtures[i][1], 1);
	 * client.getActionAssistant().deleteItem(mixtures[i][2], 1);
	 * client.getActionSender().sendInventoryItem(mixtures[i][3], 1); String
	 * potionName =
	 * GameEngine.getItemManager().getItemDefinition(mixtures[i][3]).getName();
	 * client.getActionSender().sendMessage("You make a "+
	 * potionName.toLowerCase() +".");
	 * client.getActionAssistant().addSkillXP(mixtures[i][5] *
	 * Constants.SKILL_EXPERIENCE_MULTIPLIER, Constants.HERBLORE); break; } } }
	 * } public static boolean isExtraMixture(Client client, int item) { for
	 * (int i = 0; i < mixtures.length; i++) { if (mixtures[i][1] == item) {
	 * return true; } } return false; } public static boolean
	 * isUnFinishedPotion(Client client, int item) { for (int i = 0; i <
	 * mixtures.length; i++) { if (mixtures[i][2] == item) { return true; } }
	 * return false; }
	 */
}