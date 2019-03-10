/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.Jpa.UserDaoJpa;
import domain.Kweet;
import domain.User;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

public class UserDaoJpaIT {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("kwetterTestPU");
    private EntityManager em;
    private EntityTransaction tx;
    private UserDaoJpa userDao;

    public UserDaoJpaIT() {
    }

    @Before
    public void setUp() {
        try {
            new DatabaseCleaner(emf.createEntityManager()).clean();
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoJpaIT.class.getName()).log(Level.SEVERE, null, ex);
        }
        em = emf.createEntityManager();
        tx = em.getTransaction();

        userDao = new UserDaoJpa();
        userDao.setEm(em);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addingUserSuccessfull() {
        User testUser = new User("Fred", "Fred@frans.nl");
        Integer expectedResult = 1;
        tx.begin();
        userDao.create(testUser);
        int aantal = userDao.count();
        tx.commit();
        assertThat(aantal, is(expectedResult));
    }

    @Test(expected = PersistenceException.class)
    public void addingSameUserNameFail() {
        User user = new User("Fred", "fred@henk.nl");
        User user2 = new User("Fred", "fred@frans.nl");
        tx.begin();
        userDao.create(user);
        userDao.create(user2);
        tx.commit();
        assertThat(userDao.count(), is(2));
    }

    @Test(expected = PersistenceException.class)
    public void addingSameEmailFail() {
        User user = new User("Klaas", "fred@hotmail.nl");
        User user2 = new User("Fred", "fred@hotmail.nl");
        tx.begin();
        userDao.create(user);
        userDao.create(user2);
        tx.commit();
        assertThat(userDao.count(), is(2));
    }

    @Test
    public void findUserSucceesful() {
        User testUser = new User("Fred", "Fred@frans.nl");
        tx.begin();
        userDao.create(testUser);
        User foundUser = userDao.getUser("Fred");
        tx.commit();
        assertThat(testUser, is(foundUser));
    }

    @Test
    public void follow() {
        User user1 = new User("Fred", "Fred@frans.nl");
        User user2 = new User("Henk", "Henk@frans.nl");
        user1.follow(user2);

        tx.begin();
        userDao.create(user1);
        userDao.create(user2);
        User foundUser = userDao.getUser("Fred");
        User foundUser1 = userDao.getUser("Henk");
        tx.commit();

        assertThat(foundUser.getFollowing().size(), is(1));
        assertThat(foundUser1.getFollowers().size(), is(1));

        assertThat(foundUser.getFollowing().get(0).getUsername(), is("Henk"));
        assertThat(foundUser1.getFollowers().get(0).getUsername(), is("Fred"));
    }

    @Test
    public void unfollow() {
        User user1 = new User("Fred", "Fred@frans.nl");
        User user2 = new User("Henk", "Henk@frans.nl");
        user1.follow(user2);

        tx.begin();
        userDao.create(user1);
        userDao.create(user2);
        tx.commit();

        user1.unfollow(user2);

        tx.begin();
        userDao.edit(user1);
        userDao.edit(user2);

        User foundUser = userDao.getUser("Fred");
        User foundUser1 = userDao.getUser("Henk");
        tx.commit();

        assertThat(foundUser.getFollowers().size(), is(0));
        assertThat(foundUser1.getFollowing().size(), is(0));
    }

}
