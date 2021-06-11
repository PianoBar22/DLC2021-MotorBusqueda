/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.web.api;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utn.dlc.accesodatos.DBManagerVocabulario;
import utn.dlc.entidades.ConfigDB;
//import utn.dlc.accesodatos.DBManagerVocabulario;
import utn.dlc.entidades.Vocabulario;
import utn.dlc.produces.ConfigDbProduces;
import utn.dlc.produces.DBManagerProduces;
//import utn.dlc.produces.DBManagerProduces;

@Path("helloworld") 
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON) 
public class HelloWordRest {
    
    private static String DBUrl = "jdbc:mysql://127.0.0.1:3306/homedb?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static String DBUserName = "root";
    private static String DBPassword = "root";
    
    @Path("sayhello")
    @GET  
    public HashMap<String, Vocabulario> sayHello() {
        try {
            HashMap<String, Vocabulario> listVoc = new HashMap<>(); 
            DBManagerVocabulario dbVoc = this.createVocabulario();
            listVoc.put("palabra", new Vocabulario(dbVoc.toString(), 1L, 1L));
            
            /*
            if (dbVoc == null){
                listVoc.put("palabra", new Vocabulario(dbVoc.toString(), 1L, 1L));
            }
            else {
                listVoc.put("palabra", new Vocabulario("palabra", 1L, 1L));
            }
            */
            return listVoc; 
        } catch (Exception ex) {
            
            return null;
        }
    }
    
    private DBManagerVocabulario createVocabulario(){
        try {
            DBManagerVocabulario db = new DBManagerVocabulario();
            ConfigDB config = createConfig();
            db.setConfig(config);
            db.connect();
            return db;
        } catch (Exception ex) {
            Logger.getLogger(DBManagerProduces.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private ConfigDB createConfig(){
        ConfigDB config = new ConfigDB();
        
        config.setConnectionMode(ConfigDB.SINGLECONNECTIONMODE);
        config.setDriverName(ConfigDB.MYSQLDRIVERNAME);
        config.setUrl(DBUrl);
        config.setUserName(DBUserName);
        config.setPassword(DBPassword);
        
        return config;
    }
}
