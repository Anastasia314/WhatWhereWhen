package com.epam.whatwherewhen.exception;

/**
 * Date: 12.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class ServiceException extends Exception {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
