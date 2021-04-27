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
import utn.dlc.negocio.PosteoNegocio;

/**
 *
 * @author CC31899077
 */
public class ProcesadorArchivos {
    public void procesar(Documento documento){
        PosteoNegocio posteo = new PosteoNegocio();
        try {
            File myObj = new File(documento.getPath());
            try (Scanner myReader = new Scanner(myObj)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    String[] splited = data.split(" ");
                    for (String palabra : splited) {
                        posteo.agregarPosteo(documento, palabra);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public void procesarCarpeta(final File folder) {
    for (final File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
            procesarCarpeta(fileEntry);
        } else {
            ProcesadorArchivos procesa = new ProcesadorArchivos();
            Documento doc = new Documento();
            doc.setPath(fileEntry.getAbsolutePath());
            System.out.println(doc.getPath());
            procesa.procesar(doc);
        }
    }
}
}
