package userCommands;

import java.util.List;

import command.Command;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import util.GaizaUtil;

public class Roll implements SlashCommandCreateListener {

	@Getter public static String help = "Does a roll for a specified amount [prefix]roll [roll amount]";
	private static String command = "roll";
	private static String command2 = "flip";

	@Override
	public void onSlashCommandCreate(SlashCommandCreateEvent event) {
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		if (StringUtils.equalsIgnoreCase(interaction.getCommandName(), command))
			rollTheDice(GaizaUtil.getPassedArgument(interaction.getArguments()), interaction);
		else if (StringUtils.equalsIgnoreCase(interaction.getCommandName(), command2))
			flipTheCoin(interaction);
	}

	private void flipTheCoin(SlashCommandInteraction interaction) {
		String message = "";
		if (Math.round(Math.random() * 2) + 1 == 1)
			message = "The coin landed heads";
		else
			message = "The coin landed tails";

		interaction.createImmediateResponder()
				.setContent(message)
				.respond()
				.exceptionally(e -> {
					interaction.createImmediateResponder()
							.setContent("Something broke :(")
							.respond();
					return null;
				});
	}

	private void rollTheDice(String rolledNumber, SlashCommandInteraction interaction) {
		try {
			Integer.parseInt(rolledNumber);
		} catch (NumberFormatException n) {
			interaction.createImmediateResponder()
					.setFlags(MessageFlag.EPHEMERAL)
					.setContent("Please enter a valid number")
					.respond();
			return;
		}
		if (Integer.parseInt(rolledNumber) < 2) {
			interaction.createImmediateResponder()
					.setFlags(MessageFlag.EPHEMERAL)
					.setContent("Please enter a number above 1")
					.respond();
			return;
		}

		interaction.createImmediateResponder()
				.setContent("Rolled value: " + Math.round(Math.random() * Integer.parseInt(rolledNumber)))
				.respond()
				.exceptionally(e -> {
					interaction.createImmediateResponder()
							.setContent("Something broke :(")
							.respond();
					return null;
				});
	}
}
