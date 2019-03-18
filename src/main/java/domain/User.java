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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column(unique = true)
    private String username;
    
    private String passwordHash;

    @NonNull
    @Column(unique = true)
    private String email;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @OneToMany(mappedBy = "createdBy")
    private List<Kweet> kweets = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "kweet_id", referencedColumnName = "id")
    )
    private List<Kweet> likes = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_mentions",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "kweet_id", referencedColumnName = "id")
    )
    private List<Kweet> mentions = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_follower",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id")
    )
    private List<User> followers = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_following",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "following_id", referencedColumnName = "id")
    )
    private List<User> following = new ArrayList<>();

    public void follow(User user) {
        following.add(user);
        user.followers.add(this);
    }

    public void unfollow(User user) {
        if (followers.contains(user) && user.getFollowing().contains(this)) {
            followers.remove(user);
            user.following.remove(this);
        }
    }

}
