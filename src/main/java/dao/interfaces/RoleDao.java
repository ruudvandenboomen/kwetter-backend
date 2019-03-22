/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import domain.Role;

import java.util.List;

public interface RoleDao {

    void create(Role role);

    Role find(String name);

    List<Role> getAll();

}
