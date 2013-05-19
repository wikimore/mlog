/*
 * 文件名称: Formatter.java Copyright 2011-2013 Nali All right reserved.
 */
package com.wikimore.mlog;

/**
 * format pattern message for Log
 * <p>
 * formatter repleace {} mark to specified position of the objects array
 * 
 * @author ted created on 2013-5-17
 * @since 1.0
 */
public class Formatter {
    public static final String PLACEHOLDER = "{}";

    /**
     * format pattern message with objects
     * 
     * @param pattern message which have {} mark
     * @param objects
     * @return
     */
    public static String format(String pattern, Object... objects) {
        if (objects == null || objects.length == 0) {
            return pattern;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int index = 0;
        int start = 0;
        for (Object object : objects) {
            index = pattern.indexOf(PLACEHOLDER, start);
            stringBuilder.append(pattern.substring(start, index));
            stringBuilder.append(object);
            start = index + 2;
        }
        stringBuilder.append(pattern.substring(start, pattern.length()));
        return stringBuilder.toString();
    }
}
