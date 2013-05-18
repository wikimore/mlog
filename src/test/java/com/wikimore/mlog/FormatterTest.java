/*
 * 文件名称: FormatterTest.java Copyright 2011-2013 Nali All right reserved.
 */
package com.wikimore.mlog;

import org.junit.Assert;
import org.junit.Test;

import com.wikimore.mlog.Formatter;

/**
 * test log message pattern formatter
 * 
 * @author ted created on 2013-5-17
 * @since 1.0
 */
public class FormatterTest {
    @Test
    public void testFormat() {
        String pattern1 = "first {}, second {}, end {}.";
        int first = 134334;
        Long second = 12312312412L;
        String end = "next end";
        long s = System.currentTimeMillis();
        String actual = Formatter.format(pattern1, first, second, end);
        long e = System.currentTimeMillis();
        long use = e - s;
        System.out.println("use " + use);
        String expected = "first 134334, second 12312312412, end next end.";
        Assert.assertEquals("format failed ", expected, actual);
    }
}
