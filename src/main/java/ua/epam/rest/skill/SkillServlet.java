package ua.epam.rest.skill;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import ua.epam.model.Skill;
import ua.epam.service.SkillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
@WebServlet(name = "SkillServlet", urlPatterns = "/api/v1/skills")
public class SkillServlet extends HttpServlet {
    private SkillService skillService = new SkillService();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Set<Skill> skillSet = new LinkedHashSet<>(skillService.getAll().values());
            String skillsJsonString = this.gson.toJson(skillSet);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(skillsJsonString);
            out.flush();
            log.info("SkillServlet - GET");
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
            skillService.create(gson.fromJson(req.getReader(), Skill.class));
            log.info("SkillServlet - POST");
        } catch (Exception e) {
            log.error("SkillServlet: Exeption occured while creating skill");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            skillService.update(gson.fromJson(
                    req.getReader(), Skill.class),
                    Long.parseLong(req.getParameter("id")));
            log.info("SkillServlet - PUT");
        } catch (Exception e) {
            log.error("SkillServlet: Exeption occured while updating skill");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            skillService.delete(Long.parseLong(req.getParameter("id")));
            log.info("SkillServlet - DELETE");
        } catch (Exception e) {
            log.error("SkillServlet: Exeption occured while deleting skill");
        }
    }
}
