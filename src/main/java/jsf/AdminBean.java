/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import domain.Kweet;
import domain.Role;
import domain.User;
import exceptions.UserNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import services.KweetService;
import services.UserService;

@Named
@RequestScoped
@RolesAllowed("admin")
public class AdminBean implements Serializable {

    @Inject
    UserService userService;

    @Inject
    KweetService kweetService;

    @Getter
    @Setter
    private String userFilter;

    @Getter
    @Setter
    private String kweetFilter;

    @Getter
    private final Map<User, List<String>> availableUserRoles = new HashMap<>();

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        if (userFilter != null && userFilter.length() > 0) {
            for (User u : userService.getAll()) {
                if (u.getUsername().toLowerCase().contains(userFilter.toLowerCase())) {
                    users.add(u);
                }
            }
        } else {
            users = userService.getAll();
        }
        users.forEach(u -> availableRoles(u));
        return users;
    }

    public List<Kweet> getKweets() {
        if (kweetFilter != null && kweetFilter.length() > 0) {
            List<Kweet> filtered = new ArrayList<>();
            for (Kweet k : kweetService.getAll()) {
                if (k.getContent().toLowerCase().contains(kweetFilter.toLowerCase())) {
                    filtered.add(k);
                }
            }
            return filtered;
        } else {
            return kweetService.getAll();
        }
    }

    public void deleteKweet(Kweet kweet) {
        kweetService.deleteKweet(kweet);
    }

    public void deleteUser(User user) {
        try {
            userService.deleteUser(user);
        } catch (UserNotFoundException ex) {
            Logger.getLogger(AdminBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addRole(User user, String role) {
        try {
            if (role != null && !role.equals("")) {
                userService.assignRole(user, role);
            }
        } catch (UserNotFoundException ex) {
            Logger.getLogger(AdminBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void availableRoles(User user) {
        List<String> roles = new ArrayList<>();
        for (Role role : userService.getAllRoles()) {
            if (!user.getRoles().stream().anyMatch(o -> o.getName().equals(role.getName()))) {
                roles.add(role.getName());
            }
        }
        availableUserRoles.put(user, roles);
    }
}
