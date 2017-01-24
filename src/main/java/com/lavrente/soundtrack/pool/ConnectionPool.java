package com.lavrente.soundtrack.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 123 on 02.01.2017.
 */
public class ConnectionPool {
    private static final Logger LOG = LogManager.getLogger();
    private ArrayBlockingQueue<ProxyConnection> connectionQueue;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private static ConnectionPool instance;
    private static InitDB db;


    private ConnectionPool() {
        db=new InitDB();
        this.connectionQueue = new ArrayBlockingQueue<>(db.POOL_SIZE);
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        }catch (SQLException e){
            LOG.fatal("Exception during driver registration",e);
            throw new RuntimeException(e);
        }
        for (int i = 0; i < db.POOL_SIZE; i++) {
            createConnection();
        }

        if (size() != db.POOL_SIZE) {
            int number = db.POOL_SIZE - connectionQueue.size();
            LOG.warn(number + " connections should be recreated");
            for (int i = 0; i < number; i++) {
                createConnection();
            }
        }
        if (size() == 0) {
            LOG.fatal("There's no connections in the pull");
            throw new RuntimeException();
        }
    }

    private void createConnection(){
        try {
            Connection connection = DriverManager.getConnection(db.DATABASE_URL, db.DATABASE_LOGIN, db.DATABASE_PASS);
            ProxyConnection proxyConnection = new ProxyConnection(connection);
            this.connectionQueue.put(proxyConnection);
        } catch (SQLException | InterruptedException e) {
            LOG.error("Exception during connection addition to connection queue",e);
        }
    }

    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOG.error(e);
        }
        return connection;
    }

    public void terminatePool() {
        try {
            for (int i = 0; i < db.POOL_SIZE; i++) {
                connectionQueue.take().terminateConnection();
            }
        } catch (SQLException | InterruptedException e) {
            LOG.error(e);
        }
    }

    void returnConnection(ProxyConnection connection) {
        try {
            connectionQueue.put(connection);
        } catch (InterruptedException e) {
            LOG.error(e);
        }
    }

    private int size(){
        return connectionQueue.size();
    }
}
