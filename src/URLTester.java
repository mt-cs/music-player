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
    /**
     * Get url from the audioDB website
     * @param artist String artist name
     * @return url String
     */
    public static String getAPI(String artist){
        return "https://theaudiodb.com/api/v1/json/1/search.php?s=" + artist;
    }

    /**
     * Return biography result from the website
     * @param artistName String artist name
     * @return bio StringBuilder
     * @throws IOException for failed or interrupted I/O operations
     * @throws ParseException for failed or interrupted Parse operations
     */
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
