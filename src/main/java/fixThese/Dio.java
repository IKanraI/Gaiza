package fixThese;

import command.Command;
import management.BotInfo;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Dio {

    private void listenForDio(TextChannel channel, Message message, MessageAuthor author) {
        if (!author.getIdAsString().equals(BotInfo.getOwnerId())) {
            return;
        }

        if (message.getContent().equalsIgnoreCase("KONO DIO DA")) {
            channel.sendMessage(new EmbedBuilder()
                    .setImage("https://b.catgirlsare.sexy/b3P755aD.gif")
                    .setColor(Color.YELLOW)
                    .setTimestampToNow()).thenAccept(msg -> {
                        msg.addReaction("\u274C");
                        msg.addReactionAddListener(reactEvent -> {
                            if (reactEvent.getEmoji().equalsEmoji("\u274C") && reactEvent.getUserId() != Long.parseLong(BotInfo.getBotId())) {
                                reactEvent.deleteMessage();
                            }
                        }).removeAfter(30, TimeUnit.MINUTES);
            });
        }
    }
}
