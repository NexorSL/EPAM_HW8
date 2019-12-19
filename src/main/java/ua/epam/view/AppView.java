package ua.epam.view;

import ua.epam.repository.ViewRepository;

import java.util.Scanner;

public class AppView implements ViewRepository {

    @Override
    public void printMenu() {
        SkillView skillView = new SkillView();
        Scanner in = new Scanner(System.in);
        int answer = -1;
        System.out.println("1 - Deverlopers\n2 - Skills\n3 - Account");
        try {
            answer = in.nextInt();
        } catch (Exception e){
            System.out.println(e);
        }
        switch (answer){
            case 1:
                break;
            case 2:
                skillView.printMenu();
                break;
            case 3:
                break;
        }
    }
}
