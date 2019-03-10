/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.views;

import domain.Kweet;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class ProfileView {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String location;
    @Getter
    @Setter
    private String bio;
    @Getter
    @Setter
    private String website;
    @Getter
    @Setter
    private String profileImage;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private int followerCount;
    @Getter
    @Setter
    private int followingCount;
    @Getter
    @Setter
    private List<KweetView> kweets = new ArrayList<>();

}
