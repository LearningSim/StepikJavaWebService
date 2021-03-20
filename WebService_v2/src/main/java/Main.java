import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {
    public static void main(String[] args) throws Exception {
        var server = new Server(8080);
        var handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.addServlet(Frontend.class, "/start");
        server.setHandler(handler);
        server.start();
        server.join();
    }
}
