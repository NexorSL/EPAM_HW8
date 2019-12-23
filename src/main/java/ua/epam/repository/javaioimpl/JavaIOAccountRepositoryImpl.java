package ua.epam.repository.javaioimpl;

import ua.epam.model.Account;
import ua.epam.model.AccountStatus;
import ua.epam.repository.AccountRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class JavaIOAccountRepositoryImpl implements AccountRepository {
    Map<Long, Account> accountMap = new HashMap<>();
    private static final String REGEX = ",";
    private static final String PATH = "src\\main\\resources\\accounts.txt";
    private static final String END_OF_WORD = ":";

    public JavaIOAccountRepositoryImpl() {
        getAll();
    }

    @Override
    public Account create(Account entity) {
        if (entity == null) {
            return null;
        }
        try {
            FileWriter fileWriter = new FileWriter(PATH, true);
            fileWriter.write(entity.getId() + "," + entity.getName() + "," + entity.getAccountStatus() + END_OF_WORD + "\n");
            accountMap.put(entity.getId(), entity);
            fileWriter.flush();
            fileWriter.close();
            return entity;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account getById(Long id) {
        getAll();
        if (accountMap.get(id) == null || id < 0) {
            return null;
        }
        return accountMap.get(id);
    }

    @Override
    public boolean update(Account entity, Long id) {
        getAll();
        if (accountMap.get(id) == null || id < 0 || entity == null) {
            return false;
        }
        accountMap.put(id, entity);
        updateFile();
        return true;
    }

    @Override
    public boolean delete(Long id) {
        getAll();
        if (accountMap.get(id) == null || id < 0) {
            return false;
        }
        accountMap.remove(id);
        updateFile();
        return true;
    }

    @Override
    public Map<Long, Account> getAll() {
        try {
            FileReader fr = new FileReader(PATH);
            Scanner scanner = new Scanner(fr);
            String[] list;
            String name;
            Long id;
            while (scanner.hasNextLine()) {
                for (String rev : scanner.nextLine().split(END_OF_WORD)) {
                    list = (rev.split(REGEX));
                    id = Long.parseLong(list[0]);
                    name = list[1];
                    accountMap.put(id, new Account(id, name, AccountStatus.valueOf(list[2])));
                }
            }
            fr.close();
            return accountMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long getLastIndex(){
        Long lastIndex = 0L;
        try {
            FileReader fr = new FileReader(PATH);
            Scanner scanner = new Scanner(fr);
            String[] account;
            while (scanner.hasNextLine()) {
                for (String rev : scanner.nextLine().split(END_OF_WORD)) {
                    account = rev.split(",");
                    lastIndex = Long.parseLong(account[0]);
                }
            }
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastIndex;
    }


    public void updateFile() {
        try (FileWriter fileWriter = new FileWriter(PATH)) {
            String str = "";
            for (Map.Entry<Long, Account> entry : accountMap.entrySet()) {
                str = str + entry.getValue().getId() + REGEX + entry.getValue().getName() + "," + entry.getValue().getAccountStatus() + END_OF_WORD + "\n";
            }
            fileWriter.write(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public boolean updateId(){
//        try {
//            FileReader fr = new FileReader(PATH);
//            Scanner scanner = new Scanner(fr);
//            String[] list;
//            String name;
//            Long id = 1L;
//            while (scanner.hasNextLine()) {
//                for (String rev : scanner.nextLine().split(END_OF_WORD)) {
//                    list = (rev.split(","));
//                    name = list[1];
//                    accountMap.put(id, new Account(id,name, AccountStatus.valueOf(list[2])));
//                    id++;
//                }
//            }
//            fr.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }


}
