import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * // Illustrates how to do an HTTP GET
 */
public class URLTester {
    public static String getAPI(String artist){
        String urlString = "https://theaudiodb.com/api/v1/json/1/search.php?s=" + artist;
        return urlString;
    }

    public static void main(String[] args) throws IOException {
        //String urlString = "https://theaudiodb.com/api/v1/json/1/search.php?s=";
        String urlString = getAPI("Coldplay");
        URL u = new URL(urlString);
        URLConnection connection = u.openConnection();

        HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
        int code = httpsConnection.getResponseCode();

        String message = httpsConnection.getResponseMessage();
        System.out.println(code + " " + message);
        if (code != HttpURLConnection.HTTP_OK) {
            return;
        }

        InputStream instream = connection.getInputStream();
        Scanner in = new Scanner(instream);
        StringBuilder sb = new StringBuilder();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            sb.append(line);
        }
    }
}
