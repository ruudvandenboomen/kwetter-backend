/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.jpaImpl.UserDaoJpa;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

public class UserDaoJpaTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("kwetterTestPU");
    private EntityManager em;
    private EntityTransaction tx;
    private UserDaoJpa userDao;

    User user = new User("Fred", "fred@henk.nl");

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
        tx.begin();
        userDao.create(user);
        tx.commit();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addAndCount() {
        assertThat(userDao.count(), is(1));
    }

    @Test
    public void getAll() {
        assertThat(userDao.getAll().size(), is(1));
    }

    @Test(expected = PersistenceException.class)
    public void addingSameUserNameFail() {
        User sameName = new User(user.getUsername(), "test@testtest.nl");
        tx.begin();
        userDao.create(sameName);
        tx.commit();
        assertThat(userDao.count(), is(1));
    }

    @Test(expected = PersistenceException.class)
    public void addingSameEmailFail() {
        User sameEmail = new User("testtest", user.getEmail());
        tx.begin();
        userDao.create(sameEmail);
        tx.commit();
        assertThat(userDao.count(), is(1));
    }

    @Test
    public void findUserSucceesful() {
        assertNotNull(userDao.find(user.getUsername()));
    }

    @Test
    public void edit() {
        String username = "Klaas";

        tx.begin();
        User foundUser = userDao.find(user.getUsername());
        tx.commit();

        foundUser.setUsername(username);

        tx.begin();
        userDao.edit(foundUser);
        tx.commit();

        assertNotNull(userDao.find(username));
    }

    @Test
    public void deleteUser() {
        String username = "deletableUser";
        User deleteMe = new User(username, "delete@me.nl");
        tx.begin();
        userDao.create(deleteMe);
        tx.commit();

        tx.begin();
        userDao.delete(deleteMe);
        tx.commit();
        assertNull(userDao.find(username));
    }

}
