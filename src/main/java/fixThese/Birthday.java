package fixThese;

import command.Command;
import lombok.Getter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;

import java.util.List;

public class Birthday {

    @Getter public static String help = "Set your birthday globally for the bot";

    private void handleUserBirthdays(MessageAuthor author, Message message, List<String> args) {




    }
}
