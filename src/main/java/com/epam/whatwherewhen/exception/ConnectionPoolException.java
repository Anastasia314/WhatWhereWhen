package com.epam.whatwherewhen.exception;

/**
 * Date: 29.01.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class ConnectionPoolException extends Exception {
    public ConnectionPoolException() {
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
