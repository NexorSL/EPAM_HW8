package ua.epam.repository.javaioimpl;

import ua.epam.model.Skill;
import ua.epam.repository.SkillRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class JavaIOSkillRepositoryImpl implements SkillRepository {
    private Map<Long, Skill> skillMap = new HashMap<>();
    private static final String PATH = "src\\main\\resources\\skills.txt";
    private static final String END_OF_WORD = ":";

    public JavaIOSkillRepositoryImpl() {
        try {
            FileReader fr = new FileReader(PATH);
            Scanner scanner = new Scanner(fr);
            String[] skill;
            String skillName;
            Long id;
            while (scanner.hasNextLine()) {
                for (String rev : scanner.nextLine().split(END_OF_WORD)) {
                    skill = rev.split(",");
                    id = Long.parseLong(skill[0]);
                    skillName = skill[1];
                    skillMap.put((id), new Skill(id, skillName));
                }
            }
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Skill create(Skill entity) {
        if (entity == null) {
            return null;
        }
        try {
            FileWriter fileWriter = new FileWriter(PATH, true);
            fileWriter.write(entity.getId() + "," + entity.getName() + END_OF_WORD);
            fileWriter.flush();
            fileWriter.close();
            return entity;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Skill getById(Long id) {
        if (id < 0) {
            return null;
        }
        Skill skill;
        try {
            FileReader fr = new FileReader(PATH);
            Scanner scanner = new Scanner(fr);
            String[] skillParse;
            String skillName;
            Long skillId;
            while (scanner.hasNextLine()) {
                for (String rev : scanner.nextLine().split(END_OF_WORD)) {
                    skillParse = rev.split(",");
                    skillId = Long.parseLong(skillParse[0]);
                    if(skillId == id){
                        skillName = skillParse[1];
                        skill = new Skill(skillId, skillName);
//                        skillMap.put((skillId), skill);
                        return skill;
                    }
                }
            }
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Skill entity, Long id) {
        getAll();
        if (skillMap.get(id) == null || id < 0 || entity == null) {
            return false;
        }
        skillMap.put(id, entity);
        updateFile();
        return true;
    }

    public boolean delete(Long id) {
        getAll();
        if (skillMap.get(id) == null || id < 0) {
            return false;
        }
        skillMap.remove(id);
        updateFile();
        return true;
    }

    public Map<Long, Skill> getAll() {
        try {
            FileReader fr = new FileReader(PATH);
            Scanner scanner = new Scanner(fr);
            String[] skill;
            String skillName;
            Long id;
            while (scanner.hasNextLine()) {
                for (String rev : scanner.nextLine().split(END_OF_WORD)) {
                    skill = rev.split(",");
                    id = Long.parseLong(skill[0]);
                    skillName = skill[1];
                    skillMap.put((id), new Skill(id, skillName));
                }
            }
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skillMap;
    }

    public void updateFile() {
        try (FileWriter fileWriter = new FileWriter(PATH)) {
            String str = "";
            for (Map.Entry<Long, Skill> entry : skillMap.entrySet()) {
                str = str + entry.getValue().getId() + "," + entry.getValue().getName() + END_OF_WORD;
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
            String[] skill;
            while (scanner.hasNextLine()) {
                for (String rev : scanner.nextLine().split(END_OF_WORD)) {
                    skill = rev.split(",");
                    lastIndex = Long.parseLong(skill[0]);
                }
            }
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastIndex;
    }
}
