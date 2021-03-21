package servlets

import java.io.IOException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class Mirror : HttpServlet() {
    @Throws(IOException::class)
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val keyParam = req.getParameter("key")
        resp.writer.println(keyParam)
    }
}