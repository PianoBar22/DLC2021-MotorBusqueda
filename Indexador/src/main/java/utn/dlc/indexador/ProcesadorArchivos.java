/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.indexador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import utn.dlc.entidades.Documento;
import utn.dlc.negocio.PosteoNegocio;

/**
 *
 * @author CC31899077
 */
public class ProcesadorArchivos {
    public void procesar(Documento documento) {
        PosteoNegocio posteo = new PosteoNegocio();
        FileWriter flwriter = null;
        try {
            File myObj = new File(documento.getPath());
            //crea el flujo para escribir en el archivo
            flwriter = new FileWriter("C:\\UTN\\DLC\\estudiantes.txt", true);
            //crea un buffer o flujo intermedio antes de escribir directamente en el archivo
            BufferedWriter bfwriter = new BufferedWriter(flwriter);

            try (Scanner myReader = new Scanner(myObj)) {
                while (myReader.hasNextLine()) {
                    Scanner data = new Scanner(myReader.nextLine());
                    data.useDelimiter(" ");
                    while (data.hasNext()){
                        String palabra = data.next();
                        bfwriter.write(palabra + "\n");
                        //posteo.agregarPosteo(documento, palabra);
                        
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(ProcesadorArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void procesarCarpeta(final File folder) {
        File[] lista;
        if (folder.isDirectory()){
            lista = folder.listFiles();
        }else
        {
            lista = new File[]{folder};
        }
        
        for (final File fileEntry : lista) {
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
