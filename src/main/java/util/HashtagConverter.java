/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import domain.Hashtag;
import domain.views.HashtagView;
import java.util.ArrayList;
import java.util.List;

public class HashtagConverter {

    public static List<HashtagView> convertHashtags(List<Hashtag> hashtags) {
        List<HashtagView> hashtagViews = new ArrayList<>();
        for (Hashtag hashtag : hashtags) {
            hashtagViews.add(convertHashtag(hashtag));
        }
        return hashtagViews;
    }

    private static HashtagView convertHashtag(Hashtag hashtag) {
        HashtagView hashtagView = new HashtagView();
        hashtagView.setName(hashtag.getName());
        hashtagView.setTimesUsed(hashtag.getTimesUsed());
        hashtagView.setLastUsed(hashtag.getLastUsed());
        return hashtagView;
    }

}
