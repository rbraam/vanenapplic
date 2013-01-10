/*
 * PrintPoulesAction.java
 *
 * Created on 17 april 2007, 20:55
 */

package nl.roy.vanenapplic.struts;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.roy.vanenapplic.hibernate.Ingedeeldekarateka;
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

public class PrintPoulesAction extends VanenapplicCrudAction{
    
    /* forward name="success" path="" */
    private final static String SUCCESS = "success";
    private final static String FAILURE = "failure";
    
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    public ActionForward unspecified(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Session sess = MyDatabase.currentSession();
        ArrayList poules = new ArrayList();
        if(request.getParameter("vanencompetitie")!=null){
            Integer vanenId= new Integer(request.getParameter("vanencompetitie"));
            Vanencompetitie vanen=(Vanencompetitie) sess.get(Vanencompetitie.class,vanenId);
            if (vanen==null){
                return mapping.findForward(FAILURE);
            }
            String query="select DISTINCT i.poule from Ingedeeldekarateka i where i.vanencompetitie = :v";
            if (request.getParameter("band")!=null){
                query+=" and i.poule.categorie.band="+request.getParameter("band");
            }
            List poulesdb=sess.createQuery(query).setEntity("v",vanen).list();
            for (int i=0; i < poulesdb.size(); i++){
                Poule p= (Poule) poulesdb.get(i);
                PouleBean pb = createPouleBean(p,vanen);
                poules.add(pb);
            }
        }else if(request.getParameter("poules")!=null){
            String[] tokens = request.getParameter("poules").split(",");
            for (int i=0; i < tokens.length; i++){
                Poule p= (Poule) sess.get(Poule.class,new Integer(tokens[i]));
                PouleBean pb = createPouleBean(p,null);
                poules.add(pb);
            }
            
        }        
        request.setAttribute("poules",poules);
        return mapping.findForward(SUCCESS);
        
    }
    private PouleBean createPouleBean(Poule p,Vanencompetitie v){
        Session sess = MyDatabase.currentSession();
        PouleBean pb= new PouleBean();
        pb.setPoule(p);
        if (v!=null)
            pb.setVanencompetitie(v);
        List karatekas= sess.createQuery("from Ingedeeldekarateka i where i.poule = :p").setEntity("p",p).list();
        for (int k=0; k < karatekas.size(); k++){
            Ingedeeldekarateka karateka=(Ingedeeldekarateka)karatekas.get(k);
            pb.addKarateka(karateka.getKarateka());
            if (v==null && k==0){
                pb.setVanencompetitie(karateka.getVanencompetitie());
            }
        }
        return pb;
    }
}
