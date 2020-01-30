package ua.epam.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Developer {
    private long id;
    private String name;
    private String lastName;
    private Set<Skill> skills;
    private Account account;
}
