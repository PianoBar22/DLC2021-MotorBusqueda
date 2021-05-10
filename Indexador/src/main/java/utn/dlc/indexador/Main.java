/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.indexador;

import java.io.File;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import utn.dlc.accesodatos.DBManagerVocabulario;
import utn.dlc.entidades.Vocabulario;
import utn.dlc.produces.DBManagerProduces;

/**
 *
 * @author CC31899077
 */
public class Main {

    /**
     *
     * @param args
     */
    
    public static void main(String[] args) {
        try {
            ProcesadorArchivos procesa = new ProcesadorArchivos();

            procesa.procesarCarpeta(new File("C:\\UTN\\DLC\\POMMotorBusqueda\\Documentos\\Completo"));
//            DBManagerVocabulario db = DBManagerProduces.create();
//            Iterator it = db.loadList().iterator();
//            while(it.hasNext()){
//                System.out.println(it.next());
//            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    @Inject PruebaInject prueba;
    
    public static void main(String[] args) {
        Prueba prueba = new Prueba();
        
        prueba.probando();
    }*/
}
