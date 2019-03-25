/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.jpaImpl;

import dao.interfaces.HashtagDao;
import domain.Hashtag;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import qualifier.JPA;

@JPA
@Stateless
public class HashtagDaoJpa extends BaseDaoJpa<Hashtag> implements HashtagDao {

    @PersistenceContext(unitName = "kwetterPU")
    private EntityManager em;

    public HashtagDaoJpa() {
        super(Hashtag.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Hashtag find(String name) {
        TypedQuery<Hashtag> query = em.createNamedQuery("hashtag.findByName", Hashtag.class);
        query.setParameter("name", name);
        List<Hashtag> hashtag = query.getResultList();
        if (hashtag != null && !hashtag.isEmpty()) {
            return hashtag.get(0);
        }
        return null;
    }

    @Override
    public List<Hashtag> getPopularHashtags() {
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        TypedQuery<Hashtag> query = em.createNamedQuery("hashtag.findTrendHashtags", Hashtag.class);
        query.setParameter("startDate", new Date(System.currentTimeMillis() - (7 * DAY_IN_MS)));
        query.setParameter("endDate", new Date());
        query.setFirstResult(0);
        query.setMaxResults(5);
        return query.getResultList();
    }

}
