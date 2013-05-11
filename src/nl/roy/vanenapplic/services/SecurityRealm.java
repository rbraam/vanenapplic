/**
 * @author C. van Lith
 * @version 1.00 2006/03/03
 *
 * Purpose: a class checking basic authority for users.
 *
 * @copyright 2007 All rights reserved. B3Partners
 */

package nl.roy.vanenapplic.services;

import java.security.Principal;
import nl.roy.vanenapplic.hibernate.Gebruiker;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.securityfilter.realm.ExternalAuthenticatedRealm;
import org.securityfilter.realm.SecurityRealmInterface;

public class SecurityRealm implements SecurityRealmInterface, ExternalAuthenticatedRealm {
    private final Log log = LogFactory.getLog(this.getClass());


    public SecurityRealm() {
    }
    /** Checks wether an user, given his username and password, is allowed to use the system.
     *
     * @param username String representing the username.
     * @param password String representing the password.
     *
     * @return a principal object containing the user if he has been found as a registered user. Otherwise this object wil be empty (null).
     */
    public Principal authenticate(String username, String password) {

        String encpw = null;
        try {
            encpw = Crypter.encryptText(password);
        } catch (Exception ex) {
            log.error("error encrypting password: ", ex);
        }
        Session sess = MyDatabase.currentSession();
        Transaction tx = sess.beginTransaction();
        tx.begin();
        try {
            Gebruiker gebruiker=(Gebruiker) sess.createQuery(
                    "from Gebruiker g where " +
                    "lower(g.gebruikersnaam) = lower(:gebruikersnaam) " +
                    "and g.wachtwoord = :wachtwoord")
                    .setParameter("gebruikersnaam", username)
                    .setParameter("wachtwoord", encpw)
                    .uniqueResult();
            if (gebruiker==null){
                gebruiker=(Gebruiker) sess.createQuery(
                    "from Gebruiker g where " +
                    "lower(g.gebruikersnaam) = lower(:gebruikersnaam) " +
                    "and g.wachtwoord = :wachtwoord")
                    .setParameter("gebruikersnaam", username)
                    .setParameter("wachtwoord", password)
                    .uniqueResult();
                if (gebruiker!=null){
                    gebruiker.setWachtwoord(encpw);
                    sess.save(gebruiker);
                }
            }
            if (gebruiker!=null)
                return gebruiker;
            else
                log.debug("No results using encrypted password");
        } catch (Exception e) {
            log.debug("No results using encrypted password cause: ",e);
        } finally {
            tx.commit();
        }
        return null;
    }

    public Principal getAuthenticatedPrincipal(String username) {
        Session sess = MyDatabase.currentSession();
        Transaction tx = sess.beginTransaction();
        tx.begin();
        try {
            Gebruiker gebruiker = (Gebruiker)sess.createQuery(
                    "from Gebruiker g where " +
                    "lower(g.gebruikersnaam) = lower(:gebruikersnaam) ")
                    .setParameter("gebruikersnaam", username)
                    .uniqueResult();
            return gebruiker;
        } catch (Exception e) {
            log.error("Error while getting Gebruiker principal",e);
            return null;
        } finally {
            tx.commit();
        }

    }

    /** Checks if a user is in the given role. A role represents a level of priviliges.
     *
     * @param principal Principal object representing the user.
     * @param role String representing the role this user has to checked against.
     *
     * @return a boolean which is true if the user is in the defined role otherwise false is returned.
     */
    public boolean isUserInRole(Principal principal, String role) {
        if(!(principal instanceof Gebruiker)) {
            return false;
        }
        Gebruiker gebruiker = (Gebruiker)principal;
        //log.info("Check user principal has role");
        return gebruiker.checkRole(role);
    }

    public Principal getAuthenticatedPrincipal(String username, String password) {
        return authenticate(username, password);
    }
}