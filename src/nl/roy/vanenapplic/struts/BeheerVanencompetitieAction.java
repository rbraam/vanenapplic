/*
 * BeheerVanencompetitieAction.java
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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.b3p.commons.services.FormUtils;
import nl.roy.vanenapplic.hibernate.Ingedeeldekarateka;
import nl.roy.vanenapplic.hibernate.Karateka;
import nl.roy.vanenapplic.hibernate.Vanencompetitie;
import nl.roy.vanenapplic.services.MyDatabase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import org.apache.xerces.impl.xpath.regex.ParseException;
import org.hibernate.Session;

/**
 *
 * @author Roy
 */
public class BeheerVanencompetitieAction extends VanenapplicCrudAction{
    private static final Log log = LogFactory.getLog(BeheerVanencompetitieAction.class);
    
    public ActionForward unspecified(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getParameter("selectedId")!=null){
            String selectedId=request.getParameter("selectedId");
            Vanencompetitie v= getVanencompetitie(selectedId);
            
            Session sess = MyDatabase.currentSession();
            List listKaratekas=sess.createQuery("from Karateka k order by k.achternaam").list();
            populateForm(form,v);
            if (v!=null){
                request.setAttribute("selectedId",v.getId());
                for (int i=0; i < listKaratekas.size(); i++){
                    Karateka k= (Karateka)listKaratekas.get(i);
                    k.setVanenDatum(v.getDatum());
                }
            }            
            request.setAttribute("listKaratekas",listKaratekas);
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
        request.setAttribute("listKaratekas",sess.createQuery("from Karateka k order by k.achternaam").list());
        return super.create(mapping,form,request,response);
    }
    /**
     *Reads and Saves or updates the Vanencompetitie in the form to the database.
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
        Vanencompetitie v=null;
        if (form.get("id")!=null){
            Integer id= (Integer)form.get("id");
            v = (Vanencompetitie)sess.get(Vanencompetitie.class,id);
        }
        if (v==null){
            v= new Vanencompetitie();
        }
        v.setLokatie(FormUtils.nullIfEmpty((String) form.get("lokatie")));
        v.setType(FormUtils.nullIfEmpty((String) form.get("type")));
        String dateString=FormUtils.nullIfEmpty((String) form.get("datum"));
        // Format with a custom format
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            // Parse with a custom format
            Date date = df.parse(dateString);
            v.setDatum(date);
            
        } catch(ParseException e) {
            log.error(e);
        }
        sess.saveOrUpdate(v);
        Integer[] karatekas=new Integer[0];
        if (form.get("karatekas")!=null)
            karatekas= (Integer[])form.get("karatekas");           
    
        StringBuffer whereStatement=new StringBuffer();
        //verwijder de uitgevinkte karatekas
        if (v.getId()!=null){
            whereStatement.append("  where i.vanencompetitie = "+v.getId().intValue());
            for (int i=0; i < karatekas.length; i++){                
                whereStatement.append(" and i.karateka != "+karatekas[i]);                
            }
            sess.createQuery("delete from Ingedeeldekarateka i "+whereStatement.toString()).executeUpdate();
            for (int i=0; i < karatekas.length; i++){
                if (sess.createQuery("from Ingedeeldekarateka i where i.vanencompetitie= :v and i.karateka= :k").setEntity("v",v).setInteger("k",karatekas[i].intValue()).list().size()<1){
                    Ingedeeldekarateka ik=new Ingedeeldekarateka();
                    ik.setKarateka((Karateka)sess.get(Karateka.class,karatekas[i]));
                    ik.setVanencompetitie(v);
                    ik.setBetrouwbarepunten(true);
                    sess.saveOrUpdate(ik);
                }
            }
        }
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
            Vanencompetitie v= (Vanencompetitie)sess.get(Vanencompetitie.class,id);
            if (v!=null){
                try{
                    sess.createQuery("delete from Ingedeeldekarateka where vanencompetitie = :v").setEntity("v",v).executeUpdate();
                    sess.delete(v);
                    sess.flush();
                }catch (Exception e){
                    log.error("Fout bij verwijderen", e);
                    request.setAttribute("message","De vanencompetitie is niet verwijderd i.v.m. relaties die gelegd zijn.");
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
        request.setAttribute("listVanencompetities", sess.createQuery("from Vanencompetitie d order by d.id").list());
    }
    /**
     *Get the dataprovider with id from the database
     *@param selectedId The id of the dataprovider
     *@return the dataprovider
     */
    private Vanencompetitie getVanencompetitie(String selectedId){
        Session sess = MyDatabase.currentSession();
        Vanencompetitie d = (Vanencompetitie)sess.get(Vanencompetitie.class,new Integer(selectedId));
        return d;
    }
    /**
     *Populates the dynavalidatorform given with the dataprovider object.
     *@param form the dynavalidatorform that must be filled
     *@param d the dataprovider that fills the form.
     */
    private void populateForm(DynaValidatorForm form, Vanencompetitie v) {
        form.set("lokatie",v.getLokatie());
        form.set("datum",v.getDatumString());
        form.set("id",v.getId());
        form.set("type",v.getType());
        Session sess = MyDatabase.currentSession();
        List karatekas= sess.createQuery("from Ingedeeldekarateka i where i.vanencompetitie = :id").setInteger("id",v.getId().intValue()).list();
        Integer[] selectedKaratekas=new Integer[karatekas.size()];
        for (int i=0; i < karatekas.size(); i++){
            Ingedeeldekarateka k= (Ingedeeldekarateka)karatekas.get(i);
            selectedKaratekas[i]=k.getKarateka().getId();
        }        
        form.set("karatekas",selectedKaratekas);
    }



    
}
