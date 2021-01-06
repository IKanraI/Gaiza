package AdminCommands;

import Command.Command;
import Database.InitDatabase;
import Database.Servers;
import lombok.Getter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;

import java.util.List;
import java.util.Locale;

public class Streaming extends Command {
    @Getter public static String help = "Module for streaming notifications";

    public Streaming(DiscordApi api) {
        super(api);
        api.addMessageCreateListener(event ->
                streamCommand(super.getChannel(), super.getMessage(), super.getServer(), super.getArgs()));
    }

    private void streamCommand(TextChannel channel, Message message, Server server, List<String> args) {
        if (!onAdminCommand()) {
            return;
        }
        if (args.size() == 0) {
            channel.sendMessage("Please send this command with a value to change [enable|disable|channel|message|role|status]");
            return;
        }
        Servers instance = InitDatabase.getData().get(server.getIdAsString());
        args.set(0, args.get(0).toLowerCase(Locale.ROOT));
        switch (args.get(0)) {
            case "enable":
                if (Boolean.parseBoolean(instance.getLsEnabled())) {
                    channel.sendMessage("The streaming module is already enabled");
                    return;
                }
                if (instance.getLsChannel().equals("") || instance.getLsMessage().equals("") || instance.getLsRole().equals("")) {
                    channel.sendMessage("Please set the message, channel, and role before enabling the streaming module");
                    return;
                }

                instance.setLsEnabled("true");
                message.addReaction("\u2705");
                channel.sendMessage("The streaming module has been enabled");
                break;

            case "disable":
                if (!Boolean.parseBoolean(instance.getLsEnabled())) {
                    channel.sendMessage("The streaming module is already disabled");
                    return;
                }
                instance.setLsEnabled("false");
                message.addReaction("\u2705");
                channel.sendMessage("The streaming module has been disabled");
                break;

            case "channel":
                if (args.size() == 1) {
                    channel.sendMessage("Please include a channel ");
                }
                try {
                    instance.setLsChannel(message.getMentionedChannels().get(0).getIdAsString());
                    message.addReaction("\u2705");
                    channel.sendMessage("Streaming channel has been set");
                } catch (Exception e) {
                    channel.sendMessage("Please mention a channel with the id: [prefix]streaming channel <#[id]>");
                    return;
                }
                break;

            case "message":
                if (args.size() == 1) {
                    channel.sendMessage("Please include a message to use for the streaming module. If you would like to mention a user please use <<mention>>");
                    return;
                }
                StringBuilder msg = new StringBuilder();
                for (String s : args) {
                    msg.append(s + " ");
                }
                msg.replace(0, 8, "");

                instance.setLsMessage(msg.toString());
                message.addReaction("\u2705");
                channel.sendMessage("Message has been set for the streaming module");
                break;

            case "status":
                channel.sendMessage("The current status of the streaming module is. Module Enabled: [" + instance.getLsEnabled()
                        + "] Module channel: [" + instance.getLsChannel() + "] Module message: [" + instance.getLsMessage()
                        + "] Module role: [" + instance.getLsRole() + "]");
                break;

            case "role":
                if (args.size() == 1) {
                    channel.sendMessage("Please include a role for the streaming module. Mention the role or enter it as <@&[id]>");
                    return;
                }
                try {
                    instance.setLsRole(message.getMentionedRoles().get(0).getIdAsString());
                    message.addReaction("\u2705");
                    channel.sendMessage("The role has been updated for the streaming module");
                } catch (Exception e) {
                    channel.sendMessage("Role was not able to be set. Please make sure the format is correct. Either mention the id or tag it as <@$[id]>");
                    return;
                }
                break;

            default:
                channel.sendMessage("Please send this command with a value to change [enable|disable|channel|message|role|status]");
                break;
        }

        InitDatabase.saveDatabase();
    }
}
