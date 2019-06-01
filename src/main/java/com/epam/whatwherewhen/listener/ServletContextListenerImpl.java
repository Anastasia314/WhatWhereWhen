package com.epam.whatwherewhen.listener;

import com.epam.whatwherewhen.pool.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Date: 17.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class ServletContextListenerImpl implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance().closeConnectionPool();
    }

}

