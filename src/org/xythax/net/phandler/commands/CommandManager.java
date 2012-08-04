package org.xythax.net.phandler.commands;

import java.util.HashMap;
import java.util.Map;

import org.xythax.model.Client;
import org.xythax.net.phandler.commands.impl.AddNPC;
import org.xythax.net.phandler.commands.impl.AllToMe;
import org.xythax.net.phandler.commands.impl.Bank;
import org.xythax.net.phandler.commands.impl.Char;
import org.xythax.net.phandler.commands.impl.Gfx;
import org.xythax.net.phandler.commands.impl.Interface;
import org.xythax.net.phandler.commands.impl.Jail;
import org.xythax.net.phandler.commands.impl.Kick;
import org.xythax.net.phandler.commands.impl.Level;
import org.xythax.net.phandler.commands.impl.MyPosition;
import org.xythax.net.phandler.commands.impl.ResetAll;
import org.xythax.net.phandler.commands.impl.ResetNPC;
import org.xythax.net.phandler.commands.impl.ResetNPCDrops;
import org.xythax.net.phandler.commands.impl.Runes;
import org.xythax.net.phandler.commands.impl.SaveAll;
import org.xythax.net.phandler.commands.impl.SetEmote;
import org.xythax.net.phandler.commands.impl.SetLevel;
import org.xythax.net.phandler.commands.impl.SetNPC;
import org.xythax.net.phandler.commands.impl.Special;
import org.xythax.net.phandler.commands.impl.TempNPC;

public class CommandManager {
	private static Map<String, Command> commandMap = new HashMap<String, Command>();

	public static void loadAllCommands() {
		commandMap.put("runes", new Runes());
		commandMap.put("alltome", new AllToMe());
		commandMap.put("jail", new Jail());
		commandMap.put("resetnpcdrops", new ResetNPCDrops());
		commandMap.put("setlevel", new SetLevel());
		commandMap.put("spec", new Special());
		commandMap.put("setnpc", new SetNPC());
		commandMap.put("npcreset", new ResetNPC());
		commandMap.put("resetnpc", new ResetNPC());
		commandMap.put("tempnpc", new TempNPC());
		commandMap.put("kick", new Kick());
		commandMap.put("bank", new Bank());
		commandMap.put("char", new Char());
		commandMap.put("saveall", new SaveAll());
		commandMap.put("emote", new SetEmote());
		commandMap.put("mypos", new MyPosition());
		commandMap.put("level", new Level());
		commandMap.put("gfx", new Gfx());
		commandMap.put("resetall", new ResetAll());
		commandMap.put("interface", new Interface());
		commandMap.put("addnpc", new AddNPC());
		System.out.println("Loaded " + commandMap.size() + " custom commands.");
	}

	public static void execute(Client client, String command) {
		String name = "";
		if (command.indexOf(' ') > -1)
			name = command.substring(0, command.indexOf(' '));
		else
			name = command;
		name = name.toLowerCase();
		if (commandMap.get(name) != null)
			commandMap.get(name).execute(client, command);
		else if (name.length() == 0)
			client.getActionSender().sendMessage("The command does not exist.");
		else
			client.getActionSender().sendMessage(
					"The command " + name + " does not exist.");
	}
}