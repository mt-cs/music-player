import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * This class uses Jetty & servlets to implement server for music Library
 */
public class MusicServer {

    private static final int PORT = 8081;

    /**
     * Function that starts the server with correct servlets
     *
     * @throws Exception throws exception if access failed
     */
    public static void main(String[] args) throws Exception {
        Server server = new Server(PORT); // jetty server

        HandlerList hList = new HandlerList();

        ResourceHandler rHandler = new ResourceHandler();
        rHandler.setDirectoriesListed(true);
        rHandler.setResourceBase("/Users/marisatania/IdeaProjects/lab-7-mt-cs/content");

        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(new ServletHolder(new BeatLogin()), "/login");
        handler.addServletWithMapping(new ServletHolder(new LoginServlet()), "/postLogin");
        handler.addServletWithMapping(new ServletHolder(new Homepage()), "/homepage");
        handler.addServletWithMapping(new ServletHolder(new ShowAllSongs()), "/showAllSongs");
        handler.addServletWithMapping(new ServletHolder(new DeleteServlet()), "/delete");
        handler.addServletWithMapping(new ServletHolder(new DeleteConfirm()), "/confirmDelete");
        handler.addServletWithMapping(new ServletHolder(new Deleted()), "/deleted");
        handler.addServletWithMapping(new ServletHolder(new AddServlet()), "/add");
        handler.addServletWithMapping(new ServletHolder(new Added()), "/added");
        handler.addServletWithMapping(new ServletHolder(new PlayServlet()), "/play");

        hList.setHandlers(new Handler[] {rHandler, handler});
        server.setHandler(hList);

        server.start();
        server.join();
    }

}
