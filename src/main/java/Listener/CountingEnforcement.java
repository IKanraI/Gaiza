package Listener;

import Command.Command;
import Management.BotInfo;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.user.User;

import java.util.Locale;

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
        String msg = message.getContent().replaceAll("\\*", "");
        try {
            Integer.parseInt(msg);
        } catch (Exception e) {
            channel.sendMessage("<@" + author.getIdAsString() + ">" + ", did you forget what a number is?");
            Thread.sleep(1250);
            message.delete();
            Thread.sleep(2000);
            channel.getMessages(1).get().getNewestMessage().ifPresent(Message::delete);
            return;
        }

        Integer input;
        User user;
        if (channel.getMessages(2).get().getOldestMessage().get().getAuthor().getIdAsString().equals(BotInfo.getBotId())) {
            input = Integer.parseInt(channel.getMessages(3).get().getOldestMessage().get().getContent().replaceAll("\\*", "")) + 1;
            user = channel.getMessages(3).get().getOldestMessage().get().getAuthor().asUser().get();
        } else {
            input = Integer.parseInt(channel.getMessages(2).get().getOldestMessage().get().getContent().replaceAll("\\*", "")) + 1;
            user = channel.getMessages(2).get().getOldestMessage().get().getAuthor().asUser().get();
        }

        if (!input.equals(Integer.parseInt(msg))) {
            channel.sendMessage("<@" + author.getIdAsString() + ">" + ", please input the correct next number");
            Thread.sleep(1250);
            message.delete();
        } else if (author.getIdAsString().equals(user.getIdAsString())) {
            channel.sendMessage("<@" + author.getIdAsString() + ">" + ", double counting isn't allowed. Please wait until someone else has counted");
            Thread.sleep(1250);
            message.delete();
        }

        for (Message m : channel.getMessages(10).get()) {
            if (m.getAuthor().getIdAsString().equals(BotInfo.getBotId())) {
                Thread.sleep(1500);
                m.delete();
            }
        }
    }
}
