package com.suki.remindyourself.exception;

/**
 * 自定义SQL异常类
 */
public class MySQLException extends RuntimeException {
    public MySQLException() {
    }


    public MySQLException(String message) {
        super(message);
    }

    public MySQLException(String message, Throwable cause) {
        super(message, cause);
    }
}
