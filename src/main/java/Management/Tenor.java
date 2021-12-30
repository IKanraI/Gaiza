package Management;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Tenor {

    public static JSONObject getSearchResults(String searchTerm, int limit) {
        //Make search request -- default EN_US
        final String url = String.format("https://g.tenor.com/v1/search?q=%1$s&key=%2$s&limit=%3$s", searchTerm, BotInfo.getTenorApiKey(), limit);

        try {
            return get(url);
        } catch (IOException | JSONException ex) {
            System.err.println("Issue connecting: " + ex.getMessage());
        }
        return null;
    }

    private static JSONObject get(String url) throws IOException, JSONException {
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoInput(Boolean.TRUE);
            connection.setDoOutput(Boolean.TRUE);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            //Handle failure
            //TODO improve error handling - https://tenor.com/gifapi/documentation#responseobjects-codes
            int statusCode = connection.getResponseCode();

            if (statusCode != HttpURLConnection.HTTP_OK && statusCode != HttpURLConnection.HTTP_CREATED) {
                String error = String.format("HTTP Code: '%2$s'", statusCode, url);
                throw new ConnectException(error);
            }

            return parser(connection);
        } catch (Exception ex) {
            System.err.println("Error receiving connection: " + ex.getMessage());
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        return new JSONObject("");
    }

    /**
     * Parse the response into JSONObject
     */
    private static JSONObject parser(HttpURLConnection connection) throws JSONException {
        char[] buffer = new char[1024 * 4];
        int n;
        InputStream stream = null;
        try {
            stream = new BufferedInputStream(connection.getInputStream());
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            StringWriter writer = new StringWriter();
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
            return new JSONObject(writer.toString());
        } catch (IOException ex) {
            System.err.println("Some fucking parsing error happened");
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignored) {
                    System.err.println("Some other fucking error happened");
                }
            }
        }
        return new JSONObject("");
    }
}
