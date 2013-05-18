/*
 * 文件名称: LogFactory.java Copyright 2011-2013 Nali All right reserved.
 */
package com.wikimore.mlog;

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

    private static final String DEFAULT_FACTORY_CLASS = "com.wikimore.mlog.impl.DefaultLogFactory";
    private static final String FACTORY_CLASS_KEY = "com.wikimore.mlog.LogFactory";
    protected static PrintStream diagnosticsStream = System.err;
    private static Hashtable<ClassLoader, LogFactory> cachedFactorys = new Hashtable<ClassLoader, LogFactory>(
            4);

    public abstract Log getInstance(Class<?> clazz) throws LogInitException;

    public abstract Log getInstance(String clazz) throws LogInitException;

    public static Log getLog(String name) throws LogInitException {
        return getFactory().getInstance(name);
    }

    public static Log getLog(Class<?> name) throws LogInitException {
        return getFactory().getInstance(name);
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
        final String factoryClass = System.getProperty(FACTORY_CLASS_KEY, DEFAULT_FACTORY_CLASS);
        logFactory = newFactory(factoryClass, classLoader);

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
            diagnosticsStream.println("An error occurred while loading the factory class:"
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
}
