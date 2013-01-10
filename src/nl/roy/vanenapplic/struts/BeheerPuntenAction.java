/*
 * BeheerPuntenAction.java
 *
 * Created on 12 februari 2008, 20:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nl.roy.vanenapplic.struts;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.b3p.commons.services.FormUtils;
import nl.roy.vanenapplic.hibernate.Ingedeeldekarateka;
import nl.roy.vanenapplic.hibernate.Karateka;
import nl.roy.vanenapplic.hibernate.Poule;
import nl.roy.vanenapplic.hibernate.Vanencompetitie;
import nl.roy.vanenapplic.services.MyDatabase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 *
 * @author Roy
 */
public class BeheerPuntenAction extends VanenapplicCrudAction{
    private static final Log log = LogFactory.getLog(BeheerPouleAction.class);
    private static final int MAXAANTALDEELNEMERS=6;
    private static final int MINAANTALDEELNEMERS=3;
    public ActionForward unspecified(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        Session sess = MyDatabase.currentSession();
        if (form.get("vanencompetitie")!=null){
            Vanencompetitie v=null;
            Integer id = (Integer)form.get("vanencompetitie");
            v = (Vanencompetitie)sess.get(Vanencompetitie.class,id);            
            form.set("vanencompetitie",v.getId());
            request.setAttribute("vanen",v);
        }
        /*else{
            Object o=sess.createQuery("from Vanencompetitie v order by v.datum DESC limit 1").uniqueResult();
            if (o==null){
                return mapping.findForward("success"); 
            }
            v=(Vanencompetitie)o;
        }*/
        createLists(form,request);
        return mapping.findForward("success");
        //return super.unspecified(mapping,form,request,response);
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
        if (form.getStrings("punten")!=null){
            Session sess = MyDatabase.currentSession();
            String[] punten = form.getStrings("punten");
            for (int i=0; i < punten.length; i++){
                if (punten[i].contains(",")){
                    int id=Integer.parseInt(punten[i].split(",")[0]);
                    int p=Integer.parseInt(punten[i].split(",")[1]);
                    Ingedeeldekarateka ik= (Ingedeeldekarateka)sess.get(Ingedeeldekarateka.class,new Integer(id));
                    int ikPunten=0;
                    int oudeTotaal=0;
                    boolean betrouwbarePunten=true;
                    if (ik.getPunten()!=null){
                        ikPunten=ik.getPunten().intValue();
                    }
                    Karateka k=  setTotaalpunten(ik.getKarateka());
                    if(ik.getVanencompetitie().getType().equalsIgnoreCase(Vanencompetitie.TYPE_KATA))
                       oudeTotaal=k.getTotaalpuntenka().intValue();
                    else{
                       oudeTotaal=k.getTotaalpuntenku().intValue();
                    }
                    oudeTotaal-=ikPunten;
                    while(oudeTotaal >=700){
                        oudeTotaal-=700;
                    }
                    if (oudeTotaal+p > 700){
                        betrouwbarePunten=false;
                        p= 700-oudeTotaal;
                    }
                    int result=sess.createQuery("update Ingedeeldekarateka SET punten=:p, betrouwbarepunten=:b where id=:i").setInteger("p",p).setInteger("i",id).setBoolean("b",betrouwbarePunten).executeUpdate();
                    
                    result+=result;
                }
            }
            sess.flush();
            //sess.getTransaction().commit();
        }        
        //createLists(form,request);
        form.initialize(mapping);
        return super.save(mapping,form,request,response);
        //return mapping.findForward(SUCCESS);
    } 
    
    /**
     * Overwrites the createLists methode that is called when this action is called
     * @param form The form the action is linking to
     * @param request The request of this action
     * @throws java.lang.Exception When something gone wrong
     */
    public void createLists(DynaValidatorForm form, HttpServletRequest request) throws Exception {
        Session sess = MyDatabase.currentSession();
        if (form.get("vanencompetitie")!=null){
            Integer vanenid=(Integer)form.get("vanencompetitie");            
            List l=sess.createQuery("from Ingedeeldekarateka i where i.vanencompetitie= :v order by i.poule").setInteger("v",vanenid.intValue()).list();
            request.setAttribute("ingedeeldekaratekaList",l);
            //request.setAttribute("pouleList",sess.createQuery("from Poule p where p.Categorie."))
        }
        request.setAttribute("vanenList",sess.createQuery("from Vanencompetitie").list());        
    }
    

   
      



    
}

    

