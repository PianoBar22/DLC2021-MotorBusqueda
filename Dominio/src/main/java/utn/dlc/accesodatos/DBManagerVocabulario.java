/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.accesodatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utn.dlc.entidades.Vocabulario;

/**
 *
 * @author CC31899077
 */
public class DBManagerVocabulario extends DBManager{
    private static final String ID = "ID";
    private static final String PALABRA = "Palabra";
    private static final String CANT_DOC = "CantDocumentos";
    private static final String MAXRF = "MaxRf";
    
    private static Vocabulario buildVocabulario(ResultSet rs) throws SQLException {
        Vocabulario vocabulario = null;
        if (rs.next()) {
            vocabulario = new Vocabulario(
                            //rs.getFloat(ID), 
                            rs.getString(PALABRA),
                            rs.getInt(CANT_DOC),
                            rs.getInt(MAXRF));
        }
        return vocabulario;
    }

    public DBManagerVocabulario() {
        super();
    }
    
    
    /**
     * Genera una lista de alumnos.
     *
     * @param db
     * @param limit
     * @param offset
     * @return
     * @throws Exception
     */
    public List loadList(int limit, int offset) throws Exception {
        if (limit<0) throw new Exception("DBVocabulario Error: limit incorrecto");
        if (offset<0) throw new Exception("DBVocabulario Error: offset incorrecto");
        List vocabularios = new ArrayList();
        String query = "SELECT * " +
                "FROM Vocabulario v " +
              //  "WHERE " +
                "ORDER BY v.CantDocumentos desc ";
        if (limit>0) query += "LIMIT " + limit + "OFFSET " + offset;
        ResultSet rs = this.executeQuery(query);

        Vocabulario vocabulario = null;
        while ((vocabulario = buildVocabulario(rs)) != null) {
            vocabularios.add(vocabulario);
        }
        rs.close();

        return vocabularios;
    }

    /**
     * Genera una lista de alumnos.
     *
     * @param db
     * @return
     * @throws Exception
     */
    public List loadList() throws Exception {
        return loadList(0, 0);
    }
    
    /**
     * Guarda un alumno en la ddbb.
     *
     * @param db
     * @param vocabulario
     * @return
     * @throws Exception
     */
    public float saveDB(Vocabulario vocabulario) throws Exception {
        if (vocabulario==null) throw new Exception("DBAlumno Error: Alumno NO especificado");
        String query = "INSERT INTO Vocabulario(Palabra, CantDocumentos, MaxRf) values(?, ?, ?)";
        this.prepare(query);
        this.setString(1, vocabulario.getPalabra());
        this.setFloat(2, vocabulario.getCant_documentos());
        this.setFloat(3, vocabulario.getMax_rf());
        
        if (this.executeUpdate() == 0){
            throw new SQLException("Creating user failed, no ID obtained.");
        }else
        {
            return this.getIdAffected();
        }
    }
    
    public float saveDB(ArrayList<Vocabulario> list) throws Exception {
        if (list==null) throw new Exception("DBAlumno Error: Alumno NO especificado");
        String query = "INSERT INTO VocabularioSinID(Palabra, CantDocumentos, MaxRf) values(?, ?, ?)";
        this.prepare(query);
        this.beginTransaction();
        
        int batchSize = 1000;
        int cant = 1;
        
        ArrayList<Integer> listIndex = new ArrayList<Integer>();
        
        for(Vocabulario vocabulario: list)
        {
            this.setString(1, vocabulario.getPalabra());
            this.setFloat(2, vocabulario.getCant_documentos());
            this.setFloat(3, vocabulario.getMax_rf());
            this.addBatch();
            
            if (cant % batchSize == 0){
                int[] arr = this.executeBatch();
                for(int i : arr){
                    listIndex.add(i);
                }
            }
            cant++;
        }
        int[] arr = this.executeBatch();
        for(int i : arr){
            listIndex.add(i);
        }
        
        this.commit();
        for (int i = 0; i < list.size(); i++) {
            Vocabulario voc = list.get(i);
            voc.setId(Float.parseFloat(listIndex.get(i).toString()));
        }
        return listIndex.size();
        /*
        if (this.executeUpdate() == 0){
            throw new SQLException("Creating user failed, no ID obtained.");
        }else
        {
            return this.getIdAffected();
        }*/
    }
}
