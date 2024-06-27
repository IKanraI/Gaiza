package adminCommands;

import command.Command;
import model.common.Servers;
import lombok.Getter;
import org.javacord.api.DiscordApi;

import model.InitDatabase;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;

import java.util.List;

public class Welcome extends Command {
	@Getter public static String help = "Welcome module. Make sure to set the channel and message before enabling the module. Use [prefix] welcome message|channel|enable|disable to modify the module If you want to mention a user please use <<mention>>";
	
	public Welcome(DiscordApi api) {
		super(api);
		api.addMessageCreateListener(event ->
				welcomeManager(super.getServer(), super.getMessage(), super.getChannel(), super.getArgs()));
	}

	private void welcomeManager(Server server, Message message, TextChannel channel, List<String> args) {
		if (!onAdminCommand()) {
			return;
		}
		if (args.size() < 1) {
			channel.sendMessage("Please send a message with an argument for a value to change: [enable|disable|message|channel|status]");
			return;
		}

		Servers instance = InitDatabase.getData().get(server.getIdAsString());
		switch (args.get(0)) {
			case "enable": {
				if (Boolean.parseBoolean(instance.getWEnabled())) {
					channel.sendMessage("The welcome is already enabled >:(");
					return;
				}
				if (!instance.getWChannel().equals("") || !instance.getWMsg().equals("")) {
					instance.setWEnabled("true");
					message.addReaction("\u2705");
					channel.sendMessage("The welcome module has been enabled!");
				} else {
					channel.sendMessage("Please set the message and channel before enabling the welcome module");
					return;
				}
				break;
			}
			case "disable": {
				if (Boolean.parseBoolean(instance.getWEnabled())) {
					instance.setWEnabled("false");
					message.addReaction("\u2705");
					channel.sendMessage("The welcome module has been disabled!");
				} else {
					channel.sendMessage("The welcome module is already disabled >:(");
					return;
				}
				break;
			}
			case "message": {
				if (args.size() == 1) {
					channel.sendMessage("Please include a message to change the welcome message to. The current message is: [" + instance.getWMsg() + "]");
					return;
				} else {
					StringBuilder msg = new StringBuilder();
					for (String s : args) {
						msg.append(s + " ");
					}

					msg.replace(0, 8, "");
					instance.setWMsg(msg.toString().trim());
					message.addReaction("\u2705");
					channel.sendMessage("Welcome message was set for the server!");
				}
				break;
			}
			case "channel": {
				if (args.size() == 1) {
					channel.sendMessage("Please mention a channel to have welcome messages sent to. The current channel is [<#" + instance.getWChannel() + ">]");
					return;
				}
				try {
					instance.setWChannel(message.getMentionedChannels().get(0).getIdAsString());
					message.addReaction("\u2705");
					channel.sendMessage("The welcome channel has been set!");
				} catch (Exception e) {
					channel.sendMessage("Please make sure to mention a channel with the id by either using a #[channel] or <#[id]>");
					return;
				}
				break;
			}
			case "status": {
				channel.sendMessage("The welcome module for this server is: Enabled: ["
				+ InitDatabase.getData().get(server.getIdAsString()).getWEnabled()
						+ "] Welcome channel: <#" + InitDatabase.getData().get(server.getIdAsString()).getWChannel()
						+ "> Welcome Message: [" + InitDatabase.getData().get(server.getIdAsString()).getWMsg() + "]");
				return;
			}
			default:
				channel.sendMessage("Please send a correct parameter for modifying the welcome channel: enabled, message, status, or channel");
				return;
		}

		InitDatabase.saveDatabase();
	}
}

