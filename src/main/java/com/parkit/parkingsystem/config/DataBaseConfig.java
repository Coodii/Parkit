package com.parkit.parkingsystem.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConfig {

    /**
     * Initialise a logger LOGGER.
     */
    private static final Logger LOGGER = LogManager.getLogger("DataBaseConfig");
    /**
     * Declaring an url.
     */
    private static final String DB_URL = "db.url";
    /**
     * Declaring a login.
     */
    private static final String DB_LOGIN = "db.login";
    /**
     * Declaring a password.
     */
    private static final String DB_PASSWORD = "db.password";
    /**
     * Declaring the connection.
     */
    private static Connection connection = null;
    /**
     * Declaring properties.
     */
    private static Properties properties = null;

    /**
     * This method start the connection to the SQl server.
     * @return connection to the server.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConnection() throws ClassNotFoundException,
            SQLException, IOException {
        LOGGER.info("Create DB connection");
        properties = new Properties();
        String server = "src/server.properties";
        FileInputStream server1 = new FileInputStream(server);
        properties.load(server1);
        server1.close();
        connection = DriverManager.getConnection(properties.getProperty(DB_URL),
                properties.getProperty(DB_LOGIN), properties.getProperty(DB_PASSWORD));
        return connection;
    }

    /**
     * This method closes the connection to the SQL server.
     * @param con
     * @return
     */
    public void closeConnection(final Connection con) {
        if (con != null) {
            try {
                con.close();
                LOGGER.info("Closing DB connection");
            } catch (SQLException e) {
                LOGGER.error("Error while closing connection", e);
            }
        }
    }

    /**
     * This method closes the prepared statement.
     * @param ps
     */
    public void closePreparedStatement(final PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
                LOGGER.info("Closing Prepared Statement");
            } catch (SQLException e) {
                LOGGER.error("Error while closing prepared statement", e);
            }
        }
    }

    /**
     * This method closes the result set.
     * @param rs
     */
    public void closeResultSet(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                LOGGER.info("Closing Result Set");
            } catch (SQLException e) {
                LOGGER.error("Error while closing result set", e);
            }
        }
    }
}
