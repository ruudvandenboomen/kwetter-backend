/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import domain.Kweet;
import domain.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import services.KweetService;
import services.UserService;

@Named(value = "adminBean")
@RequestScoped
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

    public List<User> getUsers() {
        if (userFilter != null && userFilter.length() > 0) {
            List<User> filtered = new ArrayList<>();
            for (User u : userService.getAll()) {
                if (u.getUsername().toLowerCase().contains(userFilter.toLowerCase())) {
                    filtered.add(u);
                }
            }
            return filtered;
        } else {
            return userService.getAll();
        }
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
        userService.deleteUser(user);
    }

}
