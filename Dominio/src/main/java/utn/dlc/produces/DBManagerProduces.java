/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.produces;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import utn.dlc.accesodatos.DBManager;
import utn.dlc.entidades.ConfigDB;

/**
 *
 * @author CC31899077
 */
public class DBManagerProduces {
    @Inject ConfigDB config;

    @Produces
    @RequestScoped
    public DBManager create(){
        return new DBManager();
    }
}
