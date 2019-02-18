/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Kweet;
import domain.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class KweetDaoImpl implements KweetDao {

    @PersistenceContext(name = "kwetterPU")
    private EntityManager em;

    KweetDaoImpl() {
    }

    @Override
    public void addTweet(Kweet kweet) {
        em.persist(kweet);
    }

    @Override
    public List<Kweet> getTweets(User user) {
        Query q = em.createNamedQuery("Kweet.getAll", Kweet.class);
        return q.getResultList();
    }

}
