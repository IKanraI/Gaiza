package client;

import org.javacord.api.DiscordApi;

public class SlashCommandManager {

    public void createAdminSlashCommands(DiscordApi api) {
//        SlashCommand welcome = SlashCommand.with("welcome", "Welcome channel management",
//                Arrays.asList(
//                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND_GROUP,"enable", "Enable or disable the welcome module for the server",
//                                Arrays.asList(
//                                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "active", "Active the welcome module for the bot",
//                                                Arrays.asList(
//                                                        SlashCommandOption.createWithChoices(SlashCommandOptionType.DECIMAL, "permission", "The permission to enable or disable the module", true,
//                                                                Arrays.asList(
//                                                                        SlashCommandOptionChoice.create("disable", 0),
//                                                                        SlashCommandOptionChoice.create("enable", 1)))
//
//
//                                )))),
//                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND_GROUP, "message", "Add or modify the welcome message for the server",
//                                Arrays.asList(
//                                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "set", "Set the welcome message of the server",
//                                                Arrays.asList(
//                                                        SlashCommandOption.create(SlashCommandOptionType.STRING, "text", "Add welcome message text. Use <<mention>> to mention the new member", true))),
//                                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "get", "Show the welcome message of the server")
//                                )),
//                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND_GROUP, "info", "Check the information of the welcome module",
//                                Arrays.asList(
//                                        SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "show", "Gets all the information for the welcome message module")
//                                )),
//                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND_GROUP, "channel", "Add or modify the welcome message channel",
//                                Arrays.asList(
//                                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "set", "Set the welcome channel of the server",
//                                                Arrays.asList(
//                                                        SlashCommandOption.create(SlashCommandOptionType.CHANNEL, "name", "Send the channel name", true)
//                                                )),
//                                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "show", "Show the channel for welcome messages")
//                                )),
//                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "embedded", "Choose whether to embed the welcome message or not",
//                                Arrays.asList(
//                                SlashCommandOption.createWithChoices(SlashCommandOptionType.DECIMAL, "enabled", "Change the status of the welcome message embed", true,
//                                        Arrays.asList(
//                                                SlashCommandOptionChoice.create("disable", 0),
//                                                SlashCommandOptionChoice.create("enable", 1)))
//                                        ))))
//                .createGlobal(api)
//                .join();
    }

    public void createSlashCommands(DiscordApi api) {
//        SlashCommand avatar = SlashCommand.with("avatar", "Return image of mentioned users avatar",
//                        Collections.singletonList(SlashCommandOption.create(SlashCommandOptionType.USER, "user", "Image of user avatar you would like to see", false))
//
//                )
//                .createGlobal(api)
//                .join();
//
//
//        SlashCommand define = SlashCommand.with("define", "Return urban dictionary definition",
//                        Collections.singletonList(SlashCommandOption.create(SlashCommandOptionType.STRING, "term", "Term you would like to search in urban dictionary", true))
//
//                )
//                .createGlobal(api)
//                .join();
//
//        SlashCommand gif = SlashCommand.with("gif", "Return a random gif from tenor",
//                        Collections.singletonList(SlashCommandOption.create(SlashCommandOptionType.STRING, "searchTerm", "Type of gif you would like to search", true))
//
//                )
//                .createGlobal(api)
//                .join();
//
//        SlashCommand github = SlashCommand.with("github", "Return the github link to view all my pretty parts")
//                .createGlobal(api)
//                .join();
//
//        SlashCommand invite = SlashCommand.with("invite", "Returns an invite for the bot to join your server")
//                .createGlobal(api)
//                .join();
//
//        SlashCommand report = SlashCommand.with("report", "Send a message to the support channel about a bug or issue",
//                        Collections.singletonList(SlashCommandOption.create(SlashCommandOptionType.STRING, "message", "Write a descriptive message about the issue or a nice message :)", true))
//
//                )
//                .createGlobal(api)
//                .join();

//        SlashCommand roll =
//                SlashCommand.with("roll", "Roll a random number between 1 and the number you choose",
//                        Arrays.asList(
//                                SlashCommandOption.create(SlashCommandOptionType.STRING, "MaxNumber", "Pick a number greater than 1)", true)))
//                        .createGlobal(api)
//                        .join();



//        SlashCommand flip = SlashCommand.with("flip", "Flip a coin")
//                .createGlobal(api)
//                .join();
//
//        SlashCommand support = SlashCommand.with("support", "Sends an invite to the support server")
//                .createGlobal(api)
//                .join();
//
//        SlashCommand hug = SlashCommand.with("hug", "Hug a mentioned user",
//                        Collections.singletonList(SlashCommandOption.create(SlashCommandOptionType.USER, "user", "User you would like to hug", false))
//
//                )
//                .createGlobal(api)
//                .join();
//
//        SlashCommand kiss = SlashCommand.with("kiss", "Kiss a mentioned user",
//                        Collections.singletonList(SlashCommandOption.create(SlashCommandOptionType.USER, "user", "User you would like to kiss", false))
//
//                )
//                .createGlobal(api)
//                .join();
//
//        SlashCommand laugh = SlashCommand.with("laugh", "Laugh at a mentioned user",
//                        Collections.singletonList(SlashCommandOption.create(SlashCommandOptionType.USER, "user", "User you would like to laugh at", false))
//
//                )
//                .createGlobal(api)
//                .join();
//
//        SlashCommand meow = SlashCommand.with("meow", "Meow at a mentioned user",
//                        Collections.singletonList(SlashCommandOption.create(SlashCommandOptionType.USER, "user", "User you would like to meow at", false))
//
//                )
//                .createGlobal(api)
//                .join();
//
//        SlashCommand pat = SlashCommand.with("pat", "Pat a mentioned user",
//                        Collections.singletonList(SlashCommandOption.create(SlashCommandOptionType.USER, "user", "User you would like to pat", false))
//
//                )
//                .createGlobal(api)
//                .join();
//
//        SlashCommand poke = SlashCommand.with("poke", "Poke a mentioned user",
//                        Collections.singletonList(SlashCommandOption.create(SlashCommandOptionType.USER, "user", "User you would like to poke", false))
//
//                )
//                .createGlobal(api)
//                .join();
//
//        SlashCommand slap = SlashCommand.with("slap", "Slap a mentioned user",
//                        Collections.singletonList(SlashCommandOption.create(SlashCommandOptionType.USER, "user", "User you would like to slap", false))
//
//                )
//                .createGlobal(api)
//                .join();
//
//        SlashCommand smug = SlashCommand.with("smug", "Look smugly at a mentioned user",
//                        Collections.singletonList(SlashCommandOption.create(SlashCommandOptionType.USER, "user", "User you would like to look at smugly", false))
//
//                )
//                .createGlobal(api)
//                .join();
//
//        SlashCommand cuddle = SlashCommand.with("cuddle", "Cuddle a mentioned user",
//                        Collections.singletonList(SlashCommandOption.create(SlashCommandOptionType.USER, "user", "User you would like to cuddle", false))
//
//                )
//                .createGlobal(api)
//                .join();
//
//        SlashCommand stare = SlashCommand.with("stare", "Stare at a mentioned user",
//                        Collections.singletonList(SlashCommandOption.create(SlashCommandOptionType.USER, "user", "User you would like to stare at", false))
//
//                )
//                .createGlobal(api)
//                .join();

    }
}
