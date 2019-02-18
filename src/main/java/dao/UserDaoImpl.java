/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless @JPA
public class UserDaoImpl implements UserDao {

    @PersistenceContext(name = "kwetterPU")
    private EntityManager em;

    public UserDaoImpl() {
    }

    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public User getUser(String name) {
        TypedQuery<User> query = em.createNamedQuery("user.findByname", User.class);
        query.setParameter("name", name);
        List<User> result = query.getResultList();
        System.out.println("count: " + result.size());
        return result.get(0);
    }

}
