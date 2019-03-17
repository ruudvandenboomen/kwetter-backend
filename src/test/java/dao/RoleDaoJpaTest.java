/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.jpaImpl.RoleDaoJpa;
import dao.jpaImpl.UserDaoJpa;
import domain.Role;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

public class RoleDaoJpaTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("kwetterTestPU");
    private EntityManager em;
    private EntityTransaction tx;
    private UserDaoJpa userDao;
    private RoleDaoJpa roleDao;

    public RoleDaoJpaTest() {
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

        userDao = new UserDaoJpa();
        userDao.setEm(em);
        roleDao = new RoleDaoJpa();
        roleDao.setEm(em);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addRole() {
        Role role = new Role("admin");

        tx.begin();
        roleDao.create(role);
        tx.commit();

        //TODO: finish test
    }

}
