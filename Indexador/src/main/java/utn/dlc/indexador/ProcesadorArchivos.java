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
        try {
            File myObj = new File(documento.getPath());
            try (Scanner myReader = new Scanner(myObj)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    String[] splited = data.split(" ");
                    for (String palabra : splited) {
                        System.out.println(palabra);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
