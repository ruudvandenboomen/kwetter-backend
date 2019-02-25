/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Kweet;
import domain.User;
import java.util.ArrayList;
import java.util.List;

public class UserDaoColl implements UserDao {

    private List<User> users = new ArrayList<User>();

    public UserDaoColl() {
    }

    @Override
    public User getUser(String name) {
        User user = null;
        for (User u : users) {
            if (u.getUsername().equals(name)) {
                user = u;
            }
        }
        return user;
    }

    @Override
    public int count() {
        return users.size();
    }

    @Override
    public void update(User user) {
        for (User u : users) {
            if (u.getId().equals(user.getId())) {
                u = user;
            }
        }
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

}
