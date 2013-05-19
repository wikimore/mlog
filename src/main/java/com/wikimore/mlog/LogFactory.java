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
package com.wikimore.mlog;

import java.io.InputStream;
import java.io.PrintStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Hashtable;

/**
 * abstraction Log factory
 * <p>
 * use this class to get the wapper Logger
 * 
 * @author ted created on 2013-5-17
 * @since 1.0
 */
public abstract class LogFactory {
    protected static final String DEFAULT_FACTORY_CLASS = "com.wikimore.mlog.impl.DefaultLogFactory";
    protected static final String FACTORY_CLASS_KEY = "com.wikimore.mlog.LogFactory";
    protected static final String LOG_CLASS = "com.wikimore.mlog.Log";
    protected static final String MLOG_PROPERTIES_FILE = "mlog.properties";
    protected static PrintStream diagnosticsStream = System.err;
    private static Hashtable<ClassLoader, LogFactory> cachedFactorys = new Hashtable<ClassLoader, LogFactory>(
            4);
    static {
        // load mlog.properties
        loadAttributes();
    }

    /**
     * internal method that get a Log instance with class name
     * 
     * @param clazz
     * @return
     * @throws LogInitException
     */
    protected abstract Log getInstance(Class<?> clazz) throws LogInitException;

    /**
     * internal method that get a Log instance with name
     * 
     * @param name
     * @return
     * @throws LogInitException
     */
    protected abstract Log getInstance(String name) throws LogInitException;

    /**
     * get a Log instance with name
     * 
     * @param name
     * @return
     * @throws LogInitException
     */
    public static Log getLog(String name) throws LogInitException {
        return getFactory().getInstance(name);
    }

    /**
     * get a Log instance with class name
     * 
     * @param clazz
     * @return
     * @throws LogInitException
     */
    public static Log getLog(Class<?> clazz) throws LogInitException {
        return getFactory().getInstance(clazz);
    }

    /**
     * get LogFactory
     * 
     * @return log factory
     */
    public static LogFactory getFactory() throws LogInitException {
        // get current classLoader
        final ClassLoader classLoader = getClassLoaderInternal();
        if (classLoader == null) {
            diagnosticsStream.println("[ERROR] LogFactory: Get classloader failed.");
        }
        // 1. get LogFactory from cache
        LogFactory logFactory = getCachedFactory(classLoader);
        if (logFactory != null) {
            return logFactory;
        }
        // 2. new LogFactory instance
        String factoryClass = System.getProperty(FACTORY_CLASS_KEY);
        if (factoryClass == null) {
            factoryClass = DEFAULT_FACTORY_CLASS;
        }
        logFactory = newFactory(factoryClass, classLoader);

        // 3. cache LogFactory
        if (logFactory != null) {
            cacheFactory(classLoader, logFactory);
        }
        return logFactory;
    }

    private static LogFactory newFactory(final String factoryClass, final ClassLoader classLoader)
            throws LogInitException {
        Object result = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                return createFactory(factoryClass, classLoader);
            }
        });
        if (result instanceof LogInitException) {
            LogInitException ex = (LogInitException) result;
            diagnosticsStream
                    .println("[ERROR] LogFactory: An error occurred while loading the factory class:"
                            + ex.getMessage());
            throw ex;
        }
        return (LogFactory) result;
    }

    /**
     * cache ClassLoader & LogFactory Pair
     * 
     * @param classLoader
     * @param logFactory
     */
    private static void cacheFactory(ClassLoader classLoader, LogFactory logFactory) {
        if (logFactory != null) {
            if (classLoader != null) {
                cachedFactorys.put(classLoader, logFactory);
            }
        }
    }

    private static Object createFactory(String factoryClass, ClassLoader classLoader) {
        Class<?> logFactoryClass = null;
        try {
            try {
                logFactoryClass = classLoader.loadClass(factoryClass);
                if (!LogFactory.class.isAssignableFrom(logFactoryClass)) {
                    diagnosticsStream.println("[ERROR] LogFactory: Init class " + factoryClass
                            + " is not a sub class of com.wikimore.mlog.LogFactory.");
                }
                return (LogFactory) logFactoryClass.newInstance();
            } catch (ClassNotFoundException e) {
                diagnosticsStream.println("[ERROR] LogFactory: Could not find class "
                        + factoryClass + ".");
                throw e;
            } catch (ClassCastException e) {
                diagnosticsStream.println("[ERROR] LogFactory: Could not cast class "
                        + factoryClass + " to com.wikimore.mlog.LogFactory.");
                throw e;
            }
        } catch (Exception e) {
            if (logFactoryClass != null && !LogFactory.class.isAssignableFrom(logFactoryClass)) {
                return new LogInitException(
                        "The chosen LogFactory implementation does not extend LogFactory. Please check your configuration.",
                        e);
            }
            return new LogInitException(e);
        }
    }

    /**
     * get LogFactory from cachedFactorys
     * 
     * @param classLoader
     * @return a LogFactory corresponding to the classLoader
     */
    private static LogFactory getCachedFactory(ClassLoader classLoader) {
        if (classLoader == null)
            return null;
        LogFactory logFactory = cachedFactorys.get(classLoader);
        return logFactory;
    }

    /**
     * get the current thread class loader with a Privilege
     * 
     * @return current classloader
     * @throws LogInitException
     */
    protected static ClassLoader getClassLoaderInternal() throws LogInitException {
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            public ClassLoader run() {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                // Return the selected class loader
                return classLoader;
            }
        });
    }

    /**
     * read mlog.properties file in classpath, load key-value pair
     */
    private static void loadAttributes() {
        ClassLoader classLoader = getClassLoaderInternal();
        if (classLoader != null) {
            InputStream in = classLoader.getResourceAsStream(MLOG_PROPERTIES_FILE);
            try {
                System.getProperties().load(in);
            } catch (Exception e) {
                diagnosticsStream
                        .println("[WARN] LogFactory: could not load mlog.properties, use default configuration.");
            }
        } else {
            diagnosticsStream
                    .println("[ERROR] LogFactory: could not load mlog.properties, class loader is null, use default configuration.");
        }
    }
}
