/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.accesodatos;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import utn.dlc.entidades.ConfigDB;
import utn.dlc.produces.ConfigDbProduces;

/**
 *
 * @author CC31899077
 */

public class DBManager {
    private static final long serialVersionUID = -5234473242999323611L;
    
    private int connectionMode = ConfigDB.SINGLECONNECTIONMODE;
    private String driverName = null;               // "org.postgresql.Driver";
    private String url = null;                      // "jdbc:postgresql://<host>:<port>/<db>";
    private String resourceName = null;             // "[java:comp/env/]jdbc/<dataSourceName>";
    private String usr = null;
    private String pwd = null;
    Context ctx = null;
    DataSource ds = null;
    Connection cn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    CallableStatement cstmt = null;
    private ConfigDB config;

    /**
     * Creates a new instance of DBManager
     */
    public DBManager() {
        super();
    }

    /**
     *
     * @return
     */
    public int getConnectionMode() {
        return this.connectionMode;
    }

    /**
     *
     * @param connectionMode
     */
    public void setConnectionMode(int connectionMode) {
        this.connectionMode = connectionMode;
        if (this.connectionMode != ConfigDB.POOLCONNECTIONMODE) {
            this.connectionMode = ConfigDB.SINGLECONNECTIONMODE;
        }
    }

    /**
     *
     * @return
     */
    public String getDriverName() {
        return this.driverName;                     // "org.postgresql.Driver";
    }

    /**
     *
     * @param driverName
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;               // "org.postgresql.Driver";
    }

    /**
     *
     * @return
     */
    public String getUrl() {
        return this.url;
    }

    /**
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     */
    public String getResourceName() {
        return this.resourceName;                   // "[java:comp/env/]jdbc/<dataSourceName>";
    }

    /**
     *
     * @param resourceName
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;           // "[java:comp/env/]jdbc/<dataSourceName>";
    }

    /**
     *
     * @return
     */
    public String getUserName() {
        return usr;
    }

    /**
     *
     * @param usr
     */
    public void setUserName(String usr) {
        this.usr = usr;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return null;
    }

    /**
     *
     * @param pwd
     */
    public void setPassword(String pwd) {
        this.pwd = pwd;
    }

    private void setContext() throws Exception {
        if (this.ds == null) {
            if (this.resourceName == null) {
                throw new Exception("DBManager ERROR: ResourceName (JNDI) NO especificado");
            }
            this.ctx = new InitialContext();
            this.ds = (DataSource) this.ctx.lookup(resourceName);
        }
    }

    /**
     *
     * @throws Exception
     */
    public void connect() throws Exception {
        if (this.cn == null) {
            if (this.connectionMode == ConfigDB.SINGLECONNECTIONMODE) {
                Class.forName(driverName);
                this.cn = DriverManager.getConnection(this.url, this.usr, this.pwd);
            } else {
                this.setContext();
                this.cn = this.ds.getConnection();
            }
        }
    }

    public void disconnect() {
        if (this.stmt != null) try {
            this.stmt.close();
        } catch (Exception e) {}
        this.stmt = null;

        if (this.pstmt != null) try {
            this.pstmt.close();
        } catch (Exception e) {}
        this.pstmt = null;

        if (this.cstmt != null) try {
            this.cstmt.close();
        } catch (Exception e) {}
        this.cstmt = null;

        if (this.cn != null) try {
            this.cn.close();
        } catch (Exception e) {}
        this.cn = null;
        this.ds = null;

        if (this.ctx != null) try {
            this.ctx.close();
        } catch (Exception e) {}
        this.ctx = null;
    }

    public void close() {
        this.disconnect();
    }

