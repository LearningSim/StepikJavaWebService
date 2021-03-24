package servlets

import dao.UserDao
import org.eclipse.jetty.http.MimeTypes
import java.io.IOException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class PersistentSignInServlet(private val userDao: UserDao) : HttpServlet() {
    @Throws(IOException::class)
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val login = req.getParameter("login")
        val pass = req.getParameter("password")

        resp.contentType = MimeTypes.Type.APPLICATION_JSON_UTF_8.toString()
        if (login == null || pass == null) {
            resp.status = HttpServletResponse.SC_BAD_REQUEST
            return
        }

        val user = userDao.getUser(login)
        if (user?.password != pass) {
            resp.status = HttpServletResponse.SC_UNAUTHORIZED
            resp.writer.println("Unauthorized")
            return
        }

        resp.writer.println("Authorized: $login")
    }
}