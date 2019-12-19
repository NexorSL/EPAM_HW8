package ua.epam.view;

import ua.epam.controller.SkillController;
import ua.epam.repository.ViewRepository;

import java.util.Scanner;

public class SkillView implements ViewRepository {
    private static final String ENTER_NEW_SKILL = "Enter new Skill:";
    private static final String ENTER_ID = "Enter id: ";
    private static final String ERROR = "Error";
    private static final String CHOICE = "1 - Create\n2 - Update\n3 - Delete\n4 - Find by id\n5 - Get all\n6 - Go back";
    private static final String X = "--------------";
    private SkillController skillController = new SkillController();

    public void printMenu() {
        Scanner in = new Scanner(System.in);
        long id = 0;
        int answer = -1;
        while (answer != 0) {
            System.out.println(X);
            System.out.println(CHOICE);
            try {
                answer = in.nextInt();
            } catch (Exception e) {
                System.out.println(e);
            }
            switch (answer) {
                case 1:
                    System.out.print(ENTER_NEW_SKILL + " ");
                    String skill = in.next();
                    skillController.addSkill(skill);
                    break;
                case 2:
                    System.out.print(ENTER_ID);
                    id = in.nextLong();
                    System.out.print(ENTER_NEW_SKILL);
                    String newSkill = in.next();
                    skillController.updateSkillById(id, newSkill);
                    break;
                case 3:
                    System.out.print(ENTER_ID);
                    id = in.nextLong();
                    skillController.deleteSkillById(id);
                    break;
                case 4:
                    System.out.print(ENTER_ID);
                    id = in.nextLong();
                    System.out.println(skillController.getDataById(id).getName());
                    break;
                case 5:
                    skillController.getAll();
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
