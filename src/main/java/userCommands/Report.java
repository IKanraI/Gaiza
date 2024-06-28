package userCommands;

import command.Command;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import util.GaizaUtil;

import java.util.List;

public class Report implements SlashCommandCreateListener {

    @Getter public static String help = "Send a bug or a problem with the bot to the owner";
    private static String command = "report";

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent event) {
        SlashCommandInteraction interaction = event.getSlashCommandInteraction();

        if (!StringUtils.equalsIgnoreCase(interaction.getCommandName(), command))
            return;

        boolean sendStatus = sendToReportChannel(GaizaUtil.getPassedArgument(interaction.getArguments()), interaction.getUser(), interaction.getApi());

        if (sendStatus) {
            interaction.createImmediateResponder()
                    .setFlags(MessageFlag.EPHEMERAL)
                    .setContent("Message sent successfully. Thank you for reporting :)")
                    .respond();
        }
    }

    private boolean sendToReportChannel(String message, User author, DiscordApi api) {
        if (api.getServerById("692871508590067823").isPresent()) {
            Server server = api.getServerById("692871508590067823").get();

            if (server.getChannelById("704097798102057071").isPresent()) {
                ServerChannel serverChannel = server.getChannelById("704097798102057071").get();

                if (serverChannel.asTextChannel().isPresent()) {
                    TextChannel textChannel = serverChannel.asTextChannel().get();

                    textChannel.sendMessage("[" + author.getDiscriminatedName() + "]: " + message);
                    return true;
                }
            }
        }

        return false;
    }
}
