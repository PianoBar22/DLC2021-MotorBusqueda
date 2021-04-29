/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.entidades;

/**
 *
 * @author CC31899077
 */
public class ConfigDB {
    public static final int SINGLECONNECTIONMODE = 1;
    public static final int POOLCONNECTIONMODE = 2;

    public static final String POSTGRESDRIVERNAME = "org.postgresql.Driver";
    public static final String MYSQLDRIVERNAME = "com.mysql.cj.jdbc.Driver";
    
    private int connectionMode = SINGLECONNECTIONMODE;
    private String driverName = null;               // "org.postgresql.Driver";
    private String url = null;                      // "jdbc:postgresql://<host>:<port>/<db>";
    private String resourceName = null;             // "[java:comp/env/]jdbc/<dataSourceName>";
    private String usr = null;
    private String pwd = null;
    
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
        if (this.connectionMode != POOLCONNECTIONMODE) {
            this.connectionMode = SINGLECONNECTIONMODE;
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
        return this.pwd;
    }

    /**
     *
     * @param pwd
     */
    public void setPassword(String pwd) {
        this.pwd = pwd;
    }
}
