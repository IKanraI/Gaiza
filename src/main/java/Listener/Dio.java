package Listener;

import Command.Command;
import Management.BotInfo;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.junit.rules.Timeout;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Dio extends Command {

    public Dio(DiscordApi api) {
        super(api);
        api.addMessageCreateListener(event ->
            listenForDio(super.getChannel(), super.getMessage(), super.getMessageAuthor()));
    }

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
