/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.interfaces.KweetDao;
import dao.interfaces.RoleDao;
import dao.interfaces.UserDao;
import domain.Kweet;
import domain.Role;
import domain.User;
import domain.views.ProfileView;
import exceptions.InvalidUserException;
import exceptions.UserNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
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
    
    @Inject
    @JPA
    private RoleDao roleDao;
    
    @Inject
    Pbkdf2PasswordHash pbkdf2Hash;
    
    public ProfileView getProfile(String username) throws UserNotFoundException {
        User user = dao.find(username);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        ProfileView profileView = new ProfileView();
        profileView.setId(user.getId());
        profileView.setUsername(username);
        profileView.setFollowerCount(user.getFollowers().size());
        profileView.setFollowingCount(user.getFollowing().size());
        profileView.setKweets(KweetConverter.convertKweets(kweetDao.getUserKweets(user)));
        return profileView;
    }
    
    public List<String> getFollowers(String username) throws UserNotFoundException {
        User user = dao.find(username);
        if (user != null) {
            return createUserArrayResponse(user.getFollowers());
        } else {
            throw new UserNotFoundException();
        }
    }
    
    public List<String> getFollowing(String username) throws UserNotFoundException {
        User user = dao.find(username);
        if (user != null) {
            return createUserArrayResponse(user.getFollowing());
        } else {
            throw new UserNotFoundException();
        }
    }
    
    public void addUser(User user) throws InvalidUserException {
        if (user.getPassword() == null || user.getPassword().equals("")) {
            throw new InvalidUserException("User has no password");
        } else {
            user.setPassword(pbkdf2Hash.generate(user.getPassword().toCharArray()));
            dao.create(user);
        }
    }
    
    private List<String> createUserArrayResponse(List<User> users) {
        List<String> response = new ArrayList<>();
        for (User u : users) {
            response.add(u.getUsername());
        }
        return response;
    }
    
    public boolean addFollow(String username, String userToFollow) throws UserNotFoundException {
        User user = dao.find(username);
        User user2 = dao.find(userToFollow);
        if (user == null || user2 == null) {
            throw new UserNotFoundException();
        }
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
    
    public void deleteUser(User user) throws UserNotFoundException {
        User foundUser = dao.find(user.getUsername());
        
        if (foundUser == null) {
            throw new UserNotFoundException();
        }
        
        for (Kweet kweet : kweetDao.getUserKweets(foundUser)) {
            kweetDao.delete(kweet);
        }
        for (Kweet likedKweet : foundUser.getLikes()) {
            likedKweet.getLikes().remove(foundUser);
        }

        
        dao.delete(foundUser);
    }
    
    public void assignRole(User user, String role) throws UserNotFoundException {
        User foundUser = dao.find(user.getUsername());
        if (foundUser == null) {
            throw new UserNotFoundException();
        }
        Role foundRole = roleDao.find(role);
        if (foundRole == null) {
            //TODO: throw exception
            return;
        }
        foundUser.getRoles().add(foundRole);
        foundRole.getUsers().add(foundUser);
    }
    
    public List<Role> getAllRoles() {
        return roleDao.getAll();
    }
    
    public User getUser(String name) {
        return dao.find(name);
    }
    
    public void editUser(User user) {
        dao.edit(user);
    }
}
