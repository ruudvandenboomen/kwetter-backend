/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import dao.Jpa.KweetDaoJpa;
import dao.Jpa.UserDaoJpa;
import dao.interfaces.KweetDao;
import qualifier.JPA;
import dao.interfaces.UserDao;
import domain.Kweet;
import domain.User;
import exceptions.UserNotFoundException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import services.KweetService;

@Singleton
@Startup
public class StartUp {

    @Inject
    @JPA
    UserDaoJpa userDoa;

    @Inject
    @JPA
    KweetDaoJpa kweetDao;

    @Inject
    KweetService kweetservice;

    public StartUp() {
    }

    @PostConstruct
    private void intData() {
        User user = new User("Ruud", "Ruud@hotmail.com");
        User user2 = new User("Henk", "Henk@hotmail.com");

        userDoa.create(user);
        userDoa.create(user2);
        userDoa.create(new User("Fred", "Fred@hotmail.com"));

        try {
            kweetservice.createKweet(new Kweet("Nice weather today!"), "Ruud");
            kweetservice.createKweet(new Kweet("Hi you @Fred !"), "Henk");
            kweetservice.createKweet(new Kweet("Nice weather today! #sunny"), "Ruud");
        } catch (UserNotFoundException ex) {
            Logger.getLogger(StartUp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
