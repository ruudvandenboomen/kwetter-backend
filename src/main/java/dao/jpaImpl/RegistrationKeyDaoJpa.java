/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.jpaImpl;

import dao.interfaces.RegistrationKeyDao;
import domain.RegistrationKey;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import qualifier.JPA;

@JPA
@Stateless
public class RegistrationKeyDaoJpa extends BaseDaoJpa<RegistrationKey> implements RegistrationKeyDao {

    @PersistenceContext(unitName = "kwetterPU")
    private EntityManager em;

    public RegistrationKeyDaoJpa() {
        super(RegistrationKey.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    @Override
    public RegistrationKey findKey(String keyName) {
        TypedQuery<RegistrationKey> query = em.createNamedQuery("registrationKey.findKey", RegistrationKey.class);
        query.setParameter("registrationKey", keyName);
        List<RegistrationKey> keys = query.getResultList();
        if (keys != null && !keys.isEmpty()) {
            return keys.get(0);
        }
        return null;
    }

}
