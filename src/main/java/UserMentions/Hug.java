package UserMentions;

import Command.Command;
import Management.BotInfo;
import UserCommands.Gif;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;

import java.util.List;

public class Hug extends Command {
    public static String help = "Hug another user by mentioning them";

    public Hug(DiscordApi api) {
        super(api);

        api.addMessageCreateListener(event -> {
            hugCommand(api, super.getChannel(), super.getMessage(), super.getMessageAuthor(), super.getArgs());
        });
    }

    public void hugCommand(DiscordApi api, TextChannel channel, Message message, MessageAuthor author, List<String> args) {
        if (!onCommand(api, channel, message, author, args)) {
            return;
        }
        if (message.getMentionedUsers().size() == 0 || args.size() == 0) {
            channel.sendMessage("Please mention a user");
            return;
        }
        if (message.getMentionedUsers().get(0).getIdAsString().equals(author.getIdAsString())) {
            channel.sendMessage("Must you really give yourself a hug? That's so sad :(");
        }

        if (message.getMentionedUsers().get(0).getIdAsString().equals(BotInfo.getBotId())) {
            channel.sendMessage("Oh you want to give me a hug? Thank you so much <3");
        }

        Gif gif = new Gif("Anime-hug");
        channel.sendMessage(Pat.buildEmbed(author, message.getMentionedUsers().get(0), gif.getGifReturnUrl(), "hugged"))
                .exceptionally(error -> {
                    error.getCause().getMessage();
                    return null;
                });
    }
}
