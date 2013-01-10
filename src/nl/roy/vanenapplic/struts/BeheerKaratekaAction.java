/*
 * BeheerKaratekaAction.java
 *
 * Created on 25 maart 2007, 16:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nl.roy.vanenapplic.struts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.b3p.commons.services.FormUtils;
import nl.roy.vanenapplic.hibernate.Band;
import nl.roy.vanenapplic.hibernate.Karateka;
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
public class BeheerKaratekaAction extends VanenapplicCrudAction{
    private static final Log log = LogFactory.getLog(BeheerKaratekaAction.class);
    
    public ActionForward unspecified(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getParameter("selectedId")!=null){
            String selectedId=request.getParameter("selectedId");
            Karateka k= getKarateka(selectedId);
            populateForm(form,k);
            if (k!=null){
                request.setAttribute("selectedId",k.getId());
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
     *Reads and Saves or updates the Karateka in the form to the database.
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
        Karateka k=null;
        if (form.get("id")!=null){
            Integer id= (Integer)form.get("id");
            k = (Karateka)sess.get(Karateka.class,id);
        }
        if (k==null){
            k= new Karateka();
        }
        k.setVoornaam(FormUtils.nullIfEmpty((String) form.get("voornaam")));
        k.setAchternaam(FormUtils.nullIfEmpty((String) form.get("achternaam")));
        Integer band=(Integer)form.get("band");
        k.setBand((Band)sess.get(Band.class,band));       
        String dateString=FormUtils.nullIfEmpty((String) form.get("geboortedatum"));
        // Format with a custom format
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            // Parse with a custom format
            Date date = df.parse(dateString);
            k.setGeboortedatum(date);
            
        } catch(ParseException e) {
            log.error(e);
        }
        k.setGeslacht(FormUtils.nullIfEmpty((String) form.get("geslacht")));
        String gewichtStr=(String) form.get("gewicht");
        try{
            double gewicht= Double.parseDouble(gewichtStr);
            k.setGewicht(gewicht);
        }catch (NumberFormatException nfe){
            log.error(nfe);
        }
        k.setTussenvoegsel(FormUtils.nullIfEmpty((String) form.get("tussenvoegsel")));
        if (form.get("beginpuntenka")!=null)
            k.setBeginpuntenka((Integer)form.get("beginpuntenka"));
        else
            k.setBeginpuntenka(new Integer("0"));
        if (form.get("beginpuntenku")!=null)
            k.setBeginpuntenku((Integer)form.get("beginpuntenku"));
        else
            k.setBeginpuntenku(new Integer("0"));
        sess.saveOrUpdate(k);
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
            Karateka k= (Karateka)sess.get(Karateka.class,id);
            if (k!=null){
                try{
                    sess.createQuery("delete from Ingedeeldekarateka where karateka= :k").setEntity("k",k).executeUpdate();
                    sess.delete(k);
                    sess.flush();
                }catch (Exception e){
                    request.setAttribute("message","De karateka is niet verwijderd i.v.m. relaties die gelegd zijn.");
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
        List karatekas =sess.createQuery("from Karateka d order by d.achternaam").list();
        /*ArrayList listKaratekas=new ArrayList();
        for (int i=0; i < karatekas.size(); i++){
            Karateka k = (Karateka)karatekas.get(i);
            k=setTotaalpunten(k) ;                         
            listKaratekas.add(k);
        }   */    
        request.setAttribute("listKaratekas",karatekas );
    }
    /**
     *Get the dataprovider with id from the database
     *@param selectedId The id of the dataprovider
     *@return the dataprovider
     */
    private Karateka getKarateka(String selectedId){
        Session sess = MyDatabase.currentSession();
        Karateka k = (Karateka)sess.get(Karateka.class,new Integer(selectedId));
        k=setTotaalpunten(k);
        return k;
    }
    /**
     *Populates the dynavalidatorform given with the dataprovider object.
     *@param form the dynavalidatorform that must be filled
     *@param d the dataprovider that fills the form.
     */
    private void populateForm(DynaValidatorForm form, Karateka k) {
        form.set("voornaam",k.getVoornaam());
        form.set("achternaam",k.getAchternaam());
        form.set("tussenvoegsel",k.getTussenvoegsel());

        form.set("geboortedatum",k.getGeboortedatumString());
        form.set("band",k.getBand().getId());
        form.set("geslacht",k.getGeslacht());
        form.set("gewicht",""+k.getGewicht());
        form.set("id",k.getId());
        form.set("totaalpuntenku",k.getTotaalpuntenku());
        form.set("totaalpuntenka",k.getTotaalpuntenka());
        form.set("beginpuntenku",k.getBeginpuntenku());
        form.set("beginpuntenka",k.getBeginpuntenka());
    }



    
}
