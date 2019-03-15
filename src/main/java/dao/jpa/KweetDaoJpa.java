/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.jpa;

import dao.interfaces.KweetDao;
import domain.Kweet;
import domain.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import qualifier.JPA;

@JPA
@Stateless
public class KweetDaoJpa implements KweetDao {

    @PersistenceContext(unitName = "kwetterPU")
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Kweet kweet, User user) {
        em.persist(kweet);
    }

    @Override
    public List<Kweet> findByContent(String content) {
        TypedQuery<Kweet> query = em.createNamedQuery("kweet.findByContent", Kweet.class);
        query.setParameter("content", "%" + content + "%");
        return query.getResultList();
    }

    @Override
    public List<Kweet> getUserKweets(User user) {
        TypedQuery<Kweet> query = em.createNamedQuery("kweet.findUserKweets", Kweet.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public void delete(Kweet kweet) {
        em.remove(kweet);
    }

    @Override
    public Kweet find(long id) {
        return em.find(Kweet.class, id);
    }

}
