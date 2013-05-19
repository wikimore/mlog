/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wikimore.mlog.impl;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.wikimore.mlog.Formatter;
import com.wikimore.mlog.Log;

/**
 * Log4j wrapper implementation
 * <p>
 * wrapper org.apache.log4j.Logger
 * <p>
 * <NOTE> only support log4j version >= 1.2.12
 * 
 * @author ted created on 2013-5-17
 * @since 1.0
 */
public class Log4jLog implements Log {
	private static final String FQCN = Log4jLog.class.getName();
	private transient Logger logger = null;
	private String name = null;

	public Log4jLog(String name) {
		this.name = name;
		this.logger = getLogger();
	}

	/**
	 * Return the log4j Logger instance we are using.
	 */
	public Logger getLogger() {
		if (logger == null) {
			logger = Logger.getLogger(name);
		}
		return (this.logger);
	}

	public boolean isDebugEnabled() {
		return getLogger().isDebugEnabled();
	}

	public boolean isErrorEnabled() {
		return getLogger().isEnabledFor(Level.ERROR);
	}

	public boolean isFatalEnabled() {
		return getLogger().isEnabledFor(Level.FATAL);
	}

	public boolean isInfoEnabled() {
		return getLogger().isInfoEnabled();
	}

	public boolean isTraceEnabled() {
		return getLogger().isTraceEnabled();
	}

	public boolean isWarnEnabled() {
		return getLogger().isEnabledFor(Level.WARN);
	}

	public void trace(String message) {
		if (isTraceEnabled()) {
			getLogger().log(FQCN, Level.TRACE, message, null);
		}
	}

	public void trace(String message, Object... params) {
		String formatMsg = Formatter.format(message, params);
		if (isTraceEnabled()) {
			getLogger().log(FQCN, Level.TRACE, formatMsg, null);
		}
	}

	public void trace(String message, Throwable t, Object... params) {
		String formatMsg = Formatter.format(message, params);
		if (isTraceEnabled()) {
			getLogger().log(FQCN, Level.TRACE, formatMsg, t);
		}
	}

	public void trace(String message, Throwable t) {
		if (isTraceEnabled()) {
			getLogger().log(FQCN, Level.TRACE, message, t);
		}
	}

	public void debug(String message) {
		if (isDebugEnabled()) {
			getLogger().log(FQCN, Level.DEBUG, message, null);
		}
	}

	public void debug(String message, Object... params) {
		String formatMsg = Formatter.format(message, params);
		if (isDebugEnabled()) {
			getLogger().log(FQCN, Level.DEBUG, formatMsg, null);
		}
	}

	public void debug(String message, Throwable t) {
		if (isDebugEnabled()) {
			getLogger().log(FQCN, Level.DEBUG, message, t);
		}
	}

	public void debug(String message, Throwable t, Object... params) {
		String formatMsg = Formatter.format(message, params);
		if (isDebugEnabled()) {
			getLogger().log(FQCN, Level.DEBUG, formatMsg, t);
		}
	}

	public void info(String message) {
		if (isInfoEnabled()) {
			getLogger().log(FQCN, Level.INFO, message, null);
		}
	}

	public void info(String message, Object... params) {
		String formatMsg = Formatter.format(message, params);
		if (isInfoEnabled()) {
			getLogger().log(FQCN, Level.INFO, formatMsg, null);
		}
	}

	public void info(String message, Throwable t) {
		if (isInfoEnabled()) {
			getLogger().log(FQCN, Level.INFO, message, t);
		}
	}

	public void info(String message, Throwable t, Object... params) {
		String formatMsg = Formatter.format(message, params);
		if (isInfoEnabled()) {
			getLogger().log(FQCN, Level.INFO, formatMsg, t);
		}
	}

	public void warn(String message) {
		getLogger().log(FQCN, Level.WARN, message, null);
	}

	public void warn(String message, Object... params) {
		String formatMsg = Formatter.format(message, params);
		getLogger().log(FQCN, Level.WARN, formatMsg, null);
	}

	public void warn(String message, Throwable t) {
		getLogger().log(FQCN, Level.WARN, message, t);
	}

	public void warn(String message, Throwable t, Object... params) {
		String formatMsg = Formatter.format(message, params);
		getLogger().log(FQCN, Level.WARN, formatMsg, t);
	}

	public void error(String message) {
		getLogger().log(FQCN, Level.ERROR, message, null);
	}

	public void error(String message, Object... params) {
		String formatMsg = Formatter.format(message, params);
		getLogger().log(FQCN, Level.ERROR, formatMsg, null);
	}

	public void error(String message, Throwable t) {
		getLogger().log(FQCN, Level.ERROR, message, t);
	}

	public void error(String message, Throwable t, Object... params) {
		String formatMsg = Formatter.format(message, params);
		getLogger().log(FQCN, Level.ERROR, formatMsg, t);
	}

	public void fatal(String message) {
		getLogger().log(FQCN, Level.FATAL, message, null);
	}

	public void fatal(String message, Object... params) {
		String formatMsg = Formatter.format(message, params);
		getLogger().log(FQCN, Level.FATAL, formatMsg, null);
	}

	public void fatal(String message, Throwable t) {
		getLogger().log(FQCN, Level.FATAL, message, t);
	}

	public void fatal(String message, Throwable t, Object... params) {
		String formatMsg = Formatter.format(message, params);
		getLogger().log(FQCN, Level.FATAL, formatMsg, t);
	}

}
