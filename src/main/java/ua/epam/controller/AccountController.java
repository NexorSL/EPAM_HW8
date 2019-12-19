package ua.epam.controller;

import ua.epam.model.Account;
import ua.epam.model.AccountStatus;
import ua.epam.repository.javaIOImpl.JavaIOAccountRepositoryImpl;

public class AccountController {
    private JavaIOAccountRepositoryImpl accountRepository = new JavaIOAccountRepositoryImpl();

    public boolean addAccount(String newAccount) {
        Account account = new Account(Account.nextId, newAccount, AccountStatus.Active);
        return accountRepository.create(account);
    }

    public boolean updateAccountById(Long key, String name, AccountStatus status) {
        Account skill = new Account(key, name, status);
        return accountRepository.update(skill, key);
    }

    public boolean deleteAccountById(Long id) {
        return accountRepository.delete(id);
    }

    public Account getAccountById(Long id) {
        return accountRepository.getById(id);
    }

    public void getAll() {
        accountRepository.print();
    }
//    public boolean updateId(){
//        return accountRepository.updateId();
//    }

}
