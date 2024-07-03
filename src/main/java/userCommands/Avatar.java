package userCommands;
import java.awt.Color;
import java.util.Objects;

import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import util.BotInfo;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public class Avatar implements SlashCommandCreateListener {
	@Getter
	public static String help = "Returns the avatar of a user.";
	private final String commandName = "avatar";

	@Override
	public void onSlashCommandCreate(SlashCommandCreateEvent slashCommandCreateEvent) {
		SlashCommandInteraction event = slashCommandCreateEvent.getSlashCommandInteraction();
		if (!StringUtils.equalsIgnoreCase(event.getCommandName(), commandName))
			return;

		User mentionedUser = null;

		if (CollectionUtils.isEmpty(event.getArguments())) 
			mentionedUser = event.getUser();
		else {
			if (event.getArguments().get(0).getUserValue().isPresent())
				mentionedUser = event.getArguments().get(0).getUserValue().get();
		}

		event.createImmediateResponder()
				.addEmbed(buildOutputMessage(Objects.requireNonNull(mentionedUser)))
				.respond().exceptionally(e -> {
					event.createImmediateResponder()
							.setContent("User not found")
							.respond();
					return null;
				});
	}

	private EmbedBuilder buildOutputMessage(User user) {
		return new EmbedBuilder()
				.setAuthor(user.getDiscriminatedName(), user.getAvatar().getUrl().toString(), user.getAvatar())
				.setColor(Color.magenta)
				.setImage(user.getAvatar().getUrl().toString())
				.setFooter(BotInfo.getInstance().getBotName(), BotInfo.getInstance().getBotImage())
				.setTimestampToNow();
	}
}