package ua.epam.view;

import ua.epam.controller.AccountController;
import ua.epam.controller.DeveloperController;
import ua.epam.controller.SkillController;
import ua.epam.model.Account;
import ua.epam.model.AccountStatus;
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
    private DeveloperController developerController = new DeveloperController();
    private SkillController skillController = new SkillController();
    private AccountController accountController = new AccountController();
    @Override
    public void printMenu() {
        Scanner in = new Scanner(System.in);
        int answer = 5;
        Long id;
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
                    String name;
                    String lastName;
                    Long choise = -1L;
                    String nickname;
                    Set<Skill> skillSet = new LinkedHashSet<>();
                    System.out.print(ENTER_NEW_ACCOUNT + " ");
                    System.out.println("name");
                    name = in.next();
                    System.out.println("Last name");
                    lastName = in.next();
                    while (choise != 0){
                        System.out.println("Select your skills:");
                        try {
                            choise = in.nextLong();
                        } catch (Exception e){
                            System.out.println(e);
                        }
                        skillSet.add(skillController.getDataById(choise));
                    }
                    System.out.println("Your nick:");
                    nickname = in.next();
                    accountController.addAccount(nickname);
                    developerController.addDeveloper(name, lastName, skillSet,accountController.getAccountById(accountController.getLastIndex()));
                    break;
                case 2:

                    break;
                case 3:
                    break;
                case 4:
                    System.out.print(ENTER_ID);
                    id = in.nextLong();
                    System.out.println(accountController.getAccountById(id).getName() + "," + accountController.getAccountById(id).getAccountStatus());
                    break;
                case 5:
                    developerController.getAll();
                    break;
                case 6:
                    AppView appView = new AppView();
                    appView.printMenu();
//                case 7:
//                    accountController.updateId();
//                    break;
                default:
                    System.out.println(ERROR);
                    break;
            }
        }
    }
}
