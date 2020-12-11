import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Search songs in the music.db by song name, artist name, and album name
 */
public class SearchServlet extends BaseServlet {
    /**
     * Get a request and generate a response
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException for failed or interrupted I/O operations
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String cookieVal = getCookie(request, response);
        if (!cookieVal.equals("")){
            response.setContentType("text/html");
            String search = request.getParameter("search");
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.println(getContent("beat_header.html"));
            out.println(get_html_style());
            out.println(get_html(search).toString());
        } else {
            response.sendRedirect("/login");
        }
    }

    /**
     * Search for song name, artist name and album name
     * @param search String search input
     * @param column String column that wants to be searched
     * @return sb StringBuilder
     */
    public StringBuilder searchDB(String search, String column){
        String query = "select songs.name, artists.artists_name, albums.albums_name from albums " +
                "inner join artists on albums.artists_id = artists.id inner join songs on songs.albums_id = albums.id " +
                "WHERE " + column + "='" + search + "';";
        return showDB(query);
    }

    /**
     * Search for song name, artist name and album name
     * @param search String search input
     * @param column String column that wants to be searched
     * @return sb StringBuilder
     */
    public String getArtistName(String search, String column){
        String query = "select songs.name, artists.artists_name, albums.albums_name from albums " +
                "inner join artists on albums.artists_id = artists.id inner join songs on songs.albums_id = albums.id " +
                "WHERE " + column + "='" + search + "';";
        return getString(query,"artists_name");
    }

    /**
     * get html codes
     * @param search data to be searched
     * @return sb StringBuilder
     */
    public StringBuilder get_html(String search){
        String artist_name = getArtistName(search, "songs.name");
        if (artist_name.equals("")){
            artist_name = getArtistName(search, "albums.albums_name");
            if (artist_name.equals("")){
                artist_name = search;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<form action=\"/biography\" method=\"GET\">")
                .append("<table><tr><td><h2>showing songs for: ").append(search).append("</h2></td>")
                .append("<input name=\"artist_name\" value='").append(artist_name).append("' style='visibility:hidden'></input>")
                .append("<td><input type = \"submit\" value=\"GET BIO\"/></form></td></table>")
                .append("<table><tr><th>TITLE</th><th>ALBUM</th><th>ARTIST</th></tr>")
                .append(searchDB(search,"songs.name"))
                .append(searchDB(search,"albums.albums_name"))
                .append(searchDB(search,"artists.artists_name"))
                .append("</table></div>");
        return sb;
    }
}
