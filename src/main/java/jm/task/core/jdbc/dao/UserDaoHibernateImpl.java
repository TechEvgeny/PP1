package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import org.hibernate.Query;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users"
//                +"(ID INTEGER PRIMARY KEY auto_increment, "
                +"NAME VARCHAR(255) NOT NULL, "
                +"LASTNAME VARCHAR(255) NOT NULL, "
                +"AGE INT NOT NULL)";
        Query query = session.createSQLQuery(createUsersTable).addEntity(User.class);
        transaction.commit();
        session.close();
    }



    @Override
    public void dropUsersTable() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
