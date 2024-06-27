package UserCommands;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.DiscordApi;
import Command.*;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.interaction.Interaction;

public class Ping {
	@Getter
	public static String help = "Typical test command. Returns pong normally.";

	public Ping(DiscordApi api) {
		//super(api);
		api.addSlashCommandCreateListener(e ->
			pingCommand(e.getSlashCommandInteraction().getFullCommandName(), e.getSlashCommandInteraction().getUser().getIdAsString(), e.getInteraction()));
	}

	private void pingCommand(String command, String messageAuthorId, Interaction event) {
		if (!StringUtils.equalsIgnoreCase(command, "ping"))
			return;

		event.createImmediateResponder()
				.setContent("<@" + messageAuthorId + "> rules! ... well pong i guess")
				.respond();
	}
}