/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import domain.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import services.UserService;

@Named(value = "adminBean")
@RequestScoped
public class AdminBean implements Serializable {

    @Inject
    UserService userService;

    private String filter;

    public List<User> getUsers() {
        if (filter != null && filter.length() > 0) {
            List<User> filtered = new ArrayList<>();
            for (User u : userService.getAll()) {
                if (u.getUsername().toLowerCase().contains(filter.toLowerCase())) {
                    filtered.add(u);
                }
            }
            return filtered;
        } else {
            return userService.getAll();
        }
    }

    public void deleteUser(User user) {
        userService.deleteUser(user);
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

}
