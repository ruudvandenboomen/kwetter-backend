/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import auth.JWTStore;
import com.nimbusds.jose.JOSEException;
import dao.interfaces.UserDao;
import domain.Role;
import domain.User;
import exceptions.InvalidLoginException;
import exceptions.InvalidUserException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import qualifier.JPA;

public class AuthService {

    @Inject
    @JPA
    UserDao userDao;

    @Inject
    Pbkdf2PasswordHash pbkdf2Hash;

    @Inject
    JWTStore jwtStore;

    public String login(String username, String password) throws InvalidLoginException, JOSEException {
        User foundUser = userDao.find(username);
        if (pbkdf2Hash.verify(password.toCharArray(), foundUser.getPassword())) {
            List<String> userRoles = new ArrayList<>();
            for (Role role : foundUser.getRoles()) {
                userRoles.add(role.getName());
            }
            return jwtStore.generateToken(username, userRoles);
        } else {
            throw new InvalidLoginException("Wrong username password combination");
        }
    }

    public void addUser(User user) throws InvalidUserException {
        if (user.getPassword() == null || user.getPassword().equals("")) {
            throw new InvalidUserException("User has no password");
        } else {
            user.setPassword(pbkdf2Hash.generate(user.getPassword().toCharArray()));
            userDao.create(user);
        }
    }

}
