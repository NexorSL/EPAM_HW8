package ua.epam.rest.developer;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import ua.epam.model.Developer;
import ua.epam.service.DeveloperService;

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
@WebServlet(name = "DeveloperServlet", urlPatterns = "/api/v1/developers")
public class DeveloperServlet extends HttpServlet {

    private static final String INVALID_ID_PARAMETER = "Invalid id parameter";
    private DeveloperService developerService = new DeveloperService();

    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Developer> developerList = new LinkedList<>(developerService.getAll().values());
            String developersJsonString = this.gson.toJson(developerList);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(developersJsonString);
            out.flush();
            log.info("DeveloperServlet - GET");
        } catch (Exception e) {
            sendError(resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            developerService.create(gson.fromJson(req.getReader(), Developer.class));
            log.info("DeveloperServlet - POST");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            developerService.update(gson.fromJson(
                    req.getReader(), Developer.class),
                    Long.parseLong(req.getParameter("id")));
            log.info("DeveloperServlet - PUT");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("id") == null) {
                log.error(INVALID_ID_PARAMETER);
                resp.sendError(400, INVALID_ID_PARAMETER);
            } else {
                developerService.delete(Long.parseLong(req.getParameter("id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendError(HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.sendError(500);
        out.flush();
    }
}