    /**
     *
     * @throws Exception
     */
    public void reconnect() throws Exception {
        if (this.cn != null) {
            this.disconnect();
        }
        this.connect();
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public Connection getNewConnection() throws Exception {
        Connection ncn = null;
        if (this.connectionMode == ConfigDB.SINGLECONNECTIONMODE) {
            Class.forName(driverName);
            ncn = DriverManager.getConnection(this.url, this.usr, this.pwd);
        } else {
            this.setContext();
            ncn = this.ds.getConnection();
        }
        return ncn;
    }

    /**
     * Comienza una transacción.
     *
     * @throws Exception
     */
    public void beginTransaction() throws Exception {
        this.cn.setAutoCommit(false);
    }

    public void commit() throws Exception {
        this.cn.commit();
        this.cn.setAutoCommit(true);
    }

    public void rollback() throws Exception {
        this.cn.rollback();
        this.cn.setAutoCommit(true);
    }

    /**
     * Ejecuta una query SQL utilizando un Statement.
     *
     * @param query
     * @return
     * @throws Exception
     */
    public ResultSet executeQuery(String query) throws Exception {
        this.stmt = this.cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return this.stmt.executeQuery(query);
    }

    /**
     * Ejecuta una instrucción SQL utilizando un Statement.
     *
     * @param statement
     * @return
     * @throws Exception
     */
    public int executeUpdate(String statement) throws Exception {
        this.stmt = this.cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return this.stmt.executeUpdate(statement);
    }

    /**
     * Precompila una instrucción SQL utilizando un PreparedStatement.
     *
     * @param statement
     * @throws Exception
     */
    public void prepare(String statement) throws Exception {
        int columnIndexes[] = new int[]{Statement.RETURN_GENERATED_KEYS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY};
        this.pstmt = this.cn.prepareStatement(statement, columnIndexes);
    }

    public void addBatch() throws SQLException{
        this.pstmt.addBatch();
    }
    
    public int[] executeBatch() throws SQLException{
        return this.pstmt.executeBatch();
    }

    public ArrayList<Long> executeBatchKeys() throws SQLException, Exception{
        this.pstmt.executeBatch();
        return this.getGeneratedKeys();
    }

    /**
     * Ejecuta una instrucción SQL, previamente preparada/precomplidada,
     * utilizando un PreparedStatement.
     *
     * @return
     * @throws Exception
     */
    public boolean execute() throws Exception {
        if (this.pstmt == null) {
            throw new Exception("DBManager Error: se intenta ejecutar una instrucción NO preparada/precompilada.");
        }
        return this.pstmt.execute();
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public ResultSet executeQuery() throws Exception {
        if (this.pstmt == null) {
            throw new Exception("DBManager Error: se intenta ejecutar una query NO preparada/precompilada.");
        }
        return this.pstmt.executeQuery();
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public int executeUpdate() throws Exception {
        if (this.pstmt == null) {
            throw new Exception("DBManager Error: se intenta ejecutar una query NO preparada/precompilada.");
        }
        return this.pstmt.executeUpdate();
    }
    
    public long getIdAffected() throws Exception {
        if (this.pstmt == null) {
            throw new Exception("DBManager Error: se intenta ejecutar una query NO preparada/precompilada.");
        }
        
        try (ResultSet generatedKeys = this.pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }
    
    private ArrayList<Long> getGeneratedKeys() throws Exception {
        if (this.pstmt == null) {
            throw new Exception("DBManager Error: se intenta ejecutar una query NO preparada/precompilada.");
        }

        ArrayList<Long> ret = new ArrayList<>();
        ResultSet rs = this.pstmt.getGeneratedKeys();
        while (rs.next()){
            ret.add(rs.getLong(1));
        }
        
        return ret;
    }
    
    /**
     * Setea un parámentro de tipo Integer de una instrucción SQL,
     * previamente preparada/precompilada, utilizando un PreparedStatement.
     *
     * @param parameterIndex
     * @param value
     * @throws Exception
     */
    public void setInt(int parameterIndex, int value) throws Exception {
        if (this.pstmt == null) {
            throw new Exception("DBManager Error: se intenta parametrizar una instrucción/query NO preparada/precompilada.");
        }
        this.pstmt.setInt(parameterIndex, value);
    }

    /**
     * Setea un parámentro de tipo Integer de una instrucción SQL,
     * previamente preparada/precompilada, utilizando un PreparedStatement.
     *
     * @param parameterIndex
     * @param value
     * @throws Exception
     */
    public void setFloat(int parameterIndex, float value) throws Exception {
        if (this.pstmt == null) {
            throw new Exception("DBManager Error: se intenta parametrizar una instrucción/query NO preparada/precompilada.");
        }
        this.pstmt.setFloat(parameterIndex, value);
    }
    /**
     * Setea un parámentro de tipo Integer de una instrucción SQL,
     * previamente preparada/precompilada, utilizando un PreparedStatement.
     *
     * @param parameterIndex
     * @param value
     * @throws Exception
     */
    public void setInt(int parameterIndex, Integer value) throws Exception {
        if (this.pstmt == null) {
            throw new Exception("DBManager Error: se intenta parametrizar una instrucción/query NO preparada/precompilada.");
        }
        if (value == null) {
            this.pstmt.setNull(parameterIndex, Types.INTEGER);
        } else {
            this.pstmt.setInt(parameterIndex, value);
        }
    }

    public void setLong(int parameterIndex, Long value) throws Exception{
        if (this.pstmt == null) {
            throw new Exception("DBManager Error: se intenta parametrizar una instrucción/query NO preparada/precompilada.");
        }
        if (value == null) {
            this.pstmt.setNull(parameterIndex, Types.BIGINT);
        } else {
            this.pstmt.setLong(parameterIndex, value);
        }
    }
    
    /**
     * Setea un parámentro de tipo ShortInt de una instrucción SQL,
     * previamente preparada/precompilada, utilizando un PreparedStatement.
     *
     * @param parameterIndex
     * @param value
     * @throws Exception
     */
    public void setShort(int parameterIndex, short value) throws Exception {
        if (this.pstmt == null) {
            throw new Exception("DBManager Error: se intenta parametrizar una instrucción/query NO preparada/precompilada.");
        }
        this.pstmt.setShort(parameterIndex, value);
    }

    /**
     * Setea un parámentro de tipo ShortInt de una instrucción SQL,
     * previamente preparada/precompilada, utilizando un PreparedStatement.
     *
     * @param parameterIndex
     * @param value
     * @throws Exception
     */
    public void setShort(int parameterIndex, Integer value) throws Exception {
        if (this.pstmt == null) {
            throw new Exception("DBManager Error: se intenta parametrizar una instrucción/query NO preparada/precompilada.");
        }
        if (value == null) {
            this.pstmt.setNull(parameterIndex, Types.SMALLINT);
        } else {
            this.pstmt.setShort(parameterIndex, value.shortValue());
        }
    }

    /**
     * Idem <code>setShort</code>.
     *
     * @param parameterIndex
     * @param value
     * @throws Exception
     */
    public void setSmallInt(int parameterIndex, short value) throws Exception {
        this.setShort(parameterIndex, value);
    }

    /**
     * Idem <code>setShort</code>.
     *
     * @param parameterIndex
     * @param value
     * @throws Exception
     */
    public void setSmallInt(int parameterIndex, Integer value) throws Exception {
        this.setShort(parameterIndex, value);
    }

    /**
     * Setea un parámentro de tipo String de una instrucción SQL,
     * previamente preparada/precompilada, utilizando un PreparedStatement.
     *
     * @param parameterIndex
     * @param value
     * @throws Exception
     */
    public void setString(int parameterIndex, String value) throws Exception {
        if (this.pstmt == null) {
            throw new Exception("DBManager Error: se intenta parametrizar una instrucción/query NO preparada/precompilada.");
        }
        if (value == null) {
            this.pstmt.setNull(parameterIndex, Types.VARCHAR);
        } else {
            this.pstmt.setString(parameterIndex, value);
        }
    }

    /**
     * Setea un parámentro de tipo String de una instrucción SQL,
     * previamente preparada/precompilada, utilizando un PreparedStatement.
     *
     * @param parameterIndex
     * @param value
     * @throws Exception
     */
    public void setString(int parameterIndex, char value) throws Exception {
        if (this.pstmt == null) {
            throw new Exception("DBManager Error: se intenta parametrizar una instrucción/query NO preparada/precompilada.");
        }
        if (value == '\0') {
            this.pstmt.setNull(parameterIndex, Types.VARCHAR);
        } else {
            this.pstmt.setString(parameterIndex, String.valueOf(value));
        }
    }

    /**
     * Setea un parámentro de tipo Date de una instrucción SQL,
     * previamente preparada/precompilada, utilizando un PreparedStatement.
     *
     * @param parameterIndex
     * @param value
     * @throws Exception
     */
    public void setDate(int parameterIndex, Date value) throws Exception {
        if (this.pstmt == null) {
            throw new Exception("DBManager Error: se intenta parametrizar una instrucción/query NO preparada/precompilada.");
        }
        if (value == null) {
            this.pstmt.setNull(parameterIndex, Types.DATE);
        } else {
            this.pstmt.setDate(parameterIndex, (java.sql.Date) value);
        }
    }

    public ConfigDB getConfig() {
        return config;
    }

    public void setConfig(ConfigDB config) {
        this.config = config;
        
        this.setConnectionMode(config.getConnectionMode());
        this.setDriverName(config.getDriverName());
        this.setUrl(config.getUrl());
        this.setUserName(config.getUserName());
        this.setPassword(config.getPassword());
        
        this.setResourceName(config.getResourceName());
    }
}
