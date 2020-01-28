package ua.epam.controller;

import ua.epam.model.Skill;
import ua.epam.service.SkillService;

import java.util.Map;

public class SkillController {
    private SkillService skillService = new SkillService();

    public Skill addSkill(String newSkill) {
        Skill skill = new Skill(null, newSkill);
        return skillService.create(skill);
    }

    public boolean updateSkillById(Long key, String name) {
        Skill skill = new Skill(key, name);
        return skillService.update(skill, key);
    }

    public boolean deleteSkillById(Long id) {
        return skillService.delete(id);
    }

    public Skill getDataById(Long id) {
        return skillService.getById(id);
    }

    public void getAll() {
        for (Map.Entry<Long, Skill> entry : skillService.getAll().entrySet()) {
            System.out.println(entry.getValue().getId() + " - " + entry.getValue().getName());
        }
    }
}
