/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.Jpa.KweetDaoJpa;
import dao.Jpa.UserDaoJpa;
import domain.Kweet;
import domain.User;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

public class KweetDaoJpaIT {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("kwetterTestPU");
    private EntityManager em;
    private EntityTransaction tx;
    private KweetDaoJpa kweetDao;
    private UserDaoJpa userDao;

    public KweetDaoJpaIT() {
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
        kweetDao = new KweetDaoJpa();
        kweetDao.setEm(em);
    }

    @After
    public void tearDown() {
    }

//    @Test
//    public void addKweetSuccessfull() {
//        User testUser = new User("Fred", "Fred@frans.nl");
//        Kweet kweet = new Kweet("Hi everyone");
//        testUser.addKweet(kweet);
//
//        tx.begin();
//        userDao.addUser(testUser);
//        List<Kweet> kweets = kweetDao.findByContent(kweet.getContent());
//        tx.commit();
//
//        assertThat(kweets.get(0), is(kweet));
//    }
    
    @Test
    public void removeKweet() {
        User testUser = new User("Fred", "Fred@frans.nl");
        Kweet kweet = new Kweet("Hi everyone");
        testUser.addKweet(kweet);

        tx.begin();
        userDao.create(testUser);
        tx.commit();

        tx.begin();
        kweetDao.delete(kweet);
        tx.commit();

        tx.begin();
        List<Kweet> kweets = kweetDao.getUserKweets(testUser);
        tx.commit();

        assertThat(kweets.size(), is(0));
    }

    @Test
    public void getUserKweets() {
        User user = new User("Fred", "Fred@frans.nl");
        Kweet kweet = new Kweet("Nice weather today! #sunny");
        user.addKweet(kweet);

        tx.begin();
        userDao.create(user);
        tx.commit();

        tx.begin();
        List<Kweet> kweets = kweetDao.getUserKweets(user);
        tx.commit();

        assertThat(kweets.size(), is(1));
        assertThat(kweets.get(0).getContent(), is(kweet.getContent()));
    }
}
