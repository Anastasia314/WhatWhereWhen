package com.epam.whatwherewhen.exception;

/**
 * Date: 29.01.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class DaoException extends Exception {
    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
