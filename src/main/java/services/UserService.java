/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import qualifier.JPA;
import dao.UserDao;
import domain.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserService {

    @Inject @JPA
    private UserDao dao;

    public User getUser(String name) {
        return dao.getUser(name);
    }

    public void addUser(User user) {
        dao.addUser(user);
    }
}
