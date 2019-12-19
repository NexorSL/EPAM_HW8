package ua.epam.repository.javaIOImpl;

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
            long i = 1;
            while (scanner.hasNextLine()) {
                for (String rev : scanner.nextLine().split(END_OF_WORD)) {
                    skillMap.put(i, new Skill(i, rev));
                    i++;
                }
            }
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean create(Skill entity) {
        if (entity == null) {
            return false;
        }
        try {
            FileWriter fileWriter = new FileWriter(PATH, true);
            fileWriter.write(entity.getName() + END_OF_WORD);
            fileWriter.flush();
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Skill getById(Long id) {
        if (skillMap.get(id) == null || id < 0) {
            return null;
        }
        return skillMap.get(id);
    }

    public boolean update(Skill entity, Long id) {
        if (skillMap.get(id) == null || id < 0 || entity == null) {
            return false;
        }
        skillMap.put(id, entity);
        print();
        return true;
    }

    public boolean delete(Long id) {
        if (skillMap.get(id) == null || id < 0) {
            return false;
        }
        skillMap.remove(id);
        print();
        return true;
    }

    public Map<Long, Skill> getAll() {
        try {
            FileReader fr = new FileReader(PATH);
            Scanner scanner = new Scanner(fr);
            long i = 1;
            while (scanner.hasNextLine()) {
                for (String rev : scanner.nextLine().split(END_OF_WORD)) {
                    skillMap.put(i, new Skill(i, rev));
                    System.out.println(i + " - " + rev);
                    i++;
                }
            }
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skillMap;
    }

    public void print() {
        try (FileWriter fileWriter = new FileWriter(PATH)) {
            String str = "";
            for (Map.Entry<Long, Skill> entry : skillMap.entrySet()) {
                str = str + entry.getValue().getName() + END_OF_WORD;
            }
            fileWriter.write(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Long getLastIndex(){
        Long lastIndex = 0L;
        for (Map.Entry<Long, Skill> entry : skillMap.entrySet()) {
            lastIndex = entry.getKey();
        }
        return lastIndex + 1;
    }
}
