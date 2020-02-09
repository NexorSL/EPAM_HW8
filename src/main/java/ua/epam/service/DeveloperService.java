package ua.epam.service;

import ua.epam.model.Account;
import ua.epam.model.AccountStatus;
import ua.epam.model.Developer;
import ua.epam.repository.jdbc.JdbcDeveloperRepository;

import java.util.Map;

public class DeveloperService {
    private JdbcDeveloperRepository developerRepository = new JdbcDeveloperRepository();
    private AccountService accountService = new AccountService();

    public Developer create(Developer developer) {
        accountService.create(new Account(
                null,
                developer.getAccount().getName(),
                AccountStatus.ACTIVE));
        Account account = accountService.getByName(developer.getAccount().getName());
        developer.setAccount(account);
        return developerRepository.create(developer);
    }

    public boolean update(Developer developer, Long id) {
        return developerRepository.update(developer, id);
    }


    public boolean delete(Long id) {
        return developerRepository.delete(id);
    }

    public Developer getById(Long id) {
        return developerRepository.getById(id);
    }

    public Map<Long, Developer> getAll() {
        return developerRepository.getAll();
    }
}
