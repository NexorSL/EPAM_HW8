package ua.epam.model;

public class Account {
    private long id;
    private String name;
    private AccountStatus accountStatus;

    public Account(long id, String name, AccountStatus accountStatus) {
        this.id = id;
        this.name = name;
        this.accountStatus = accountStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
}
