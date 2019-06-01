package com.epam.whatwherewhen.pool;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.sql.Connection;

import static org.testng.Assert.assertNotNull;

public class ConnectionPoolTest {

    @AfterClass
    public void tearDown() {
        ConnectionPool.getInstance().closeConnectionPool();
    }

    @Test
    public void testTakeConnection() {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        assertNotNull(connection);
    }
}