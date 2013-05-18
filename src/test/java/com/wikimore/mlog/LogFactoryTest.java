/*
 * 文件名称: LogFactoryTest.java Copyright 2011-2013 Nali All right reserved.
 */
package com.wikimore.mlog;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LogFactory test case
 * 
 * @author ted created on 2013-5-17
 * @since 1.0
 */
public class LogFactoryTest {

    @Test
    public void testGetFactory() {
        LogFactory logFactory = LogFactory.getFactory();
        Assert.assertNotNull(logFactory);
    }

    @Test
    public void testGetLog() {
        String testString = new String("test String");
        Object testObject = new Object();
        Long testLong = Long.valueOf(100000000L);
        Integer testInteger = Integer.valueOf(123213213);
        long testlong = 123456789012345L;
        Logger logger = LoggerFactory.getLogger("test.log2");
        Log log = LogFactory.getLog("test.log1");
        long b1 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            log.info(
                    "String test log {} hello {} hello {} hello {} hello {} hello {} hello {} hello",
                    testString, testInteger, testInteger, testInteger, testInteger, testInteger,
                    testInteger);
            log.info(
                    "Object test log {} hello {} hello {} hello {} hello {} hello {} hello {} hello",
                    testObject, testObject, testObject, testObject, testObject, testObject,
                    testObject);
            log.info(
                    "Long test log {} hello {} hello {} hello {} hello {} hello {} hello {} hello",
                    testLong, testLong, testLong, testLong, testLong, testLong, testLong);
            log.info(
                    "Integer test log {} hello {} hello {} hello {} hello {} hello {} hello {} hello",
                    testInteger, testInteger, testInteger, testInteger, testInteger, testInteger,
                    testInteger);
            log.info(
                    "long test log {} hello {} hello {} hello {} hello {} hello {} hello {} hello",
                    testlong, testlong, testlong, testlong, testlong, testlong, testlong);
        }
        long e1 = System.currentTimeMillis();
        long u1 = e1 - b1;

        long b2 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            logger.info(
                    "String test log {} hello {} hello {} hello {} hello {} hello {} hello {} hello",
                    testString, testInteger, testInteger, testInteger, testInteger, testInteger,
                    testInteger);
            logger.info(
                    "Object test log {} hello {} hello {} hello {} hello {} hello {} hello {} hello",
                    testObject, testObject, testObject, testObject, testObject, testObject,
                    testObject);
            logger.info(
                    "Long test log {} hello {} hello {} hello {} hello {} hello {} hello {} hello",
                    testLong, testLong, testLong, testLong, testLong, testLong, testLong);
            logger.info(
                    "Integer test log {} hello {} hello {} hello {} hello {} hello {} hello {} hello",
                    testInteger, testInteger, testInteger, testInteger, testInteger, testInteger,
                    testInteger);
            logger.info(
                    "long test log {} hello {} hello {} hello {} hello {} hello {} hello {} hello",
                    testlong, testlong, testlong, testlong, testlong, testlong, testlong);
        }
        long e2 = System.currentTimeMillis();
        long u2 = e2 - b2;
        System.out.println(u2);
        System.out.println(u1);
        Assert.assertNotNull(log);

    }
}
