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
    @Produces
    @SessionScoped
    public ConfigDB create(){
        return new ConfigDB();
    }
}
