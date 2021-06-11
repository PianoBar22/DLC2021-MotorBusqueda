/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.produces;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import utn.dlc.entidades.ConfigDB;

/**
 *
 * @author CC31899077
 */
public class ConfigDbProduces {
    // DATABASE
    private static String DBUrl = "jdbc:mysql://localhost:3306/homedb?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static String DBUserName = "root";
    private static String DBPassword = "root";

    private static String DBResourceName = "jdbc/_DLCMotorBusqueda";
    
    @Produces
    public static ConfigDB create(){
        ConfigDB config = new ConfigDB();
        
        config.setConnectionMode(ConfigDB.SINGLECONNECTIONMODE);
        config.setDriverName(ConfigDB.MYSQLDRIVERNAME);
        config.setUrl(DBUrl);
        config.setUserName(DBUserName);
        config.setPassword(DBPassword);
        
        config.setResourceName(DBResourceName);
        
        return config;
    }
}
