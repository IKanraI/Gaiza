package UserCommands;

import Command.Command;
import Database.GlobalUserInformation;
import Listener.Uwu;
import Management.Keywords;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Fines extends Command {

    public Fines(DiscordApi api) {
        super(api);
        api.addMessageCreateListener(event ->
                userFine(super.getChannel(), super.getMessageAuthor(), super.getMessage(), super.getArgs()));
    }

    private void userFine(TextChannel channel, MessageAuthor author, Message message, List<String> args) {
        if (!onCommand()) {
            return;
        }
        if (message.getMentionedUsers().size() == 0 && args.size() == 1) {
            channel.sendMessage("Please mention a valid user");
        }

        if (args.size() == 0) {
            System.err.println(Uwu.getUserFine(author.getIdAsString()));
            channel.sendMessage("<@" + author.getIdAsString()
                    + ">, you currently owe: "
                    + NumberFormat.getCurrencyInstance(Locale.US).format(Uwu.getUserFine(GlobalUserInformation.getUserByIdDb(author.getIdAsString()))));

        } else {
            channel.sendMessage("<@" + message.getMentionedUsers().get(0).getIdAsString()
                    + "> currently owes: "
                    + NumberFormat.getCurrencyInstance(Locale.US).format(Uwu
                    .getUserFine(GlobalUserInformation.getUserByIdDb(message.getMentionedUsers().get(0).getIdAsString()))));
        }
    }
}