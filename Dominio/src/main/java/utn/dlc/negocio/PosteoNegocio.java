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
import javax.persistence.Tuple;
import utn.dlc.accesodatos.DBManagerDocumento;
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
    private DBManagerDocumento dbDocumento;
    private DBManagerPost dbPost;
    private HashMap<String, Vocabulario> listVoc;

    public PosteoNegocio() {
        try {
            dbVocabulario = DBManagerProduces.createVocabulario();
            dbPost = DBManagerProduces.createPost();
            this.dbDocumento = DBManagerProduces.createDocumento();
            this.listVoc = dbVocabulario.loadListHash();
        } catch (Exception ex) {
            Logger.getLogger(PosteoNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void guardarPosteoEnBD(Iterator it, Documento doc){
        try {
            ArrayList<Post> list = new ArrayList<Post>();
            
            this.dbDocumento.saveDB(doc);
            
            while(it.hasNext()){
                Map.Entry me = (Map.Entry)it.next();
                String palabra = (String) me.getKey();
                Long cantPalabras = (Long) me.getValue();
                
                
                Vocabulario vocabulario = this.listVoc.get(palabra);
                if (vocabulario == null){
                    vocabulario = new Vocabulario(palabra, 1L, cantPalabras);
                    this.listVoc.put(palabra, vocabulario);
                }
                else
                {
                    vocabulario.setCantDocumentos(vocabulario.getCant_documentos() + 1);
                    if (vocabulario.getMax_rf() < cantPalabras){
                        vocabulario.setMaxRf(cantPalabras);
                    }
                    vocabulario.setModificado(true);
                }
                    
                Post post = new Post(vocabulario, doc, cantPalabras);
                list.add(post);
            }
            
            ArrayList<Vocabulario> listVocabulario = new ArrayList<>();
            list.stream().filter(post -> post.getVocabulario().getId() <= 0).forEach(post -> {
                listVocabulario.add(post.getVocabulario());
            });
            float retVoc = dbVocabulario.saveDB(listVocabulario);
            
            float ret = dbPost.saveDB(list);
            System.out.println(ret);
            System.out.println(retVoc);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void agregarPosteo(Iterator it, Documento doc){
        guardarPosteoEnBD(it, doc);
    }

    public void ActualizarVocabulario() throws Exception {
        ArrayList<Vocabulario> listVocabulario = new ArrayList<>();
        this.listVoc.values().stream().filter(voc -> voc.getId() > 0 && voc.isModificado()).forEach(voc -> {
            listVocabulario.add(voc);
        });
        float retVoc = dbVocabulario.saveDB(listVocabulario);
    }
}
