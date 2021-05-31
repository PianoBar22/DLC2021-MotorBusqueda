/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.indexador;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

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

            procesa.procesarCarpeta(new File("C:\\UTN\\DLC\\POMMotorBusqueda\\Documentos\\Prueba"));
            procesa.ActualizarVocabulario();
//            DBManagerVocabulario db = DBManagerProduces.create();
//            Iterator it = db.loadList().iterator();
//            while(it.hasNext()){
//                System.out.println(it.next());
//            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static int fibo(int n)
    {
        if(n == 0) return 0;
        if(n == 1) return 1;
        System.out.println("Fibo("+n+")");
        return fibo(n-1) + fibo(n-2);
    }
    /*
    @Inject PruebaInject prueba;
    
    public static void main(String[] args) {
        Prueba prueba = new Prueba();
        
        prueba.probando();
    }*/
}
