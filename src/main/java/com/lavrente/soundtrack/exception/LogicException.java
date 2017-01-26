package com.lavrente.soundtrack.exception;

/**
 * Created by 123 on 02.01.2017.
 */
public class LogicException extends Exception {

    /**
     * Instantiates a new logic exception.
     */
    public LogicException() {
        super();
    }

    /**
     * Instantiates a new logic exception.
     *
     * @param message the message
     */
    public LogicException(String message) {
        super(message);
    }

    /**
     * Instantiates a new logic exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new logic exception.
     *
     * @param cause the cause
     */
    public LogicException(Throwable cause) {
        super(cause);
    }
}
