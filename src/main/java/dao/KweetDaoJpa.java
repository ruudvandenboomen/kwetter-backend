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
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import qualifier.JPA;

@JPA
@Stateless
public class KweetDaoJpa extends DaoFacade<Kweet> implements KweetDao {

    @PersistenceContext(unitName = "kwetterPU")
    private EntityManager em;

    public KweetDaoJpa() {
        super(Kweet.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
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
        query.setParameter("content", "%" + content + "%");
        return query.getResultList();
    }

    @Override
    public Kweet findById(long id) {
        return find(id);
    }

}
