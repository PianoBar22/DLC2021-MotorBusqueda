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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import utn.dlc.accesodatos.DBManagerPost;
import utn.dlc.accesodatos.DBManagerVocabulario;
import utn.dlc.entidades.Documento;
import utn.dlc.entidades.Post;
import utn.dlc.entidades.Vocabulario;
import utn.dlc.produces.DBManagerProduces;

/**
 *
 * @author CC31899077
 */
public class PosteoNegocio {
    private DBManagerVocabulario dbVocabulario;
    private DBManagerPost dbPost;
    private HashMap<String, Vocabulario> listVoc;

    public PosteoNegocio() {
        try {
            dbVocabulario = DBManagerProduces.createVocabulario();
            dbPost = DBManagerProduces.createPost();
            this.listVoc = dbVocabulario.loadListHash();
            
        } catch (Exception ex) {
            Logger.getLogger(PosteoNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void guardarPosteoEnBD(Iterator it, Documento doc){
        try {
            ArrayList<Post> list = new ArrayList<Post>();
            while(it.hasNext()){
                Map.Entry me = (Map.Entry)it.next();
                String palabra = (String) me.getKey();
                Integer cantPalabras = (Integer) me.getValue();
                
                
                Vocabulario vocabulario = this.listVoc.get(palabra);
                if (vocabulario == null){
                    vocabulario = new Vocabulario(palabra, 1, cantPalabras);
                }
                else
                {
                    vocabulario.setCantDocumentos(vocabulario.getCant_documentos() + 1);
                    if (vocabulario.getMax_rf() < cantPalabras){
                        vocabulario.setMaxRf(cantPalabras);
                    }
                }
                    
                Documento documento = new Documento(doc.getPath());
                
                Post post = new Post(vocabulario, documento, cantPalabras);
                list.add(post);
            }
            
            float ret = dbPost.saveDB(list);
            System.out.println(ret);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void agregarPosteo(Iterator it, Documento doc){
        guardarPosteoEnBD(it, doc);
    }
}
