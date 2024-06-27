package userCommands;

import command.Command;
import model.GlobalUserInformation;
import model.InitDatabase;
import listener.UwuListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Fines extends Command {

    public static String help = "Checks the fine amount of a mentioned user. Usage [prefix]fines | [prefix]fines <user>";

    public Fines(DiscordApi api) {
        super(api);
        api.addMessageCreateListener(event ->
                userFine(super.getChannel(), super.getServer(), super.getMessageAuthor(), super.getMessage(), super.getArgs()));
    }

    private void userFine(TextChannel channel, Server server, MessageAuthor author, Message message, List<String> args) {
        if (!onCommand()) {
            return;
        }
        if (message.getMentionedUsers().size() == 0 && args.size() == 1) {
            channel.sendMessage("Please mention a valid user");
        }
        if (!Boolean.parseBoolean(InitDatabase.getData().get(server.getIdAsString()).getUwu())) {
            channel.sendMessage("The uwu module is disabled");
            return;
        }

        if (args.size() == 0) {
            channel.sendMessage("<@" + author.getIdAsString()
                    + ">, you currently owe: "
                    + NumberFormat.getCurrencyInstance(Locale.US).format(UwuListener.getUserFine(GlobalUserInformation.filePath + author.getIdAsString())));

        } else {
            channel.sendMessage(message.getMentionedUsers().get(0).getName()
                    + " currently owes: "
                    + NumberFormat.getCurrencyInstance(Locale.US).format(UwuListener
                    .getUserFine(GlobalUserInformation.filePath + message.getMentionedUsers().get(0).getIdAsString())));
        }
    }
}
