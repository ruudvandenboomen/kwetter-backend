/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import domain.Hashtag;
import java.util.List;

public interface HashtagDao {

    Hashtag findHashtag(String name);

    void updateHashtag(Hashtag hashtag);

    void addHashtag(Hashtag result);

    public List<Hashtag> getPopularHashtags();
}
