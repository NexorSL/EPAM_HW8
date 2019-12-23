package ua.epam.controller;

import ua.epam.model.Skill;
import ua.epam.repository.javaioimpl.JavaIOSkillRepositoryImpl;

import java.util.HashMap;
import java.util.Map;

public class SkillController {
    private JavaIOSkillRepositoryImpl skillRepository = new JavaIOSkillRepositoryImpl();

    public Skill addSkill(String newSkill) {
        Skill skill = new Skill(skillRepository.getLastIndex() + 1, newSkill);
        return skillRepository.create(skill);
    }

    public boolean updateSkillById(Long key, String name) {
        Skill skill = new Skill(key, name);
        return skillRepository.update(skill, key);
    }

    public boolean deleteSkillById(Long id) {
        return skillRepository.delete(id);
    }

    public Skill getDataById(Long id) {
        return skillRepository.getById(id);
    }

    public void getAll() {
        Map<Long, Skill> skillMap = new HashMap<>(skillRepository.getAll());
        for (Map.Entry<Long, Skill> entry : skillMap.entrySet()){
            System.out.println(entry.getValue().getId() + " - " + entry.getValue().getName());
        }
    }
}
