package ua.epam.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {
    private static Connection connection;
    public static Connection getConnection(){
        if(connection != null){
            return connection;
        }

        Properties prop = readPropFile("src/main/resources/application.properties");

        final String JDBC_DRIVER = prop.getProperty("database.driver");
        final String DATABASE_URL = prop.getProperty("database.url");
        final String DATABASE_USERNAME = prop.getProperty("database.username");
        final String DATABASE_PASS = prop.getProperty("database.password");
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASS);
            return connection;
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private static Properties readPropFile(String filePath){
        Properties properties = null;

        try(FileInputStream fileInputStream = new FileInputStream(filePath)){
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e){
            e.printStackTrace();
        }

        return properties;
    }
}
