/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import qualifier.JPA;
import domain.Kweet;
import domain.User;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@JPA
@Stateless
public class UserDaoJpa extends DaoFacade<User> implements UserDao {

    @PersistenceContext(unitName = "kwetterPU")
    private EntityManager em;

    public UserDaoJpa() {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public User getUser(String name) {
        TypedQuery<User> query = em.createNamedQuery("user.findByName", User.class);
        query.setParameter("name", name);
        List<User> users = query.getResultList();
        if (users != null && !users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public int count() {
        Query query = em.createQuery("SELECT u FROM User u");
        return new ArrayList<>(query.getResultList()).size();
    }

    @Override
    public void update(User user) {
        edit(user);
    }

    @Override
    public void addUser(User user) {
        create(user);
    }

}
