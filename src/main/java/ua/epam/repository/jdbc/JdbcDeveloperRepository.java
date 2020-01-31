package ua.epam.repository.jdbc;

import lombok.extern.slf4j.Slf4j;
import ua.epam.controller.AccountController;
import ua.epam.controller.SkillController;
import ua.epam.model.Account;
import ua.epam.model.Developer;
import ua.epam.model.Skill;
import ua.epam.repository.DeveloperRepository;
import ua.epam.util.ConnectionPool;

import java.sql.*;
import java.util.*;

@Slf4j
public class JdbcDeveloperRepository implements DeveloperRepository {

    private Connection connection;
    private SkillController skillController = new SkillController();
    private AccountController accountController = new AccountController();

    public JdbcDeveloperRepository() {
        this.connection = ConnectionPool.getConnection();
    }

    @Override
    public Developer create(Developer entity) {
        String sql = "INSERT INTO developers (id ,developerName, accountId) values (NULL,?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getAccount().getId());
            statement.executeUpdate();
            addToDevSkills(entity);
            log.info("Developer created {}", entity);
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Developer getById(Long id) {
        String name = "";
        Set<Skill> skillSet = new LinkedHashSet<>();
        Account account = null;
        String sql = "SELECT d.id, d.developerName, d.accountId, ds.skillId, s.skillName FROM epam.developersskills ds\n" +
                "join developers d on ds.developerId = d.id\n" +
                "join skills s on ds.skillId = s.id\n" +
                "where d.id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getLong("id") == id) {
                    name = resultSet.getString("developerName");
                    skillSet.add(new Skill(resultSet.getLong("skillId"), resultSet.getString("skillName")));
                    account = accountController.getAccountById(resultSet.getLong("accountId"));
                }
            }
            return new Developer(id, name, skillSet, account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Developer entity, Long id) {
        String sql = "Update epam.developers set developerName = ? where id = ?;";
        updateDevSkills(entity, id);
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.setLong(2, id);
            statement.executeUpdate();
            addToDevSkills(entity, id);
            log.info("Developer updated {}", entity);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from epam.accounts where id = (select accountId from epam.developers " +
                "where id = ?);";
        deleteUtil(sql, id);
        sql = "delete from epam.developers where id = ?;";
        deleteUtil(sql, id);
        sql = "delete from epam.developersskills where id = ?;";
        log.info("Developer from developersskills deleted id = {}", id);
        deleteUtil(sql, id);

        return true;
    }

    @Override
    public Map<Long, Developer> getAll() {
        Map<Long, Developer> developerMap = new HashMap<>();
        Long id = 0L;
        String name = "";
        Set<Skill> skillSet = null;
        Account acc = null;
        boolean notEmpty = false;
        String sql = "SELECT d.id, d.developerName, d.accountId, ds.skillId, s.skillName FROM epam.developersskills ds\n" +
                "join developers d on ds.developerId = d.id\n" +
                "join skills s on ds.skillId = s.id\n" +
                "order by d.id;";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (id != resultSet.getLong("id")) {
                    if (notEmpty) {
                        developerMap.put(id, new Developer(id, name, skillSet, acc));
                    }
                    skillSet = new HashSet<>();

                    id = resultSet.getLong("id");
                    name = resultSet.getString("developerName");
                    skillSet.add(new Skill(resultSet.getLong("skillId"), resultSet.getString("skillName")));
                    acc = accountController.getAccountById(resultSet.getLong("accountId"));
                    notEmpty = true;
                } else {
                    skillSet.add(new Skill(resultSet.getLong("skillId"), resultSet.getString("skillName")));
                }
            }
            developerMap.put(id, new Developer(id, name, skillSet, acc));
            return developerMap;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean addToDevSkills(Developer developer) {
        String sql = "insert into developersskills (developerId, skillId) values (" +
                "(select d.id from developers d where d.developerName = ?),?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Skill skill : developer.getSkills()) {
                statement.setString(1, developer.getName());
                statement.setLong(2, skill.getId());
                statement.executeUpdate();
            }
            log.info("Developer Skills updated !");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean addToDevSkills(Developer developer, Long id) {
        String sql = "insert into developersskills (developerId, skillId) values (" +
                "?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Skill skill : developer.getSkills()) {
                statement.setLong(1, id);
                statement.setLong(2, skill.getId());
                statement.executeUpdate();
            }
            log.info("Developer Skills updated !");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean updateDevSkills(Developer developer, Long id) {
        String sql = "delete from developersskills where developerId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean deleteUtil(String sql, Long id) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
