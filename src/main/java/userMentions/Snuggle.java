package userMentions;

import command.Command;
import management.BotInfo;
import userCommands.Gif;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;

import java.util.List;

public class Snuggle extends Command {
    public static String help = "Snuggle another user by mentioning them";

    public Snuggle(DiscordApi api) {
        super(api);

        api.addMessageCreateListener(event ->
            snuggleCommand(super.getChannel(), super.getMessage(), super.getMessageAuthor(), super.getArgs()));
    }

    public void snuggleCommand(TextChannel channel, Message message, MessageAuthor author, List<String> args) {
        if (!onCommand()) {
            return;
        }
        if (message.getMentionedUsers().size() == 0 || args.size() == 0) {
            channel.sendMessage("Please mention a user");
            return;
        }
        if (message.getMentionedUsers().get(0).getIdAsString().equals(author.getIdAsString())) {
            channel.sendMessage("How are you going to snuggle yourself? That's so sad :(");
        }

        if (message.getMentionedUsers().get(0).getIdAsString().equals(BotInfo.getBotId())) {
            channel.sendMessage("Oh you want to snuggle me? Thank you so much <3");
        }

        channel.sendMessage(Pat.buildEmbed(author, message.getMentionedUsers().get(0), Gif.searchGif("Anime-snuggle"), "snuggled"))
                .exceptionally(error -> {
                    channel.sendMessage("Message could not be sent");
                    return null;
                });
    }
}
