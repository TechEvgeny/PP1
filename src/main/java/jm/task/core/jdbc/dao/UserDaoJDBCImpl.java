package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {
    }


    public void createUsersTable() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users"
                +"(ID INTEGER PRIMARY KEY auto_increment, "
                +"NAME VARCHAR(255) NOT NULL, "
                +"LASTNAME VARCHAR(255) NOT NULL, "
                +"AGE INT NOT NULL)";
        try (Statement statement = connection.createStatement()) {
                statement.execute(createUsersTable);
        } catch (SQLException e) {
            System.out.println("Table not created" + " " + e.getMessage());
        }
    }


    public void dropUsersTable() {

        String dropUsersTable = "DROP TABLE IF EXISTS users";

        try (Statement statement = connection.createStatement()) {
            statement.execute(dropUsersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void saveUser(String name, String lastName, byte age) {


        String sql = "INSERT INTO users (NAME, LASTNAME, AGE) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("не удалось создать юзера в таблице" + e.getMessage());
        }
    }


    public void removeUserById(long id) {

        String sql = "DELETE FROM users WHERE ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

            try (Statement statement = connection.createStatement()) {

                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("NAME"));
                    user.setLastName(resultSet.getString("LASTNAME"));
                    user.setAge(resultSet.getByte("AGE"));
                    users.add(user);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return users;
    }


    public void cleanUsersTable() {

        String cleanUsersTable = "DELETE FROM users";


            try (Statement statement = connection.createStatement()) {

                statement.execute(cleanUsersTable);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

