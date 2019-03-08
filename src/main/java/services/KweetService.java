/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import qualifier.JPA;
import dao.KweetDao;
import dao.UserDao;
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
    private KweetDao dao;

    @Inject
    @JPA
    private UserDao userDao;

    public void createKweet(Kweet kweet, String username) throws UserNotFoundException {
        User user = userDao.getUser(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        kweet.setCreatedBy(user);
        setMentions(kweet);
        this.dao.create(kweet, user);
    }

    public List<Kweet> findByContent(String content) {
        return this.dao.findByContent(content);
    }

    public boolean likeKweet(long id, String username) throws UserNotFoundException, KweetNotFoundException {
        Kweet kweet = dao.findById(id);
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

    private void setMentions(Kweet kweet) {
        String pattern = "\\@\\S*";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(kweet.getContent());

        List<String> names = new ArrayList<>();
        while (m.find()) {
            names.add(m.group().replace("@", ""));
        }

        for (String name : names) {
            User user = userDao.getUser(name);
            if (user != null) {
//                kweet.getMentions().add(user);
                user.getMentions().add(kweet);
            }
        }
    }
}
