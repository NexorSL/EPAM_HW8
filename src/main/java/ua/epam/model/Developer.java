package ua.epam.model;

import java.util.Set;

public class Developer {
    private long id;
    private String name;
    private String lastName;
    private Set<Skill> skills;
    private Account account;

    public Developer(long id, String name, String lastName, Set<Skill> skills, Account account) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.skills = skills;
        this.account = account;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", skills=" + skills +
                ", account id=" + account.getId() +
                ", account name=" + account.getName() +
                '}';
    }
}
