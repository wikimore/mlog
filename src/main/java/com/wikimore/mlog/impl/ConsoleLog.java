/*
 * 文件名称: ConsoleLog.java Copyright 2011-2013 Nali All right reserved.
 */
package com.wikimore.mlog.impl;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wikimore.mlog.Formatter;
import com.wikimore.mlog.Log;
import com.wikimore.mlog.StringWriter;

/**
 * Log implementation with console output:System.err
 * 
 * @author ted created on 2013-5-17
 * @since 1.0
 */
public class ConsoleLog implements Log {
    private static final int TRACE_LOG_LEVEL = 1;
    private static final int DEBUG_LOG_LEVEL = 2;
    private static final int INFO_LOG_LEVEL = 3;
    private static final int WARN_LOG_LEVEL = 4;
    private static final int ERROR_LOG_LEVEL = 5;
    private static final int FATAL_LOG_LEVEL = 6;
    private static final int DEFAULT_LOG_LEVEL = TRACE_LOG_LEVEL;
    private static final Object[] NULL_OBJ_ARRAY = null;
    private int currentLogLevel;
    private String name = null;
    private ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();
    private PrintStream writer;

    public ConsoleLog(String name) {
        this.name = name;
        this.currentLogLevel = DEFAULT_LOG_LEVEL;
        this.writer = System.err;
    }

    public boolean isDebugEnabled() {
        return isLevelEnabled(DEBUG_LOG_LEVEL);
    }

    public boolean isErrorEnabled() {
        return isLevelEnabled(ERROR_LOG_LEVEL);
    }

    public boolean isFatalEnabled() {
        return isLevelEnabled(FATAL_LOG_LEVEL);
    }

    public boolean isInfoEnabled() {
        return isLevelEnabled(INFO_LOG_LEVEL);
    }

    public boolean isTraceEnabled() {
        return isLevelEnabled(TRACE_LOG_LEVEL);
    }

    public boolean isWarnEnabled() {
        return isLevelEnabled(WARN_LOG_LEVEL);
    }

    public void trace(String message) {
        if (isTraceEnabled()) {
            log(TRACE_LOG_LEVEL, message, null, NULL_OBJ_ARRAY);
        }
    }

    public void trace(String message, Object... params) {
        if (isTraceEnabled()) {
            log(TRACE_LOG_LEVEL, message, null, params);
        }
    }

    public void trace(String message, Throwable t, Object... params) {
        if (isTraceEnabled()) {
            log(TRACE_LOG_LEVEL, message, t, params);
        }
    }

    public void trace(String message, Throwable t) {
        if (isTraceEnabled()) {
            log(TRACE_LOG_LEVEL, message, t, NULL_OBJ_ARRAY);
        }
    }

    public void debug(String message) {
        if (isDebugEnabled()) {
            log(DEBUG_LOG_LEVEL, message, null, NULL_OBJ_ARRAY);
        }
    }

    public void debug(String message, Object... params) {
        if (isDebugEnabled()) {
            log(DEBUG_LOG_LEVEL, message, null, params);
        }
    }

    public void debug(String message, Throwable t) {
        if (isDebugEnabled()) {
            log(DEBUG_LOG_LEVEL, message, t, NULL_OBJ_ARRAY);
        }
    }

    public void debug(String message, Throwable t, Object... params) {
        if (isDebugEnabled()) {
            log(DEBUG_LOG_LEVEL, message, t, params);
        }
    }

    public void info(String message) {
        if (isInfoEnabled()) {
            log(INFO_LOG_LEVEL, message, null, NULL_OBJ_ARRAY);
        }

    }

    public void info(String message, Object... params) {
        if (isInfoEnabled()) {
            log(INFO_LOG_LEVEL, message, null, params);
        }
    }

    public void info(String message, Throwable t) {
        if (isInfoEnabled()) {
            log(INFO_LOG_LEVEL, message, t, NULL_OBJ_ARRAY);
        }
    }

    public void info(String message, Throwable t, Object... params) {
        if (isInfoEnabled()) {
            log(INFO_LOG_LEVEL, message, t, params);
        }
    }

    public void warn(String message) {
        log(WARN_LOG_LEVEL, message, null, NULL_OBJ_ARRAY);
    }

    public void warn(String message, Object... params) {
        log(WARN_LOG_LEVEL, message, null, params);
    }

    public void warn(String message, Throwable t) {
        log(WARN_LOG_LEVEL, message, t, NULL_OBJ_ARRAY);
    }

    public void warn(String message, Throwable t, Object... params) {
        log(WARN_LOG_LEVEL, message, t, params);
    }

    public void error(String message) {
        log(ERROR_LOG_LEVEL, message, null, NULL_OBJ_ARRAY);
    }

    public void error(String message, Object... params) {
        log(ERROR_LOG_LEVEL, message, null, params);
    }

    public void error(String message, Throwable t) {
        log(ERROR_LOG_LEVEL, message, t, NULL_OBJ_ARRAY);
    }

    public void error(String message, Throwable t, Object... params) {
        log(ERROR_LOG_LEVEL, message, t, params);
    }

    public void fatal(String message) {
        log(FATAL_LOG_LEVEL, message, null, NULL_OBJ_ARRAY);
    }

    public void fatal(String message, Object... params) {
        log(FATAL_LOG_LEVEL, message, null, params);
    }

    public void fatal(String message, Throwable t) {
        log(FATAL_LOG_LEVEL, message, t, NULL_OBJ_ARRAY);
    }

    public void fatal(String message, Throwable t, Object... params) {
        log(FATAL_LOG_LEVEL, message, t, params);
    }

    /**
     * Is the given log level currently enabled?
     * 
     * @param logLevel is this level enabled?
     */
    private boolean isLevelEnabled(int logLevel) {
        return (logLevel >= currentLogLevel);
    }

    /**
     * do actual log, write message use System.err
     * 
     * @param logLevel log write level
     * @param message pattern message
     * @param t any throwable
     * @param parameters many parameters
     */
    protected void log(int logLevel, String message, Throwable t, Object... parameters) {
        // Use a string buffer for better performance
        StringBuilder builder = new StringBuilder();

        // Append date-time if so configured
        Date now = new Date();
        SimpleDateFormat dateFormatter = threadLocal.get();
        if (dateFormatter == null) {
            dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
            threadLocal.set(dateFormatter);
        }
        String dateText = dateFormatter.format(now);
        builder.append(dateText);
        builder.append(" ");

        // Append a readable representation of the log level
        switch (logLevel) {
        case TRACE_LOG_LEVEL:
            builder.append("[TRACE] ");
            break;
        case DEBUG_LOG_LEVEL:
            builder.append("[DEBUG] ");
            break;
        case INFO_LOG_LEVEL:
            builder.append("[INFO] ");
            break;
        case WARN_LOG_LEVEL:
            builder.append("[WARN] ");
            break;
        case ERROR_LOG_LEVEL:
            builder.append("[ERROR] ");
            break;
        case FATAL_LOG_LEVEL:
            builder.append("[FATAL] ");
            break;
        }

        // append Log name
        builder.append(name).append(" - ");
        // Append the message
        String formatMessage = Formatter.format(message, parameters);
        builder.append(formatMessage);

        // Append stack trace if not null
        if (t != null) {
            builder.append("\t");
            StringWriter sw = new StringWriter(1024);
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            builder.append(sw.toString());
        }

        // Print to the appropriate destination
        writer.println(builder.toString());

    }

}
