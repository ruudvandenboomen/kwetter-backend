/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.Jpa;

import dao.DaoFacade;
import dao.interfaces.RoleDao;
import domain.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RoleDaoJpa extends DaoFacade<Role> implements RoleDao {

    @PersistenceContext(unitName = "kwetterPU")
    private EntityManager em;

    public RoleDaoJpa() {
        super(Role.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public void addRole(Role role) {
        create(role);
    }

}
