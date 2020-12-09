import javax.servlet.ServletException;
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
 * Ask user to confirm delete
 */
public class Deleted extends HttpServlet {
    String songName;
    String artistName;
    int artistID;

    /**
     * Read a request and generate a response
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException for file not found
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Header
        PrintWriter out = response.getWriter();
        out.println(getContent("src/beat_header.html"));
        out.println(getContent("src/deleted.html"));

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
    }

    /**
     * Get content from HTML file
     * @return result.toString()
     */
    public String getContent(String path) {
        StringBuilder result = new StringBuilder();
        try {
            Scanner sc = new Scanner(new File(path));
            while (sc.hasNextLine()) {
                result.append(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return result.toString();
    }

    public int getArtistID(String artistName) {
        int get_id = 0;
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery("SELECT * FROM artists WHERE artists_name='" + artistName + "';");
            if (rs.next()) {
                get_id = rs.getInt("id");
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

    public Boolean deleteSongDB(String song, String artistName){
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery("SELECT * FROM songs WHERE name='" + song + "' AND artists_id=" + artistID + ";");
            if (rs.next()) {
                statement.executeUpdate("DELETE FROM songs WHERE name='" + song + "' AND EXISTS ( SELECT * FROM artists WHERE artists_name='" + artistName +"');");
                return true;
            } else {
                return false;
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
}
