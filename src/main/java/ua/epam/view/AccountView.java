package ua.epam.view;

import ua.epam.controller.AccountController;
import ua.epam.model.AccountStatus;
import ua.epam.repository.ViewRepository;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountView implements ViewRepository {
    private static final String ENTER_NEW_ACCOUNT = "Enter new Account name:";
    private static final String ENTER_ID = "Enter id: ";
    private static final String ERROR = "Error";
    private static final String CHOICE = "1 - Create\n2 - Update\n3 - Delete\n4 - Find by id\n5 - Get all\n6 - Go back\n";
    private static final String X = "--------------";
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
                    System.out.print(ENTER_NEW_ACCOUNT + " ");
                    String account = in.next();
                    accountController.addAccount(account);
                    break;
                case 2:
                    System.out.print(ENTER_ID);
                    id = in.nextLong();
                    System.out.print(ENTER_NEW_ACCOUNT);
                    String newAccount = in.next();
                    System.out.println("Account status:\n1 - Active\n2 - Banned\n3 - Deleted");
                    AccountStatus status = AccountStatus.ACTIVE;
                    int stat = 0;
                    try {
                        stat = in.nextInt();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    switch (stat) {
                        case 1:
                            status = AccountStatus.ACTIVE;
                            break;
                        case 2:
                            status = AccountStatus.BANNED;
                            break;
                        case 3:
                            status = AccountStatus.DELETED;
                            break;
                        default:
                            System.out.println("Set Active");
                            break;
                    }
                    accountController.updateAccountById(id, newAccount, status);
                    break;
                case 3:
                    System.out.print(ENTER_ID);
                    id = in.nextLong();
                    accountController.deleteAccountById(id);
                    break;
                case 4:
                    System.out.print(ENTER_ID);
                    id = in.nextLong();
                    System.out.println(accountController.getAccountById(id).getName() + "," + accountController.getAccountById(id).getAccountStatus());
                    break;
                case 5:
                    accountController.getAll();
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
