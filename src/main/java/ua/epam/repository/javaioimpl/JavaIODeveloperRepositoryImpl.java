package ua.epam.repository.javaIOImpl;

import ua.epam.controller.AccountController;
import ua.epam.controller.SkillController;
import ua.epam.model.Developer;
import ua.epam.model.Skill;
import ua.epam.repository.DeveloperRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JavaIODeveloperRepositoryImpl implements DeveloperRepository {
    Map<Long, Developer> developerMap = new HashMap<>();
    SkillController skillController = new SkillController();
    AccountController accountController = new AccountController();
    private static final String REGEX = ",";
    private static final String PATH = "src\\main\\resources\\developers.txt";
    private static final String END_OF_WORD = ":";

    public JavaIODeveloperRepositoryImpl() {
        this.getAll();
    }

    @Override
    public boolean create(Developer entity) {
        if (entity == null) {
            return false;
        }
        try {
            FileWriter fileWriter = new FileWriter(PATH, true);
            fileWriter.write("\n" +entity.getId() + ","  + entity.getName() + "," + entity.getLastName() + "," + entity.getSkills() + "," + entity.getAccount().getId() + END_OF_WORD + "\n");
            developerMap.put(entity.getId(), entity);
            fileWriter.flush();
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Developer getById(Long aLong) {
        return null;
    }

    @Override
    public boolean update(Developer entity, Long aLong) {
        return false;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    @Override
    public Map<Long, Developer> getAll() {
        try {
            FileReader fr = new FileReader(PATH);
            Scanner scanner = new Scanner(fr);
            Set<Skill> skillSet = new LinkedHashSet<>();
            String[] list;
            String[] skillsParse;
            long id;
            String name;
            String lastName;
            while (scanner.hasNextLine()) {
                for (String rev : scanner.nextLine().split(END_OF_WORD)) {
                    list = (rev.split(REGEX));
                    id = Long.parseLong(list[0]);
                    name = list[1];
                    lastName = list[2];
                    skillsParse = list[3].split("/");
                    for (int i = 0; i < skillsParse.length; i++) {
                        skillSet.add(skillController.getDataById(Long.parseLong(skillsParse[i])));
                    }
                    developerMap.put(id, new Developer(id, name, lastName,skillSet,accountController.getAccountById(Long.parseLong(list[4]))));
                }
            }
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//    public void print() {
//        try (FileWriter fileWriter = new FileWriter(PATH)) {
//            String str = "";
//            for (Map.Entry<Long, Developer> entry : developerMap.entrySet()) {
//                str = str + entry.getValue().getId() + "" + entry.getValue().getName() + END_OF_WORD;
//            }
//            fileWriter.write(str);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public void print(){
        String str = "";
        for (Map.Entry<Long, Developer> entry : developerMap.entrySet()) {
            str = str + "ID - "+ entry.getValue().getId() + ","  + entry.getValue().getName() + "," + entry.getValue().getLastName() + "," + entry.getValue().getSkills() + "," + entry.getValue().getAccount().getId() + END_OF_WORD + "\n";
        }
        System.out.println(str);
    }
    public Long getLastIndex(){
        Long lastIndex = 0L;
        for (Map.Entry<Long, Developer> entry : developerMap.entrySet()) {
            lastIndex = entry.getKey();
        }
        return lastIndex;
    }
}
