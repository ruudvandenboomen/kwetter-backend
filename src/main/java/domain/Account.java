/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.List;

public class Account {

    private String username;
    private String password;
    private List<User> users;
    
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
}
