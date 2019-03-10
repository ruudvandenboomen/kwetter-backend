/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.Jpa;

import dao.interfaces.RoleDao;
import domain.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RoleDaoJpa implements RoleDao {

    @PersistenceContext(unitName = "kwetterPU")
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public void addRole(Role role) {
        em.persist(role);
    }

}
