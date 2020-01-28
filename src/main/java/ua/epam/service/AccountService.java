package ua.epam.service;

import ua.epam.model.Account;
import ua.epam.repository.jdbc.JdbcAccountRepository;

import java.util.Map;

public class AccountService {
    private JdbcAccountRepository accountRepository = new JdbcAccountRepository();

    public Account create(Account account) {
        return accountRepository.create(account);
    }

    public Account getById(Long id) {
        return accountRepository.getById(id);
    }

    public boolean update(Account account, Long key) {
        return accountRepository.update(account, key);
    }

    public boolean delete(Long id){
        return accountRepository.delete(id);
    }

    public Map<Long, Account> getAll(){
       return accountRepository.getAll();
    }
}
