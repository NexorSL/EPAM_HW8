package ua.epam.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Developer {
    private Long id;
    private String name;
    private Set<Skill> skills;
    private Account account;
}
