/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.Jpa;

import dao.DaoFacade;
import dao.interfaces.HashtagDao;
import domain.Hashtag;
import domain.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import qualifier.JPA;

@JPA
@Stateless
public class HashtagDaoJpa extends DaoFacade<Hashtag> implements HashtagDao {

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
    public Hashtag findHashtag(String name) {
        TypedQuery<Hashtag> query = em.createNamedQuery("hashtag.findByName", Hashtag.class);
        query.setParameter("name", name);
        List<Hashtag> hashtag = query.getResultList();
        if (hashtag != null && !hashtag.isEmpty()) {
            return hashtag.get(0);
        }
        return null;
    }

    @Override
    public void updateHashtag(Hashtag hashtag) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addHashtag(Hashtag hashtag) {
        create(hashtag);
    }
}
