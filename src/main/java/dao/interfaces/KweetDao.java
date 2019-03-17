/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import domain.Kweet;
import domain.User;
import java.util.List;

public interface KweetDao extends BaseDao<Kweet> {

    List<Kweet> findByContent(String content);

    List<Kweet> getUserKweets(User user);

    List<Kweet> getAll();

}
