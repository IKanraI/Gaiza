package Model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.javacord.api.entity.server.Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static ServerDto mapDocumentToServerDto(Document doc) {
        ServerDto dto = new ServerDto();

        dto.setId(String.valueOf(doc.get("id")));
        dto.setPrefix(String.valueOf(doc.get("prefix")));

        dto.setWelcomeEnabled(Boolean.getBoolean(String.valueOf(doc.get("welcomeEnabled"))));
        dto.setWelcomeChannel(String.valueOf(doc.get("welcomeChannel")));
        dto.setWelcomeMessage(String.valueOf(doc.get("welcomeMessage")));

        dto.setCountingChannelId(String.valueOf(doc.get("countingChannelId")));

        dto.setUwuEnabled(Boolean.getBoolean(String.valueOf(doc.get("uwuEnabled"))));
        dto.setUwuIgnoredChannels( -> {

        }));

        dto.setAutoRoleEnabled(Boolean.getBoolean(String.valueOf(doc.get("autoRoleEnabled"))));
        //dto.setAutoRoles(String.valueOf(doc.get("id")));

        return dto;

    }
}
