/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.indexador;

import utn.dlc.entidades.Documento;

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
        ProcesadorArchivos procesa = new ProcesadorArchivos();
        Documento doc = new Documento();
        doc.setPath("C:\\UTN\\DLC\\POMMotorBusqueda\\Documentos\\Prueba\\0ddc809a.txt");
        
        procesa.procesar(doc);
 }
}
