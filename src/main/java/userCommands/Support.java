package userCommands;

import command.Command;
import management.BotInfo;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public class Support implements SlashCommandCreateListener {
    public static String help = "Returns the invite to the support server";
    public static String command = "support";

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent event) {
        SlashCommandInteraction interaction = event.getSlashCommandInteraction();

        if (!StringUtils.equalsIgnoreCase(interaction.getCommandName(), command))
            return;

        interaction.createImmediateResponder()
                .setContent("Here's the invite to the support server: https://discord.gg/Zpr5qTzKmd")
                .respond()
                .exceptionally(e -> {
                    interaction.createImmediateResponder()
                            .setContent("Something broke")
                            .respond();

                    return null;
                });
    }
}
