package Listener;

import Command.Command;
import Database.InitDatabase;
import Database.Servers;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.activity.Activity;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.user.User;

import java.util.Map;

public class GoingLiveService {

    @SneakyThrows
    public GoingLiveService(DiscordApi api) {
        api.addUserChangeActivityListener(event ->
                checkForLiveStatus(api, event.getOldActivity().get(), event.getNewActivity().get(), event.getUser()));
    }

    private void checkForLiveStatus(DiscordApi api, Activity oldActivity, Activity newActivity, User user) {
        if (newActivity.getType().equals(ActivityType.STREAMING)) {
            for (Map.Entry<String, Servers> s : InitDatabase.getData().entrySet()) {
                if (Boolean.parseBoolean(s.getValue().getLsEnabled())) {
                    StringBuilder msg = new StringBuilder();
                    msg.append(s.getValue().getLsMessage().replaceAll("<<mention>>", user.getName()));

                    api.getServerById(s.getValue().getId())
                            .get()
                            .getChannelById(s.getValue().getLsChannel())
                            .get()
                            .asServerTextChannel()
                            .get()
                            .sendMessage(msg.toString() + " " + newActivity.getStreamingUrl().get());
                }
            }
        }
    }
}
