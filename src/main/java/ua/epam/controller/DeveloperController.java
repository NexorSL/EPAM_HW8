package ua.epam.controller;

import ua.epam.model.Account;
import ua.epam.model.AccountStatus;
import ua.epam.model.Developer;
import ua.epam.model.Skill;
import ua.epam.repository.javaioimpl.JavaIODeveloperRepositoryImpl;
import ua.epam.service.DeveloperService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DeveloperController {
    private DeveloperService developerService = new DeveloperService();

    public Developer addDeveloper(String name, Set<Skill> skillSet, Account account) {
        Developer developer = new Developer(null, name, skillSet, account);
        return developerService.create(developer);
    }

//    public boolean updateDeveloperById(Long id, String name, String lastName, Set<Skill> skillSet) {
//        Account account = developerRepository.getById(id).getAccount();
//        Developer developer = new Developer(id, name, lastName, skillSet ,account);
//        return developerRepository.update(developer, id);
//    }

    public boolean deleteDeveloperById(Long id) {
        return developerService.delete(id);
    }

    public Developer getDeveloperById(Long id) {
        return developerService.getById(id);
    }

    public void getAll() {
        for (Map.Entry<Long, Developer> entry : developerService.getAll().entrySet()) {
            System.out.println("ID - " + entry.getValue().getId() + "," + entry.getValue().getName() + "," + entry.getValue().getSkills() + "," + " Account ID - " + entry.getValue().getAccount().getId() + ":" + "\n");
        }
    }
}
