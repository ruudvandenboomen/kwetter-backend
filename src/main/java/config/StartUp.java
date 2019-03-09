/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import dao.interfaces.KweetDao;
import qualifier.JPA;
import dao.interfaces.UserDao;
import domain.Kweet;
import domain.User;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class StartUp {

    @Inject
    @JPA
    UserDao userDoa;

    @Inject
    @JPA
    KweetDao kweetDao;

    public StartUp() {
    }

    @PostConstruct
    private void intData() {
        User user = new User("Ruud", "Ruud@hotmail.com");
        User user2 = new User("Henk", "Henk@hotmail.com");
        user.follow(user2);

        Kweet kweet = new Kweet("Nice weather today!");
        user.addKweet(kweet);
        Kweet kweet2 = new Kweet("Hi you @Fred!");
        Kweet kweet3 = new Kweet("Nice weather today! #sunny");
        user2.addKweet(kweet2);
        user2.addKweet(kweet3);
        userDoa.addUser(user);
        userDoa.addUser(user2);
        userDoa.addUser(new User("Fred", "Fred@hotmail.com"));
    }

}
