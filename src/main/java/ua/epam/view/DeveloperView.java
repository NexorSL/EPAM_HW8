package ua.epam.view;

import ua.epam.controller.AccountController;
import ua.epam.controller.DeveloperController;
import ua.epam.controller.SkillController;
import ua.epam.model.Developer;
import ua.epam.model.Skill;
import ua.epam.repository.ViewRepository;

import java.util.InputMismatchException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class DeveloperView implements ViewRepository {
    private static final String ENTER_NEW_ACCOUNT = "Enter new Developer:";
    private static final String ENTER_ID = "Enter id: ";
    private static final String ERROR = "Error";
    private static final String CHOICE = "1 - Create\n2 - Update\n3 - Delete\n4 - Find by id\n5 - Get all\n6 - Go back\n";
    private static final String X = "--------------";
    private static final String NAME = "name";
    private static final String LAST_NAME = "Last name";
    private static final String UPDATE_ACCOUNT = "Update account";
    private static final String SELECT_YOUR_SKILLS = "Select your skills:";
    private static final String EXIT = "0 - Exit";
    private DeveloperController developerController = new DeveloperController();
    private SkillController skillController = new SkillController();
    private AccountController accountController = new AccountController();

    @Override
    public void printMenu() {
        Scanner in = new Scanner(System.in);
        int answer = 5;
        Long id;
        String name;
        String lastName;
        String nickname;
        Long choise;
        Set<Skill> skillSet = new LinkedHashSet<>();
        while (answer != 0) {
            System.out.println(X);
            System.out.println(CHOICE);
            try {
                answer = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(e);
            }
            switch (answer) {
                case 1:
                    choise = -1L;
                    System.out.println(ENTER_NEW_ACCOUNT + " ");
                    System.out.println(NAME);
                    name = in.next();
                    System.out.println(LAST_NAME);
                    lastName = in.next();
                    while (choise != 0) {
                        System.out.println(SELECT_YOUR_SKILLS);
                        System.out.println(EXIT);
                        try {
                            skillController.getAll();
                            choise = in.nextLong();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        if (choise != 0) {
                            skillSet.add(skillController.getDataById(choise));
                        }
                    }
                    System.out.println("Your nick:");
                    nickname = in.next();
                    accountController.addAccount(nickname);
//                    developerController.addDeveloper(name, lastName, skillSet, accountController.getAccountById(accountController.getLastIndex()));
                    skillSet.clear();
                    break;
                case 2:
                    choise = -1L;
                    System.out.println(UPDATE_ACCOUNT);
                    System.out.println(ENTER_ID);
                    id = in.nextLong();
                    System.out.println(NAME);
                    name = in.next();
                    System.out.println(LAST_NAME);
                    lastName = in.next();
                    while (choise != 0) {
                        System.out.println(SELECT_YOUR_SKILLS);
                        try {
                            skillController.getAll();
                            System.out.println(EXIT);
                            choise = in.nextLong();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        if (choise != 0) {
                            skillSet.add(skillController.getDataById(choise));
                        }
                    }
                    developerController.updateDeveloperById(id,name, lastName, skillSet);
                    skillSet.clear();
                    break;
                case 3:
                    System.out.println(ENTER_ID);
                    id = in.nextLong();
                    boolean isDeleted = developerController.deleteDeveloperById(id);
                    if(isDeleted){
                        System.out.println("Developer deleted");
                    } else {
                        System.out.println("Developer does not exist");
                    }
                    break;
                case 4:
                    System.out.print(ENTER_ID);
                    id = in.nextLong();
                    System.out.println(developerController.getDeveloperById(id));
                    break;
                case 5:
                    developerController.getAll();
                    break;
                case 6:
                    AppView appView = new AppView();
                    appView.printMenu();
                default:
                    System.out.println(ERROR);
                    break;
            }
        }
    }
}
