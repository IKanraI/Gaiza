package listener;

import command.Command;
import management.BotInfo;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.user.User;

import java.lang.Exception;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        User previousUser;
        MessageSet lastTwoMessages = channel.getMessages(2).get();
        Boolean previousMessageAuthorIsTheBot = lastTwoMessages.getOldestMessage().get().getAuthor().getIdAsString().equals(BotInfo.getBotId());
        Message previousHumanMessage;
        if (previousMessageAuthorIsTheBot) {
            // assumes the econd to last message is not written by the bot implicitly
            // there's probably a good pattern for scrolling up the last few message to find a non-bot message
            // but i'm getting bored
            previousHumanMessage = channel.getMessages(3).get().getOldestMessage().get();
        } else {
            previousHumanMessage = lastTwoMessages.getOldestMessage().get();
        }  
        previousUser = previousHumanMessage.getAuthor().asUser().get();
        if (author.getIdAsString().equals(previousUser.getIdAsString())) {
            channel.sendMessage("<@" + author.getIdAsString() + ">" + ", double counting isn't allowed. Please wait until someone else has counted");
            Thread.sleep(1250);
            message.delete();
        }
        
        try {
            previousNum = parseIntFromMessage(previousHumanMessage.getContent().replaceAll("\\*", ""));
            if (!previousNum.equals(msgNum - 1)) {
                channel.sendMessage("<@" + author.getIdAsString() + ">" + ", please input the correct next number");
                Thread.sleep(1250);
                message.delete();
            }
        } catch (BadInputException bie) {
            // if we can't parse numbers from the previous messages
            // trust the user and ask for help
            channel.sendMessage("<@" + author.getIdAsString() + ">" + ", I can't read the number from the previous messages, so I am trusting you, please call my dad uwu");
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
        Matcher matchBeginsWithInt = beginsWithInteger.matcher(message);
        if (matchBeginsWithInt.find()) {
            try {
                msgNum = Integer.parseInt(matchBeginsWithInt.group(1));
                return msgNum;
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
