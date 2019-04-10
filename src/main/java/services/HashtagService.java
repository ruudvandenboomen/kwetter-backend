/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.interfaces.HashtagDao;
import domain.views.HashtagView;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import qualifier.JPA;
import util.HashtagConverter;

@Stateless
public class HashtagService {

    @Inject
    @JPA
    private HashtagDao hashtagDao;

    public List<HashtagView> getPopularHashtags() {
        return HashtagConverter.convertHashtags(hashtagDao.getPopularHashtags());
    }

}
