package UserMentions;

import Command.Command;
import Management.BotInfo;
import UserCommands.Gif;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;

import java.util.List;

public class Bite extends Command {
    public static String help = "Take a bite of another mentioned user";

    public Bite(DiscordApi api) {
        super(api);

        api.addMessageCreateListener(event -> {
            biteCommand(super.getChannel(), super.getMessage(), super.getMessageAuthor(), super.getArgs());
        });
    }

    public void biteCommand(TextChannel channel, Message message, MessageAuthor author, List<String> args) {
        if (!onCommand()) {
            return;
        }
        if (message.getMentionedUsers().size() == 0 || args.size() == 0) {
            channel.sendMessage("Please mention a user");
            return;
        }
        if (message.getMentionedUsers().get(0).getIdAsString().equals(author.getIdAsString())) {
            channel.sendMessage("Don't eat yourself that's strange");
        }

        if (message.getMentionedUsers().get(0).getIdAsString().equals(BotInfo.getBotId())) {
            channel.sendMessage("Ow!");
        }

        Gif gif = new Gif("Anime-bite");
        channel.sendMessage(Pat.buildEmbed(author, message.getMentionedUsers().get(0), gif.getGifReturnUrl(), "bitten"))
                .exceptionally(error -> {
                    error.getCause().getMessage();
                    return null;
                });
    }
}