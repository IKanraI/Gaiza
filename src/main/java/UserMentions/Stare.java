package UserMentions;

import Command.Command;
import Management.BotInfo;
import UserCommands.Gif;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;

import java.util.List;

public class Stare extends Command {
    public static String help = "Admire another user from a distance";

    public Stare(DiscordApi api) {
        super(api);

        api.addMessageCreateListener(event -> {
            stareCommand(api, super.getChannel(), super.getMessage(), super.getMessageAuthor(), super.getArgs());
        });
    }

    public void stareCommand(DiscordApi api, TextChannel channel, Message message, MessageAuthor author, List<String> args) {
        if (!onCommand(api, channel, message, author, args)) {
            return;
        }
        if (message.getMentionedUsers().size() == 0 || args.size() == 0) {
            channel.sendMessage("Please mention a user");
            return;
        }
        if (message.getMentionedUsers().get(0).getIdAsString().equals(author.getIdAsString())) {
            channel.sendMessage("The mirror adds 10 pounds");
        }

        if (message.getMentionedUsers().get(0).getIdAsString().equals(BotInfo.getBotId())) {
            channel.sendMessage(":eyes:");
        }

        Gif gif = new Gif("Anime-stare");
        channel.sendMessage(Pat.buildEmbed(author, message.getMentionedUsers().get(0), gif.getGifReturnUrl(), "stared at"))
                .exceptionally(error -> {
                    error.getCause().getMessage();
                    return null;
                });
    }
}
