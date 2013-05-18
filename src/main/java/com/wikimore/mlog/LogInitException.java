/*
 * 文件名称: LogInitException.java Copyright 2011-2013 Nali All right reserved.
 */
package com.wikimore.mlog;

/**
 * Wrapper exception when initializing Log
 * 
 * @author ted created on 2013-5-17
 * @since 1.0
 */
public class LogInitException extends RuntimeException {

    /**  */
    private static final long serialVersionUID = -2231595628576980218L;

    public LogInitException() {
        super();
    }

    public LogInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogInitException(String message) {
        super(message);
    }

    public LogInitException(Throwable cause) {
        super(cause);
    }

}
