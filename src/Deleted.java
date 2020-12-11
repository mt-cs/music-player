import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Ask user to confirm delete
 */
public class Deleted extends BaseServlet {
    String songName;
    String artistName;
    int artistID;

    /**
     * Read a request and generate a response
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException for failed or interrupted I/O operations
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cookieVal = getCookie(request, response);
        if (!cookieVal.equals("")){
            PrintWriter out = response.getWriter();
            //Header
            out.println(getContent("beat_header.html"));
            out.println(getContent("deleted.html"));

            // Body
            songName = request.getParameter("sname");
            artistName = request.getParameter("aname");
            artistID = getArtistID(artistName);

            response.setContentType("text/html");
            StringBuilder sb = new StringBuilder();
            sb.append("<div style=\"color:SlateGray;padding-left:20px;\">");
            if (deleteSongDB(songName, artistName)){
                sb.append(songName).append(" by ").append(artistName).append(" has been deleted.<br></br>");
            } else {
                sb.append(songName).append(" by ").append(artistName).append(" doesn't exist.<br></br>");
            }
            String resp = "<br><b>" + sb.toString() + "</b></div>";
            out.println(resp);
        } else {
            response.sendRedirect("/login");
        }
    }

    /**
     * Get artist id
     * @param artistName String artist name
     * @return get_id int of artist id
     */
    public int getArtistID(String artistName) {
        String query = "SELECT * FROM artists WHERE artists_name='" + artistName + "';";
        return getDB(query, "id");
    }

    /**
     * Delete song from the songs table in music.db
     * @param song String song name
     * @param artistName String artist name
     * @return true if song is deleted, false if song doesn't exist
     */
    public Boolean deleteSongDB(String song, String artistName) {
        String query = "SELECT * FROM songs WHERE name='" + song + "' AND artists_id=" + artistID + ";";
        String update = "DELETE FROM songs WHERE name='" + song + "' AND EXISTS ( SELECT * FROM artists WHERE artists_name='" + artistName + "');";
        return BaseServlet.checkDB_true(query, update);
    }
}
