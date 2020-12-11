import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Add song into playlist table
 */
public class PlaylistServlet extends BaseServlet {
    /**
     * check if logged in, add song to playlist
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException failed or interrupted I/O operations
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String cookieVal = getCookie(request, response);
        if (!cookieVal.equals("")){
            String song = request.getParameter("song_name");
            String artist = request.getParameter("artist_name");
            String album = request.getParameter("album_name");
            String resp;

            if (addPlaylist(song, artist, album)){
                resp = "<br><b><div style=\"color:SlateGray;padding-left:20px;\">" +
                        song + ", " + album + " by " + artist + " has been added to your playlist.<br></br></div></b>";
            } else {
                resp = "<br><b><div style=\"color:SlateGray;padding-left:20px;\">" +
                        song + " already exists.<br></br></div></b>";
            }

            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.println(getContent("beat_header.html"));
            out.println(resp);
        } else {
            response.sendRedirect("/login");
        }
    }

    /**
     * Check if song is already in playlist, if not add song to the playlist table in music.db
     * @param song String song name
     * @param artist String artist name
     * @param album String album name
     * @return true if song is added to playlist, false otherwise
     */
    public boolean addPlaylist(String song, String artist, String album) {
        String query = "SELECT * FROM playlist WHERE song_name='" + song + "';";
        String statement = "insert into playlist(song_name, artists_name, albums_name) values ('" +
                song + "', '" + artist + "', '" + album + "');";
        return checkDB(query, statement);
    }
}
