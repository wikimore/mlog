/*
 * 文件名称: DefaultLogFactory.java Copyright 2011-2013 Nali All right reserved.
 */
package com.wikimore.mlog.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

import org.apache.commons.logging.LogConfigurationException;

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
    private static final String[] adapterClasses = { LOG4J_LOG, JDK_LOG, CONSOLE_LOG };
    protected Hashtable<String, Log> instances = new Hashtable<String, Log>();
    protected Constructor<Log> logConstructor = null;
    protected Class<?> logConstructorSignature[] = { java.lang.String.class };

    @Override
    public Log getInstance(Class<?> clazz) throws LogInitException {
        return getInstance(clazz.getName());
    }

    @Override
    public Log getInstance(String clazz) throws LogInitException {
        // get Log from cache
        Log log = instances.get(clazz);
        if (log != null) {
            return log;
        }
        // create Log with constructor
        log = fastCreateLog(clazz);
        if (log == null) {
            // create Log with class name
            log = discoverSuitableLog(clazz);
        }
        if (log != null) {
            // cache Log
            instances.put(clazz, log);
        }
        return log;
    }

    private Log fastCreateLog(String logCategory) {
        if (logConstructor == null) {
            return null;
        }
        Log log = null;
        try {
            log = logConstructor.newInstance(logCategory);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        ClassLoader currentCL = LogFactory.getClassLoaderInternal();
        for (;;) {
            try {
                Class<Log> c = null;
                try {
                    c = (Class<Log>) Class.forName(logAdapterClassName, true, currentCL);
                } catch (ClassNotFoundException e) {
                }
                constructor = c.getConstructor(logConstructorSignature);
                Object o = constructor.newInstance(params);
                if (o instanceof Log) {
                    logAdapter = (Log) o;
                    break;
                }
            } catch (NoClassDefFoundError e) {
                break;
            } catch (ExceptionInInitializerError e) {
                break;
            } catch (LogConfigurationException e) {
                throw e;
            } catch (Throwable t) {
            }
            if (currentCL == null) {
                break;
            }
        }
        if ((logAdapter != null) && affectState) {
            // We've succeeded, so set instance fields
            this.logConstructor = constructor;
        }
        return logAdapter;
    }
}
