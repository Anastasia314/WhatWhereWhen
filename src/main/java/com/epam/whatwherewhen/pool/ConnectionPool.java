package com.epam.whatwherewhen.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import static com.epam.whatwherewhen.pool.PropertyConstant.*;

/**
 * Date: 29.01.2019
 *
 * Implementation of simple connection pool. The ConnectionPool uses a BlockingQueue
 * for storing active connections and ConcurrentSkipListSet for storing given away connections
 * to control connections.
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public final class ConnectionPool {
    private final static Logger logger = LogManager.getLogger();

    /**
     * Maximum connection usage time.
     */
    private final static int CONNECTION_DELAY = 5000;

    private static ConnectionPool instance = null;

    private static AtomicBoolean instanceCreated = new AtomicBoolean();

    private static ReentrantLock lock = new ReentrantLock();

    /**
     * BlockingQueue for storing active connections.
     */
    private BlockingQueue<ProxyConnection> connections;

    /**
     * ConcurrentSkipListSet for storing given away connections
     * to control connections.
     */
    private ConcurrentSkipListSet<ProxyConnection> givenConnections;

    private ConnectionPool() {
        ResourceBundle dbBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        connections = PoolInitializer.initPoolData(dbBundle);
        givenConnections = new ConcurrentSkipListSet<>();
        Timer timer = new Timer(true);
        TimerTask connectionsCheck = new TimerTask() {
            @Override
            public void run() {
                ProxyConnection connection = givenConnections.pollFirst();
                if (connection != null) {
                    if (System.currentTimeMillis() - connection.getStartTime() > CONNECTION_DELAY) {
                        returnConnection(connection);
                        givenConnections.remove(connection);
                        logger.warn("Connection captured more then 5 seconds");
                    } else {
                        givenConnections.add(connection);
                    }
                }

            }
        };
        timer.schedule(connectionsCheck, CONNECTION_DELAY, CONNECTION_DELAY);
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


    /**
     * Gives connections from connection pool.
     *
     * @return ProxyConnection object, custom connection of a onnection pool
     */
    public ProxyConnection takeConnection() {
        ProxyConnection connection = null;
        try {
            connection = connections.take();
            connection.setStartTime(System.currentTimeMillis());
            givenConnections.add(connection);
        } catch (InterruptedException e) {
            logger.error(e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * Takes connection back to connection pool.
     *
     * @param connection of ProxyConnection
     */
    public void returnConnection(ProxyConnection connection) {
        if (connection != null) {
            try {
                connections.put(connection);
                givenConnections.remove(connection);
            } catch (InterruptedException e) {
                logger.error(e);
                Thread.currentThread().interrupt();
            }
        }
    }

    private void deregisterDriver() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        if (drivers != null) {
            while (drivers.hasMoreElements()) {
                try {
                    Driver driver = drivers.nextElement();
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
    }

    /**
     * Closes all connection pool connections and makes driver deregistration.
     */
    public void closeConnectionPool() {
        deregisterDriver();
        int size = connections.size();
        for (int i = 0; i < size; i++) {
            try {
                connections.take().reallyClose();
            } catch (SQLException e) {
                logger.error("Cannot close pool.\n", e);
            } catch (InterruptedException e) {
                logger.error(e);
                Thread.currentThread().interrupt();
            }
        }
        logger.info("Connection pool successfully released");
    }
}
