import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * confirm add servlet
 */
public class Added extends HttpServlet {
    int artist_id;
    int albums_id;

    public Boolean addSongDB(String song, int artistID, int albumID){
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery("SELECT * FROM songs WHERE name='" + song + "';");
            if (rs.next()) {
                return false;
            } else {
                ResultSet rs2 = statement.executeQuery("SELECT COUNT (*) as 'count' FROM songs;");
                int id = rs2.getInt("count") + 1;
                statement.executeUpdate("insert into songs values ("+ id +", '" + song + "', " + albumID + ", " + artistID + ");");
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

    public int checkDB(String data, String column, String table) {
        int get_id = 0;
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery("SELECT * FROM " + table + " WHERE " + column + "='" + data + "';");
            if (rs.next()) {
                get_id = rs.getInt("id");
            } else {
                ResultSet rs2 = statement.executeQuery("SELECT COUNT (*) as 'count' FROM " + table +";");
                int id = rs2.getInt("count") + 1;
                if (table.equals("artists")){
                    statement.executeUpdate("insert into artists values ("+ id +", '" + data + "');");
                } else if (table.equals("albums")){
                    statement.executeUpdate("insert into albums values ("+ id +", '" + data + "', " + this.artist_id +");");
                }
                get_id = id;
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
        return get_id;
    }
    /**
     * Read a request and generate a response
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException for file not found
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String song = request.getParameter("sname");
        String artist = request.getParameter("aname");
        String album = request.getParameter("album");
        String resp;
        this.artist_id = checkDB(artist, "artists_name", "artists");
        this.albums_id = checkDB(album, "albums_name", "albums");
        if (addSongDB(song, artist_id, albums_id)){
            resp = "<br><b> <div style=\"color:SlateGray;padding-left:20px;\">" +
                    song + ", " + album + " by " + artist + " has been added<br></br>";
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
