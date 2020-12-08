import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class PlaylistServlet extends HttpServlet {
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

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String song = request.getParameter("song_name");
        String artist = request.getParameter("artist_name");
        String album = request.getParameter("album_name");
        String resp;

        if (addPlaylist(song, artist, album)){
            resp = "<br><b> <div style=\"color:SlateGray;padding-left:20px;\">" +
                    song + ", " + album + " by " + artist + " has been added to your playlist.<br></br>";
        } else {
            resp = "<br><b> <div style=\"color:SlateGray;padding-left:20px;\">" +
                    song + " already exists.<br></br>";
        }
        resp += "<button onclick=\"location.href='http://localhost:8081/homepage'\">Homepage</button></div></b>";

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println(resp);
    }
}
