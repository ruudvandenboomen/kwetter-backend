/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.interfaces.HashtagDao;
import dao.interfaces.KweetDao;
import dao.interfaces.UserDao;
import domain.Hashtag;
import domain.Kweet;
import domain.User;
import domain.views.KweetView;
import domain.views.UserListView;
import event.KweetEvent;
import exceptions.KweetNotFoundException;
import exceptions.UnauthorizedException;
import exceptions.UserNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import qualifier.JPA;
import util.KweetConverter;
import util.TimelineComparator;

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

    @Inject
    private Event<KweetEvent> kweetEvent;

    public void createKweet(Kweet kweet, String username) throws UserNotFoundException {
        User user = userDao.find(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        kweet.setCreatedBy(user);
        this.kweetDao.create(kweet);
        kweetEvent.fire(new KweetEvent(kweet));
        setMentions(kweet, regex(kweet.getContent(), "@"));
        setHashtags(kweet, regex(kweet.getContent(), "#"));
    }

    public List<KweetView> findByContent(String content) {
        return KweetConverter.convertKweets(kweetDao.findByContent(content));
    }

    public boolean likeKweet(long id, String username) throws UserNotFoundException, KweetNotFoundException {
        Kweet kweet = kweetDao.find(id);
        if (kweet == null) {
            throw new KweetNotFoundException();
        }

        User user = userDao.find(username);
        if (user == null) {
            throw new UserNotFoundException();
        }

        if (!user.getLikes().contains(kweet)) {
            user.getLikes().add(kweet);
            kweet.getLikes().add(user);
            return true;
        } else {
            return false;
        }
    }

    public List<KweetView> getMentions(String username) throws UserNotFoundException {
        User user = userDao.find(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return KweetConverter.convertKweets(user.getMentions());
    }

    private Collection<String> regex(String content, String prefix) {
        Pattern r = Pattern.compile("\\" + prefix + "\\S*");
        Matcher m = r.matcher(content);

        Collection<String> names = new HashSet<>();
        while (m.find()) {
            names.add(m.group().replace(prefix, ""));
        }
        return names;
    }

    private void setMentions(Kweet kweet, Collection<String> names) {
        for (String name : names) {
            User user = userDao.find(name);
            if (user != null) {
                user.getMentions().add(kweet);
            }
        }
    }

    private void setHashtags(Kweet kweet, Collection<String> foundTags) {
        for (String tag : foundTags) {
            Hashtag hashtag = hashtagDao.find(tag);
            if (hashtag == null) {
                hashtag = new Hashtag(tag);
                hashtagDao.create(hashtag);
            }
            hashtag.setLastUsed(kweet.getPostedOn());
            hashtag.setTimesUsed(hashtag.getTimesUsed() + 1);
            hashtag.getKweets().add(kweet);
            kweet.getHashtags().add(hashtag);
        }
    }

    public List<KweetView> getTimeline(String username) throws UserNotFoundException {
        List<KweetView> timeline = new ArrayList<>();
        User user = userDao.find(username);
        if (user == null) {
            throw new UserNotFoundException();
        } 
        timeline.addAll(KweetConverter.convertKweets(kweetDao.getUserKweets(user)));
        timeline.addAll(KweetConverter.convertKweets(getFollowingKweets(user.getFollowing())));
        timeline.sort(new TimelineComparator());
        return timeline;
    }

    private List<Kweet> getFollowingKweets(List<User> following) {
        List<Kweet> kweet = new ArrayList<>();
        for (User user : following) {
            kweet.addAll(kweetDao.getUserKweets(user));
        }
        return kweet;
    }

    public List<Kweet> getUserKweets(String username) {
        return kweetDao.getUserKweets(userDao.find(username));
    }

    public void deleteKweet(Kweet kweet) {
        if (kweet.getId() != null) {
            editKweetHashtags(kweet);
            Kweet foundKweet = kweetDao.find(kweet.getId());
            kweetDao.delete(foundKweet);
        }
    }

    public List<Kweet> getAll() {
        return kweetDao.getAll();
    }

    public List<UserListView> getKweetLikes(long id) throws KweetNotFoundException {
        Kweet kweet = kweetDao.find(id);
        if (kweet == null) {
            throw new KweetNotFoundException();
        }
        return KweetConverter.createUserListItems(kweet.getLikes());
    }

    public void deleteKweet(long id, String name) throws KweetNotFoundException, UnauthorizedException {
        Kweet kweet = kweetDao.find(id);
        if (kweet == null) {
            throw new KweetNotFoundException();
        }
        if (kweet.getCreatedBy().getUsername().equals(name)) {
            editKweetHashtags(kweet);
            kweetDao.delete(kweet);
        } else {
            throw new UnauthorizedException();
        }

    }

    private void editKweetHashtags(Kweet kweet) {
        for (Hashtag hashtag : kweet.getHashtags()) {
            hashtag.setTimesUsed(hashtag.getTimesUsed() - 1);
            if (hashtag.getKweets().size() > 1) {
                hashtag.getKweets().remove(kweet);
                hashtag.setLastUsed(hashtag.getKweets().get(hashtag.getKweets().size() - 1).getPostedOn());
                hashtagDao.edit(hashtag);
            } else if (hashtag.getKweets().size() == 1) {
                hashtagDao.delete(hashtagDao.find(hashtag.getName()));
            }
        }
    }
}
