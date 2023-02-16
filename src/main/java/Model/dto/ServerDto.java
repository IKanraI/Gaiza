package Model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javacord.api.entity.server.Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerDto implements Serializable {
    String id;
    String prefix;

    Boolean welcomeEnabled;
    String welcomeChannel;
    String welcomeMessage;

    String countingChannelId;

    Boolean uwuEnabled;
    List<String> uwuIgnoredChannels;

    Boolean autoRoleEnabled;
    List<String> autoRoles;

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
}
