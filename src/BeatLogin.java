import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Login servlet for the music library
 */

public class BeatLogin extends HttpServlet {
    /**
     * Get content from HTML file
     * @return result.toString()
     */
    public String getContent() {
        StringBuilder result = new StringBuilder();
        try {
            Scanner sc = new Scanner(new File("src/login.html"));

            while (sc.hasNextLine()) {
                result.append(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return result.toString();
    }

    /**
     * Get a request and generate a response
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException for failed or interrupted I/O operations
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        String content = getContent();

        out.println(content);
    }
}