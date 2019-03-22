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
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import qualifier.JPA;
import services.KweetService;

@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "${'jdbc/kwetter'}",
        callerQuery = "#{'select password from user where email = ?'}",
        groupsQuery = "select r.name from role r, user u, user_roles ur where u.email = ? AND u.id = ur.user_id AND r.id = ur.role_id",
        hashAlgorithm = PasswordHash.class,
        priority = 10
)
@FormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login.xhtml",
                errorPage = "/login-error.xhtml"))
@ApplicationScoped
@Named
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

    @Inject
    Pbkdf2PasswordHash pbkdf2Hash;

    public StartUp() {
    }

    @PostConstruct
    private void intData() {
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");

        User user = new User("Ruud", pbkdf2Hash.generate("Test123".toCharArray()), "Ruud@hotmail.com");
        User user2 = new User("Henk", pbkdf2Hash.generate("Test123".toCharArray()), "Henk@hotmail.com");
        User user3 = new User("Fred", pbkdf2Hash.generate("Test123".toCharArray()), "Fred@hotmail.com");

        roleDao.create(userRole);
        roleDao.create(adminRole);

        user.getRoles().add(adminRole);
        user2.getRoles().add(userRole);
        user3.getRoles().add(userRole);

        Kweet kweet = new Kweet("Nice weather today!");
        Kweet kweet2 = new Kweet("Hi you @Fred !");
        Kweet kweet3 = new Kweet("Nice weather today! #sunny");

        userDoa.create(user);
        userDoa.create(user2);
        userDoa.create(user3);

        try {
            kweetservice.createKweet(kweet, "Ruud");
            kweetservice.createKweet(kweet2, "Henk");
            kweetservice.createKweet(kweet3, "Ruud");
        } catch (UserNotFoundException ex) {
            Logger.getLogger(StartUp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
