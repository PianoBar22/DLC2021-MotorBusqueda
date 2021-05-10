/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.indexador;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 *
 * @author CC31899077
 */
public class PruebaProducer {
    @Produces
    @ApplicationScoped
    public PruebaInject create(){
        PruebaInject pr = new PruebaInject();
        pr.setCampo1(15);
        pr.setCampo2(100);
        
        return pr;
    }
}
