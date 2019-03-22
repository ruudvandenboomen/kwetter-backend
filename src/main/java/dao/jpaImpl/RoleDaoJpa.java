/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.jpaImpl;

import dao.interfaces.RoleDao;
import domain.Role;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import qualifier.JPA;

@JPA
@Stateless
public class RoleDaoJpa implements RoleDao {

    @PersistenceContext(unitName = "kwetterPU")
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Role role) {
        em.persist(role);
    }

    @Override
    public Role find(String name) {
        TypedQuery<Role> query = em.createNamedQuery("role.findByName", Role.class);
        query.setParameter("name", name);
        List<Role> roles = query.getResultList();
        if (roles != null && !roles.isEmpty()) {
            return roles.get(0);
        }
        return null;
    }

    @Override
    public List<Role> getAll() {
        return em.createQuery("SELECT r FROM Role r").getResultList();
    }

}
