/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wikimore.mlog.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.wikimore.mlog.Formatter;
import com.wikimore.mlog.Log;

/**
 * Log implementation wrapper Java native log
 * 
 * @author ted created on 2013-5-19
 * @since 1.0
 */
public class JdkLog implements Log {
    private transient Logger logger = null;
    private String name = null;

    public JdkLog(String name) {
        this.name = name;
        this.logger = getLogger();
    }

    @Override
    public boolean isDebugEnabled() {
        return getLogger().isLoggable(Level.CONFIG);
    }

    @Override
    public boolean isErrorEnabled() {
        return getLogger().isLoggable(Level.SEVERE);
    }

    @Override
    public boolean isFatalEnabled() {
        return getLogger().isLoggable(Level.SEVERE);
    }

    @Override
    public boolean isInfoEnabled() {
        return getLogger().isLoggable(Level.INFO);
    }

    @Override
    public boolean isTraceEnabled() {
        return getLogger().isLoggable(Level.FINE);
    }

    @Override
    public boolean isWarnEnabled() {
        return getLogger().isLoggable(Level.WARNING);
    }

    @Override
    public void trace(String message) {
        if (isTraceEnabled()) {
            getLogger().log(Level.FINE, message);
        }
    }

    @Override
    public void trace(String message, Object... params) {
        String formatMsg = Formatter.format(message, params);
        if (isTraceEnabled()) {
            getLogger().log(Level.FINE, formatMsg);
        }
    }

    @Override
    public void trace(String message, Throwable t, Object... params) {
        String formatMsg = Formatter.format(message, params);
        if (isTraceEnabled()) {
            getLogger().log(Level.FINE, formatMsg, t);
        }
    }

    @Override
    public void trace(String message, Throwable t) {
        if (isTraceEnabled()) {
            getLogger().log(Level.FINE, message, t);
        }
    }

    @Override
    public void debug(String message) {
        if (isDebugEnabled()) {
            getLogger().log(Level.CONFIG, message);
        }
    }

    @Override
    public void debug(String message, Object... params) {
        String formatMsg = Formatter.format(message, params);
        if (isDebugEnabled()) {
            getLogger().log(Level.CONFIG, formatMsg);
        }
    }

    @Override
    public void debug(String message, Throwable t) {
        if (isDebugEnabled()) {
            getLogger().log(Level.CONFIG, message, t);
        }
    }

    @Override
    public void debug(String message, Throwable t, Object... params) {
        String formatMsg = Formatter.format(message, params);
        if (isDebugEnabled()) {
            getLogger().log(Level.CONFIG, formatMsg, t);
        }
    }

    @Override
    public void info(String message) {
        if (isInfoEnabled()) {
            getLogger().log(Level.INFO, message);
        }
    }

    @Override
    public void info(String message, Object... params) {
        String formatMsg = Formatter.format(message, params);
        if (isInfoEnabled()) {
            getLogger().log(Level.INFO, formatMsg);
        }
    }

    @Override
    public void info(String message, Throwable t) {
        if (isInfoEnabled()) {
            getLogger().log(Level.INFO, message, t);
        }
    }

    @Override
    public void info(String message, Throwable t, Object... params) {
        String formatMsg = Formatter.format(message, params);
        if (isInfoEnabled()) {
            getLogger().log(Level.INFO, formatMsg, t);
        }
    }

    @Override
    public void warn(String message) {
        getLogger().log(Level.WARNING, message);
    }

    @Override
    public void warn(String message, Object... params) {
        String formatMsg = Formatter.format(message, params);
        getLogger().log(Level.WARNING, formatMsg);
    }

    @Override
    public void warn(String message, Throwable t) {
        getLogger().log(Level.WARNING, message, t);
    }

    @Override
    public void warn(String message, Throwable t, Object... params) {
        String formatMsg = Formatter.format(message, params);
        getLogger().log(Level.WARNING, formatMsg, t);
    }

    @Override
    public void error(String message) {
        getLogger().log(Level.SEVERE, message);
    }

    @Override
    public void error(String message, Object... params) {
        String formatMsg = Formatter.format(message, params);
        getLogger().log(Level.SEVERE, formatMsg);
    }

    @Override
    public void error(String message, Throwable t) {
        getLogger().log(Level.SEVERE, message, t);
    }

    @Override
    public void error(String message, Throwable t, Object... params) {
        String formatMsg = Formatter.format(message, params);
        getLogger().log(Level.SEVERE, formatMsg, t);
    }

    @Override
    public void fatal(String message) {
        getLogger().log(Level.SEVERE, message);
    }

    @Override
    public void fatal(String message, Object... params) {
        String formatMsg = Formatter.format(message, params);
        getLogger().log(Level.SEVERE, formatMsg);
    }

    @Override
    public void fatal(String message, Throwable t) {
        getLogger().log(Level.SEVERE, message, t);
    }

    @Override
    public void fatal(String message, Throwable t, Object... params) {
        String formatMsg = Formatter.format(message, params);
        getLogger().log(Level.SEVERE, formatMsg, t);
    }

    private Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger(name);
        }
        return logger;
    }
}
