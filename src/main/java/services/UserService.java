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
import domain.views.KweetView;
import domain.views.ProfileView;
import domain.views.UserListView;
import exceptions.UserNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import qualifier.JPA;
import util.KweetConverter;
import util.TimelineComparator;

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
    KweetService kweetService;

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
        List<KweetView> kweets = KweetConverter.convertKweets(kweetDao.getUserKweets(user));
        kweets.sort(new TimelineComparator());
        profileView.setKweets(kweets);
        return profileView;
    }

    public List<UserListView> getFollowers(String username) throws UserNotFoundException {
        return createUserArrayResponse(getUser(username).getFollowers());
    }

    public List<UserListView> getFollowing(String username) throws UserNotFoundException {
        return createUserArrayResponse(getUser(username).getFollowing());
    }

    private List<UserListView> createUserArrayResponse(List<User> users) {
        List<UserListView> response = new ArrayList<>();
        for (User u : users) {
            UserListView userListView = new UserListView();
            userListView.setUsername(u.getUsername());
            response.add(userListView);
        }
        return response;
    }

    public boolean addFollow(String username, String userToFollow) throws UserNotFoundException {
        User user = getUser(username);
        User user2 = getUser(userToFollow);
        if (user.getFollowing().contains(user2)) {
            return false;
        } else {
            user.follow(user2);
            return true;
        }
    }

    public boolean removeFollow(String username, String userToUnfollow) throws UserNotFoundException {
        User user = getUser(username);
        User user2 = getUser(userToUnfollow);
        if (user.getFollowing().contains(user2)) {
            user.unfollow(user2);
            return true;
        } else {
            return false;
        }
    }

    public List<User> getAll() {
        return dao.getAll();
    }

    public void deleteUser(User user) throws UserNotFoundException {
        User foundUser = getUser(user.getUsername());

        for (Kweet kweet : kweetDao.getUserKweets(foundUser)) {
            kweetService.deleteKweet(kweet);
        }
        for (Kweet likedKweet : foundUser.getLikes()) {
            likedKweet.getLikes().remove(foundUser);
        }
        for (User following : foundUser.getFollowing()) {
            following.getFollowers().remove(foundUser);
        }
        for (User follower : foundUser.getFollowers()) {
            follower.getFollowing().remove(foundUser);
        }

        dao.delete(foundUser);
    }

    public void assignRole(User user, String role) throws UserNotFoundException {
        User foundUser = getUser(user.getUsername());
        Role foundRole = roleDao.find(role);
        if (foundRole == null) {
            throw new NotFoundException();
        }
        foundUser.getRoles().add(foundRole);
        foundRole.getUsers().add(foundUser);
    }

    public List<Role> getAllRoles() {
        return roleDao.getAll();
    }

    public User getUserByName(String name) {
        return dao.find(name);
    }

    public void editUser(User user) {
        dao.edit(user);
    }

    private User getUser(String username) throws UserNotFoundException {
        User user = dao.find(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }
}
