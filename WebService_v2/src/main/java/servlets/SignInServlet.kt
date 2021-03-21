package servlets

import account.AccountService
import java.io.IOException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SignInServlet(private val accountService: AccountService) : HttpServlet() {
    @Throws(IOException::class)
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val login = req.getParameter("login")
        val pass = req.getParameter("password")

        resp.contentType = "application/json;charset=utf-8"
        if (login == null || pass == null) {
            resp.status = HttpServletResponse.SC_BAD_REQUEST
            return
        }

        val user = accountService.getUserByLogin(login)
        if (user?.pass != pass) {
            resp.status = HttpServletResponse.SC_UNAUTHORIZED
            resp.writer.println("Unauthorized")
            return
        }

        resp.writer.println("Authorized: $login")
    }
}