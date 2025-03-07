package jm.task.core.jdbc.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/mytestdb";
    private static final String USER = "root117";
    private static final String PASSWORD = "root";
    // реализуйте настройку соеденения с БД
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database successfully");

        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
        }
        return connection;

    }
}
