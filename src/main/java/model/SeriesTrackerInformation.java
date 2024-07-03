package model;

import model.common.Media;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SeriesTrackerInformation {

    @Getter private static final String dbPath = "/home/kanra/projects/Gaiza/bin/Storage/Series/";
//	@Getter private static final String dbPath = "C:\\Users\\joelm\\IdeaProjects\\Gaiza\\bin\\Storage\\Series\\";
    @Getter private  List<Media> movies = new ArrayList<>();
    private final String fileName = "seriesTracker.json";


    public SeriesTrackerInformation() {
        initializeTrackerFile();
    }

    public void initializeTrackerFile() {
        File target = new File(dbPath + fileName);
        try {
            if (!target.createNewFile()) {
                Gson gson = new Gson();
                Reader reader = Files.newBufferedReader(Paths.get(dbPath + fileName));
                movies = gson.fromJson(reader, new TypeToken<List<Media>>() {}.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    private void loadInfoFromDisk() {
        Object obj = new JSONParser().parse(new FileReader(dbPath + fileName));
        JSONObject read = (JSONObject) obj;
        JSONArray movieListJson;


    }

//    public boolean addShowToList() {
//
//    }
}
//Gson gson=newGsonBuilder().setPrettyPrinting().create();
//FileWriterwriter=newFileWriter(dbPath+fileName);
//gson.toJson(movies,writer);
//writer.close();