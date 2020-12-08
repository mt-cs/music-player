import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SearchServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String search = request.getParameter("search");
        System.out.println(search);

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        String resp = "<br><b><div style=\"color:SlateGray;padding-left:20px;\">" +
                search + " doesn't exist.<br></br>";
        resp += "<button onclick=\"location.href='http://localhost:8081/homepage'\">Homepage</button></div></b>";
        out.println(resp);
    }


}
