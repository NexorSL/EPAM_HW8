package ua.epam.controller;

import ua.epam.model.Skill;
import ua.epam.service.SkillService;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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

    public Set<Skill> getAll() {
        for (Map.Entry<Long, Skill> entry : skillService.getAll().entrySet()) {
            System.out.println(entry.getValue().getId() + " - " + entry.getValue().getName());
        }

        return new LinkedHashSet<>(skillService.getAll().values());
    }
}
