import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Shows form asking for user input to add song
 */
public class AddServlet extends BaseServlet {
    /**
     * Get a request and generate a response
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException for failed or interrupted I/O operations
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // The servlet should check the cookie to make sure the user is logged in.
        BaseServlet bs = new BaseServlet();
        String cookieVal = bs.getCookie(request, response);
        if (!cookieVal.equals("")){
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(bs.getContent("beat_header.html"));
            out.println(bs.getContent("add.html"));
        } else {
            response.sendRedirect("/login");
        }
    }
}
