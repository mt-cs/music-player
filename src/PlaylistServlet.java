import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PlaylistServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String song = request.getParameter("song_name");
        String artist = request.getParameter("artist_name");
        String album = request.getParameter("album_name");
        System.out.println(song+ artist+ album);

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        String resp = "<br><b><div style=\"color:SlateGray;padding-left:20px;\">" +
                song + ", " + album + " by " + artist + " has been added to your playlist.<br></br>";
        resp += "<button onclick=\"location.href='http://localhost:8081/homepage'\">Homepage</button></div></b>";
        out.println(resp);
    }
}
