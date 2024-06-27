package userCommands;

import command.Command;
import model.common.Media;
import management.BotInfo;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.*;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.MessageComponentInteraction;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Movie extends Command {

    @Getter public static String help = "Include something in this later";

    public Movie(DiscordApi api) {
        super(api);
        api.addMessageCreateListener(event -> movieListManagement(api, super.getChannel(), super.getServer(), super.getMessage(), super.getMessageAuthor(), super.getArgs()));
    }

    public void movieListManagement(DiscordApi api, TextChannel channel, Server server, Message message, MessageAuthor author, List<String> args) {
        if (!onCommand() || StringUtils.equalsAny(BotInfo.getOwnerId(), BotInfo.getZaraiUserId()))
            return;
        Media media = new Media();

        new MessageBuilder()
                .setContent("Que le gustaria hacer?")
                        .addComponents(
                                ActionRow.of(SelectMenu.createStringMenu("options", "value", 1, 1,
                                        Arrays.asList(SelectMenuOption.create("Anadir", "Anadir una entrada", "Anadir una entrada"),
                                                SelectMenuOption.create("Borrar", "second", "Borrar una entrada"),
                                                SelectMenuOption.create("Random", "third", "Elige una pelicula al azar")))))
                                .send(channel);

        api.addMessageComponentCreateListener(event -> {
            MessageComponentInteraction messageInteraction = event.getMessageComponentInteraction();
            String optionId = "empty";
            if (messageInteraction.asSelectMenuInteraction().isPresent())
                optionId = messageInteraction.asSelectMenuInteraction().get().getChosenOptions().get(0).getLabel();

            switch (StringUtils.lowerCase(optionId)) {
                case "anadir":
                    addShowToList(messageInteraction, media);

                    break;
                case "borrar":
                    break;
                case "random":
                    break;
                default:

            }

        });

    }

    private void selectGenreOfMedia(Media media) {
        new MessageBuilder()
                .setContent("Cual genera es?")
                .addComponents(
                        ActionRow.of(SelectMenu.createStringMenu("options", "value", 1, 1,
                                Arrays.asList(SelectMenuOption.create("Comedy", "Anadir una entrada", "Comedy"),
                                        SelectMenuOption.create("Horror", "second", "Horror"),
                                        SelectMenuOption.create("Thriller", "third", "Thriller")))))
                .send(getChannel());
        getApi().addMessageComponentCreateListener(event -> {
            MessageComponentInteraction messageInteraction = event.getMessageComponentInteraction();
            if (messageInteraction.asSelectMenuInteraction().isPresent())
                media.setGenre(messageInteraction.asSelectMenuInteraction().get().getChosenOptions().get(0).getLabel());
        });
    }

    private void addShowToList(MessageComponentInteraction messageInteraction, Media media) {
        messageInteraction.respondWithModal("name", "Nombre",
                ActionRow.of(TextInput.create(TextInputStyle.SHORT, "nameInput", "Escriba aqui el nombre de la pelicula"))).join();

        getApi().addModalSubmitListener(event -> {
            Optional<ActionRow> actionRow = event.getModalInteraction().getComponents().get(0).asActionRow();
            if (actionRow.isPresent())
                if(actionRow.get().getComponents().get(0).asTextInput().isPresent())
                    media.setTitle(actionRow.get().getComponents().get(0).asTextInput().get().getValue());

            event.getModalInteraction().createImmediateResponder()
                    .setContent("Accepted")
                    .respond();
            selectGenreOfMedia(media);
        });

        
    }


}
