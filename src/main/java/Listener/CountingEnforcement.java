package Listener;

import Command.Command;
import Management.BotInfo;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.user.User;

import java.lang.Exception;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Locale;

public class CountingEnforcement extends Command {

    public CountingEnforcement (DiscordApi api) {
        super(api);

        api.addMessageCreateListener(event ->
                countCheck(super.getChannel(), super.getMessage(), super.getMessageAuthor()));
    }
    @SneakyThrows
    private void countCheck(TextChannel channel, Message message, MessageAuthor author) {
        // ignore messages not in this one hardcoded channel or from the bot
        if (!channel.getIdAsString().equals("519563130603307018") ||
            author.getIdAsString().equals(BotInfo.getBotId())) {
            return;
        }
        String msg = message.getContent().replaceAll("\\*", "");
        Integer msgNum;
        try {
            msgNum = parseIntFromMessage(msg);
        } catch (Exception e) {
            channel.sendMessage("<@" + author.getIdAsString() + ">" + ", did you forget what a number is?");
            Thread.sleep(1250);
            message.delete();
            Thread.sleep(2000);
            channel.getMessages(1).get().getNewestMessage().ifPresent(Message::delete);
            return;
        }

        Integer previousNum;
        User user;
        try {
            if (channel.getMessages(2).get().getOldestMessage().get().getAuthor().getIdAsString().equals(BotInfo.getBotId())) {
                previousNum = parseIntFromMessage(channel.getMessages(3).get().getOldestMessage().get().getContent().replaceAll("\\*", "")); 
                user = channel.getMessages(3).get().getOldestMessage().get().getAuthor().asUser().get();
            } else {
                previousNum = parseIntFromMessage(channel.getMessages(2).get().getOldestMessage().get().getContent().replaceAll("\\*", ""));
                user = channel.getMessages(2).get().getOldestMessage().get().getAuthor().asUser().get();
            }

            if (!previousNum.equals(msgNum - 1)) {
                channel.sendMessage("<@" + author.getIdAsString() + ">" + ", please input the correct next number");
                Thread.sleep(1250);
                message.delete();
            } else if (author.getIdAsString().equals(user.getIdAsString())) {
                channel.sendMessage("<@" + author.getIdAsString() + ">" + ", double counting isn't allowed. Please wait until someone else has counted");
                Thread.sleep(1250);
                message.delete();
            }
        } catch (BadInputException bie) {
            // if we can't parse numbers from the previous messages
            // trust the user and ask for help
            channel.sendMessage("<@" + author.getIdAsString() + ">" + ", I can't read the previous input, so I am trusting you, please call my dad uwu");
        }

        for (Message m : channel.getMessages(10).get()) {
            if (m.getAuthor().getIdAsString().equals(BotInfo.getBotId())) {
                Thread.sleep(1500);
                m.delete();
            }
        }
    }
    private Integer parseIntFromMessage(String message) throws BadInputException {
        Integer msgNum;
        // idk if this will cause issues when I throw a null error into the Throwable
        Exception hopefullyNot = null;
        // https://regex101.com/r/fwHchU/1
        Pattern beginsWithInteger = Pattern.compile("^([0-9]+)");
        Matcher matchBeginsWithInt = pattern.matcher(message);
        if (matchBeginsWithInt.find()) {
            try {
                msgNum = Integer.parseInt(matchBeginsWithInt.group(1));
            } catch (Exception e) {
                throw new BadInputException("Somehow failed to parse a series of numbers as an Int", e);
            }
        } else {
            throw new BadInputException("User input did not begin with an int", hopefullyNot);
        }
            
    }
    private class BadInputException extends Exception {
        public BadInputException(String errorMessage, Throwable err) {
            super(errorMessage, err);
        }
    }
}
