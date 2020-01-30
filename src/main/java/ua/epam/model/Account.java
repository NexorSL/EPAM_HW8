package ua.epam.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Account {
    private Long id;
    private String name;
    private AccountStatus accountStatus;

}
