package com.epam.whatwherewhen.pool;

/**
 * Date: 02.02.2019
 *
 * Contains properties for database connection.
 * Uses in {@link ConnectionPool} class
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
final class PropertyConstant {
    final static String DB_URL = "db.url";
    final static String DB_USER = "db.user";
    final static String DB_PASSWORD = "db.password";
    final static String DB_POOL_SIZE = "db.poolSize";
    final static String DB_USE_UNICODE = "db.useUnicode";
    final static String DB_ENCODING = "db.encoding";
    final static String BUNDLE_NAME = "db";
    final static String USER_PROPERTY = "user";
    final static String PASSWORD_PROPERTY = "password";
    final static String UNICODE_PROPERTY = "useUnicode";
    final static String ENCODING_PROPERTY = "characterEncoding";

    private PropertyConstant() {
    }
}
