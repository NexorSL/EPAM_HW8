package ua.epam.view;

import ua.epam.repository.ViewRepository;

import java.util.Scanner;

public class AppView implements ViewRepository {

    @Override
    public void printMenu() {
        SkillView skillView = new SkillView();
        AccountView accountView = new AccountView();
        Scanner in = new Scanner(System.in);
        int answer = 5;
        System.out.println("1 - Deverlopers\n2 - Skills\n3 - Account");
        try {
            answer = in.nextInt();
        } catch (Exception e) {
            System.out.println(e);
        }
        switch (answer) {
            case 1:
                break;
            case 2:
                skillView.printMenu();
                break;
            case 3:
                accountView.printMenu();
                break;
            default:
                AppView appView = new AppView();
                System.out.println("Error");
                appView.printMenu();
        }
    }
}
