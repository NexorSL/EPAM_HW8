package ua.epam.controller;

import ua.epam.model.Skill;
import ua.epam.repository.javaIOImpl.JavaIOSkillRepositoryImpl;

public class SkillController {
    private JavaIOSkillRepositoryImpl skillRepository = new JavaIOSkillRepositoryImpl();

    public boolean addSkill(String newSkill) {
        Skill skill = new Skill(Skill.nextId, newSkill);
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
        skillRepository.getAll();
    }
}
