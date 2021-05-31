/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.indexador;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
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
    private PosteoNegocio posteoNegocio;

    public ProcesadorArchivos() {
        posteoNegocio = new PosteoNegocio();
    }
    
    
    private void procesarDocumento(Documento documento) {
        
        try {
            File myObj = new File(documento.getPath());
            
            try (Scanner myReader = new Scanner(myObj)) {
                myReader.useDelimiter("[ .,/[\\r\\n;]\\[\\]'\\(\\)\\-\":;0-9!?@*]");
                
                HashMap<String, Long> palabras = new HashMap<>(); 
                myReader.tokens()
                        .filter(p -> p.length() >= 2)
                        .map(p -> p.toLowerCase())
                        .forEach(entry -> palabras.put(entry, palabras.getOrDefault(entry, 0L) + 1));
                
                Iterator it = palabras.entrySet().iterator();
                this.posteoNegocio.agregarPosteo(it, documento);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
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
                Instant start = Instant.now();
                Documento doc = new Documento(fileEntry.getAbsolutePath());
                System.out.println(doc.getPath());
                procesarDocumento(doc);
                Duration interval = Duration.between(start, Instant.now());
                System.out.println("Execution time in seconds: " + interval.getSeconds());
            }
        }
}

    void ActualizarVocabulario() {
        try {
            this.posteoNegocio.ActualizarVocabulario();
        } catch (Exception ex) {
            Logger.getLogger(ProcesadorArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
