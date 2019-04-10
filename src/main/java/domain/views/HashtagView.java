/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.views;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HashtagView {

    private String name;
    private int timesUsed = 0;
    private Date lastUsed;
}
