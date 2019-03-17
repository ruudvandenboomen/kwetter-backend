/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.jpaImpl;

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
public class KweetDaoJpa extends BaseDaoJpa<Kweet> implements KweetDao {

    @PersistenceContext(unitName = "kwetterPU")
    private EntityManager em;

    public KweetDaoJpa() {
        super(Kweet.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
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
    public List<Kweet> getAll() {
        return em.createQuery("SELECT k FROM Kweet k").getResultList();

    }

}
