package ua.epam.service;

import org.junit.Before;
import org.junit.Test;
import ua.epam.model.Account;
import ua.epam.model.AccountStatus;

import static org.junit.Assert.assertEquals;

public class AccountServiceTest {

    private AccountService accountService;
    private Long accountId;
    private String accountName;
    private Account testAccountCreate;
    private Account testAccountUpdate;

    @Before
    public void setUp() {
        accountId = 4L;
        accountName = "Qwerty";
        accountService = new AccountService();
        testAccountCreate = new Account(accountId, accountName, AccountStatus.ACTIVE);
        testAccountUpdate = new Account(3L, "Ytrewq", AccountStatus.BANNED);
    }

    @Test
    public void testCreateAccount() {
        Account created = testAccountCreate;
        accountService.create(testAccountCreate);
        Account res = accountService.getById(accountId);
        assertEquals(created, res);
    }

    @Test
    public void testDeleteAccount() {
        boolean isDeleted = true;
        boolean res = accountService.delete(accountId);
        assertEquals(isDeleted, res);
    }

    @Test
    public void testUpdateAccount() {
        accountService.update(testAccountUpdate, 3L);
        Account res = accountService.getById(3L);
        assertEquals(testAccountUpdate, res);
    }
}
