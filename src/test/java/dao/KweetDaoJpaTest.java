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

public class KweetDaoJpaTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("kwetterTestPU");
    private EntityManager em;
    private EntityTransaction tx;
    private KweetDaoJpa kweetDao;
    private UserDaoJpa userDao;

    User user = new User("Fred", "Fred@fransfadfs.nl");
    Kweet kweet = new Kweet("Nice weather today! #sunny");

    public KweetDaoJpaTest() {
    }

    @Before
    public void setUp() {
        try {
            new DatabaseCleaner(emf.createEntityManager()).clean();
        } catch (SQLException ex) {
            Logger.getLogger(KweetDaoJpaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        em = emf.createEntityManager();
        tx = em.getTransaction();

        userDao = new UserDaoJpa();
        userDao.setEm(em);
        kweetDao = new KweetDaoJpa();
        kweetDao.setEm(em);
        createDummyData();
    }

    private void createDummyData() {
        kweet.setCreatedBy(user);
        tx.begin();
        userDao.create(user);
        kweetDao.create(kweet, user);
        tx.commit();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addKweetSuccessfull() {
        tx.begin();
        List<Kweet> kweets = kweetDao.getUserKweets(user);
        tx.commit();

        assertThat(kweets.size(), is(1));
    }

    @Test
    public void findByContent() {
        tx.begin();
        List<Kweet> kweets = kweetDao.findByContent(kweet.getContent());
        tx.commit();

        assertThat(kweets.size(), is(1));
    }

    @Test
    public void findUserKweet() {
        tx.begin();
        List<Kweet> kweets = kweetDao.getUserKweets(user);
        tx.commit();

        assertThat(kweets.size(), is(1));
    }

    @Test
    public void removeKweet() {
        tx.begin();
        List<Kweet> kweets = kweetDao.getUserKweets(user);
        kweetDao.delete(kweets.get(0));
        tx.commit();

        tx.begin();
        List<Kweet> result = kweetDao.getUserKweets(user);
        tx.commit();

        assertThat(result.size(), is(0));
    }
    
}
