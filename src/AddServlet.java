import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Shows form asking for user input to add song
 */
public class AddServlet extends HttpServlet {
    /**
     * Get a request and generate a response
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException for failed or interrupted I/O operations
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // The servlet should check the cookie to make sure the user is logged in.
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
        if (!cookieVal.equals("")){
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(getContent("beat_header.html"));
            out.println(getContent("add.html"));
        } else {
            response.sendRedirect("/login");
        }
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
