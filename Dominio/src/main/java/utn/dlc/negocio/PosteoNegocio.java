/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.negocio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import utn.dlc.accesodatos.DBManagerVocabulario;
import utn.dlc.entidades.Documento;
import utn.dlc.entidades.Vocabulario;
import utn.dlc.produces.DBManagerProduces;

/**
 *
 * @author CC31899077
 */
public class PosteoNegocio {
    private DBManagerVocabulario dbVocabulario;

    public PosteoNegocio() {
        dbVocabulario = DBManagerProduces.create();
    }
    
    private void guardarPosteoEnArchivo(Iterator it, Documento doc){
        try {
            String ruta = "C:\\UTN\\DLC\\Posteos\\";
            File fileOrigen = new File(doc.getPath());
            File fileDestino = new File(ruta + fileOrigen.getName());
            // Si el archivo no existe es creado
            if (!fileDestino.exists()) {
                fileDestino.createNewFile();
            }
            FileWriter fw = new FileWriter(fileDestino, fileDestino.exists());
            BufferedWriter bw = new BufferedWriter(fw);
            ArrayList<Vocabulario> list = new ArrayList<Vocabulario>();
            while(it.hasNext()){
                Map.Entry me = (Map.Entry)it.next();
                String palabra = (String) me.getKey();
                Integer cantPalabras = (Integer) me.getValue();
                
                bw.write(palabra + " " + cantPalabras);
                Vocabulario vocabulario = new Vocabulario(palabra, 1, cantPalabras);
                list.add(vocabulario);
                //float newId = dbVocabulario.saveDB(vocabulario);
                bw.newLine();
            }
            bw.close();
            float ret = dbVocabulario.saveDB(list);
            System.out.println(ret);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void agregarPosteo(Iterator it, Documento doc){
        guardarPosteoEnArchivo(it, doc);
    }
}
