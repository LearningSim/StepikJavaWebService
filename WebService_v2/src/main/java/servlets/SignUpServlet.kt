package servlets

import account.AccountService
import account.UserProfile
import com.google.gson.Gson
import java.io.IOException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SignUpServlet(private val accountService: AccountService) : HttpServlet() {
    @Throws(IOException::class)
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val login = req.getParameter("login")
        val pass = req.getParameter("password")

        resp.contentType = "application/json;charset=utf-8"
        if (login == null || pass == null) {
            resp.status = HttpServletResponse.SC_BAD_REQUEST
            return
        }

        val user = UserProfile(login, pass, login)
        accountService.addUser(user)
        val json = Gson().toJson(user)
        resp.writer.println(json)
    }
}