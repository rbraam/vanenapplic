/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.roy.vanenapplic.hibernate;

import java.security.Principal;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Roy
 */
public class Gebruiker implements Principal{
    private Integer id;
    private String naam;
    private String gebruikersnaam;
    private String wachtwoord;

    private Set roles;

    /** Creates a new instance of Beheerder */
    public Gebruiker() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public boolean checkRole(String role) {
        if(roles == null) return false;
        Iterator it = roles.iterator();
        while (it.hasNext()) {
            Role theUserroles = (Role) it.next();
            if (role.equalsIgnoreCase(theUserroles.getName())) {
                return true;
            }
        }
        return false;
    }
    public String getName() {
        return naam;
    }

    public Set getRoles() {
        return roles;
    }

    public void setRoles(Set roles) {
        this.roles = roles;
    }

    public String toString(){
        return getNaam();

    }

}
