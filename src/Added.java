import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * confirm add servlet
 */
public class Added extends BaseServlet {
    int artist_id;
    int albums_id;

    /**
     * Read a request and generate a response
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException for failed or interrupted I/O operations
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String cookieVal = getCookie(request, response);
        if (!cookieVal.equals("")){
            String song = request.getParameter("sname");
            String artist = request.getParameter("aname");
            String album = request.getParameter("album");
            String resp;
            this.artist_id = updateDB(artist, "artists_name", "artists");
            this.albums_id = updateDB(album, "albums_name", "albums");
            if (addSongDB(song, artist_id, albums_id)){
                resp = "<br><b><div style=\"color:SlateGray;padding-left:20px;\">" +
                        song + ", " + album + " by " + artist + " has been added.<br></br></div></b>";
            } else {
                resp = "<br><b><div style=\"color:SlateGray;padding-left:20px;\">" +
                        song + " already exists.<br></br></div></b>";
            }
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(getContent("beat_header.html"));
            out.println(resp);
        } else {
            response.sendRedirect("/login");
        }
    }

    /**
     * Add song in music.db's songs table
     * @param song String song name
     * @param artistID int artist ID
     * @param albumID int album ID
     * @return true if song is added, false if song doesn't exist
     */
    public Boolean addSongDB(String song, int artistID, int albumID){
        String query = "SELECT * FROM songs WHERE name='" + song + "';";
        String update = "insert into songs (name, albums_id, artists_id) values ('" + song + "', " + albumID + ", " + artistID + ");";
        return checkDB(query, update);
    }

    /**
     * Add into  db if data doesn't exists
     * @param data String
     * @param column String
     * @param table String
     * @return get_id;
     */
    public int updateDB(String data, String column, String table) {
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
}
