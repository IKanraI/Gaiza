package UserCommands;

import java.util.List;

import Command.Command;
import lombok.Getter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;

public class Roll extends Command {
	@Getter
	public static String help = "Does a roll for a specified amount [prefix]roll [roll amount]";
	
	public Roll(DiscordApi api) {
		super(api);
		api.addMessageCreateListener(event ->
			rollOrFlip(super.getChannel(), super.getArgs()));
	}

	private void rollOrFlip(TextChannel channel, List<String> args) {
		if (!onCommand()) {
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
