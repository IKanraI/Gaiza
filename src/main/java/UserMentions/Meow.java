package UserMentions;

import Command.Command;
import Management.BotInfo;
import UserCommands.Gif;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;

import java.util.List;

public class Meow extends Command {
    public static String help = "Meow at another mentioned user";

    public Meow(DiscordApi api) {
        super(api);
        api.addMessageCreateListener(event -> {
            meowCommand(super.getChannel(), super.getMessage(), super.getMessageAuthor(), super.getArgs());
        });
    }

    public void meowCommand(TextChannel channel, Message message, MessageAuthor author, List<String> args) {
        if (!onCommand()) {
            return;
        }
        if (message.getMentionedUsers().size() == 0 || args.size() == 0) {
            channel.sendMessage("Please mention a user");
            return;
        }
        if (message.getMentionedUsers().get(0).getIdAsString().equals(author.getIdAsString())) {
            channel.sendMessage("Who are you meowing to. Are you okay?");
        }

        if (message.getMentionedUsers().get(0).getIdAsString().equals(BotInfo.getBotId())) {
            channel.sendMessage("Nya!");
        }

        channel.sendMessage(Pat.buildEmbed(author, message.getMentionedUsers().get(0), Gif.searchGif("Anime-meow"), "meowed at"))
                .exceptionally(error -> {
                    error.getCause().getMessage();
                    return null;
                });
    }
}
