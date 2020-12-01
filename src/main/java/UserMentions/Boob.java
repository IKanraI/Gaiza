package UserMentions;

import Command.Command;
import Management.BotInfo;
import UserCommands.Gif;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;

import java.util.List;

public class Boob extends Command {
    public static String help = "Send boobs to another user by mentioning them";

    public Boob(DiscordApi api) {
        super(api);

        api.addMessageCreateListener(event -> {
            boobCommand(super.getChannel(), super.getMessage(), super.getMessageAuthor(), super.getArgs());
        });
    }

    public void boobCommand(TextChannel channel, Message message, MessageAuthor author, List<String> args) {
        if (!onCommand()) {
            return;
        }
        if (message.getMentionedUsers().size() == 0 || args.size() == 0) {
            channel.sendMessage("Please mention a user");
            return;
        }
        if (message.getMentionedUsers().get(0).getIdAsString().equals(author.getIdAsString())) {
            channel.sendMessage("Sending boobs to yourself? Oof...");
        }

        if (message.getMentionedUsers().get(0).getIdAsString().equals(BotInfo.getBotId())) {
            channel.sendMessage("Hell yeah bobs are great");
        }

        Gif gif = new Gif("Anime-boobs");
        channel.sendMessage(Pat.buildEmbed(author, message.getMentionedUsers().get(0), gif.getGifReturnUrl(), "bamboobzled"))
                .exceptionally(error -> {
                    error.getCause().getMessage();
                    return null;
                });
    }
}
