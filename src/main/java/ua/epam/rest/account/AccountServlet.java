package ua.epam.rest.account;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import ua.epam.model.Account;
import ua.epam.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@WebServlet(name = "AccountServlet", urlPatterns = "/api/v1/accounts")
public class AccountServlet extends HttpServlet {

    private AccountService accountService = new AccountService();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Account> accountList = new LinkedList<>(accountService.getAll().values());
            String accountsJsonString = this.gson.toJson(accountList);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(accountsJsonString);
            out.flush();
            log.info("AccountServlet - GET");
        } catch (Exception e) {
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.sendError(500);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            accountService.create(gson.fromJson(req.getReader(), Account.class));
            log.info("AccountServlet - POST");
        } catch (Exception e) {
            log.error("AccountServlet: Exeption occured while creating account");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            accountService.update(gson.fromJson(
                    req.getReader(), Account.class),
                    Long.parseLong(req.getParameter("id")));
            log.info("AccountServlet - PUT");
        } catch (Exception e) {
            log.error("AccountServlet: Exeption occured while updating account");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            accountService.delete(Long.parseLong(req.getParameter("id")));
            log.info("AccountServlet - DELETE");
        } catch (Exception e) {
            log.error("AccountServlet: Exeption occured while deleting account");
        }
    }
}
