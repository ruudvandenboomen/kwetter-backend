/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import domain.User;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
//        try {
//            new DatabaseCleaner(emf.createEntityManager()).clean();
//        } catch (SQLException ex) {
//            Logger.getLogger(UserDaoJpaIT.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
        Integer expectedResult = 1;
        User user = new User("Fred");
        tx.begin();
        userDao.addUser(user);
        tx.commit();
        tx.begin();
        int aantal = userDao.count();
        tx.commit();
        assertThat(aantal, is(expectedResult));
    }

    @Test(expected = RollbackException.class)
    public void addingUserFail() {
        User user = new User("Fred");
        User user2 = new User("Frans");
        tx.begin();
        userDao.addUser(user);
        userDao.addUser(user2);
        tx.commit();
        assertThat(userDao.count(), is(2));
    }

    @Test
    public void findUserSucceesful() {
        User user = new User("Frank");
        tx.begin();
        userDao.addUser(user);
        User foundUser = userDao.getUser("Frank");
        tx.commit();
        assertThat(user, is(foundUser));
    }
}
