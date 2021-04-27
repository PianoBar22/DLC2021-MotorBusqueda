/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.indexador;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import utn.dlc.entidades.Documento;

/**
 *
 * @author CC31899077
 */
public class ProcesadorArchivos {
    public void procesar(Documento documento){
        String path;
        path = documento.getPath();
        
        try {
            File myObj = new File(documento.getPath());
            Scanner myReader = new Scanner(myObj);
            
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}