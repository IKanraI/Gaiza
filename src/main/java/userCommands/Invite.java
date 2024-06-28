package userCommands;

import command.Command;
import management.BotInfo;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.DiscordApi;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

import java.io.Serializable;

public class Invite implements SlashCommandCreateListener {
	@Getter
	public static String help = "Returns an invite link for the bot";
	public static String command = "invite";

	@Override
	public void onSlashCommandCreate(SlashCommandCreateEvent event) {
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();

		if (!StringUtils.equalsIgnoreCase(interaction.getCommandName(), command))
			return;

		interaction.createImmediateResponder()
				.setContent("Here is your invite : " + BotInfo.getBotInvite())
				.respond()
				.exceptionally(e -> {
					interaction.createImmediateResponder()
							.setContent("Something broke")
							.respond();

					return null;
				});
	}
}
