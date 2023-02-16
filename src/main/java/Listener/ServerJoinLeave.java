package Listener;

import Command.Command;
import Database.Servers;
import Management.BotInfo;
import Model.dto.ServerDto;
import lombok.Getter;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Database.InitDatabase.saveDatabase;

public class ServerJoinLeave extends Command {
    @Getter
    private static Map<String, Servers> data = new HashMap();

    public ServerJoinLeave(DiscordApi api) {
        super(api);
        api.addServerJoinListener(event ->
                serverJoin(event.getServer()));
        api.addServerLeaveListener(event ->
                serverLeave(event.getServer()));
    }

    @SneakyThrows
    public void serverJoin(Server server) {
        if (checkForServerId(server.getIdAsString()))
            return;

        ServerDto serverDto = prepareNewServer(server);
        BotInfo.setServerCount(BotInfo.getServerCount() + 1);


        //TODO Replace this with a database table log
        //System.out.println("The server " + server.getName() + " joined with ID of " + server.getIdAsString() + ", with " + server.getMembers().size() + " members!");
        saveDatabase();
    }

    private Boolean checkForServerId(String idAsString) {

    }

    public static ServerDto prepareNewServer(Server server) {
        ServerDto serverDto = new ServerDto();

        serverDto.setId(server.getIdAsString());
        serverDto.setPrefix("$");

        serverDto.setWelcomeEnabled(Boolean.FALSE);
        serverDto.setWelcomeChannel(null);
        serverDto.setWelcomeMessage(null);

        serverDto.setCountingChannelId(null);

        serverDto.setUwuEnabled(Boolean.FALSE);
        serverDto.setUwuIgnoredChannels(new ArrayList<>());

        serverDto.setAutoRoleEnabled(Boolean.FALSE);
        serverDto.setAutoRoles(new ArrayList<>());

        return serverDto;
    }

    public void serverLeave(Server server) {
        data.remove(server.getIdAsString());
        BotInfo.setServerCount(BotInfo.getServerCount() - 1);
        System.out.println("Server " + server.getName() + " has left with id of " + server.getIdAsString());
    }
}
