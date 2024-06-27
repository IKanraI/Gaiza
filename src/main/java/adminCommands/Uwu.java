package adminCommands;

import command.Command;
import model.InitDatabase;
import model.common.Servers;
import lombok.Getter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;

import java.util.List;

public class Uwu extends Command {
    @Getter public static String help = "This module will toggle the uwu module from being active. Using [enable|disable|status] after the initial [prefix]uwu";

    public Uwu(DiscordApi api) {
        super(api);
        api.addMessageCreateListener(event ->
                uwuManager(super.getServer(), super.getMessage(), super.getChannel(), super.getArgs()));
    }

    private void uwuManager(Server server, Message message, TextChannel channel, List<String> args) {
        if (!onAdminCommand()) {
            return;
        }
        if (args.size() == 0) {
            channel.sendMessage("Please include another argument with this. " + super.getKey() + " enable|disable|status");
            return;
        }

        Servers instance = InitDatabase.getData().get(server.getIdAsString());
        switch (args.get(0)) {
            case "enable":
                if (Boolean.parseBoolean(instance.getUwu())) {
                    channel.sendMessage("This is already enabled :P");
                    return;
                }
                instance.setUwu("true");
                message.addReaction("\u2705");
                channel.sendMessage("The uwu module has been enabled");
                break;
            case "disable":
                if (!Boolean.parseBoolean(instance.getUwu())) {
                    channel.sendMessage("This is already disabled :P");
                    return;
                }
                instance.setUwu("false");
                message.addReaction("\u2705");
                channel.sendMessage("The uwu module has been disabled");
                break;
            case "status":
                String check = Boolean.parseBoolean(instance.getUwu()) ? "enabled" : "disabled";
                channel.sendMessage("The uwu module is " + check);
                return;
        }
        InitDatabase.saveDatabase();
    }
}
