package ua.epam.controller;

import ua.epam.model.Account;
import ua.epam.model.AccountStatus;
import ua.epam.model.Skill;
import ua.epam.repository.javaioimpl.JavaIOAccountRepositoryImpl;

import java.util.HashMap;
import java.util.Map;

public class AccountController {
    private JavaIOAccountRepositoryImpl accountRepository = new JavaIOAccountRepositoryImpl();

    public Account addAccount(String newAccount) {
        Account account = new Account(accountRepository.getLastIndex()+1, newAccount, AccountStatus.ACTIVE);
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
        Map<Long, Account> skillMap = new HashMap<>(accountRepository.getAll());
        for (Map.Entry<Long, Account> entry : skillMap.entrySet()){
            System.out.println(entry.getValue().getId() + "," + entry.getValue().getName() + "," + entry.getValue().getAccountStatus());
        }
    }
//    public boolean updateId(){
//        return accountRepository.updateId();
//    }
    public Long getLastIndex(){
        return accountRepository.getLastIndex();
    }

}
