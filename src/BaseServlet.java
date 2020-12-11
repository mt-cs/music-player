import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

/**
 * A base servlet that contains shared method between all servlets
 */
public class BaseServlet extends HttpServlet {
    /**
     * get cookie
     * @param request HttpServletRequest request
     * @param response HttpServletResponse response
     * @return cookieVal String username
     * @throws IOException for failed or interrupted I/O operations
     */
    public String getCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get cookie
        Cookie[] cookies = request.getCookies();
        String cookieVal = "";
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase("name")) {
                    cookieVal = cookie.getValue();
                }
            }
        } else {
            response.sendRedirect("/login");
        }
        return cookieVal;
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

    /**
     * html style codes
     * @return string Style
     */
    public String get_html_style(){
        return "<style>\n" +
                "table {\n" +
                "  font-family: arial, sans-serif;\n" +
                "  border-collapse: collapse;\n" +
                "  width: 100%;}\n" +
                "td, th {\n" +
                "  border: 1px solid #dddddd;\n" +
                "  text-align: left;\n" +
                "  padding: 8px;\n}\n" +
                "tr:nth-child(even) {\n" +
                "  background-color: #dddddd;\n}\n" +
                "</style><div style=\"color:SlateGray;padding:20px;\">";
    }

    /**
     * Check if data is already in the database
     * @param query String
     * @param update String
     * @return true if doesn't exist, false otherwise
     */
    public static Boolean checkDB(String query, String update){
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return false;
            } else {
                statement.executeUpdate(update);
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
     * Check if data is already in the database
     * @param query String
     * @param update String
     * @return true if exists, false otherwise
     */
    public static Boolean checkDB_true(String query, String update){
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                statement.executeUpdate(update);
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

    /**
     * Get int from database
     * @param query String
     * @param id String
     * @return get_id
     */
    public int getDB(String query, String id) {
        int get_id = 0;
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                get_id = rs.getInt(id);
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
     * Get string from database
     * @param query String
     * @param id String
     * @return str
     */
    public String getString(String query, String id) {
        String str = "";
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                str = rs.getString(id);
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
        return str;
    }

    /**
     * Show a table from the music.db
     * @return sb StringBuilder
     */
    public StringBuilder showDB(String query){
        StringBuilder sb = new StringBuilder();

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery(query);
            while ( rs.next() ) {
                String sName = rs.getString("name");
                String albumName = rs.getString("albums_name");
                String artistName = rs.getString("artists_name"); // use a label in html
                sb.append("</td><td>").append(sName)
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
