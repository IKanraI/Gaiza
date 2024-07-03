package listener;

import util.BotInfo;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Yare implements MessageCreateListener {


    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        MessageAuthor author = event.getMessageAuthor();
        TextChannel channel = event.getChannel();
        Message message = event.getMessage();

        if (!author.getIdAsString().equals("824853199998418985")) //Puppo's discord id
            return;

        if (StringUtils.equalsIgnoreCase(message.getContent(), "yare yare daze")) {
            channel.sendMessage(new EmbedBuilder()
                    .setImage("https://b.catgirlsare.sexy/Fi5kF_gN8x_y.gif")
                    .setColor(Color.YELLOW)
                    .setTimestampToNow()).thenAccept(msg -> {
                msg.addReaction("\u274C");
                msg.addReactionAddListener(reactEvent -> {
                    if (reactEvent.getEmoji().equalsEmoji("\u274C") && reactEvent.getUserId() != Long.parseLong(BotInfo.getInstance().getBotId())) {
                        reactEvent.deleteMessage();
                    }
                }).removeAfter(30, TimeUnit.MINUTES);
            });
        }
    }
}
