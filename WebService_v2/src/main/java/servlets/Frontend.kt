package servlets

import freemarker.template.Configuration
import java.io.File
import javax.servlet.http.HttpServlet
import kotlin.Throws
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class Frontend : HttpServlet() {
    @Throws(IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val config = Configuration(Configuration.getVersion())
        config.setDirectoryForTemplateLoading(File("templates"))
        response.writer.println(config.getTemplate("page.html"))
    }
}