/*
 * Copyright 2015 Psycho_Coder <Animesh Shaw>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.letshackit.chromeforensics.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Psycho_Coder
 */
abstract class BaseDbModel {

    public String driver = null;
    public String connUrl = null;
    public int connTimeout = 60;
    public Connection conn = null;
    public Statement stmt = null;

    /**
     * Default constructor
     */
    public BaseDbModel() {
    }

    /**
     * @param driver
     * @param connUrl
     */
    public BaseDbModel(String driver, String connUrl) {
        initialize(driver, connUrl);
    }

    /**
     * @param driver
     * @param connUrl
     * @param connTimeout
     */
    public BaseDbModel(String driver, String connUrl, int connTimeout) {
        initialize(driver, connUrl, connTimeout);
    }

    /**
     * @param driver
     * @param connUrl
     */
    public final void initialize(String driver, String connUrl) {
        setDriver(driver);
        setConnectionUrl(connUrl);
        try {
            setConnection();
            setStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param driver
     * @param connUrl
     * @param connTimeout
     */
    public final void initialize(String driver, String connUrl, int connTimeout) {
        setDriver(driver);
        setConnectionUrl(connUrl);
        setConnectionTimeout(connTimeout);
        try {
            setConnection();
            setStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "Driver : " + driver + " Connection Url : " + connUrl;
    }

    /**
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void setConnection() throws ClassNotFoundException, SQLException {
        assert driver != null && connUrl != null;
        Class.forName(driver);
        conn = DriverManager.getConnection(connUrl);
    }

    /**
     * @return
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     *
     */
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public int getConnectionTimeout() {
        return connTimeout;
    }

    /**
     * @param connTimeout
     */
    public void setConnectionTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    /**
     * @return
     */
    public String getConnectionUrl() {
        return connUrl;
    }

    /**
     * @param connUrl
     */
    public void setConnectionUrl(String connUrl) {
        this.connUrl = connUrl;
    }

    /**
     * @return
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void setStatement() throws SQLException, ClassNotFoundException {
        if (getConnection() != null) {
            setConnection();
        }
        stmt = conn.createStatement();
        stmt.setQueryTimeout(connTimeout);
    }

    /**
     * @return
     */
    public Statement getStatement() {
        return stmt;
    }

    /**
     *
     */
    public void closeStatement() {
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param sqlQuery
     * @throws SQLException
     */
    public void executeStatement(String sqlQuery) throws SQLException {
        stmt.executeUpdate(sqlQuery);
    }

    /**
     * @param sqlQueryArray
     * @throws SQLException
     */
    public void executeStatement(String[] sqlQueryArray) throws SQLException {
        for (String sql : sqlQueryArray) {
            executeStatement(sql);
        }
    }

    public ResultSet executeQuery(String sqlQuery) throws SQLException {
        return stmt.executeQuery(sqlQuery);
    }
}
