/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Kweet;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class KweetDaoJpa implements KweetDao {

    @PersistenceContext(name = "kwetterPU")
    private EntityManager em;

    public KweetDaoJpa() {
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Kweet kweet, User user) {
        em.persist(kweet);
        em.merge(user);
    }

    @Override
    public void delete(Kweet kweet) {
        em.remove(kweet);
    }

    @Override
    public List<Kweet> findByContent(String content) {
        TypedQuery<Kweet> query = em.createNamedQuery("kweet.findByContent", Kweet.class);
        query.setParameter("content", content);
        return query.getResultList();
    }

}
