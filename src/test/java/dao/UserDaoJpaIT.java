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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

public class UserDaoJpaIT {
 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("StudentTestPU");
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
        Integer expectedResult = 1;
        User user = new User("Frank");
        tx.begin();
        userDao.addUser(student);
        tx.commit();
        tx.begin();
        int aantal = userDao.aantal();
        tx.commit();
        assertThat(aantal, is(expectedResult));
    }

//    @Test(expected = RollbackException.class)
//    public void addingUserFail() {
//        User user = new User("Frank");
//        User student1 = new User("Frank");
//        tx.begin();
//        userDao.addUser(user);
//        userDao.addUser(student1);
//        tx.commit();
//        assertThat(userDao.aantal(), is(2));
//    }

    @Test
    public void findStudentSucceesful() {
        User user = new User("Frank");
        tx.begin();
        userDao.addUser(user);
        User foundUser = userDao.getUser("Frank");
        tx.commit();
        assertThat(user, is(foundUser));
    }
}
