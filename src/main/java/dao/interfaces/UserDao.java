/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import domain.User;
import java.util.List;

public interface UserDao extends BaseDao<User> {

    User getUser(String name);

    int count();

    List<User> getAll();
    
}
