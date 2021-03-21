package servlets;

import freemarker.template.Configuration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class Frontend extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var config = new Configuration(Configuration.getVersion());
        config.setDirectoryForTemplateLoading(new File("templates"));
        response.getWriter().println(config.getTemplate("page.html"));
    }
}
