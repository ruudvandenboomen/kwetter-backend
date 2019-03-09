/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import domain.Kweet;
import java.util.Comparator;

public class TimelineComparator implements Comparator<Kweet> {

    @Override
    public int compare(Kweet t, Kweet t1) {
        return t.getPostedOn().compareTo(t1.getPostedOn());
    }

}
