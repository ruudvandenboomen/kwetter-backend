/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import qualifier.JPA;
import dao.KweetDao;
import domain.Kweet;
import domain.User;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class KweetService {

    @Inject
    @JPA
    private KweetDao dao;

    public void createKweet(User user, Kweet kweet) {
        this.dao.create(kweet, user);
    }

    public List<Kweet> findByContent(String content) {
        return this.dao.findByContent(content);
    }
}
