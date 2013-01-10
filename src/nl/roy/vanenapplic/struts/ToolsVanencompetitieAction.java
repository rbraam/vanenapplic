/*
 * ToolsVanencompetitieAction.java
 *
 * Created on 4 september 2007, 11:02
 */

package nl.roy.vanenapplic.struts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.b3p.commons.struts.ExtendedMethodProperties;
import nl.roy.vanenapplic.hibernate.Ingedeeldekarateka;
import nl.roy.vanenapplic.hibernate.Karateka;
import nl.roy.vanenapplic.hibernate.Poule;
import nl.roy.vanenapplic.hibernate.Vanencompetitie;
import nl.roy.vanenapplic.services.MyDatabase;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Session;
/**
 *
 * @author Roy
 * @version
 */

public class ToolsVanencompetitieAction extends VanenapplicCrudAction {
    
    /* forward name="success" path="" */
    private final static String SUCCESS = "success";
    private final static String LIST = "list";
    private final static String NIK = "nietIngedeeldeKaratekas";
    private final static String DK= "deelnemendeKaratekas";
    private final static String IP="ingedeeldePoules";
    protected Map getActionMethodPropertiesMap() {
        Map map = super.getActionMethodPropertiesMap();        
        ExtendedMethodProperties crudProp = new ExtendedMethodProperties(NIK);
        crudProp.setDefaultForwardName(SUCCESS);
        crudProp.setDefaultMessageKey("beheer.toolsVanencompetitie");
        crudProp.setAlternateForwardName(FAILURE);
        crudProp.setAlternateMessageKey("beheer.toolsVanencompetitie");
        map.put(NIK, crudProp);
        
        crudProp = new ExtendedMethodProperties(DK);
        crudProp.setDefaultForwardName(SUCCESS);
        crudProp.setDefaultMessageKey("beheer.toolsVanencompetitie");
        crudProp.setAlternateForwardName(FAILURE);
        crudProp.setAlternateMessageKey("beheer.toolsVanencompetitie");
        map.put(DK, crudProp);
        
        crudProp = new ExtendedMethodProperties(IP);
        crudProp.setDefaultForwardName(SUCCESS);
        crudProp.setDefaultMessageKey("beheer.toolsVanencompetitie");
        crudProp.setAlternateForwardName(FAILURE);
        crudProp.setAlternateMessageKey("beheer.toolsVanencompetitie");
        map.put(IP, crudProp);
       
        
        return map;
    }
    
    public ActionForward unspecified(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.unspecified(mapping,form,request,response);
    }
     public void createLists(DynaValidatorForm form, HttpServletRequest request) throws Exception {
        Session sess = MyDatabase.currentSession();
        List vanencompetities=sess.createQuery("from Vanencompetitie v order by v.datum DESC").list();
        request.setAttribute("vanencompetities",vanencompetities);
     }
    public ActionForward nietIngedeeldeKaratekas(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (form.get("vanencompetitie")!=null){
            Session sess = MyDatabase.currentSession();
            Vanencompetitie vanen=(Vanencompetitie) sess.get(Vanencompetitie.class,(Integer)form.get("vanencompetitie"));
            List nietIngedeelde=sess.createQuery("select distinct(i.karateka) from Ingedeeldekarateka i where i.poule = null AND i.vanencompetitie = :v").setEntity("v",vanen).list();
            ArrayList nietIngedeeldeKaratekas=new ArrayList();
            for (int i=0; i < nietIngedeelde.size(); i++){
                Karateka k = (Karateka)nietIngedeelde.get(i);
                k.setVanenDatum(vanen.getDatum());
                nietIngedeeldeKaratekas.add(k);
            }
            request.setAttribute("list",nietIngedeeldeKaratekas);
            request.setAttribute("vanencompetitie", vanen.toString());
        }
        return mapping.findForward(LIST);
    }
    public ActionForward deelnemendeKaratekas(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (form.get("vanencompetitie")!=null){
            Session sess = MyDatabase.currentSession();
            Vanencompetitie vanen=(Vanencompetitie) sess.get(Vanencompetitie.class,(Integer)form.get("vanencompetitie"));
            String sorteerOp=null;
            if (request.getParameter("sorteerOp")!=null){
                sorteerOp=(String)request.getParameter("sorteerOp");
            }
            String queryString = "select distinct (i.karateka) from Ingedeeldekarateka i where i.vanencompetitie = :v";
           
            if (sorteerOp!=null && sorteerOp.length()>0){
                queryString+= " order by i.karateka."+sorteerOp;
            }
            List nietIngedeelde=sess.createQuery(queryString).setEntity("v",vanen).list();
            ArrayList nietIngedeeldeKaratekas=new ArrayList();
            for (int i=0; i < nietIngedeelde.size(); i++){
                //Ingedeeldekarateka ik= (Ingedeeldekarateka) nietIngedeelde.get(i);
                Karateka k = (Karateka)nietIngedeelde.get(i);
                k.setVanenDatum(vanen.getDatum());
                nietIngedeeldeKaratekas.add(k.toString());
            }
            request.setAttribute("list",nietIngedeeldeKaratekas);
            request.setAttribute("vanencompetitie", vanen.toString());
        }
        return mapping.findForward(LIST);
    }
    public ActionForward ingedeeldePoules(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (form.get("vanencompetitie")!=null){
            Session sess = MyDatabase.currentSession();
            Vanencompetitie vanen=(Vanencompetitie) sess.get(Vanencompetitie.class,(Integer)form.get("vanencompetitie"));
            
            String queryString="select DISTINCT i.poule from Ingedeeldekarateka i where i.vanencompetitie = :v";
            List poules=sess.createQuery(queryString).setEntity("v",vanen).list();
            ArrayList list=new ArrayList();
            for (int i=0; i < poules.size(); i++){
                Poule p = (Poule) poules.get(i);
                list.add("<b>"+p.toString()+"</b>");
                queryString = "select i.karateka from Ingedeeldekarateka i where i.vanencompetitie = :v and i.poule = :p";
                List karatekas=sess.createQuery(queryString).setEntity("v",vanen).setEntity("p",p).list();
                for (int b=0; b < karatekas.size(); b++){
                    //Ingedeeldekarateka ik= (Ingedeeldekarateka) nietIngedeelde.get(i);
                    Karateka k = (Karateka)karatekas.get(b);
                    k.setVanenDatum(vanen.getDatum());
                    list.add(k.toString());
                }
                list.add("");
            }
            request.setAttribute("list",list);
            request.setAttribute("vanencompetitie", vanen.toString());
        }
        return mapping.findForward(LIST);
}   }
