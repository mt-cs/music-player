import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * When your servlet is responding to a request to display an artist or album, have it call out to The AudioDB,
 * get back the description, and include that in the result you are returning.
 * (You'll need to open a separate URLConnection, read the results, and then hand that string to your JSON parser.)
 */
public class URLTester {
    public static String getAPI(String artist){
        return "https://theaudiodb.com/api/v1/json/1/search.php?s=" + artist;
    }

    public static StringBuilder audioDB(String artistName) throws IOException, ParseException {
        String urlString = getAPI(artistName);
        URL u = null;
        try {
            u = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection connection = null;
        try {
            connection = u.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
        int code = httpsConnection.getResponseCode();
        StringBuilder sb = new StringBuilder();
        String message = httpsConnection.getResponseMessage();
        System.out.println(code + " " + message);
        if (code != HttpURLConnection.HTTP_OK) {
            return sb;
        }

        InputStream instream = connection.getInputStream();
        Scanner in = new Scanner(instream);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            sb.append(line);
        }

        StringBuilder bio = new StringBuilder();
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(sb.toString());
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray artist = (JSONArray) jsonObject.get("artists");
        for (Object o : artist) {
            JSONObject ob = (JSONObject) jsonParser.parse(o.toString());
            bio.append(ob.get("strBiographyEN"));
        }
        return bio;
    }

    public static void main(String[] args) throws IOException, ParseException {
        System.out.println(audioDB("ColdPlay").toString());
    }
}
