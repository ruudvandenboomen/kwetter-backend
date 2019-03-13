/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.interfaces.KweetDao;
import dao.interfaces.UserDao;
import domain.User;
import domain.views.ProfileView;
import exceptions.UserNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import qualifier.JPA;
import util.KweetConverter;

@Stateless
public class UserService {
    
    @Inject
    @JPA
    private UserDao dao;
    
    @Inject
    @JPA
    private KweetDao kweetDao;
    
    public ProfileView getProfile(String username) throws UserNotFoundException {
        User user = dao.getUser(username);
        if (user == null) {
            throw new UserNotFoundException("HUts");
        }
        ProfileView profileView = new ProfileView();
        profileView.setUsername(username);
        profileView.setFollowerCount(user.getFollowers().size());
        profileView.setFollowingCount(user.getFollowing().size());
        profileView.setKweets(KweetConverter.convertKweets(kweetDao.getUserKweets(user)));
        return profileView;
    }
    
    public List<String> getFollowers(String username) throws UserNotFoundException {
        User user = dao.getUser(username);
        if (user != null) {
            return createUserArrayResponse(user.getFollowers());
        } else {
            throw new UserNotFoundException();
        }
    }
    
    public List<String> getFollowing(String username) throws UserNotFoundException {
        User user = dao.getUser(username);
        if (user != null) {
            return createUserArrayResponse(user.getFollowing());
        } else {
            throw new UserNotFoundException();
        }
    }
    
    public void addUser(User user) {
        dao.create(user);
    }
    
    private List<String> createUserArrayResponse(List<User> users) {
        List<String> response = new ArrayList<>();
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
    
    public List<User> getAll() {
        return dao.getAll();
    }
    
    public void deleteUser(User user) {
        dao.deleteUser(dao.getUser(user.getUsername()));
    }
}

