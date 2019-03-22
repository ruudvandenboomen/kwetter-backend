/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.views;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileView {

    private long id;
    private String name;
    private String location;
    private String bio;
    private String website;
    private String profileImage;
    private String username;
    private int followerCount;
    private int followingCount;
    private List<KweetView> kweets = new ArrayList<>();

}
