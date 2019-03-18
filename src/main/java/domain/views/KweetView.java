/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.views;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

public class KweetView {

    @Getter
    @Setter
    private String content;
    @Getter
    @Setter
    private Date postedOn;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String profileImage;
    @Getter
    @Setter
    private int likes;
}
