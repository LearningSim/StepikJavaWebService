import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {
    public static void main(String[] args) throws Exception {
        var server = new Server(8080);
        var context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(Frontend.class, "/start");
        server.setHandler(context);
        server.start();
        server.join();
    }
}
