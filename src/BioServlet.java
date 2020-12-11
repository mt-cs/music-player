import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class BioServlet extends HttpServlet {
    /**
     * Get a request and generate a response
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException for failed or interrupted I/O operations
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        BaseServlet bs = new BaseServlet();
        String cookieVal = bs.getCookie(request, response);
        if (!cookieVal.equals("")){
            response.setContentType("text/html");
            String search = request.getParameter("artist_name");
            String html = bs.get_html_style();
            try {
                html += get_html(search).toString();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.println(bs.getContent("beat_header.html"));
            out.println(html);
        } else {
            response.sendRedirect("/login");
        }
    }

    /**
     * get html codes
     * @param search data to be searched
     * @return sb StringBuilder
     */
    public StringBuilder get_html(String search) throws IOException, ParseException {
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>")
                .append(search)
                .append("</h2><br><p>").append(audioDB(search).toString())
                .append("</p></div>");
        return sb;
    }

    /**
     * Get url from the audioDB website
     * @param artist String artist name
     * @return url String
     */
    public static String getAPI(String artist){
        artist = artist.replaceAll(" ", "_");
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
        if (artist == null){
            bio.append("The artist is not found in the audioDB.");
        } else {
            for (Object o : artist) {
                JSONObject ob = (JSONObject) jsonParser.parse(o.toString());
                bio.append(ob.get("strBiographyEN"));
            }
        }
        return bio;
    }
}
