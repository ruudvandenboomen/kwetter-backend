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

        kweetDao = new KweetDaoJpa();
        kweetDao.setEm(em);
        userDao = new UserDaoJpa();
        userDao.setEm(em);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addKweetSuccessful() {
        String content = "Hi everyone";
        User testUser = new User("Fred", "Fred@frans.nl");
        Kweet kweet = new Kweet(content);

        testUser.addKweet(kweet);
        tx.begin();
        userDao.addUser(testUser);
        kweetDao.create(kweet, testUser);
        List<Kweet> kweets = kweetDao.findByContent(content);
        tx.commit();

        assertThat(kweets.get(0), is(kweet));
    }

    @Test
    public void removeKweet() {
        String content = "Hi everyone";
        User testUser = new User("Fred", "Fred@frans.nl");

        tx.begin();
        userDao.addUser(testUser);
        tx.commit();

        Kweet kweet = new Kweet(content);
        testUser.addKweet(kweet);

        tx.begin();
        kweetDao.create(kweet, testUser);

        kweetDao.delete(kweet);

        List<Kweet> kweets = kweetDao.findByContent(content);
        tx.commit();

        assertThat(kweets.size(), is(0));
    }

}
