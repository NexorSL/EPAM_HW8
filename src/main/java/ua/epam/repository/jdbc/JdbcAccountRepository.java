package ua.epam.repository.jdbc;

import lombok.extern.slf4j.Slf4j;
import ua.epam.model.Account;
import ua.epam.model.AccountStatus;
import ua.epam.repository.AccountRepository;
import ua.epam.util.ConnectionPool;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JdbcAccountRepository implements AccountRepository {

    private Connection connection;

    public JdbcAccountRepository() {
        connection = ConnectionPool.getConnection();
    }

    @Override
    public Account create(Account entity) {
        String sql = "Insert into accounts (accountName, accountStatus) values (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getAccountStatus().toString());
            statement.executeUpdate();
            log.info("AccountRepo: Account created {}", entity);
            return entity;
        } catch (SQLException e) {
            log.error("AccountRepo: Exeption occured while creating account, {}", e);
        }
        return null;
    }

    @Override
    public Account getById(Long accountId) {
        Account account;
        String sql = "SELECT * FROM accounts where id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                account = new Account(accountId, resultSet.getString("accountName"), AccountStatus.valueOf(resultSet.getString("accountStatus")));
                return account;
            }
            log.info("AccountRepo: Account found by id = {}", accountId);
        } catch (SQLException e) {
            log.error("AccountRepo: Exeption occured while getting account by id, {}", e);
        }
        return null;
    }


    @Override
    public boolean update(Account entity, Long id) {
        String sql = "Update accounts set accountName = ?, accountStatus = ? where id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getAccountStatus().toString());
            statement.setLong(3, id);
            statement.executeUpdate();
            log.info("AccountRepo: Account updated {}", entity);
            return true;
        } catch (SQLException e) {
            log.error("AccountRepo: Exeption occured while updating account, {}", e);
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "Delete from accounts where id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            log.info("AccountRepo: Account deleted id - {}", id);
            return true;
        } catch (SQLException e) {
            log.error("AccountRepo: Exeption occured while deleting account, {}", e);
        }
        return false;
    }

    @Override
    public Map<Long, Account> getAll() {
        Map<Long, Account> accountMap = new HashMap<>();
        String sql = "Select * from accounts;";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                accountMap.put(resultSet.getLong("id"), new Account(
                        resultSet.getLong("id"),
                        resultSet.getString("accountName"),
                        AccountStatus.valueOf(resultSet.getString("AccountStatus"))
                ));
            }
            log.info("AccountRepo: Accounts found");
            return accountMap;
        } catch (SQLException e) {
            log.error("AccountRepo: Exeption occured while getting all accounts, {}", e);
        }
        return accountMap;
    }

    public Account getByName(String name){
        String sql = "Select * from accounts where accountName = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account(
                        resultSet.getLong("id"),
                        resultSet.getString("accountName"),
                        AccountStatus.valueOf(resultSet.getString("accountStatus")));
                log.info("AccountRepo: Account found by name {}", name);
                return account;
            }
        } catch (SQLException e){
            log.error("AccountRepo: Exeption occured while getting account by name, {}", e);
        }
        return null;
    }
}
