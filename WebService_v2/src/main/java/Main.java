import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import servlets.Frontend;
import servlets.Mirror;

public class Main {
    public static void main(String[] args) throws Exception {
        var server = new Server(8080);
        var handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.addServlet(Frontend.class, "/start");
        handler.addServlet(Mirror.class, "/mirror");
        server.setHandler(handler);
        server.start();
        server.join();
    }
}
