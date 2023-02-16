package Database;

import Model.dto.ServerDto;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bson.Document;
import org.javacord.api.DiscordApi;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class InitDatabase {
    @Getter
    private static Map<String, ServerDto> data = new HashMap();

    private final String uriPath = "C:\\Users\\joelm\\Documents\\JavaProjects\\Hidden\\sqlConnection.txt";

    @SneakyThrows
    public static boolean checkForChanges(String path, String id) {
        Object obj = new JSONParser().parse(new FileReader(path));
        JSONObject read = (JSONObject) obj;

        for (Field f : data.get(id).getClass().getDeclaredFields()) {
            if (!read.get(f.getName()).equals(f.get(data.get(id)))) {
                return true;
            }
        }
        return false;
    }


    @SneakyThrows
    public void initializeServers(DiscordApi api) {
        String uri = Files.readAllLines(Paths.get(uriPath)).get(0);

        try (MongoClient client = MongoClients.create(uri)) {
            MongoDatabase database = client.getDatabase("Gaiza");
            MongoCollection<Document> collection = database.getCollection("Server");

            api.getServers().forEach((server) -> {
                data.put(server.getIdAsString(), ServerDto.prepareNewServer(server));
                Document doc = collection.find(eq("id", server.getIdAsString())).first();

                if (doc == null)
                    collection.insertOne(new Document("Server", ServerDto.prepareNewServer(server)));

                if (target.createNewFile()) {
                    Files.write(Paths.get(target.toString()), data.get(server.getIdAsString()).toJSONString().getBytes());
                } else {
                    data.get(server.getIdAsString()).loadJson(target);
                }


            });
        }
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static void saveDatabase() {
        for (Map.Entry<String, Servers> s : data.entrySet()) {
            String currPath = dbPath.concat(s.getKey() + ".json");

            if (checkForChanges(currPath, s.getKey())) {
                Files.write(Paths.get(currPath), data.get(s.getKey()).toJSONString().getBytes());
            }
        }
    }
}
