package UserMentions;

import Command.Command;
import Management.BotInfo;
import UserCommands.Gif;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;

import java.util.List;

public class Poke extends Command {
    public static String help = "Get another user's attention by poking them";

    public Poke(DiscordApi api) {
        super(api);

        api.addMessageCreateListener(event ->
            patCommand(super.getChannel(), super.getMessage(), super.getMessageAuthor(), super.getArgs()));
    }

    public void patCommand(TextChannel channel, Message message, MessageAuthor author, List<String> args) {
        if (!onCommand()) {
            return;
        }
        if (message.getMentionedUsers().size() == 0 || args.size() == 0) {
            channel.sendMessage("Please mention a user");
            return;
        }
        if (message.getMentionedUsers().get(0).getIdAsString().equals(author.getIdAsString())) {
            channel.sendMessage("This is strange, but okay I guess");
        }

        if (message.getMentionedUsers().get(0).getIdAsString().equals(BotInfo.getBotId())) {
            channel.sendMessage("Hello :) How may I help you?");
        }

        channel.sendMessage(Pat.buildEmbed(author, message.getMentionedUsers().get(0), Gif.searchGif("Anime-poke"), "poked"))
                .exceptionally(error -> {
                    error.getCause().getMessage();
                    return null;
                });
    }
}
