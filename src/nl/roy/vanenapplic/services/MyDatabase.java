/*
 * HibernateUtil.java
 *
 * Created on 11 januari 2007, 14:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nl.roy.vanenapplic.services;

import java.util.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.NullEnumeration;

import org.hibernate.*;

public class MyDatabase extends HttpServlet {
    
    private static Log log = LogFactory.getLog(MyDatabase.class);
    
    private static Random rg = null;
    private static String cachePath = null;
    
    private static SessionFactory sessionFactory;
    private static String hibernateInitExceptionMessage;
    
    
    /** Initializes the servlet.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        // Nodig voor Castor icm XSLT
        System.setProperty("org.xml.sax.parser", "org.apache.xerces.parsers.SAXParser" );
        System.setProperty("org.exolab.castor.xml.serializer.factory", "org.exolab.castor.xml.XercesXMLSerializerFactory" );
        
        
        // Zet de logger
        if (log.isInfoEnabled())
            log.info("Initializing MyDatabase servlet");
        
        // Initialize cache pad
        try {
            cachePath = getServletContext().getRealPath( config.getInitParameter("cache") );
            log("cache pad: " + cachePath);
        } catch (Exception e) {
            log("Cache Path load exception", e);
            throw new ServletException("Cannot load cache path");
        }
        
        // Randomizer
        rg = new Random();
        
        // Initialize Hibernate session
        try {
            initHibernate();
        } catch(Exception e) {
            throw new ServletException(e);
        }
        
    }
    
    /** Destroys the servlet.
     */
    public void destroy() {
        
        /* Matthijs: fix voor memleak bij vaak reloaden
         * http://marc.theaimsgroup.com/?t=109578393000004&r=1&w=2
         */
        java.beans.Introspector.flushCaches();
        
        /* sluit Hibernate af */
        log.info("closing Hibernate SessionFactory");
        sessionFactory.close();
        
        LogManager.shutdown();
        LogFactory.releaseAll();
        
        super.destroy();
    }
    
    /**
     * Initializes Hibernate
     */
    public static void initHibernate() throws Exception {
        
        try {
            // Create the SessionFactory
            sessionFactory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
        } catch (Exception ex) {
            // Make sure you log the exception, as it might be swallowed
            log.error("Hibernate init error: Initial SessionFactory creation failed.", ex);
            
            hibernateInitExceptionMessage = ex.getMessage();
            if(ex.getCause() != null) {
                hibernateInitExceptionMessage += ": " + ex.getCause().getMessage();
            }
            throw ex;
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static Session currentSession() {
        return getSessionFactory().getCurrentSession();
    }
    
    public static void closeSession() {
        currentSession().close();
        return;
    }
    
    public static String localPath(String fileName) {
        if (fileName==null)
            return "";
        return cachePath + fileName;
    }    
}