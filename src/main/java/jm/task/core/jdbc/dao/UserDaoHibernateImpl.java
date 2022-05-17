package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;



public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getSessionFactory();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        doWithUsersTable("CREATE TABLE IF NOT EXISTS user (id int NOT NULL auto_increment, " +
                "name varchar(64), lastname varchar(64), age int, PRIMARY KEY (id))");
    }


    @Override
    public void dropUsersTable() {
        doWithUsersTable("DROP TABLE if EXISTS user");
    }


    @Override
    public void cleanUsersTable() {
        doWithUsersTable("TRUNCATE TABLE user");
    }


    private void doWithUsersTable(String SQL) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery(SQL).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            User deletedUser = session.get(User.class, id);
            if (deletedUser != null) {
                session.delete(deletedUser);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers(){
        List <User> result = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            result = session.createQuery("FROM User").getResultList();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return result;
    }
}
