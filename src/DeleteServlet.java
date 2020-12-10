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
 * Shows form for delete input
 */
public class DeleteServlet extends HttpServlet {
    /**
     * Get a request and generate a response
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException for failed or interrupted I/O operations
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
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
            out.println(getContent("delete.html"));
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
