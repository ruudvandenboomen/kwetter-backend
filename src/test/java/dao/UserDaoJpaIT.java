/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
        User testUser = new User("Fred", "Fred@frans.nl", new Date());
        Integer expectedResult = 1;
        tx.begin();
        userDao.create(testUser);
        tx.commit();
        tx.begin();
        int aantal = userDao.count();
        tx.commit();
        assertThat(aantal, is(expectedResult));
    }

    @Test(expected = PersistenceException.class)
    public void addingSameUserNameFail() {
        User user = new User("Fred", "fred@henk.nl", new Date());
        User user2 = new User("Fred", "fred@frans.nl", new Date());
        tx.begin();
        userDao.addUser(user);
        userDao.addUser(user2);
        tx.commit();
        assertThat(userDao.count(), is(2));
    }

    @Test(expected = PersistenceException.class)
    public void addingSameEmailFail() {
        User user = new User("Klaas", "fred@hotmail.nl", new Date());
        User user2 = new User("Fred", "fred@hotmail.nl", new Date());
        tx.begin();
        userDao.addUser(user);
        userDao.addUser(user2);
        tx.commit();
        assertThat(userDao.count(), is(2));
    }

    @Test
    public void findUserSucceesful() {
        User testUser = new User("Fred", "Fred@frans.nl", new Date());
        tx.begin();
        userDao.create(testUser);
        User foundUser = userDao.getUser("Fred");
        tx.commit();
        assertThat(testUser, is(foundUser));
    }

//    @Test
//    public void addKweetToUser() {
//        User user = new User("Fred", "Fred@frans.nl", new Date());
//
//        tx.begin();
//        userDao.addUser(user);
//        tx.commit();
//
//        user.addKweet(new Kweet("Nice weather today!"));
//
//        tx.begin();
//        userDao.update(user);
//        tx.commit();
//
//        tx.begin();
//        User foundUser = userDao.getUser("Fred");
//        tx.commit();
//
//        assertThat(foundUser.getKweets().size(), is(1));
//        assertThat(foundUser.getKweets().get(0).getContent(), is("Nice weather today!"));
//    }

    @Test
    public void likeKweet() {
        Date date = new Date();
        User user = new User("Fred", "Fred@frans.nl", date);
        User user2 = new User("Frans", "Frans@frans.nl", date);

        tx.begin();
        userDao.addUser(user);
        userDao.addUser(user2);
        tx.commit();

        Kweet kweet = new Kweet("Nice weather today!");
        user.addKweet(kweet);

        tx.begin();
        userDao.update(user);
        tx.commit();

        user2.addLike(kweet);

        tx.begin();
        userDao.update(user2);
        tx.commit();

        tx.begin();
        User foundUser = userDao.getUser("Frans");
        tx.commit();

        assertThat(foundUser.getLikes().size(), is(1));
        assertThat(foundUser.getLikes().get(0).getContent(), is("Nice weather today!"));
    }

    @Test
    public void startFollowing() {
        User user1 = new User("Fred", "Fred@frans.nl", new Date());
        User user2 = new User("Henk", "Henk@frans.nl", new Date());

        tx.begin();
        userDao.addUser(user1);
        userDao.addUser(user2);
        tx.commit();

        user1.addFollower(user2);
        user2.addFollowing(user1);

        tx.begin();
        userDao.update(user1);
        userDao.update(user2);
        tx.commit();

        tx.begin();
        User foundUser = userDao.getUser("Fred");
        User foundUser1 = userDao.getUser("Henk");
        tx.commit();

        assertThat(foundUser.getFollowers().size(), is(1));
        assertThat(foundUser1.getFollowing().size(), is(1));

        assertThat(foundUser.getFollowers().get(0).getUsername(), is("Henk"));
        assertThat(foundUser1.getFollowing().get(0).getUsername(), is("Fred"));

    }
}
