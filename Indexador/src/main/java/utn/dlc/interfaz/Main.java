/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.interfaz;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utn.dlc.buscador.Buscador;
import utn.dlc.entidades.Documento;
import utn.dlc.indexador.ProcesadorArchivos;

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
            /*
            ProcesadorArchivos procesa = new ProcesadorArchivos();

            procesa.procesarCarpeta(new File("C:\\UTN\\DLC\\POMMotorBusqueda\\Documentos\\Prueba"));
            procesa.ActualizarVocabulario();
            */
            
            
            Buscador buscador = new Buscador();
            ArrayList<String> palabras = new ArrayList<>();
            
            palabras.add("commends");
            palabras.add("ientleman");
            palabras.add("disperse");
            
            ArrayList<Documento> resultado = buscador.Buscar(palabras);
            System.out.println("------------Resultados de la busqueda-------------");
            for(Documento doc : resultado){
                System.out.println(doc);
            }
            System.out.println("--------------------------------------------------");
            
            //17	commends	2	15
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
