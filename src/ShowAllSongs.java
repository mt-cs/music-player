import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class ShowAllSongs extends HttpServlet {
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
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String sName = rs.getString("name");
                String albumName = rs.getString("albums_name");
                String artistName = rs.getString("artists_name");
                sb.append("<tr><td>").append(id)
                        .append("</td><td><input name=\"song_name\" value='").append(sName).append("' disabled></input>")
                        .append("</td><td><input name=\"album_name\" value='").append(albumName).append("' disabled></input>")
                        .append("</td><td><input name=\"artist_name\" value='").append(artistName).append("' disabled></input></td>")
                        .append("<td><input type = \"submit\" value=\"ADD\"/></td></tr>");
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
                    "'s Songs: </h2><form action=\"/playlist\" method=\"POST\"><table><tr>\n" +
                    "    <th>ID</th>\n" +
                    "    <th>SONG NAME</th>\n" +
                    "    <th>ALBUM</th>\n" +
                    "    <th>ARTIST</th>\n" +
                    "    <th>PLAYLIST</th>\n" +
                    "  </tr>" +
                    songDB() +
                    "</table></form></div></body></html>";
            out.println(sb);
        } else {
            response.sendRedirect("/login");
        }
    }
}
