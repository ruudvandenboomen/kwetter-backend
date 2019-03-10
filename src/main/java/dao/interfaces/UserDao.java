/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import domain.Kweet;
import domain.User;
import java.util.List;

public interface UserDao {

    User getUser(String name);

    void create(User user);

    int count();

    void edit(User user);

}
