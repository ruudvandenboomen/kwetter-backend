/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
@NamedQueries({
    @NamedQuery(name = "registrationKey.findKey", query = "SELECT r FROM RegistrationKey r WHERE r.registrationKey =:registrationKey")})
public class RegistrationKey {

    @Id
    @GeneratedValue
    private long id;
     
    private String registrationKey;
    
    @OneToOne
    private User user;

    public RegistrationKey() {
    }

    public RegistrationKey(String registrationKey, User user) {
        this.registrationKey = registrationKey;
        this.user = user;
    }

}
