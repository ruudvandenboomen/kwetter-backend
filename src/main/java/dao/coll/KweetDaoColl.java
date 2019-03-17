/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.coll;

import dao.interfaces.KweetDao;
import domain.Kweet;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

@ApplicationScoped
@Default
public class KweetDaoColl implements KweetDao {

    private List<Kweet> kweets = new ArrayList<Kweet>();

    public KweetDaoColl() {
    }

    @Override
    public List<Kweet> findByContent(String content) {
        List<Kweet> result = new ArrayList<>();
        for (Kweet kweet : kweets) {
            if (kweet.getContent().contains(content)) {
                result.add(kweet);
            }
        }
        return result;
    }

    public List<Kweet> getUserKweets(User user) {
        List<Kweet> result = new ArrayList<>();
        for (Kweet kweet : kweets) {
            if (kweet.getCreatedBy() == user) {
                result.add(kweet);
            }
        }
        return result;
    }

    @Override
    public void create(Kweet Object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Kweet find(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(Kweet Object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Kweet Object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Kweet> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
