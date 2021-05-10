/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.indexador;

import javax.inject.Inject;

/**
 *
 * @author CC31899077
 */
public class Prueba {
    @Inject PruebaInject prueba;
    
    public void probando()
    {
        System.out.println(prueba);
    }
}
