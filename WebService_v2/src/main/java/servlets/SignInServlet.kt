package servlets;

import account.AccountService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var login = req.getParameter("login");
        var pass = req.getParameter("password");

        resp.setContentType("application/json;charset=utf-8");
        if (login == null || pass == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        var user = accountService.getUserByLogin(login);
        if (user == null || !user.getPass().equals(pass)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().println("Unauthorized");
            return;
        }

        resp.getWriter().println("Authorized: " + login);
    }
}
