package UserMentions;

import Command.Command;
import Management.BotInfo;
import UserCommands.Gif;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;

import java.util.List;

public class Lewd extends Command {
    public static String help = "Lewd another mentioned user";

    public Lewd(DiscordApi api) {
        super(api);

        api.addMessageCreateListener(event -> {
            lewdCommand(super.getChannel(), super.getMessage(), super.getMessageAuthor(), super.getArgs());
        });
    }

    public void lewdCommand(TextChannel channel, Message message, MessageAuthor author, List<String> args) {
        if (!onCommand()) {
            return;
        }
        if (message.getMentionedUsers().size() == 0 || args.size() == 0) {
            channel.sendMessage("Please mention a user");
            return;
        }
        if (message.getMentionedUsers().get(0).getIdAsString().equals(author.getIdAsString())) {
            channel.sendMessage("Get a room");
        }

        if (message.getMentionedUsers().get(0).getIdAsString().equals(BotInfo.getBotId())) {
            channel.sendMessage("Please don't lewd me");
        }

        Gif gif = new Gif("Anime-lewd");
        channel.sendMessage(Pat.buildEmbed(author, message.getMentionedUsers().get(0), gif.getGifReturnUrl(), "lewded"))
                .exceptionally(error -> {
                    error.getCause().getMessage();
                    return null;
                });
    }
}
