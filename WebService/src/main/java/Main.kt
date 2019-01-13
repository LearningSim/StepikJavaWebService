import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import java.util.logging.Logger

open class Main {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val server = Server(8080)
            val context = ServletContextHandler(ServletContextHandler.SESSIONS)
            server.handler = context
            context.addServlet(ServletHolder(Frontend()), "/authform")
            context.addServlet(ServletHolder(MirrorPage()), "/mirror")

            server.start()
            Logger.getGlobal().info("Server started")
            server.join()
        }
    }
}
