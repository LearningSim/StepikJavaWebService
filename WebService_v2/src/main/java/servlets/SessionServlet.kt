package servlets

import account.AccountService
import com.google.gson.Gson
import java.io.IOException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SessionServlet(private val accountService: AccountService) : HttpServlet() {
    @Throws(IOException::class)
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val sessionId = req.session.id
        val user = accountService.getUserBySessionId(sessionId)

        resp.contentType = "application/json;charset=utf-8"
        if (user == null) {
            resp.status = HttpServletResponse.SC_UNAUTHORIZED
        } else {
            val json = Gson().toJson(user)
            resp.writer.println(json)
        }
    }

    @Throws(IOException::class)
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val login = req.getParameter("login")
        val pass = req.getParameter("pass")

        resp.contentType = "application/json;charset=utf-8"
        if (login == null || pass == null) {
            resp.status = HttpServletResponse.SC_BAD_REQUEST
            return
        }

        val user = accountService.getUserByLogin(login)
        if (user?.pass != pass) {
            resp.status = HttpServletResponse.SC_UNAUTHORIZED
            return
        }

        accountService.addSession(req.session.id, user)
        val json = Gson().toJson(user)
        resp.writer.println(json)
    }

    @Throws(IOException::class)
    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
        val sessionId = req.session.id
        val user = accountService.getUserBySessionId(sessionId)

        resp.contentType = "application/json;charset=utf-8"
        if (user == null) {
            resp.status = HttpServletResponse.SC_UNAUTHORIZED
        } else {
            accountService.deleteSession(sessionId)
            resp.writer.println("Goodbye!")
        }
    }
}