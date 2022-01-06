package UserMentions;

import Command.Command;
import Management.BotInfo;
import UserCommands.Gif;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;

import java.util.List;

public class Boop extends Command {
    public static String help = "Boop your friends nose";

    public Boop(DiscordApi api) {
        super(api);

        api.addMessageCreateListener(event ->
                boopCommand(super.getChannel(), super.getMessage(), super.getMessageAuthor(), super.getArgs()));
    }

    public void boopCommand(TextChannel channel, Message message, MessageAuthor author, List<String> args) {
        System.err.println("1");
        if (!onCommand()) {
            return;
        }
        if (message.getMentionedUsers().size() == 0 || args.size() == 0) {
            channel.sendMessage("Please mention a user");
            return;
        }
        if (message.getMentionedUsers().get(0).getIdAsString().equals(author.getIdAsString())) {
            channel.sendMessage("Are you itchy?");
        }

        if (message.getMentionedUsers().get(0).getIdAsString().equals(BotInfo.getBotId())) {
            channel.sendMessage("*honk*!");
        }
        System.err.println("1");
        channel.sendMessage(Pat.buildEmbed(author, message.getMentionedUsers().get(0), Gif.searchGif("boop"), "booped"))
                .exceptionally(error -> {
                    channel.sendMessage("Message could not be sent");
                    return null;
                });
    }
}