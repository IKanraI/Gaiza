package UserMentions;

import Command.Command;
import Management.BotInfo;
import UserCommands.Gif;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;

import java.util.List;

public class Butt extends Command {
    public static String help = "Give a booty call to your favorite user";

    public Butt(DiscordApi api) {
        super(api);

        api.addMessageCreateListener(event -> {
            buttCommand(super.getChannel(), super.getMessage(), super.getMessageAuthor(), super.getArgs());
        });
    }

    public void buttCommand(TextChannel channel, Message message, MessageAuthor author, List<String> args) {
        if (!onCommand()) {
            return;
        }
        if (message.getMentionedUsers().size() == 0 || args.size() == 0) {
            channel.sendMessage("Please mention a user");
            return;
        }
        if (message.getMentionedUsers().get(0).getIdAsString().equals(author.getIdAsString())) {
            channel.sendMessage("Isn't there a place for this?");
        }
        if (message.getMentionedUsers().get(0).getIdAsString().equals(BotInfo.getBotId())) {
            channel.sendMessage("B00ty 8)");

        }

        channel.sendMessage(Pat.buildEmbed(author, message.getMentionedUsers().get(0), Gif.searchGif("Anime-booty"), "bootified"))
                .exceptionally(error -> {
                    error.getCause().getMessage();
                    return null;
                });
    }
}
