package ua.epam.repository.jdbc;

import lombok.extern.slf4j.Slf4j;
import ua.epam.model.Skill;
import ua.epam.repository.SkillRepository;
import ua.epam.util.ConnectionPool;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JdbcSkillRepository implements SkillRepository {

    private Connection connection;

    public JdbcSkillRepository() {
        connection = ConnectionPool.getConnection();
    }

    @Override
    public Skill create(Skill entity) {
        String sql = "Insert into skills (skillName) values (?);";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, entity.getName());
            statement.executeUpdate();
            log.info("SkillRepo: Skill added {}", entity);
            return entity;
        } catch (SQLException e){
            log.error("SkillRepo: Exeption occured while creating skill, {}", e);
        }
        return null;
    }

    @Override
    public Skill getById(Long id) {
        Skill skill;
        String sql = "SELECT * FROM skills where id = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                skill = new Skill(id, resultSet.getString("skillName"));
                return skill;
            }
            log.info("SkillRepo: skill found by id = {}", id);
        } catch (SQLException e){
            log.error("SkillRepo: Exeption occured while getting skill by id, {}", e);
        }
        return null;
    }

    @Override
    public boolean update(Skill entity, Long id) {
        String sql = "Update skills set skillName = ? where id = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.setLong(2, id);
            statement.executeUpdate();
            log.info("SkillRepo; Skill updated {}", entity);
            return true;
        } catch (SQLException e){
            log.error("SkillRepo: Exeption occured while updating skill, {}", e);
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "Delete from skills where id = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            log.info("SkillRepo: Skill deleted id={}", id);
            return true;
        } catch (SQLException e){
            log.error("SkillRepo: Exeption occured while deleting skill, {}", e);
        }
        return false;
    }

    @Override
    public Map<Long, Skill> getAll() {
        Map<Long, Skill> skillMap = new HashMap<>();
        String sql = "Select * from skills;";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                skillMap.put(resultSet.getLong("id"), new Skill(
                        resultSet.getLong("id"),
                        resultSet.getString("skillName")
                ));
            }
            log.info("SkillRepo: skills found");
            return skillMap;
        } catch (SQLException e) {
            log.error("SkillRepo: Exeption occured while getting skills, {}", e);
        }
        return skillMap;
    }
}
