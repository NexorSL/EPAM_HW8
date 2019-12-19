package ua.epam.controller;

import ua.epam.model.Account;
import ua.epam.model.Developer;
import ua.epam.model.Skill;
import ua.epam.repository.javaIOImpl.JavaIODeveloperRepositoryImpl;

import java.util.Set;

public class DeveloperController {
    private JavaIODeveloperRepositoryImpl developerRepository = new JavaIODeveloperRepositoryImpl();
    public boolean addDeveloper(String name, String lastName, Set<Skill> skillSet, Account account) {
        Developer developer = new Developer(developerRepository.getLastIndex()+1, name, lastName, skillSet, account);
        return developerRepository.create(developer);
    }

//    public boolean updateDeveloperById() {
//        return developerRepository.update(d, key);
//    }
//
//    public boolean deleteDeveloperById(Long id) {
//        return developerRepository.delete(id);
//    }
//
//    public Account getDeveloperById(Long id) {
//        return developerRepository.getById(id);
//    }
//
    public void getAll() {
        developerRepository.print();
    }
    public Long getLastIndex(){
        return developerRepository.getLastIndex();
    }
}
