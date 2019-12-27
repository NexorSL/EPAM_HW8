package ua.epam.controller;

import ua.epam.model.Account;
import ua.epam.model.AccountStatus;
import ua.epam.model.Developer;
import ua.epam.model.Skill;
import ua.epam.repository.javaioimpl.JavaIODeveloperRepositoryImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DeveloperController {
    private JavaIODeveloperRepositoryImpl developerRepository = new JavaIODeveloperRepositoryImpl();

    public Developer addDeveloper(String name, String lastName, Set<Skill> skillSet, Account account) {
        Developer developer = new Developer(developerRepository.getLastIndex() + 1, name, lastName, skillSet, account);
        return developerRepository.create(developer);
    }

    public boolean updateDeveloperById(Long id, String name, String lastName, Set<Skill> skillSet) {
        Account account = developerRepository.getById(id).getAccount();
        Developer developer = new Developer(id, name, lastName, skillSet ,account);
        return developerRepository.update(developer, id);
    }

    public boolean deleteDeveloperById(Long id) {
        return developerRepository.delete(id);
    }

    //
    public Developer getDeveloperById(Long id) {
        return developerRepository.getById(id);
    }

    public void getAll() {
        Map<Long, Developer> developerMap = new HashMap<>(developerRepository.getAll());
        for (Map.Entry<Long, Developer> entry : developerMap.entrySet()) {
            System.out.println("ID - " + entry.getValue().getId() + "," + entry.getValue().getName() + "," + entry.getValue().getLastName() + "," + entry.getValue().getSkills() + "," + " Account ID - " + entry.getValue().getAccount().getId() + ":" + "\n");
        }
    }
}
