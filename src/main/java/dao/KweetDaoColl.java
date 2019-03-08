/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
    public void create(Kweet kweet, User user) {
        kweets.add(kweet);
    }

    @Override
    public void delete(Kweet kweet) {
        kweets.remove(kweet);
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

    @Override
    public Kweet findById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
