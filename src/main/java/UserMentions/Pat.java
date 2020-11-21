package UserMentions;

import Command.Command;
import Management.BotInfo;
import UserCommands.Gif;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.util.List;

public class Pat extends Command {
    public static String help = "Pat another user by mentioning them";
    public Pat(DiscordApi api) {
        super(api);

        api.addMessageCreateListener(event -> {
            patCommand(api, super.getChannel(), super.getMessage(), super.getMessageAuthor(), super.getArgs());
        });
    }

    public void patCommand(DiscordApi api, TextChannel channel, Message message, MessageAuthor author, List<String> args) {
        if (!onCommand(api, channel, message, author, args)) {
            return;
        }
        if (message.getMentionedUsers().size() == 0 || args.size() == 0) {
            channel.sendMessage("Please mention a user");
            return;
        }
        String term = "Anime pat";
        Gif gif = new Gif(term);

        channel.sendMessage(buildEmbed(author, message.getMentionedUsers().get(0), gif.getGifReturnUrl(), "patted"))
        .exceptionally(error -> {
            error.getCause().getMessage();
            return null;
        });
    }

    public static EmbedBuilder buildEmbed(MessageAuthor author, User mentioned, String gif, String command) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(mentioned.getName() + ", you have been " + command + " by " + author.getName())
                .setImage(gif)
                .setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
                .setColor(Color.MAGENTA)
                .setTimestampToNow();
        return embed;
    }
}
