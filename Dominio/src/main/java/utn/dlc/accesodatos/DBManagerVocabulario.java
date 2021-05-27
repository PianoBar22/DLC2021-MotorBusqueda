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
import java.util.HashMap;
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
                            rs.getLong(ID), 
                            rs.getString(PALABRA),
                            rs.getLong(CANT_DOC),
                            rs.getLong(MAXRF));
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
    
    public HashMap<String, Vocabulario> loadListHash() throws Exception{
        ArrayList<Vocabulario> list = (ArrayList<Vocabulario>) this.loadList(0, 0);
        HashMap<String, Vocabulario> map = new HashMap<String, Vocabulario>();
        
        for(Vocabulario voc : list){
            map.put(voc.getPalabra(), voc);
        }
        return map;
    }
    /**
     * Guarda un alumno en la ddbb.
     *
     * @param db
     * @param vocabulario
     * @return
     * @throws Exception
     */
    public long saveDB(Vocabulario vocabulario) throws Exception {
        if (vocabulario==null) throw new Exception("DBAlumno Error: Alumno NO especificado");
        
        if(vocabulario.getId() > 0){
            return this.updateDB(vocabulario);
        }
        else
        {
            return this.insertDB(vocabulario);
        }
    }
    
    private long updateDB(Vocabulario vocabulario) throws Exception{
        StringBuilder query = new StringBuilder();
        
        query.append("UPDATE Vocabulario SET ");
        query.append("CantDocumentos = ?,");
        query.append("MaxRf = ?");
        query.append("WHERE ID = ?");
        
        this.prepare(query.toString());
        this.setLong(1, vocabulario.getCant_documentos());
        this.setLong(2, vocabulario.getMax_rf());
        this.setLong(3, vocabulario.getId());
        
        if (this.executeUpdate() == 0){
            throw new SQLException("Creating user failed, no ID obtained.");
        }
        else
        {
            return this.getIdAffected();
        }
    }
    
    private long insertDB(Vocabulario vocabulario) throws Exception{
        StringBuilder query = new StringBuilder();
        
        query.append("INSERT INTO Vocabulario ");
        query.append("(Palabra, CantDocumentos, MaxRf) ");
        query.append("VALUES(?, ?, ?)");
        
        this.prepare(query.toString());
        this.setString(1, vocabulario.getPalabra());
        this.setLong(2, vocabulario.getCant_documentos());
        this.setLong(3, vocabulario.getMax_rf());
        
        if (this.executeUpdate() == 0){
            throw new SQLException("Creating user failed, no ID obtained.");
        }
        else
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
        
        ArrayList<Long> listIndex = new ArrayList<Long>();
        
        for(Vocabulario vocabulario: list)
        {
            this.setString(1, vocabulario.getPalabra());
            this.setFloat(2, vocabulario.getCant_documentos());
            this.setFloat(3, vocabulario.getMax_rf());
            this.addBatch();
            
            if (cant % batchSize == 0){
                int[] arr = this.executeBatch();
                for(long i : arr){
                    listIndex.add(i);
                }
            }
            cant++;
        }
        int[] arr = this.executeBatch();
        for(long i : arr){
            listIndex.add(i);
        }
        
        this.commit();
        for (int i = 0; i < list.size(); i++) {
            Vocabulario voc = list.get(i);
            voc.setId(listIndex.get(i));
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
