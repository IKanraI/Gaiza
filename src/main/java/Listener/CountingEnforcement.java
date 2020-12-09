package Listener;

import Command.Command;
import Management.BotInfo;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.user.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CountingEnforcement extends Command {

    public CountingEnforcement (DiscordApi api) {
        super(api);

        api.addMessageCreateListener(event ->
                countCheck(super.getChannel(), super.getMessage(), super.getMessageAuthor()));
    }
    @SneakyThrows
    private void countCheck(TextChannel channel, Message message, MessageAuthor author) {
        if (author.getIdAsString().equals(BotInfo.getBotId())) {
            return;
        }
        if (!channel.getIdAsString().equals("519563130603307018")) {
            return;
        }
        try {
            if (!author.getIdAsString().equals(BotInfo.getBotId())) {
                Integer.parseInt(message.getContent());
            }
        } catch (Exception e) {
            channel.sendMessage("<@" + author.getIdAsString() + ">" + ", did you forget what a number is?");
            Thread.sleep(1250);
            message.delete();
            Thread.sleep(2000);
            channel.getMessages(1).get().getNewestMessage().get().delete();
            return;
        }

        Integer compare;
        User user;
        if (channel.getMessages(2).get().getOldestMessage().get().getAuthor().getIdAsString().equals(BotInfo.getBotId())) {
            compare = Integer.parseInt(channel.getMessages(3).get().getOldestMessage().get().getContent()) + 1;
            user = channel.getMessages(3).get().getOldestMessage().get().getAuthor().asUser().get();
        } else {
            compare = Integer.parseInt(channel.getMessages(2).get().getOldestMessage().get().getContent()) + 1;
            user = channel.getMessages(2).get().getOldestMessage().get().getAuthor().asUser().get();
        }

        if (!compare.equals(Integer.parseInt(message.getContent()))) {
            channel.sendMessage("<@" + author.getIdAsString() + ">" + ", do you know how to fucking count cunt?");
            Thread.sleep(1250);
            message.delete();
            Thread.sleep(2000);
            channel.getMessages(1).get().getNewestMessage().get().delete();
        } else if (author.getIdAsString().equals(user.getIdAsString())) {
            channel.sendMessage("<@" + author.getIdAsString() + ">" + ", stop being a greedy cunt and wait your turn");
            Thread.sleep(1250);
            message.delete();
            Thread.sleep(2000);
            channel.getMessages(1).get().getNewestMessage().get().delete();
        }
    }
}
