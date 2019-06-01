package com.epam.whatwherewhen.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.epam.whatwherewhen.pool.PropertyConstant.*;

/**
 * Date: 20.03.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
final class PoolInitializer {

    private final static Logger logger = LogManager.getLogger();

    /**
     * Default initial connection pool capacity.
     */
    private final static int DEFAULT_POOL_SIZE = 32;

   static BlockingQueue<ProxyConnection> initPoolData(ResourceBundle dbBundle) {
        int poolSize;

        try {
            poolSize = Integer.parseInt(dbBundle.getString(DB_POOL_SIZE));
        } catch (MissingResourceException | NumberFormatException e) {
            poolSize = DEFAULT_POOL_SIZE;
        }
        BlockingQueue<ProxyConnection> connections = new LinkedBlockingQueue<>(poolSize);
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Properties properties = new Properties();
            properties.setProperty(USER_PROPERTY, dbBundle.getString(DB_USER));
            properties.setProperty(PASSWORD_PROPERTY, dbBundle.getString(DB_PASSWORD));
            properties.setProperty(UNICODE_PROPERTY, dbBundle.getString(DB_USE_UNICODE));
            properties.setProperty(ENCODING_PROPERTY, dbBundle.getString(DB_ENCODING));
            String dbURL = dbBundle.getString(DB_URL);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(dbURL, properties);
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                connections.add(proxyConnection);
            }
        } catch (MissingResourceException e) {
            logger.fatal("Error while reading pool properties", e);
            throw new RuntimeException("Error while reading pool properties", e);
        } catch (SQLException e) {
            logger.fatal("Error while getting pool from Driver Manager", e);
            throw new RuntimeException("Error while getting pool from Driver Manager", e);
        }
        return connections;
    }
}
