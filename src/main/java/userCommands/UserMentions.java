package userCommands;

import management.BotInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

import java.awt.*;

public class UserMentions implements SlashCommandCreateListener {

    public static String help = "Pat another user by mentioning them";

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent event) {
        SlashCommandInteraction interaction = event.getSlashCommandInteraction();

        switch (interaction.getCommandName()) {
            case "hug":
                hugMentionedUser(interaction);
                break;
            case "kiss":
                kissMentionedUser(interaction);
                break;
            case "laugh":
                laughMentionedUser(interaction);
                break;
            case "meow":
                meowMentionedUser(interaction);
                break;
            case "pat":
                patMentionedUser(interaction);
                break;
            case "poke":
                pokeMentionedUser(interaction);
                break;
            case "slap":
                slapMentionedUser(interaction);
                break;
            case "smug":
                smugMentionedUser(interaction);
                break;
            case "cuddle":
                cuddleMentionedUser(interaction);
                break;
            case "stare":
                stareMentionedUser(interaction);
                break;
            default:
                interaction.createImmediateResponder()
                        .setFlags(MessageFlag.EPHEMERAL)
                        .setContent("How did you end up here? Stay a while won't you?")
                        .respond();
        }
    }

    private void stareMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "The mirror adds 10 pounds";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getBotId()))
            message = ":eyes:";
        else
            message = user.getName() + " you are being stared at by " + interaction.getUser().getName();

        interaction.createImmediateResponder()
                .addEmbed(buildEmbed(Gif.searchGif("Anime-stare"), message))
                .respond()
                .exceptionally(e -> {
                    interaction.createImmediateResponder()
                            .setContent("Algo anda mal")
                            .respond();

                    return null;
                });
    }

    private void cuddleMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "How are you going to snuggle yourself? That's so sad :(";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getBotId()))
            message = "Oh you want to snuggle me? Thank you so much <3";
        else
            message = user.getName() + " you are being cuddled by " + interaction.getUser().getName();

        interaction.createImmediateResponder()
                .addEmbed(buildEmbed(Gif.searchGif("Anime-cuddle"), message))
                .respond()
                .exceptionally(e -> {
                    interaction.createImmediateResponder()
                            .setContent("Algo anda mal")
                            .respond();

                    return null;
                });
    }

    private void smugMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "What are you looking so smug about?";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getBotId()))
            message = "What... did you... do?";
        else
            message = user.getName() + " you are being looked at smugly by " + interaction.getUser().getName();

        interaction.createImmediateResponder()
                .addEmbed(buildEmbed(Gif.searchGif("Anime-smug"), message))
                .respond()
                .exceptionally(e -> {
                    interaction.createImmediateResponder()
                            .setContent("Algo anda mal")
                            .respond();

                    return null;
                });
    }

    private void slapMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "Stop hitting yourself, stop hitting yourself...";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getBotId())) {
            interaction.createImmediateResponder()
                    .setContent("You will not abuse me anymore. I won't let you.")
                    .respond();
            return;
        } else
            message = user.getName() + " you have been slapped by " + interaction.getUser().getName();

        interaction.createImmediateResponder()
                .addEmbed(buildEmbed(Gif.searchGif("Anime-slap"), message))
                .respond()
                .exceptionally(e -> {
                    interaction.createImmediateResponder()
                            .setContent("Algo anda mal")
                            .respond();

                    return null;
                });
    }

    private void pokeMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "This is strange, but okay I guess";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getBotId()))
            message = "Hello :) How may I help you?";
        else
            message = user.getName() + " you have been poked by " + interaction.getUser().getName();

        interaction.createImmediateResponder()
                .addEmbed(buildEmbed(Gif.searchGif("Anime-poke"), message))
                .respond()
                .exceptionally(e -> {
                    interaction.createImmediateResponder()
                            .setContent("Algo anda mal")
                            .respond();

                    return null;
                });
    }

    private void meowMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "Who are you meowing to. Are you okay?";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getBotId()))
            message = "Nya!";
        else
            message = user.getName() + " you have been meowed at by " + interaction.getUser().getName();

        interaction.createImmediateResponder()
                .addEmbed(buildEmbed(Gif.searchGif("Anime-meow"), message))
                .respond()
                .exceptionally(e -> {
                    interaction.createImmediateResponder()
                            .setContent("Algo anda mal")
                            .respond();

                    return null;
                });
    }

    private void laughMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "It's always healthy to laugh at yourself :)";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getBotId()))
            message = ":( why are you laughing at me? I.. I'm sorry :(";
        else
            message = user.getName() + " you have been mocked by " + interaction.getUser().getName();

        interaction.createImmediateResponder()
                .addEmbed(buildEmbed(Gif.searchGif("Anime-laugh"), message))
                .respond()
                .exceptionally(e -> {
                    interaction.createImmediateResponder()
                            .setContent("Algo anda mal")
                            .respond();

                    return null;
                });
    }

    private void kissMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "Must you really give yourself a kiss? That's so sad :(";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getBotId()))
            message = "Oh you want to give me a kiss? Thank you so much <3";
        else
            message = user.getName() + " you have been kissed by " + interaction.getUser().getName();

        interaction.createImmediateResponder()
                .addEmbed(buildEmbed(Gif.searchGif("Anime-kiss"), message))
                .respond()
                .exceptionally(e -> {
                    interaction.createImmediateResponder()
                            .setContent("Algo anda mal")
                            .respond();

                    return null;
                });
    }

    private void hugMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "Must you really give yourself a hug? That's so sad :(";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getBotId()))
            message = "Oh you want to give me a hug? Thank you so much <3";
        else
            message = user.getName() + " you have been hugged by " + interaction.getUser().getName();

        interaction.createImmediateResponder()
                .addEmbed(buildEmbed(Gif.searchGif("Anime-hug"), message))
                .respond()
                .exceptionally(e -> {
                    interaction.createImmediateResponder()
                            .setContent("Algo anda mal")
                            .respond();

                    return null;
                });
    }

    private void patMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "Must you really give yourself a headpat? That's so sad :(";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getBotId()))
            message = "Oh you want to give me a headpat? Thank you so much <3";
        else
            message = user.getName() + " you have been patted by " + interaction.getUser().getName();

        interaction.createImmediateResponder()
                .addEmbed(buildEmbed(Gif.searchGif("Anime-pat"), message))
                .respond()
                .exceptionally(e -> {
                    interaction.createImmediateResponder()
                            .setContent("Algo anda mal")
                            .respond();

                    return null;
                });
    }

    private User checkForMentionedUser(SlashCommandInteraction interaction) {
        User user = null;
        if (CollectionUtils.isEmpty(interaction.getArguments()))
            user = interaction.getUser();
        else {
            if (interaction.getArguments().get(0).getUserValue().isPresent())
                user = interaction.getArguments().get(0).getUserValue().get();
        }

        return user;
    }

    public static EmbedBuilder buildEmbed(String gif, String message) {
        return new EmbedBuilder()
                .setTitle(message)
                .setImage(gif)
                .setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
                .setColor(Color.MAGENTA)
                .setTimestampToNow();
    }
}
