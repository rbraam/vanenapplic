/*
 * BeheerPouleAction.java
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
import nl.roy.vanenapplic.hibernate.Categorie;
import nl.roy.vanenapplic.hibernate.Ingedeeldekarateka;
import nl.roy.vanenapplic.hibernate.Karateka;
import nl.roy.vanenapplic.hibernate.Poule;
import nl.roy.vanenapplic.hibernate.Vanencompetitie;
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
public class BeheerPouleAction extends VanenapplicCrudAction{
    private static final Log log = LogFactory.getLog(BeheerPouleAction.class);
    private static final int MAXAANTALDEELNEMERS=6;
    private static final int MINAANTALDEELNEMERS=3;
    public ActionForward unspecified(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getParameter("selectedId")!=null){
            Session sess= MyDatabase.currentSession();
            String selectedId=request.getParameter("selectedId");
            Poule p= getPoule(selectedId);
            Query q=sess.createQuery("from Ingedeeldekarateka i where i.poule = :p");
            q.setEntity("p",p);
            
            List karatekas= q.list();
            if (karatekas.size()>0){
                Ingedeeldekarateka ik=(Ingedeeldekarateka) karatekas.get(0);
                createSelectableKaratekasList(form,request,ik.getVanencompetitie(),p);                
            }
            request.setAttribute("selectedCategorie",p.getCategorie());
            populateForm(form,p,request);
            
        }
       
        createLists(form,request);
        return mapping.findForward("success");
        //return super.unspecified(mapping,form,request,response);
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
        if (form.get("searchCategorie")!=null){
            Session sess= MyDatabase.currentSession();
            Integer catId= (Integer)form.get("searchCategorie");
            Categorie c= (Categorie)sess.get(Categorie.class,catId);
            request.setAttribute("selectedCategorie",c);
            
        }
        createSelectableKaratekasList(form,request);
        createLists(form,request);
        return mapping.findForward("success");
    }
    /**
     *Reads and Saves or updates the Poule in the form to the database.
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
        Poule p=null;
        if (form.get("id")!=null){
            Integer id= (Integer)form.get("id");
            p = (Poule)sess.get(Poule.class,id);
        }
        if (p==null){
            p= new Poule();
        }
        
        p.setNaam(FormUtils.nullIfEmpty((String) form.get("naam")));
        if (form.get("categorie")!=null){
            Integer categorieId= (Integer)form.get("categorie");
            Categorie cat=(Categorie)sess.get(Categorie.class, categorieId);
            p.setCategorie(cat);
        }
        Integer[] iKaratekas=new Integer[0];
        if (form.get("karatekas")!=null)
            iKaratekas= (Integer[])form.get("karatekas"); 
        
        if (iKaratekas.length > MAXAANTALDEELNEMERS || iKaratekas.length < MINAANTALDEELNEMERS){
            request.setAttribute("message","Kan poule niet maken omdat er minimaal "+ MINAANTALDEELNEMERS + " en maximaal "+MAXAANTALDEELNEMERS+ " deelnemers in een poule moeten zitten");
            return super.save(mapping,form,request,response);
        }
        sess.saveOrUpdate(p);
        
                  
    
        StringBuffer whereStatement=new StringBuffer();
        //verwijder de uitgevinkte karatekas
       sess.createQuery("update Ingedeeldekarateka i set i.poule=null where i.poule = :p").setEntity("p",p).executeUpdate();
        if (p.getId()!=null){            
            for (int i=0; i < iKaratekas.length; i++){
                Ingedeeldekarateka ik= (Ingedeeldekarateka) sess.get(Ingedeeldekarateka.class, iKaratekas[i]);
                ik.setPoule(p);
                sess.save(ik);
            };
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
            Poule v= (Poule)sess.get(Poule.class,id);
            if (v!=null){
                try{
                    sess.createQuery("UPDATE Ingedeeldekarateka i set i.poule =null where i.poule= :p").setEntity("p",v).executeUpdate();
                    sess.delete(v);
                    sess.flush();
                }catch (Exception e){
                    log.error(e);
                    request.setAttribute("message","De poule is niet verwijderd i.v.m. relaties die gelegd zijn.");
                    
                    
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
        request.setAttribute("listVanencompetities", sess.createQuery("from Vanencompetitie v order by v.datum desc").list());
        request.setAttribute("listCategorien", sess.createQuery("from Categorie c order by c.id").list());
        StringBuffer whereStatement= new StringBuffer();
        try{
            if (form.get("searchVanencompetitie")!=null && ((Integer)form.get("searchVanencompetitie")).intValue()>0){
                whereStatement.append (" where i.vanencompetitie= "+((Integer)form.get("searchVanencompetitie")).intValue());
            }
            if (form.get("searchCategorie")!=null && ((Integer)form.get("searchCategorie")).intValue()>0){
                if (whereStatement.length()>0){
                    whereStatement.append(" and ");
                }else{
                    whereStatement.append(" where ");
                }
                whereStatement.append("i.poule.categorie = "+((Integer)form.get("searchCategorie")).intValue());                
            }
        }catch (IllegalArgumentException iae){
            log.info("Nog geen form opgebouwd");
        }        
        if (form.get("searchVanencompetitie")!=null && ((Integer)form.get("searchVanencompetitie")).intValue()>0){
            String queryString="select DISTINCT i.poule from Ingedeeldekarateka i";
            if (whereStatement.length()>0){
                queryString+= whereStatement.toString();
            }
            ArrayList listPoules=new ArrayList();      
            List foundPoules= sess.createQuery(queryString).list();
            for (int i=0; i < foundPoules.size(); i++){
                listPoules.add((Poule)foundPoules.get(i));
            }
            request.setAttribute("listPoules", listPoules);
        
            //maak lijst met nog niet ingedeelde karatekas        
            Vanencompetitie vanen=(Vanencompetitie) sess.get(Vanencompetitie.class,(Integer)form.get("searchVanencompetitie"));
            List nietIngedeelde=sess.createQuery("select i.karateka from Ingedeeldekarateka i where i.poule = null AND i.vanencompetitie = :v order by i.karateka.band, i.karateka.geboortedatum desc").setEntity("v",vanen).list();
            ArrayList nietIngedeeldeKaratekas=new ArrayList();
            for (int i=0; i < nietIngedeelde.size(); i++){                
                Karateka k = (Karateka)nietIngedeelde.get(i);
                k.setVanenDatum(vanen.getDatum());
                nietIngedeeldeKaratekas.add(k);
            }
            request.setAttribute("listNietIngedeelde",nietIngedeeldeKaratekas);
        }
        
    }
    /**
     *Get the dataprovider with id from the database
     *@param selectedId The id of the dataprovider
     *@return the dataprovider
     */
    private Poule getPoule(String selectedId){
        Session sess = MyDatabase.currentSession();
        Poule d = (Poule)sess.get(Poule.class,new Integer(selectedId));
        return d;
    }
    /**
     *Populates the dynavalidatorform given with the dataprovider object.
     *@param form the dynavalidatorform that must be filled
     *@param d the dataprovider that fills the form.
     */
    private void populateForm(DynaValidatorForm form, Poule p, HttpServletRequest request) {
        form.set("categorie",p.getCategorie().getId());
        form.set("naam",p.getNaam());
        form.set("id",p.getId());
        request.setAttribute("selectedId",p.getId());
        Session sess = MyDatabase.currentSession();
        List karatekas= sess.createQuery("from Ingedeeldekarateka i where i.poule = :id").setInteger("id",p.getId().intValue()).list();
        Integer[] selectedKaratekas=new Integer[karatekas.size()];
        Vanencompetitie v=null;
        for (int i=0; i < karatekas.size(); i++){
            Ingedeeldekarateka k= (Ingedeeldekarateka)karatekas.get(i);
            selectedKaratekas[i]=k.getId();
            v=k.getVanencompetitie();
        }        
        form.set("karatekas",selectedKaratekas);
        if (v!=null)
            form.set("searchVanencompetitie",v.getId());
        if (p.getCategorie()!=null)
            form.set("searchCategorie",p.getCategorie().getId());
    }
    private void createSelectableKaratekasList(DynaValidatorForm form, HttpServletRequest request) {
        Session sess = MyDatabase.currentSession();
        Integer vanencompetitieId=(Integer)form.get("searchVanencompetitie");
        Integer categorieId=(Integer)form.get("searchCategorie");
        if (vanencompetitieId.intValue()>0 && categorieId.intValue()>0){
            Vanencompetitie v= (Vanencompetitie) sess.get(Vanencompetitie.class,vanencompetitieId);        
            Categorie cat= (Categorie) sess.get(Categorie.class,categorieId);
            createSelectableKaratekasList(form,request,v,cat);
        }
    }

    private void createSelectableKaratekasList(DynaValidatorForm form, HttpServletRequest request, Vanencompetitie vanen, Categorie cat) {
        Session sess = MyDatabase.currentSession();
        Query q=sess.createQuery("from Ingedeeldekarateka i where i.vanencompetitie = :v and i.poule=null");
        q.setInteger("v", vanen.getId().intValue());
        List vanenKaratekas =q.list();
        ArrayList categorieKaratekas = new ArrayList();
        for (int i=0; i < vanenKaratekas.size(); i++){
            Ingedeeldekarateka ik= (Ingedeeldekarateka)vanenKaratekas.get(i);
            Karateka k = ik.getKarateka();
            k.setVanenDatum(vanen.getDatum());
            if (k.getBand().getId().intValue()==cat.getBand().getId().intValue()){
                if (k.getLeeftijd()<=cat.getLeeftijdtot().intValue() && 
                k.getLeeftijd()>=cat.getLeeftijdvan().intValue()){
                    categorieKaratekas.add(ik);                    
                }
            }
                    
        }                   
        request.setAttribute("listKaratekas",categorieKaratekas);
    }
    private void createSelectableKaratekasList(DynaValidatorForm form, HttpServletRequest request, Vanencompetitie vanen, Poule p) {
        Session sess = MyDatabase.currentSession();
        Categorie cat = p.getCategorie();
        List vanenKaratekas=sess.createQuery("from Ingedeeldekarateka i where i.vanencompetitie = :v and (i.poule=null or i.poule= :p)").setInteger("v", vanen.getId().intValue()).setEntity("p",p).list();
        ArrayList categorieKaratekas = new ArrayList();
        for (int i=0; i < vanenKaratekas.size(); i++){
            Ingedeeldekarateka ik= (Ingedeeldekarateka)vanenKaratekas.get(i);
            Karateka k = ik.getKarateka();
            k.setVanenDatum(vanen.getDatum());
            if (k.getBand().getId().intValue()==cat.getBand().getId().intValue()){
                if (k.getLeeftijd()<=cat.getLeeftijdtot().intValue() && 
                k.getLeeftijd()>=cat.getLeeftijdvan().intValue()){
                    categorieKaratekas.add(ik);                    
                }
            }
                    
        }                   
        request.setAttribute("listKaratekas",categorieKaratekas);
    }    



    
}
