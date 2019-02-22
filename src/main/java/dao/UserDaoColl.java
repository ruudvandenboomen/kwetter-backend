/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.User;
import java.util.ArrayList;
import java.util.List;

public class UserDaoColl implements UserDao {

    private List<User> users = new ArrayList<User>();

    public UserDaoColl() {
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public User getUser(String name) {
        User user = null;
        for (User u : users) {
            if (u.getName().equals(name)) {
                user = u;
            }
        }
        return user;
    }

    @Override
    public int count() {
        return users.size();
    }

}
