package userCommands;

import util.BotInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
        }
    }

    private void stareMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "The mirror adds 10 pounds";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getInstance().getBotId()))
            message = ":eyes:";
        else
            message = user.getMentionTag() + " you are being stared at by " + interaction.getUser().getName();

        respondToInteraction(interaction, "stare", message);
    }

    private void cuddleMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "How are you going to snuggle yourself? That's so sad :(";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getInstance().getBotId()))
            message = "Oh you want to snuggle me? Thank you so much <3";
        else
            message = user.getMentionTag() + " you are being cuddled by " + interaction.getUser().getName();

        respondToInteraction(interaction, "cuddle", message);
    }

    private void smugMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "What are you looking so smug about?";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getInstance().getBotId()))
            message = "What... did you... do?";
        else
            message = user.getMentionTag() + " you are being looked at smugly by " + interaction.getUser().getName();

        respondToInteraction(interaction, "smug", message);
    }

    private void slapMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "Stop hitting yourself, stop hitting yourself...";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getInstance().getBotId())) {
            interaction.createImmediateResponder()
                    .setContent("You will not abuse me anymore. I won't let you.")
                    .respond();
            return;
        } else
            message = user.getMentionTag() + " you have been slapped by " + interaction.getUser().getName();

        respondToInteraction(interaction, "slap", message);
    }

    private void pokeMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "This is strange, but okay I guess";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getInstance().getBotId()))
            message = "Hello :) How may I help you?";
        else
            message = user.getMentionTag() + " you have been poked by " + interaction.getUser().getName();

        respondToInteraction(interaction, "poke", message);
    }

    private void meowMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "Who are you meowing to. Are you okay?";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getInstance().getBotId()))
            message = "Nya!";
        else
            message = user.getMentionTag() + " you have been meowed at by " + interaction.getUser().getName();

        respondToInteraction(interaction, "meow", message);
    }

    private void laughMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "It's always healthy to laugh at yourself :)";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getInstance().getBotId()))
            message = ":( why are you laughing at me? I.. I'm sorry :(";
        else
            message = user.getMentionTag() + " you have been mocked by " + interaction.getUser().getName();

        respondToInteraction(interaction, "laugh", message);
    }

    private void kissMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "Must you really give yourself a kiss? That's so sad :(";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getInstance().getBotId()))
            message = "Oh you want to give me a kiss? Thank you so much <3";
        else
            message = user.getMentionTag() + " you have been kissed by " + interaction.getUser().getName();

        respondToInteraction(interaction, "kiss", message);
    }

    private void hugMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "Must you really give yourself a hug? That's so sad :(";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getInstance().getBotId()))
            message = "Oh you want to give me a hug? Thank you so much <3";
        else
            message = user.getMentionTag() + " you have been hugged by " + interaction.getUser().getName();

        respondToInteraction(interaction, "hug", message);
    }

    private void patMentionedUser(SlashCommandInteraction interaction) {
        User user = checkForMentionedUser(interaction);
        String message = "";

        if (StringUtils.equalsIgnoreCase(user.getIdAsString(), interaction.getUser().getIdAsString()))
            message = "Must you really give yourself a headpat? That's so sad :(";
        else if (StringUtils.equalsIgnoreCase(user.getIdAsString(), BotInfo.getInstance().getBotId()))
            message = "Oh you want to give me a headpat? Thank you so much <3";
        else
            message = user.getMentionTag() + " you have been patted by " + interaction.getUser().getName();

        respondToInteraction(interaction, "pat", message);
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

    private void respondToInteraction(SlashCommandInteraction interaction, String term, String message) {
        interaction.createImmediateResponder()
                .setContent(message)
                .addEmbed(buildEmbed(Gif.searchGif("Anime-" + term)))
                .respond()
                .exceptionally(e -> {
                    interaction.createImmediateResponder()
                            .setContent("Algo anda mal")
                            .respond();

                    return null;
                });
    }

    public static EmbedBuilder buildEmbed(String gif) {
        return new EmbedBuilder()
                .setImage(gif)
                .setFooter(BotInfo.getInstance().getBotName(), BotInfo.getInstance().getBotImage())
                .setColor(Color.MAGENTA)
                .setTimestampToNow();
    }
}
