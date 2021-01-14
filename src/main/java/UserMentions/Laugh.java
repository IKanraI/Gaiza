package UserMentions;

import Command.Command;
import Management.BotInfo;
import UserCommands.Gif;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;

import java.util.List;

public class Laugh extends Command {
    public static String help = "Laugh at a mentioned user";

    public Laugh(DiscordApi api) {
        super(api);
        api.addMessageCreateListener(event ->
                laughCommand(super.getChannel(), super.getMessage(), super.getMessageAuthor(), super.getArgs()));
    }

    public void laughCommand(TextChannel channel, Message message, MessageAuthor author, List<String> args) {
        if (!onCommand()) {
            return;
        }
        if (message.getMentionedUsers().size() == 0 || args.size() == 0) {
            channel.sendMessage("Please mention a user");
            return;
        }
        if (message.getMentionedUsers().get(0).getIdAsString().equals(author.getIdAsString())) {
            channel.sendMessage("It's always healthy to laugh at yourself :) ");
        }

        if (message.getMentionedUsers().get(0).getIdAsString().equals(BotInfo.getBotId())) {
            channel.sendMessage(":( why are you laughing at me? I.. I'm sorry :(");
        }

        channel.sendMessage(Pat.buildEmbed(author, message.getMentionedUsers().get(0), Gif.searchGif("Anime-laugh"), "laughed"))
                .exceptionally(error -> {
                    channel.sendMessage("Message could not be sent");
                    return null;
                });
    }
}
