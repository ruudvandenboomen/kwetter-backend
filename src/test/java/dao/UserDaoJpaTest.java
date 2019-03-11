/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.Jpa.UserDaoJpa;
import domain.User;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

public class UserDaoJpaTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("kwetterTestPU");
    private EntityManager em;
    private EntityTransaction tx;
    private UserDaoJpa userDao;

    public UserDaoJpaTest() {
    }

    @Before
    public void setUp() {
        try {
            new DatabaseCleaner(emf.createEntityManager()).clean();
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoJpaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        em = emf.createEntityManager();
        tx = em.getTransaction();

        userDao = new UserDaoJpa();
        userDao.setEm(em);
        createDummyData();
    }

    private void createDummyData() {
        User user = new User("Fred", "fred@henk.nl");
        User user2 = new User("Henk", "fred@frans.nl");
        user.follow(user2);

        tx.begin();
        userDao.create(user);
        userDao.create(user2);
        tx.commit();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addingUserSuccessfull() {
        assertThat(userDao.count(), is(2));
    }

    @Test(expected = PersistenceException.class)
    public void addingSameUserNameFail() {
        User user = new User("Fred", "fred@henk.nl");
        tx.begin();
        userDao.create(user);
        tx.commit();
        assertThat(userDao.count(), is(2));
    }

    @Test(expected = PersistenceException.class)
    public void addingSameEmailFail() {
        User user = new User("Klaas", "fred@henk.nl");
        tx.begin();
        userDao.create(user);
        tx.commit();
        assertThat(userDao.count(), is(2));
    }

    @Test
    public void findUserSucceesful() {
        String username = "Fred";
        tx.begin();
        User foundUser = userDao.getUser(username);
        tx.commit();
        assertThat(foundUser.getUsername(), is(username));
    }

    @Test
    public void follow() {
        tx.begin();
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
        tx.begin();
        User foundUser = userDao.getUser("Fred");
        User foundUser1 = userDao.getUser("Henk");
        tx.commit();

        foundUser.unfollow(foundUser1);

        tx.begin();
        userDao.edit(foundUser);
        userDao.edit(foundUser1);

        User foundUser2 = userDao.getUser("Fred");
        User foundUser3 = userDao.getUser("Henk");
        tx.commit();

        assertThat(foundUser2.getFollowers().size(), is(0));
        assertThat(foundUser3.getFollowing().size(), is(0));
    }

}
