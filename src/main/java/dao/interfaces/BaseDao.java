/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

public interface BaseDao<T> {

    void create(T Object);

    T find(long id);

    void edit(T Object);

    void delete(T Object);
}
