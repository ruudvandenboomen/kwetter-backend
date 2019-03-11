/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.Jpa.HashtagDaoJpa;
import dao.Jpa.KweetDaoJpa;
import dao.Jpa.UserDaoJpa;
import domain.Hashtag;
import java.sql.SQLException;
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

public class HashtagDaoJpaTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("kwetterTestPU");
    private EntityManager em;
    private EntityTransaction tx;
    private HashtagDaoJpa hashtagDao;
    private UserDaoJpa userDao;
    private KweetDaoJpa kweetDao;

    public HashtagDaoJpaTest() {
    }

    @Before
    public void setUp() {
        try {
            new DatabaseCleaner(emf.createEntityManager()).clean();
        } catch (SQLException ex) {
            Logger.getLogger(HashtagDaoJpaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        em = emf.createEntityManager();
        tx = em.getTransaction();

        hashtagDao = new HashtagDaoJpa();
        hashtagDao.setEm(em);
        userDao = new UserDaoJpa();
        userDao.setEm(em);
        kweetDao = new KweetDaoJpa();
        kweetDao.setEm(em);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addAndFindHashtag() {
        Hashtag hashtag = new Hashtag("Fun");

        tx.begin();
        hashtagDao.create(hashtag);
        tx.commit();

        assertThat(hashtagDao.findHashtag(hashtag.getName()).getName(), is(hashtag.getName()));
    }

}
