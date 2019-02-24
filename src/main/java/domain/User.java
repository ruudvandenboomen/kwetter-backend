/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@NamedQueries({
    @NamedQuery(name = "user.findByName", query = "SELECT u FROM User u WHERE u.username = :name"), //    @NamedQuery(name = "user.getKweets", query = "SELECT * FROM User u, Kweet k WHERE k.user == u.id")
})
@RequiredArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Getter
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @NonNull
    @Column(unique = true)
    private String username;

    @Getter
    private String displayName;

    @NonNull
    @Column(unique = true)
    private String email;

    @Getter
    @NonNull
    private Date dateOfBirth;

    @Getter
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Kweet> kweets = new ArrayList<Kweet>();

    @Getter
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Kweet> likes = new ArrayList<Kweet>();

    @Getter
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Kweet> mentions = new ArrayList<Kweet>();

    @Getter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_followers")
    private List<User> followers = new ArrayList<User>();

    @Getter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_following")
    private List<User> following = new ArrayList<User>();

    public void addKweet(Kweet kweet) {
        kweet.setCreatedBy(this);
        kweets.add(kweet);
    }

    public void removeKweet(Kweet kweet) {
        if (kweets.contains(kweet)) {
            kweets.remove(kweet);
        }
    }

    public void addLike(Kweet kweet) {
        likes.add(kweet);
    }

    public void removeLike(Kweet kweet) {
        if (likes.contains(kweet)) {
            likes.remove(kweet);
        }
    }

    public void addFollower(User user) {
        if (!followers.contains(user)) {
            followers.add(user);
        }
    }

    public void removeFollower(User user) {
        if (followers.contains(user)) {
            followers.remove(user);
        }
    }

    public void addFollowing(User user) {
        if (!following.contains(user)) {
            followers.add(user);
        }
    }

    public void removeFollow(User user) {
        if (following.contains(user)) {
            following.remove(user);
        }
    }
}
