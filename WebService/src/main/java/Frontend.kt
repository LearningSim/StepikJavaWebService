import freemarker.template.Configuration
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import freemarker.template.Configuration.VERSION_2_3_28
import java.io.File


class Frontend : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val cfg = Configuration(VERSION_2_3_28)
        cfg.setDirectoryForTemplateLoading(File("templates"))
        resp.writer.println(cfg.getTemplate("page.html"))
    }
}