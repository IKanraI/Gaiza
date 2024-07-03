package fixThese;

import model.GlobalUserInformation;
import model.InitDatabase;
import util.BotInfo;
import util.UserFineObject;
import lombok.Getter;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Ranks{

    @Getter
    public static String help = "Returns the leaderboards for UWU fines. Usage [prefix]ranks";

    private void displayRanks(DiscordApi api, TextChannel channel, Server server) {

        if (!Boolean.parseBoolean(InitDatabase.getData().get(server.getIdAsString()).getUwu())) {
            channel.sendMessage("The uwu module is disabled");
            return;
        }

        List<UserFineObject> fineList = new ArrayList();
        for (User user : server.getMembers()) {
            fineList.add(
                    new UserFineObject(user.getIdAsString(),
                            UwuListener.getUserFine(GlobalUserInformation.filePath + user.getIdAsString())));
        }

        Collections.sort(fineList);
        channel.sendMessage(buildEmbed(fineList, api)).exceptionally(e -> {
            channel.sendMessage("An issue came up. Use the report command to report this issue");
            return null;
        });
    }

    @SneakyThrows
    private EmbedBuilder buildEmbed(List<UserFineObject> users, DiscordApi api) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("UWU Degeneracy Leaderboards")
                .setFooter(BotInfo.getInstance().getBotName(), BotInfo.getInstance().getBotImage())
                .setTimestampToNow()
                .setColor(Color.MAGENTA);

        int i = 0;
        for (UserFineObject obj : users) {
            i++;
            embed.addField(i + ") " + api.getUserById(obj.getId()).get().getName(),
                    NumberFormat.getCurrencyInstance(Locale.US).format(obj.getFine()));

            if (i == 10) {
                break;
            }
        }

        return embed;
    }
}
