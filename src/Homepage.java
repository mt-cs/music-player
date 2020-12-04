import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Homepage Servlet after login
 */

public class Homepage extends HttpServlet {
    Library myLibrary;

    /**
     * Initialize library
     */
    public void init(){
        myLibrary = new Library();
        myLibrary.readFromFile("/Users/marisatania/IdeaProjects/lab-7-mt-cs/src/Test");
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

    public StringBuilder lib_html(String cookieVal){
        ArrayList<Song> allSongs = myLibrary.getSongs();
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
                "</style>");
        sb.append("<div style=\"color:SlateGray;padding:20px;\"><h2>");
        sb.append(cookieVal);
        sb.append("'s Library: </h2><table>");
        for (Song song : allSongs) {
            sb.append(song.toHTML());
        }
        sb.append("</table></div>");
        return sb;
    }

    /**
     * Get a request and generate a response
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException for file not found
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
        StringBuilder sb = lib_html(cookieVal);

        // Set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        String content = getContent();

        out.println(content);
        out.println(sb.toString());
    }
}