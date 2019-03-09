/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.Coll;

import dao.interfaces.HashtagDao;
import domain.Hashtag;
import java.util.ArrayList;
import java.util.List;

public class HashtagDaoColl implements HashtagDao {

    private List<Hashtag> hashtags = new ArrayList<>();

    public HashtagDaoColl() {
    }

    @Override
    public Hashtag findHashtag(String name) {
        Hashtag found = null;
        for (Hashtag hashtag : hashtags) {
            if (hashtag.getName().equals(name)) {
                found = hashtag;
            }
        }
        return found;
    }

    @Override
    public void addHashtag(Hashtag hashtag) {
        hashtags.add(hashtag);
    }

    @Override
    public List<Hashtag> getPopularHashtags() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
