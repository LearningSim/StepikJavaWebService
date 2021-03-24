package servlets

import com.google.gson.Gson
import dao.UserDao
import org.eclipse.jetty.http.MimeTypes
import java.io.IOException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class PersistentSignUpServlet(private val userDao: UserDao) : HttpServlet() {
    @Throws(IOException::class)
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val login = req.getParameter("login")
        val pass = req.getParameter("password")

        resp.contentType = MimeTypes.Type.APPLICATION_JSON_UTF_8.toString()
        if (login == null || pass == null) {
            resp.status = HttpServletResponse.SC_BAD_REQUEST
            return
        }

        val id = userDao.addUser(login, pass)
        val json = Gson().toJson(userDao.getUser(id))
        resp.writer.println(json)
    }
}