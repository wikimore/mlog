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

import java.lang.reflect.Constructor;
import java.util.Hashtable;

import com.wikimore.mlog.Log;
import com.wikimore.mlog.LogFactory;
import com.wikimore.mlog.LogInitException;

/**
 * default LogFactory implementation
 * 
 * @author ted created on 2013-5-17
 * @since 1.0
 */
public class DefaultLogFactory extends LogFactory {

    private static final String LOG4J_LOG = "com.wikimore.mlog.impl.Log4jLog";
    private static final String JDK_LOG = "com.wikimore.mlog.impl.JdkLog";
    private static final String CONSOLE_LOG = "com.wikimore.mlog.impl.ConsoleLog";
    private static final String LOGBACK_LOG = "com.wikimore.mlog.impl.LogbackLog";
    private static final String[] adapterClasses = { LOG4J_LOG, LOGBACK_LOG, JDK_LOG, CONSOLE_LOG };
    protected Hashtable<String, Log> instances = new Hashtable<String, Log>();
    protected Constructor<Log> logConstructor = null;
    protected Class<?> logConstructorSignature[] = { java.lang.String.class };

    @Override
    protected Log getInstance(Class<?> clazz) throws LogInitException {
        return getInstance(clazz.getName());
    }

    @Override
    protected Log getInstance(String logName) throws LogInitException {
        // 1. get Log from cache
        Log log = instances.get(logName);
        if (log != null) {
            return log;
        }
        // 2. fast create Log with className or constructor
        log = fastCreateLog(logName);

        if (log != null) {
            return log;
        }
        // 3. create Log with listed Log class name
        log = discoverSuitableLog(logName);

        if (log != null) {
            // cache Log
            instances.put(logName, log);
        } else {
            diagnosticsStream.println("[ERROR] LogFactory: initialize Log instance failed.");
            throw new LogInitException("initialize Log instance failed");
        }
        return log;
    }

    private Log fastCreateLog(String logCategory) {
        Log log = null;
        if (logConstructor == null) {
            String logClassName = System.getProperty(LOG_CLASS);
            if (logClassName != null) {
                log = createLogFromClass(logClassName, logCategory, true);
            }
            return log;
        }
        try {
            log = logConstructor.newInstance(logCategory);
        } catch (Exception e) {
            // ignore
        }
        return log;
    }

    private Log discoverSuitableLog(String logCategory) {
        Log log = null;
        for (String adapterClass : adapterClasses) {
            log = createLogFromClass(adapterClass, logCategory, true);
            if (log != null) {
                break;
            }
        }
        return log;
    }

    @SuppressWarnings("unchecked")
    private Log createLogFromClass(String logAdapterClassName, String logCategory,
            boolean affectState) throws LogInitException {
        Object[] params = { logCategory };
        Constructor<Log> constructor = null;
        Log logAdapter = null;
        ClassLoader classLoader = LogFactory.getClassLoaderInternal();
        try {
            Class<Log> c = null;
            try {
                c = (Class<Log>) Class.forName(logAdapterClassName, true, classLoader);
            } catch (ClassNotFoundException e) {
            }
            constructor = c.getConstructor(logConstructorSignature);
            Object o = constructor.newInstance(params);
            if (o instanceof Log) {
                logAdapter = (Log) o;
            }
        } catch (Throwable t) {
            // ignore
        }
        if ((logAdapter != null) && affectState) {
            this.logConstructor = constructor;
        }
        return logAdapter;
    }
}
