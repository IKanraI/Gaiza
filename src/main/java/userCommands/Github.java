package userCommands;

import lombok.Getter;
import management.BotInfo;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public class Github implements SlashCommandCreateListener {
    @Getter
    public static String help = "Returns the repo of the bot";
    public final String command = "github";

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent event) {
        SlashCommandInteraction interaction = event.getSlashCommandInteraction();

        if (!StringUtils.equalsIgnoreCase(interaction.getCommandName(), command))
            return;

        interaction.createImmediateResponder()
                .setContent("Here is the repo : " + BotInfo.getBotRepo())
                .respond()
                .exceptionally(e -> {
                    interaction.createImmediateResponder()
                            .setContent("Something broke")
                            .respond();

                    return null;
                });
    }
}
