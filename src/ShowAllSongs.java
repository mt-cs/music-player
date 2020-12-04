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
                sb.append("<tr><td>").append(id).append("</td><td>").append(sName).append("</td><td>")
                        .append(albumName).append("</td><td>").append(artistName).append("</td></tr>");
            }
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
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
            // Show allSongs

            // Set response content type
            response.setContentType("text/html");
            String sb = "<!DOCTYPE html><html><title>ALL SONG</title><body>" +
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
                    "'s Songs: </h2><table><tr>\n" +
                    "    <th>ID</th>\n" +
                    "    <th>SONG NAME</th>\n" +
                    "    <th>ALBUM</th>\n" +
                    "    <th>ARTIST</th>\n" +
                    "  </tr>" +
                    songDB() +
                    "</table></div></body></html>";
            out.println(sb);
        } else {
            response.sendRedirect("/login");
        }
    }
}
