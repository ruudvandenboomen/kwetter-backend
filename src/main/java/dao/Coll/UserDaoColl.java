/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.Coll;

import dao.interfaces.UserDao;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

@ApplicationScoped
@Default
public class UserDaoColl implements UserDao {

    private List<User> users = new ArrayList<>();

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
    public void edit(User user) {
        for (User u : users) {
            if (u.getId().equals(user.getId())) {
                u = user;
            }
        }
    }

    @Override
    public User create(User user) {
        users.add(user);
        return user;
    }

    @Override
    public List<User> getAll() {
        return this.users;
    }

    @Override
    public void deleteUser(User user) {
        for (User u : users) {
            if (u == user) {
                users.remove(u);
            }
        }
    }

}
