/*
 * 文件名称: JdkLogTest.java Copyright 2011-2013 Nali All right reserved.
 */
package com.wikimore.mlog.impl;

import org.junit.Test;

import com.wikimore.mlog.Log;

/**
 * JdkLog test
 * 
 * @author ted created on 2013-5-19
 * @since 1.0
 */
public class JdkLogTest {

    @Test
    public void test() {
        Log log = new JdkLog("testjdklog");
        log.debug("test jdklog {}", new Throwable("test throwable"), 1111);
    }

}
