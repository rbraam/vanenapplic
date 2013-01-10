/*
 * BeheerCategorieAction.java
 *
 * Created on 25 maart 2007, 16:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nl.roy.vanenapplic.struts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.b3p.commons.services.FormUtils;
import nl.roy.vanenapplic.hibernate.Band;
import nl.roy.vanenapplic.hibernate.Categorie;
import nl.roy.vanenapplic.services.MyDatabase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import org.apache.xerces.impl.xpath.regex.ParseException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Roy
 */
public class BeheerCategorieAction extends VanenapplicCrudAction{
    private static final Log log = LogFactory.getLog(BeheerCategorieAction.class);
    
    public ActionForward unspecified(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getParameter("selectedId")!=null){
            String selectedId=request.getParameter("selectedId");
            Categorie c= getCategorie(selectedId);
            populateForm(form,c);
            if (c!=null){
                request.setAttribute("selectedId",c.getId());
            }
            Session sess = MyDatabase.currentSession();
            request.setAttribute("banden",sess.createQuery("from Band b order by b.id").list());
        }
        return super.unspecified(mapping,form,request,response);
    }
    /**
     * If a create submit is done. The methode fills the doNew attribute with a value (can be any value)
     * So the JSP knows that it must show the input fields
     * @param mapping The mapping of the action
     * @param form The form the action is linking to
     * @param request The request of this action
     * @param response response of this action
     * @return The action forward
     * @throws java.lang.Exception When something gone wrong
     */
    public ActionForward create(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("doNew","vul");
        Session sess = MyDatabase.currentSession();
        request.setAttribute("banden",sess.createQuery("from Band b order by b.id").list());
        return super.create(mapping,form,request,response);
    }
    /**
     *Reads and Saves or updates the Categorie in the form to the database.
     * @param mapping The mapping of the action
     * @param form The form the action is linking to
     * @param request The request of this action
     * @param response response of this action
     * @return The action forward
     * @throws java.lang.Exception When something gone wrong
     */
    public ActionForward save(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
       //validate and check for errors
        ActionErrors errors = form.validate(mapping, request);
        if(!errors.isEmpty()) {
            addMessages(request, errors);
            prepareMethod(form, request, EDIT, LIST);
            addAlternateMessage(mapping, request, VALIDATION_ERROR_KEY);
            return getAlternateForward(mapping, request);
        }
        Session sess = MyDatabase.currentSession();
        Categorie c=null;
        if (form.get("id")!=null){
            Integer id= (Integer)form.get("id");
            c = (Categorie)sess.get(Categorie.class,id);
        }
        if (c==null){
            c= new Categorie();
        }
        c.setLeeftijdtot((Integer)form.get("leeftijdtot"));
        c.setLeeftijdvan((Integer)form.get("leeftijdvan"));
        Integer band=(Integer)form.get("band");
        c.setBand((Band)sess.get(Band.class,band));       
        
        sess.saveOrUpdate(c);
        return super.save(mapping,form,request,response);
    }
    /**
     * Is called when a submit delete is done. The function deletes a dataprovider if there are no relations with maps (kaarten).
     * @param mapping The mapping of the action
     * @param form The form the action is linking to
     * @param request The request of this action
     * @param response response of this action
     * @return The action forward
     * @throws java.lang.Exception When something gone wrong
     */
    public ActionForward delete(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //validate and check for errors
        ActionErrors errors = form.validate(mapping, request);
        if(!errors.isEmpty()) {
            addMessages(request, errors);
            prepareMethod(form, request, EDIT, LIST);
            addAlternateMessage(mapping, request, VALIDATION_ERROR_KEY);
            return getAlternateForward(mapping, request);
        }
        
        Session sess = MyDatabase.currentSession();
        if (form.get("id")!=null){
            Integer id= (Integer)form.get("id");
            Categorie c= (Categorie)sess.get(Categorie.class,id);
            if (c!=null){
                try{
                    sess.delete(c);
                    sess.flush();
                }catch (Exception e){
                    log.error(e);
                    request.setAttribute("message","De categorie is niet verwijderd i.v.m. relaties die gelegd zijn.");
                }                
            }else{
                request.setAttribute("message","Het gegeven Id bestaat (niet meer) in de database.");
            }            
        }else{
            request.setAttribute("message","Geen id gevonden");
        }
        return super.delete(mapping,form,request,response);
    }
    /**
     * Overwrites the createLists methode that is called when this action is called
     * @param form The form the action is linking to
     * @param request The request of this action
     * @throws java.lang.Exception When something gone wrong
     */
    public void createLists(DynaValidatorForm form, HttpServletRequest request) throws Exception {
        Session sess = MyDatabase.currentSession();
        request.setAttribute("listCategorien", sess.createQuery("from Categorie c order by c.id").list());
    }
    /**
     *Get the dataprovider with id from the database
     *@param selectedId The id of the dataprovider
     *@return the dataprovider
     */
    private Categorie getCategorie(String selectedId){
        Session sess = MyDatabase.currentSession();
        Categorie d = (Categorie)sess.get(Categorie.class,new Integer(selectedId));
        return d;
    }
    /**
     *Populates the dynavalidatorform given with the dataprovider object.
     *@param form the dynavalidatorform that must be filled
     *@param d the dataprovider that fills the form.
     */
    private void populateForm(DynaValidatorForm form, Categorie c) {
        form.set("band",c.getBand().getId());
        form.set("leeftijdvan",c.getLeeftijdvan());
        form.set("leeftijdtot",c.getLeeftijdtot());
        form.set("id",c.getId());
    }



    
}
