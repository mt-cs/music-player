import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Homepage Servlet after login
 */
public class Homepage extends BaseServlet {
    /**
     * Get a request and generate a response
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException for failed or interrupted I/O operations
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String cookieVal = getCookie(request, response);
        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(getContent("homepage.html"));
        out.println(get_html_style() + get_html(cookieVal));
    }

    /**
     * html codes
     * @param cookieVal String username
     * @return sb StringBuilder consisting of the html code
     */
    public StringBuilder get_html(String cookieVal){
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>")
        .append(cookieVal)
        .append("'s playlist.")
        .append("</h2><table><tr><th>TITLE</th><th>ALBUM</th><th>ARTIST</th></tr>")
        .append(showDB("SELECT * FROM playlist"))
        .append("</table></div>");
        return sb;
    }
}