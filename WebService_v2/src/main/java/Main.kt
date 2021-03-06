import account.AccountService
import account.UserProfile
import dao.UserDao
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.ResourceHandler
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import servlets.*

object Main {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val server = Server(8080)

        server.handler = createServletHandler()
        server.insertHandler(createResourceHandler())

        server.start()
        println("Server started")
        server.join()
    }

    private fun createServletHandler(): ServletContextHandler {
        val servletHandler = ServletContextHandler(ServletContextHandler.SESSIONS)
        servletHandler.addServlet(Frontend::class.java, "/start")
        servletHandler.addServlet(Mirror::class.java, "/mirror")

        val accountService = AccountService()
        accountService.addUser(UserProfile("admin"))
        accountService.addUser(UserProfile("test"))

        servletHandler.addServlet(ServletHolder(SessionServlet(accountService)), "/api/v1/sessions")
        servletHandler.addServlet(ServletHolder(SignUpServlet(accountService)), "/signup_o")
        servletHandler.addServlet(ServletHolder(SignInServlet(accountService)), "/signin_o")

        val dbService = DBService()
        dbService.printConnectInfo()
        val userDao = UserDao(dbService)

        servletHandler.addServlet(ServletHolder(PersistentSignUpServlet(userDao)), "/signup")
        servletHandler.addServlet(ServletHolder(PersistentSignInServlet(userDao)), "/signin")

        servletHandler.addServlet(EchoSocketServlet::class.java, "/chat")

        return servletHandler
    }

    private fun createResourceHandler(): ResourceHandler {
        val resourceHandler = ResourceHandler()
        resourceHandler.resourceBase = "public_html"
        return resourceHandler
    }
}