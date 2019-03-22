/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class AuthBean {

    public String doLogout() {
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();

        return "admin.xhtml?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() != null;
    }

    public String getPrincipalName() {
        if (isLoggedIn()) {
            return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        } else {
            return "ANONYMOUS";
        }
    }

    public boolean isAdmin() {
        FacesContext fc = FacesContext.getCurrentInstance();
        return fc.getExternalContext().isUserInRole("admin");
    }
}
