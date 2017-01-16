package com.lavrente.soundtrack.dao;

import com.lavrente.soundtrack.entity.Entity;
import com.lavrente.soundtrack.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by 123 on 02.01.2017.
 */
public abstract class AbstractDAO <T extends Entity> {
    private static final Logger LOG = LogManager.getLogger();
    protected ProxyConnection connection;

    public AbstractDAO(ProxyConnection connection) {
        this.connection = connection;
    }

    public void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            } else {
                LOG.warn("Can't close statement. Statement was'n created");
            }
        } catch (SQLException e) {
            LOG.error("Error during statement closing", e);
        }
    }

    public void closeConnection(ProxyConnection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error("Error during return to connection pull", e);
        }
    }
}
