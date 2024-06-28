package userCommands;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.DiscordApi;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.Interaction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public class Ping implements SlashCommandCreateListener {
	@Getter
	public static String help = "Typical test command. Returns pong normally.";

	@Override
	public void onSlashCommandCreate(SlashCommandCreateEvent event) {
		if (!StringUtils.equalsIgnoreCase(event.getSlashCommandInteraction().getCommandName(), "ping"))
			return;

		String messageAuthorId = event.getSlashCommandInteraction().getUser().getIdAsString();

		event.getSlashCommandInteraction().createImmediateResponder()
				.setContent("<@" + messageAuthorId + "> rules! ... well pong i guess")
				.respond();
	}
}