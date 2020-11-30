package UserCommands;

import java.awt.Color;
import java.io.File;
import java.util.List;
import java.util.Random;

import Command.Command;
import lombok.Getter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import Management.Keywords;

public class Roll extends Command
{
	@Getter
	public static String help = "Does a roll for a specified amount [flip | 6 | 10 | 20]";
	private String commandCoin = "flip";
	private String commandRoll = "roll";
	private String command6 = "6";
	private String command10 = "10";
	private String command20 = "20";
	
	public Roll(DiscordApi api)
	{
		super(api);
		api.addMessageCreateListener(event -> {
			rollOrFlip(api, super.getChannel(), super.getMessage(), super.getArgs());
		});
	}

	private void rollOrFlip(DiscordApi api, TextChannel channel, Message message, List<String> args) {
		if (!onCommand(api, channel, message, args)) {
			return;
		}
		try {
			Integer.parseInt(args.get(0));
		} catch (NumberFormatException n) {
			channel.sendMessage("Please input a valid number");
			return;
		}
		if (args.size() == 0 || args.get(0).trim().equalsIgnoreCase("")) {
			channel.sendMessage("Please enter a specific number to roll");
			return;
		}
		if (Integer.parseInt(args.get(0)) < 2) {
			channel.sendMessage("Please enter a number greater than 1");
			return;
		}

		String msg;
		if (Integer.parseInt(args.get(0)) == 2) {
			if (Math.round(Math.random() * 2) + 1 == 1) {
				msg = "The coin landed heads";
			} else {
				msg = "The coin landed tails";
			}
		} else {
			msg = "Rolled value: " + Math.round(Math.random() * Integer.parseInt(args.get(0)));
		}

		channel.sendMessage(msg).exceptionally(e -> {
			e.printStackTrace();
			return null;
		});

	}
}
