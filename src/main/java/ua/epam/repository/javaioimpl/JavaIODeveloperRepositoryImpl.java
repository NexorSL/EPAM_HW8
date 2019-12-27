package ua.epam.repository.javaioimpl;

import ua.epam.controller.AccountController;
import ua.epam.controller.SkillController;
import ua.epam.model.Account;
import ua.epam.model.Developer;
import ua.epam.model.Skill;
import ua.epam.repository.DeveloperRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaIODeveloperRepositoryImpl implements DeveloperRepository {
    Map<Long, Developer> developerMap = new HashMap<>();
    SkillController skillController = new SkillController();
    AccountController accountController = new AccountController();
    private static final String REGEX = ",";
    private static final String PATH = "src\\main\\resources\\developers.txt";
    private static final String END_OF_WORD = ":";
    private Pattern skillPat = Pattern.compile("\\d+");

    public JavaIODeveloperRepositoryImpl() {
        this.getAll();
    }

    @Override
    public Developer create(Developer entity) {
        if (entity == null) {
            return null;
        }
        try {
            FileWriter fileWriter = new FileWriter(PATH, true);
            StringBuilder skillList = new StringBuilder();
            Matcher matcher = skillPat.matcher(entity.getSkills().toString());
            while (matcher.find()) {
                skillList.append(matcher.group() + "/");
            }
            fileWriter.write(entity.getId() + "," + entity.getName() + "," + entity.getLastName() + "," + skillList + "," + entity.getAccount().getId() + END_OF_WORD + "\n");
            developerMap.put(entity.getId(), entity);
            fileWriter.flush();
            fileWriter.close();
            this.updateFile();
            return entity;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Developer getById(Long id) {
        if (id < 0) {
            return null;
        }
        Developer developer;
        try {
            FileReader fr = new FileReader(PATH);
            Scanner scanner = new Scanner(fr);
            String[] developerParse;
            Set<Skill> skillSet = new LinkedHashSet<>();
            String[] skillsParse;
            Long devId;
            String devName;
            String devLastName;
            Account account;
            while (scanner.hasNextLine()) {
                for (String rev : scanner.nextLine().split(END_OF_WORD)) {
                    developerParse = rev.split(",");
                    devId = Long.parseLong(developerParse[0]);
                    if (devId == id) {
                        devName = developerParse[1];
                        devLastName = developerParse[2];
                        skillsParse = developerParse[3].split("/");
                        for (int i = 0; i < skillsParse.length; i++) {
                            skillSet.add(skillController.getDataById(Long.parseLong(skillsParse[i])));
                        }
                        account = accountController.getAccountById(Long.parseLong(developerParse[4]));
                        developer = new Developer(devId, devName, devLastName, skillSet, account);
                        fr.close();
                        this.updateFile();
                        return developer;
                    }
                }
            }
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Developer entity, Long id) {
        this.getAll();
        if (developerMap.get(id) == null || id < 0 || entity == null) {
            return false;
        }
        developerMap.put(id, entity);
        this.updateFile();
        return false;
    }

    @Override
    public boolean delete(Long id) {
        this.getAll();
        if (developerMap.get(id) == null || id < 0) {
            return false;
        }
        accountController.deleteAccountById(developerMap.get(id).getAccount().getId());
        developerMap.remove(id);
        this.updateFile();
        return true;
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
                skillSet.clear();
                for (String rev : scanner.nextLine().split(END_OF_WORD)) {
                    list = (rev.split(REGEX));
                    id = Long.parseLong(list[0]);
                    name = list[1];
                    lastName = list[2];
                    skillsParse = list[3].split("/");
                    for (int i = 0; i < skillsParse.length; i++) {
                        skillSet.add(skillController.getDataById(Long.parseLong(skillsParse[i])));
                    }
                    developerMap.put(id, new Developer(id, name, lastName, new LinkedHashSet<Skill>(skillSet), accountController.getAccountById(Long.parseLong(list[4]))));
                }
            }
            fr.close();
            return developerMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateFile() {
        try (FileWriter fileWriter = new FileWriter(PATH)) {
            String str = "";
            StringBuilder skillList = new StringBuilder();
            Matcher matcher;
            for (Map.Entry<Long, Developer> entry : developerMap.entrySet()) {

                matcher = skillPat.matcher(entry.getValue().getSkills().toString());
                while (matcher.find()) {
                    skillList.append(matcher.group() + "/");
                }
                str = str + entry.getValue().getId() + "," + entry.getValue().getName() + "," + entry.getValue().getLastName() + "," + skillList + "," + entry.getValue().getAccount().getId() + END_OF_WORD + "\n";
                skillList.setLength(0);
            }
            fileWriter.write(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Long getLastIndex() {
        Long lastIndex = 0L;
        try {
            FileReader fr = new FileReader(PATH);
            Scanner scanner = new Scanner(fr);
            String[] developer;
            while (scanner.hasNextLine()) {
                for (String rev : scanner.nextLine().split(END_OF_WORD)) {
                    developer = rev.split(",");
                    lastIndex = Long.parseLong(developer[0]);
                }
            }
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastIndex;
    }
}
