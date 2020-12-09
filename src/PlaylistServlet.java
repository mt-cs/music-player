import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

/**
 * Add song into playlist table
 */
public class PlaylistServlet extends HttpServlet {
    /**
     * check if logged in, add song to playlist
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException failed or interrupted I/O operations
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Cookie[] cookies = request.getCookies();
        String cookieVal = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("name")) {
                cookieVal = cookie.getValue();
            }
        }
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
    public boolean addPlaylist(String song, String artist, String album){
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery("SELECT * FROM playlist WHERE song_name='" + song + "';");
            if (rs.next()) {
                return false;
            } else {
                statement.executeUpdate("insert into playlist(song_name, artists_name, albums_name) values ('" + song + "', '" + artist + "', '" + album + "');");
                return true;
            }
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return false;
    }

    /**
     * Get content from HTML file
     * @return result.toString()
     */
    public String getContent(String filename) {
        StringBuilder result = new StringBuilder();
        try {
            Scanner sc = new Scanner(new File("src/" + filename));

            while (sc.hasNextLine()) {
                result.append(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return result.toString();
    }
}
