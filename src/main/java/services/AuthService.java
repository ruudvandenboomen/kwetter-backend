/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import auth.JWTStore;
import com.nimbusds.jose.JOSEException;
import dao.interfaces.RegistrationKeyDao;
import dao.interfaces.UserDao;
import domain.RegistrationKey;
import domain.Role;
import domain.User;
import exceptions.InvalidLoginException;
import exceptions.InvalidUserException;
import exceptions.UnauthorizedException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import qualifier.JPA;
import util.EmailSender;

public class AuthService {

    @JPA
    @Inject
    UserDao userDao;

    @JPA
    @Inject
    RegistrationKeyDao registrationKeyDao;

    @Inject
    Pbkdf2PasswordHash pbkdf2Hash;

    @Inject
    JWTStore jwtStore;

    @Inject
    EmailSender emailSender;

    public String login(String username, String password) throws InvalidLoginException, JOSEException, UnauthorizedException {
        User foundUser = userDao.find(username);
        if (!foundUser.isVerified()) {
            throw new UnauthorizedException();
        }
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

            try {
                String registrationKey = UUID.randomUUID().toString().replace("-", "");
                User createdUser = userDao.find(user.getUsername());
                registrationKeyDao.create(new RegistrationKey(registrationKey, createdUser));
                emailSender.sendEmail(user, registrationKey);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void confirmEmail(String key) {
        RegistrationKey registrationKey = registrationKeyDao.findKey(key);
        User user = userDao.find(registrationKey.getUser().getUsername());
        user.setVerified(true);
        userDao.edit(user);
    }

}
