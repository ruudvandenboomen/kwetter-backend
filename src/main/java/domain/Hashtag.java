/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@NamedQueries({
    @NamedQuery(name = "hashtag.findByName", query = "SELECT h FROM Hashtag h WHERE h.name = :name"),})
@NoArgsConstructor
@RequiredArgsConstructor
public class Hashtag {

    @Getter
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Column(unique = true)
    @NonNull
    private String name;

    @Getter
    @Setter
    private int timesUsed = 0;

    @Getter
    @Setter
    private Date lastUsed;

    @Getter
    @ManyToMany(mappedBy = "hashtags", cascade = CascadeType.ALL)
    private List<Kweet> kweets = new ArrayList<>();

}
