/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.indexador;

import java.io.File;
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
        
        procesa.procesarCarpeta(new File("C:\\UTN\\DLC\\POMMotorBusqueda\\Documentos\\Completo"));
 }
}
