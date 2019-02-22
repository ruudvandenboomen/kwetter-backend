/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.KweetDao;
import domain.Kweet;
import domain.User;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class KweetService {

    @Inject
    private KweetDao dao;

    public void addTweet(User user, Kweet kweet) {
        this.dao.addTweet(user, kweet);
    }

    public List<Kweet> getTweets() {
        User user = new User("Henk");
        return this.dao.getTweets(user);
    }
}
