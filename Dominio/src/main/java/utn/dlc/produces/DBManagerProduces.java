/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.produces;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import utn.dlc.accesodatos.DBManagerDocumento;
import utn.dlc.accesodatos.DBManagerPost;
import utn.dlc.accesodatos.DBManagerVocabulario;
import utn.dlc.entidades.ConfigDB;

/**
 *
 * @author CC31899077
 */
public class DBManagerProduces {
    @Inject ConfigDB config;

    @Produces
    public static DBManagerVocabulario createVocabulario(){
        try {
            DBManagerVocabulario db = new DBManagerVocabulario();
            ConfigDB config = ConfigDbProduces.create();
            db.setConfig(config);
            db.connect();
            return db;
        } catch (Exception ex) {
            Logger.getLogger(DBManagerProduces.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Produces
    public static DBManagerPost createPost(){
        try {
            DBManagerPost db = new DBManagerPost();
            ConfigDB config = ConfigDbProduces.create();
            
            db.setConfig(config);
            return db;
        } catch (Exception ex) {
            Logger.getLogger(DBManagerProduces.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static DBManagerDocumento createDocumento(){
        try {
            DBManagerDocumento db = new DBManagerDocumento();
            ConfigDB config = ConfigDbProduces.create();
            
            db.setConfig(config);
            return db;
        } catch (Exception ex) {
            Logger.getLogger(DBManagerProduces.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
