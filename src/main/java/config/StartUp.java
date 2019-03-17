/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import dao.interfaces.KweetDao;
import dao.interfaces.RoleDao;
import dao.interfaces.UserDao;
import domain.Kweet;
import domain.Role;
import domain.User;
import exceptions.UserNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import qualifier.JPA;
import services.KweetService;

@Singleton
@Startup
public class StartUp {

    @Inject
    @JPA
    UserDao userDoa;

    @Inject
    @JPA
    KweetDao kweetDao;

    @Inject
    @JPA
    RoleDao roleDao;

    @Inject
    KweetService kweetservice;

    public StartUp() {
    }

    @PostConstruct
    private void intData() {
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");

        roleDao.create(userRole);
        roleDao.create(adminRole);

        User user = new User("Ruud", "Ruud@hotmail.com");
        User user2 = new User("Henk", "Henk@hotmail.com");
        User user3 = new User("Fred", "Fred@hotmail.com");

        user.getRoles().add(userRole);
        user2.getRoles().add(userRole);
        user3.getRoles().add(userRole);

        userDoa.create(user);
        userDoa.create(user2);
        userDoa.create(user3);

        try {
            kweetservice.createKweet(new Kweet("Nice weather today!"), "Ruud");
            kweetservice.createKweet(new Kweet("Hi you @Fred !"), "Henk");
            kweetservice.createKweet(new Kweet("Nice weather today! #sunny"), "Ruud");
        } catch (UserNotFoundException ex) {
            Logger.getLogger(StartUp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
