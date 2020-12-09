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
 * Homepage Servlet after login
 */
public class Homepage extends HttpServlet {
    /**
     * Get a request and generate a response
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException for failed or interrupted I/O operations
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Get cookie
        Cookie[] cookies = request.getCookies();
        String cookieVal = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("name")) {
                cookieVal = cookie.getValue();
            }
        }

        // Show library
        StringBuilder sb = get_html(cookieVal);

        // Set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        String content = getContent();

        out.println(content);
        out.println(sb.toString());
    }

    /**
     * Get content from HTML file
     * @return result.toString()
     */
    public String getContent() {
        StringBuilder result = new StringBuilder();
        try {
            Scanner sc = new Scanner(new File("src/homepage.html"));

            while (sc.hasNextLine()) {
                result.append(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return result.toString();
    }

    /**
     * html codes
     * @param cookieVal String username
     * @return sb StringBuilder consisting of the html code
     */
    public StringBuilder get_html(String cookieVal){
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
        .append(cookieVal)
        .append("'s playlist:")
        .append("</h2><table><tr><th>ID</th><th>TITLE</th><th>ALBUM</th><th>ARTIST</th></tr>")
        .append(playlistDB())
        .append("</table></div>");
        return sb;
    }

    /**
     * Get playlist table from the music.db and return a html table
     * @return sb StringBuilder
     */
    public StringBuilder playlistDB(){
        StringBuilder sb = new StringBuilder();

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery("SELECT * FROM playlist");
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String sName = rs.getString("song_name");
                String albumName = rs.getString("albums_name");
                String artistName = rs.getString("artists_name"); // use a label in html
                sb.append("<tr><td>").append(id)
                        .append("</td><td>").append(sName)
                        .append("</td><td>").append(albumName)
                        .append("</td><td>").append(artistName).append("</td></tr>");
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
}