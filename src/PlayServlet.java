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
 * PlayServlet that reads HTML audioPlayer
 */
public class PlayServlet extends HttpServlet {
    /**
     * Get a request and generate a response
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException for file not found
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String content = getContent();
        StringBuilder rs = getFile();
        // Set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println(content);
        out.println(rs.toString());

    }

    public StringBuilder getFile(){
        StringBuilder sb = new StringBuilder();
        sb.append("<ul id=\"playlist\">\n");
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery("select files.file_name, songs.name from files inner join songs on files.song_id=songs.id;");
            while (rs.next()) {
                String sName = rs.getString("name");
                String fileName = rs.getString("file_name");
                sb.append("<li><a href=\"songs/").append(fileName).append("\">").append(sName).append("</a> </li>\n");
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
        sb.append("</ul><script src=\"https://code.jquery.com/jquery-2.2.0.js\"></script>\n" +
                "    <script src=\"audioPlayer.js\"></script>\n" +
                "    <script>\n" +
                "        audioPlayer();\n" +
                "        function audioPlayer(){\n" +
                "            $(\"#audioPlayer\")[0].src = $(\"#playlist li a\")[0];\n" +
                "        }    \n" +"$(\"#playlist li a\").click(function(e){\n" +
                "        e.preventDefault();\n" +
                "        $(\"#audioPlayer\")[0].src = this;\n" +
                "        $(\"#audioPlayer\")[0].play();\n" +
                "        $(\"#playlist li\").removeClass(\"current-song\");\n" +
                "        currentSong = $(this).parent().index();\n" +
                "        $(this).parent().addClass(\"current-song\");\n" +
                "    });\n" +
                "\n" +
                "    $(\"#audioPlayer\")[0].addEventListener(\"ended\", function(){\n" +
                "        currentSong++;\n" +
                "        if(currentSong == $(\"#playlist li a\").length)\n" +
                "            currentSong = 0;\n" +
                "        $(\"#playlist li\").removeClass(\"current-song\");\n" +
                "        $(\"#playlist li:eq(\"+currentSong+\")\").addClass(\"current-song\");\n" +
                "        $(\"#audioPlayer\")[0].src = $(\"#playlist li a\")[currentSong].href;\n" +
                "        $(\"#audioPlayer\")[0].play();\n" +
                "    });"+
                "    </script>")
                .append("<button onclick=\"location.href='http://localhost:8081/homepage'\">Homepage</button>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
        return sb;
    }

    /**
     * Get content from HTML file
     * @return result.toString()
     */
    public String getContent() {
        StringBuilder result = new StringBuilder();
        try {
            Scanner sc = new Scanner(new File("src/audioPlayer.html"));

            while (sc.hasNextLine()) {
                result.append(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return result.toString();
    }


}