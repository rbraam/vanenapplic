/*
 * PrintOorkondesAction.java
 *
 * Created on 17 april 2007, 20:55
 */

package nl.roy.vanenapplic.struts;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.roy.vanenapplic.hibernate.Ingedeeldekarateka;
import nl.roy.vanenapplic.hibernate.Karateka;
import nl.roy.vanenapplic.hibernate.Poule;
import nl.roy.vanenapplic.hibernate.Vanencompetitie;
import nl.roy.vanenapplic.services.MyDatabase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Session;
/**
 *
 * @author Roy
 * @version
 */

public class PrintOorkondesAction extends VanenapplicCrudAction{
    private static final Log log = LogFactory.getLog(PrintOorkondesAction.class);
    /* forward name="success" path="" */
    private final static String SUCCESS = "success";
    private final static String FAILURE = "failure";
    
    private final static int MIN_PUNTEN=40;
    private final static int MAX_PUNTEN=90;
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
        ArrayList karatekasOorkondes = new ArrayList();
        if(request.getParameter("vanencompetitie")!=null && request.getParameter("punten")!=null){
            Integer vanenId= new Integer(request.getParameter("vanencompetitie"));            
            int puntenVan;
            int puntenTot;
            try{
                if (request.getParameter("punten").indexOf(",")>0){
                    puntenVan=Integer.parseInt(request.getParameter("punten").split(",")[0]);
                    puntenTot=Integer.parseInt(request.getParameter("punten").split(",")[1]);
                }
                else{
                    puntenVan=Integer.parseInt(request.getParameter("punten"));
                    puntenTot=Integer.parseInt(request.getParameter("punten"))+1;
                }
            }catch(NumberFormatException nfe){
                log.error("Geen integer!!!!", nfe);
                throw new Exception("De punten kunnen niet als integers worden gelezen.");
            }
            Vanencompetitie vanen=(Vanencompetitie) sess.get(Vanencompetitie.class,vanenId);
            if (vanen==null){
                return mapping.findForward(FAILURE);
            }
            //String query="from Ingedeeldekarateka i where i.vanencompetitie = :v and i.poule IS NOT NULL";            
            String query="from Ingedeeldekarateka i where i.vanencompetitie = :v";            
            List ikList=sess.createQuery(query).setEntity("v",vanen).list();
            for (int i=0; i < ikList.size(); i++){
                Ingedeeldekarateka ik= (Ingedeeldekarateka) ikList.get(i);
                Karateka k = setTotaalpunten(ik.getKarateka());
                Object o=sess.createQuery("select avg(i.punten) from Ingedeeldekarateka i where i.karateka = :k and i.vanencompetitie.type= :t and i.punten >= :m and i.betrouwbarepunten=true").setEntity("k",k).setInteger("m",MIN_PUNTEN).setString("t",vanen.getType()).uniqueResult();
                Integer avg;
                if (o!=null){
                    Float avgDouble=(Float)o;
                    avg=new Integer(""+(10*Math.round(avgDouble.doubleValue()/10)));                
                }else{
                    avg=new Integer(0);
                }
                if (avg.intValue() < MIN_PUNTEN || avg.intValue() > MAX_PUNTEN){
                    avg=new Integer(60);
                }
                int voorspellingPunten;
                if (vanen.getType()==Vanencompetitie.TYPE_KUMITE){
                    voorspellingPunten = k.getTotaalpuntenku().intValue()+avg.intValue();
                }else{
                    voorspellingPunten = k.getTotaalpuntenka().intValue()+avg.intValue();
                }
                if (voorspellingPunten >= puntenVan && voorspellingPunten < (puntenTot)){
                    while (k.getTotaalpuntenka().intValue()/700 > 1){
                        k.setTotaalpuntenka(new Integer(k.getTotaalpuntenka().intValue()-700));
                    }
                    while (k.getTotaalpuntenku().intValue()/700 > 1){
                        k.setTotaalpuntenku(new Integer(k.getTotaalpuntenku().intValue()-700));
                    }
                    karatekasOorkondes.add(k);                
                }
            }
            request.setAttribute("vanen",vanen);            
        }else{
            throw new Exception("Geen vanencompetitie en/of punten meegegeven!");
        }        
        request.setAttribute("oorkondeList",karatekasOorkondes);
        
        return mapping.findForward(SUCCESS);
        
    }

}
