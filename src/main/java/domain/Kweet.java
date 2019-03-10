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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@NamedQueries({
    @NamedQuery(name = "kweet.findByContent", query = "SELECT k FROM Kweet k WHERE k.content LIKE :content")
    ,
        @NamedQuery(name = "kweet.findUserKweets", query = "SELECT k FROM Kweet k WHERE k.createdBy = :user"),})
@NoArgsConstructor
@RequiredArgsConstructor
public class Kweet implements Serializable {

    @Getter
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    private User createdBy;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "likes", cascade = CascadeType.ALL)
    private List<User> likes = new ArrayList<>();

    @Getter
    @Setter
    @ManyToMany(mappedBy = "mentions", cascade = CascadeType.ALL)
    private List<User> mentions = new ArrayList<>();

    @Getter
    @Setter
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "kweet_hashtags",
            joinColumns = @JoinColumn(name = "kweet_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "id")
    )
    private List<Hashtag> hashtags = new ArrayList<>();

    @Getter
    @Setter
    @NonNull
    @Size(max = 140)
    private String content;

    @Getter
    @Temporal(TemporalType.DATE)
    private Date postedOn;

    @PrePersist
    protected void onCreate() {
        postedOn = new Date();
    }
}
