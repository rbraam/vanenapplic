/*
 * VanenapplicCrudAction.java
 *
 * Created on 25 maart 2007, 16:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nl.roy.vanenapplic.struts;

import nl.b3p.commons.struts.CrudAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.roy.vanenapplic.hibernate.Karateka;
import nl.roy.vanenapplic.services.MyDatabase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Roy
 */
public class VanenapplicCrudAction extends CrudAction{
    private static final Log log = LogFactory.getLog(VanenapplicCrudAction.class);
    /**
     *Returns the current hibernate session.
     *@return the current session opened.
     */
    protected Session getHibernateSession() {
        return MyDatabase.currentSession();
    }
    /**
     *Overwrites the execute action that is called when the action is called.
     *It's opening and closing a database connection and a transaction to handle the requests to the database.
     * @param mapping The mapping of the action
     * @param form The form the action is linking to
     * @param request The request of this action
     * @param response response of this action
     * @return The action forward
     * @throws java.lang.Exception When something gone wrong
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        
        Session sess = getHibernateSession();
        Transaction tx = sess.beginTransaction();
        
        ActionForward forward = null;
        String msg = null;
        
        try {
            forward = super.execute(mapping, form, request, response);
            tx.commit();
            return forward;
        } catch(Exception e) {
            tx.rollback();
            log.error("Exception occured, rollback", e);
            addMessage(request, "errors.exception", e.getClass() + ": " + e.getMessage());
        }
        // Start tweede sessie om tenminste nog de lijsten op te halen
        sess = getHibernateSession();
        tx = sess.beginTransaction();
        try {
            prepareMethod((DynaValidatorForm)form, request, LIST, EDIT);
            tx.commit();
        } catch(Exception e) {
            tx.rollback();
            log.error("Exception occured in second session, rollback", e);
        }
        return getAlternateForward(mapping, request);
    }
    public Karateka setTotaalpunten(Karateka k){
        Session sess = MyDatabase.currentSession();
        Object oka=sess.createQuery("select sum(i.punten) from Ingedeeldekarateka i where i.karateka = :k and vanencompetitie.type= 'kata'").setEntity("k",k).uniqueResult();
        Integer totaalpka=new Integer("0");
        if (oka!=null){
            totaalpka=(Integer)oka;
            k.setTotaalpuntenka(new Integer(k.getBeginpuntenka().intValue()+totaalpka.intValue()));
        }else{
            k.setTotaalpuntenka(k.getBeginpuntenka());
        }
        Object oku=sess.createQuery("select sum(i.punten) from Ingedeeldekarateka i where i.karateka = :k and vanencompetitie.type= 'kumite'").setEntity("k",k).uniqueResult();
        Integer totaalpku=new Integer("0");
        if (oku!=null){
            totaalpku=(Integer)oku;
            k.setTotaalpuntenku(new Integer(k.getBeginpuntenku().intValue()+totaalpku.intValue()));
        }else{
            k.setTotaalpuntenku(k.getBeginpuntenku());
        }
        return k;
    }
}
