package com.lavrente.soundtrack.exception;

/**
 * Created by 123 on 02.01.2017.
 */
public class LogicException extends Exception {
    public LogicException() {
        super();
    }

    public LogicException(String message) {
        super(message);
    }

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogicException(Throwable cause) {
        super(cause);
    }
}
