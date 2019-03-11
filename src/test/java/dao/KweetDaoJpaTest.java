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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import util.DatabaseCleaner;

public class KweetDaoJpaTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("kwetterTestPU");
    private EntityManager em;
    private EntityTransaction tx;
    private KweetDaoJpa kweetDao;
    private UserDaoJpa userDao;

    public KweetDaoJpaTest() {
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
        kweetDao = new KweetDaoJpa();
        kweetDao.setEm(em);
        createDummyData();
    }

    private void createDummyData() {
        User user = new User("Fred", "Fred@frans.nl");
        Kweet kweet = new Kweet("Nice weather today! #sunny");
        kweet.setCreatedBy(user);
        user.getKweets().add(kweet);

        tx.begin();
        userDao.create(user);
        kweetDao.create(kweet, user);
        tx.commit();
    }

    @After
    public void tearDown() {
    }

//    @Test
//    public void addKweetSuccessfull() {
//        tx.begin();
//        User user = userDao.getUser("Fred");
//        List<Kweet> kweets = kweetDao.getUserKweets(user);
//        tx.commit();
//
//        assertThat(kweets.size(), is(1));
//    }

//    @Test
//    public void findByContent() {
//        tx.begin();
//        List<Kweet> kweets = kweetDao.findByContent("Nice weather today! #sunny");
//        tx.commit();
//
//        assertThat(kweets.size(), is(1));
//    }

//    @Test
//    public void removeKweet() {
//        tx.begin();
//        User user = userDao.getUser("Fred");
//        tx.commit();
//        
//        tx.begin();
//        List<Kweet> kweets = kweetDao.getUserKweets(user);
//        kweetDao.delete(kweets.get(0));
//        tx.commit();
//
//        tx.begin();
//        List<Kweet> result = kweetDao.getUserKweets(user);
//        tx.commit();
//
//        assertThat(result.size(), is(0));
//    }
}
