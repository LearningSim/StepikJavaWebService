import account.AccountService;
import account.UserProfile;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.Frontend;
import servlets.Mirror;
import servlets.SessionServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        var server = new Server(8080);

        server.setHandler(createServletHandler());
        server.insertHandler(createResourceHandler());

        server.start();
        System.out.println("Server started");
        server.join();
    }

    private static ServletContextHandler createServletHandler() {
        var servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletHandler.addServlet(Frontend.class, "/start");
        servletHandler.addServlet(Mirror.class, "/mirror");

        var accountService = new AccountService();
        accountService.addUser(new UserProfile("admin"));
        accountService.addUser(new UserProfile("test"));
        servletHandler.addServlet(new ServletHolder(new SessionServlet(accountService)),"/api/v1/sessions");
        return servletHandler;
    }

    private static ResourceHandler createResourceHandler() {
        var resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("public_html");
        return resourceHandler;
    }
}
