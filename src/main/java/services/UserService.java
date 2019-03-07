/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import qualifier.JPA;
import dao.UserDao;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserService {

    @Inject
    @JPA
    private UserDao dao;

    public User getUser(String username) {
        return dao.getUser(username);
    }

    public List<String> getFollowers(String username) {
        return createUserArrayResponse(dao.getUser(username).getFollowers());
    }

    public List<String> getFollowing(String username) {
        return createUserArrayResponse(dao.getUser(username).getFollowing());
    }

    public void addUser(User user) {
        dao.addUser(user);
    }

    private List<String> createUserArrayResponse(List<User> users) {
        List<String> response = new ArrayList<String>();
        for (User u : users) {
            response.add(u.getUsername());
        }
        return response;
    }

    public boolean addFollow(String username, String userToFollow) {
        User user = dao.getUser(username);
        User user2 = dao.getUser(userToFollow);
        if (user.getFollowing().contains(user2)) {
            return false;
        } else {
            user.follow(user2);
            return true;
        }
    }
}
