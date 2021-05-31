/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.accesodatos;

import java.sql.SQLException;
import utn.dlc.entidades.Documento;
import utn.dlc.entidades.Vocabulario;

/**
 *
 * @author CC31899077
 */
public class DBManagerDocumento extends DBManager{

    public long saveDB(Documento documento) throws Exception {
        if (documento==null) throw new Exception("DBAlumno Error: Alumno NO especificado");
        long newId = 0;
        
        if(documento.getId() > 0){
            newId = this.updateDB(documento);
        }
        else
        {
            newId = this.insertDB(documento);
        }
        
        documento.setId(newId);
        return newId;
    }
    
    private long insertDB(Documento documento) throws Exception{
        StringBuilder query = new StringBuilder();
        
        query.append("INSERT INTO Documentos ");
        query.append("(PathDoc) ");
        query.append("VALUES (?)");
        
        this.prepare(query.toString());
        this.setString(1, documento.getPath());
        
        if (this.executeUpdate() == 0){
            throw new SQLException("Creating user failed, no ID obtained.");
        }
        else
        {
            return this.getIdAffected();
        }
    }
    
    private long updateDB(Documento documento) throws Exception{
        StringBuilder query = new StringBuilder();
        
        query.append("UPDATE Documentos SET ");
        query.append("PathDoc = ? ");
        query.append("WHERE ID = ?");
        
        this.prepare(query.toString());
        this.setString(1, documento.getPath());
        this.setLong(2, documento.getId());
        
        if (this.executeUpdate() == 0){
            throw new SQLException("Creating user failed, no ID obtained.");
        }
        else
        {
            return documento.getId(); //this.getIdAffected();
        }
    }
}
