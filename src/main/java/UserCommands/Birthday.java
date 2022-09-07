package UserCommands;

import Command.Command;
import lombok.Getter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;

import java.util.List;

public class Birthday extends Command {

    @Getter public static String help = "Set your birthday globally for the bot";

    public Birthday(DiscordApi api) {
        super(api);
        api.addMessageCreateListener(event ->
                handleUserBirthdays(super.getMessageAuthor(), super.getMessage(), super.getArgs()));
    }

    private void handleUserBirthdays(MessageAuthor author, Message message, List<String> args) {




    }
}
