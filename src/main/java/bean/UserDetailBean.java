/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import domain.Kweet;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import services.KweetService;

@Named(value = "userDetailBean")
@RequestScoped
public class UserDetailBean {

    @Inject
    private KweetService kweetService;
    private String username;

    public void deleteKweet(Kweet kweet) {
        System.out.println("deleteKweet method");
        kweetService.deleteKweet(kweet);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Kweet> getKweets() {
        return kweetService.getUserKweets(username);
    }

}
