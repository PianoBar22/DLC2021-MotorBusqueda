/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.negocio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import utn.dlc.entidades.Documento;

/**
 *
 * @author CC31899077
 */
public class PosteoNegocio {
    public void agregarPosteo(Documento doc, String palabra){
        FileWriter flwriter = null;
        try {
                //crea el flujo para escribir en el archivo
                flwriter = new FileWriter("C:\\UTN\\DLC\\estudiantes.txt");
                //crea un buffer o flujo intermedio antes de escribir directamente en el archivo
                BufferedWriter bfwriter = new BufferedWriter(flwriter);
                //escribe los datos en el archivo
                bfwriter.write(palabra + "\n");
                
                //cierra el buffer intermedio
                bfwriter.close();
                System.out.println("Archivo creado satisfactoriamente..");

        } catch (IOException e) {
                e.printStackTrace();
        } finally {
            if (flwriter != null) {
                try {//cierra el flujo principal
                    flwriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
