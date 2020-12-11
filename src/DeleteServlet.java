import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Shows form for delete input
 */
public class DeleteServlet extends BaseServlet {
    /**
     * Get a request and generate a response
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException for failed or interrupted I/O operations
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String cookieVal = getCookie(request, response);
        if (!cookieVal.equals("")){
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(getContent("beat_header.html"));
            out.println(getContent("delete.html"));
        } else {
            response.sendRedirect("/login");
        }
    }
}
