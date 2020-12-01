package UserMentions;

import Command.Command;
import Management.BotInfo;
import UserCommands.Gif;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;

import java.util.List;

public class Slap extends Command {
    public static String help = "Slap some sense into a bitch";

    public Slap(DiscordApi api) {
        super(api);

        api.addMessageCreateListener(event -> {
            slapCommand(super.getChannel(), super.getMessage(), super.getMessageAuthor(), super.getArgs());
        });
    }

    public void slapCommand(TextChannel channel, Message message, MessageAuthor author, List<String> args) {
        if (!onCommand()) {
            return;
        }
        if (message.getMentionedUsers().size() == 0 || args.size() == 0) {
            channel.sendMessage("Please mention a user");
            return;
        }
        if (message.getMentionedUsers().get(0).getIdAsString().equals(author.getIdAsString())) {
            channel.sendMessage("Stop hitting yourself, stop hitting yourself...");
        }
        if (message.getMentionedUsers().get(0).getIdAsString().equals(BotInfo.getBotId())) {
            channel.sendMessage("You will not abuse me anymore. I won't let you.");
            return;
        }

        channel.sendMessage(Pat.buildEmbed(author, message.getMentionedUsers().get(0), Gif.searchGif("Anime-slap"), "slapped"))
                .exceptionally(error -> {
                    error.getCause().getMessage();
                    return null;
                });
    }
}
