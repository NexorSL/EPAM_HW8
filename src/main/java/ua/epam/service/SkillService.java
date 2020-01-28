package ua.epam.service;

import ua.epam.model.Skill;
import ua.epam.repository.jdbc.JdbcSkillRepository;

import java.util.Map;

public class SkillService {
    private JdbcSkillRepository skillRepository = new JdbcSkillRepository();

    public Skill create(Skill skill){
        return skillRepository.create(skill);
    }
    public Skill getById(Long id){
        return skillRepository.getById(id);
    }
    public boolean update(Skill skill, Long key){
        return skillRepository.update(skill, key);
    }
    public boolean delete(Long id){
        return skillRepository.delete(id);
    }
    public Map<Long, Skill> getAll(){
        return skillRepository.getAll();
    }
}
