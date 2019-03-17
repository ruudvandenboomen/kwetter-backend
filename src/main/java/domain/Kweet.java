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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Getter
@Setter
public class Kweet implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User createdBy;

    @ManyToMany(mappedBy = "likes")
    private List<User> likes = new ArrayList<>();

    @ManyToMany(mappedBy = "mentions")
    private List<User> mentions = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "kweet_hashtags",
            joinColumns = @JoinColumn(name = "kweet_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "id")
    )
    private List<Hashtag> hashtags = new ArrayList<>();

    @NonNull
    @Size(max = 140)
    private String content;

    @Temporal(TemporalType.DATE)
    private Date postedOn;

    @PrePersist
    protected void onCreate() {
        postedOn = new Date();
    }
}
