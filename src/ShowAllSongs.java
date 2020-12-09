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
 * Show all song from songs table in music.db
 */
public class ShowAllSongs extends HttpServlet {
    /**
     * Handle a GET request
     * @param request HttpServletRequest request
     * @param response HttpServletResponse response
     * @throws IOException failed or interrupted I/O operations
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // The servlet should check the cookie to make sure the user is logged in.
        Cookie[] cookies = request.getCookies();
        String cookieVal = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("name")) {
                cookieVal = cookie.getValue();
            }
        }
        if (!cookieVal.equals("")){
            response.setContentType("text/html");
            out.println(getContent("beat_header.html"));
            out.println(get_html(cookieVal));
        } else {
            response.sendRedirect("/login");
        }
    }

    /**
     * html codes
     * @param cookieVal String username
     * @return sb string
     */
    public String get_html(String cookieVal){
        String sb = "<!DOCTYPE html><html><head>" +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\"" +
                " integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">"+
                "</head><title>ALL SONG</title><body>" +
                "<style>\n" +
                "table {\n" +
                "  font-family: arial, sans-serif;\n" +
                "  border-collapse: collapse;\n" +
                "  width: 100%;\n" +
                "}\n" +
                "\n" +
                "td, th {\n" +
                "  border: 1px solid #dddddd;\n" +
                "  text-align: left;\n" +
                "  padding: 8px;\n" +
                "}\n" +
                "\n" +
                "tr:nth-child(even) {\n" +
                "  background-color: #dddddd;\n" +
                "}\n" +
                "</style>" +
                "<div style=\"color:SlateGray;padding:20px;\"><h2>" +
                cookieVal +
                "'s songs: </h2><table><tr>\n" +
                "    <th>ID</th>\n" +
                "    <th>TITLE</th>\n" +
                "    <th>ALBUM</th>\n" +
                "    <th>ARTIST</th>\n" +
                "    <th>PLAYLIST</th>\n" +
                "  </tr>" +
                songDB().toString() +
                "</table></div></body></html>";
        return sb;
    }

    /**
     * Get all songs from the songs table in music db
     * @return sb, StringBuilder of the html code
     */
    public StringBuilder songDB(){
        StringBuilder sb = new StringBuilder();

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery("select songs.id, songs.name, artists.artists_name, albums.albums_name from albums " +
                    "inner join artists on albums.artists_id = artists.id inner join songs on songs.albums_id = albums.id;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String sName = rs.getString("name");
                String albumName = rs.getString("albums_name");
                String artistName = rs.getString("artists_name"); // use a label in html
                sb.append("<tr><form action=\"/playlist\" method=\"POST\"><td>").append(id)
                        .append("</td><td>").append(sName).append("<input name=\"song_name\" value='").append(sName).append("' style='visibility:hidden'></input>")
                        .append("</td><td>").append(albumName).append("<input name=\"album_name\" value='").append(albumName).append("' style='visibility:hidden'></input>")
                        .append("</td><td>").append(artistName).append("<input name=\"artist_name\" value='").append(artistName).append("' style='visibility:hidden'></input></td>")
                        .append("<td><input type = \"submit\" value=\"ADD\"/></td></form></tr>");
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
        return sb;
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
