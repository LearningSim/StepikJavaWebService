package servlets;

import account.AccountService;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionServlet extends HttpServlet {
    private final AccountService accountService;

    public SessionServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var sessionId = req.getSession().getId();
        var user = accountService.getUserBySessionId(sessionId);

        resp.setContentType("application/json;charset=utf-8");
        if (user == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            var json = new Gson().toJson(user);
            resp.getWriter().println(json);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var login = req.getParameter("login");
        var pass = req.getParameter("pass");

        resp.setContentType("application/json;charset=utf-8");
        if (login == null || pass == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        var user = accountService.getUserByLogin(login);
        if (user == null || !user.getPass().equals(pass)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        accountService.addSession(req.getSession().getId(), user);
        var json = new Gson().toJson(user);
        resp.getWriter().println(json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var sessionId = req.getSession().getId();
        var user = accountService.getUserBySessionId(sessionId);

        resp.setContentType("application/json;charset=utf-8");
        if (user == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            accountService.deleteSession(sessionId);
            resp.getWriter().println("Goodbye!");
        }
    }
}
