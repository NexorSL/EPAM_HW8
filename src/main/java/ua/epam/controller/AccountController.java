package ua.epam.controller;

import ua.epam.model.Account;
import ua.epam.model.AccountStatus;
import ua.epam.service.AccountService;

import java.util.Map;

public class AccountController {
    private AccountService accountRepository = new AccountService();

    public Account addAccount(String newAccount) {
        Account account = new Account(null, newAccount, AccountStatus.ACTIVE);
        return accountRepository.create(account);
    }

    public boolean updateAccountById(Long key, String name, AccountStatus status) {
        Account account = new Account(key, name, status);
        return accountRepository.update(account, key);
    }

    public boolean deleteAccountById(Long id) {
        return accountRepository.delete(id);
    }

    public Account getAccountById(Long id) {
        return accountRepository.getById(id);
    }

    public void getAll() {
        for (Map.Entry<Long, Account> entry : accountRepository.getAll().entrySet()){
            System.out.println(entry.getValue().getId() + "," + entry.getValue().getName() + "," + entry.getValue().getAccountStatus());
        }
    }

}
