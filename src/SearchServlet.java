import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class SearchServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String search = request.getParameter("search");
        StringBuilder resp = get_html(search);
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println(resp.toString());
    }

    public boolean checkDB(String data, String column, String table) {
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery("SELECT * FROM " + table + " WHERE " + column + "='" + data + "';");
            if (rs.next()) {
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

    public StringBuilder searchDB(String search, String column){
        StringBuilder sb = new StringBuilder();
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery("select songs.name, artists.artists_name, albums.albums_name from albums " +
                    "inner join artists on albums.artists_id = artists.id inner join songs on songs.albums_id = albums.id " +
                    "WHERE " + column + "='" + search + "';");
            while (rs.next()) {
                String sName = rs.getString("name");
                String albumName = rs.getString("albums_name");
                String artistName = rs.getString("artists_name");
                sb.append("<tr><td>").append(sName)
                        .append("</td><td>").append(albumName)
                        .append("</td><td>").append(artistName)
                        .append("</td></tr>");
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

    public StringBuilder get_html(String search){
        StringBuilder sb = new StringBuilder();
        sb.append("<style>\n" +
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
                "</style>")
                .append("<div style=\"color:SlateGray;padding:20px;\"><h2>")
                .append("Showing Songs for: ").append(search)
                .append("</h2><table><tr><th>TITLE</th><th>ALBUM</th><th>ARTIST</th></tr>")
                .append(searchDB(search,"songs.name"))
                .append(searchDB(search,"albums.albums_name"))
                .append(searchDB(search,"artists.artists_name"))
                .append("</table><br><button onclick=\"location.href='http://localhost:8081/homepage'\">Homepage</button></div>");
        return sb;
    }
}
