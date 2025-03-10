package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;



public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();
    private Transaction transaction = null;


    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users"
                +"(ID INTEGER PRIMARY KEY auto_increment, "
                +"NAME VARCHAR(255) NOT NULL, "
                +"LASTNAME VARCHAR(255) NOT NULL, "
                +"AGE INT NOT NULL)";
        try (Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(createUsersTable).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();

    } catch (Exception e) {
        e.printStackTrace();}
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
        transaction = session.beginTransaction();
        session.save(new User(name, lastName, age));
        transaction.commit();
    } catch (Exception e) {
            if(transaction != null) {
            transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        try (Session session = sessionFactory.openSession())
        {
            users = (List<User>) session.createCriteria(User.class).list();
            transaction = session.beginTransaction();
            session.flush();
            transaction.commit();

        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return users;

    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }
}
