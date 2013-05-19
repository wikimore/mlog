/*
 * 文件名称: ConsoleLogTest.java Copyright 2011-2013 Nali All right reserved.
 */
package com.wikimore.mlog.impl;

import org.junit.Test;

import com.wikimore.mlog.Log;
import com.wikimore.mlog.LogInitException;

/**
 * 
 * @author ted created on 2013-5-19
 * @since 1.0
 */
public class ConsoleLogTest {

    @Test
    public void testLog() {
        Log log = new ConsoleLog("console.log");
        log.trace("test trace");
        log.debug("test debug");
        log.info("test info");
        log.warn("test warn");
        log.error("test error");
        log.fatal("test fatal");

        log.trace("test trace {}", "param1");
        log.debug("test debug {}", "param1");
        log.info("test info {}", "param1");
        log.warn("test warn {}", "param1");
        log.error("test error {}", "param1");
        log.fatal("test fatal {}", "param1");

        Throwable t = new Throwable("wrapper throwable", new LogInitException("inner throwable"));
        log.trace("test trace {}", t, "param1");
        log.debug("test debug {}", t, "param1");
        log.info("test info {}", t, "param1");
        log.warn("test warn {}", t, "param1");
        log.error("test error {}", t, "param1");
        log.fatal("test fatal {}", t, "param1");
    }

}
