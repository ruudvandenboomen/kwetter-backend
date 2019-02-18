/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Kweet;
import domain.User;
import java.util.List;

public interface KweetDao {

    void addTweet(Kweet kweet);

    List<Kweet> getTweets(User user);
}
