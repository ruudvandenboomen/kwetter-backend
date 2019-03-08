/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.HashtagDao;
import qualifier.JPA;
import dao.KweetDao;
import dao.UserDao;
import domain.Hashtag;
import domain.Kweet;
import domain.User;
import exceptions.KweetNotFoundException;
import exceptions.UserNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class KweetService {

    @Inject
    @JPA
    private KweetDao kweetDao;

    @Inject
    @JPA
    private UserDao userDao;

    @Inject
    @JPA
    private HashtagDao hashtagDao;

    public void createKweet(Kweet kweet, String username) throws UserNotFoundException {
        User user = userDao.getUser(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        kweet.setCreatedBy(user);
        setMentions(kweet, regex(kweet.getContent(), "@"));
        setHashtags(kweet, regex(kweet.getContent(), "#"));
        this.kweetDao.create(kweet, user);
    }

    public List<Kweet> findByContent(String content) {
        return this.kweetDao.findByContent(content);
    }

    public boolean likeKweet(long id, String username) throws UserNotFoundException, KweetNotFoundException {
        Kweet kweet = kweetDao.findById(id);
        if (kweet == null) {
            throw new KweetNotFoundException();
        }

        User user = userDao.getUser(username);
        if (user == null) {
            throw new UserNotFoundException();
        }

        if (!user.getLikes().contains(kweet)) {
            user.getLikes().add(kweet);
            return true;
        } else {
            return false;
        }
    }

    public List<Kweet> getMentions(String username) throws UserNotFoundException {
        User user = userDao.getUser(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user.getMentions();
    }

    private List<String> regex(String content, String prefix) {
        Pattern r = Pattern.compile("\\" + prefix + "\\S*");
        Matcher m = r.matcher(content);

        List<String> names = new ArrayList<>();
        while (m.find()) {
            names.add(m.group().replace("@", ""));
        }
        return names;
    }

    private void setMentions(Kweet kweet, List<String> names) {
        for (String name : names) {
            User user = userDao.getUser(name);
            if (user != null) {
                user.getMentions().add(kweet);
            }
        }
    }

    private void setHashtags(Kweet kweet, List<String> foundTags) {
        for (String tag : foundTags) {
            Hashtag hashtag = hashtagDao.findHashtag(tag);
            if (hashtag == null) {
                hashtag = new Hashtag(tag);
                hashtagDao.addHashtag(hashtag);
            }
            hashtag.setLastUsed(kweet.getPostedOn());
            hashtag.setTimesUsed(hashtag.getTimesUsed() + 1);
            kweet.getHashtags().add(hashtag);
        }
    }
}
