/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import domain.Hashtag;
import java.util.List;

public interface HashtagDao extends BaseDao<Hashtag>  {

    Hashtag find(String name);

    public List<Hashtag> getPopularHashtags();
}
