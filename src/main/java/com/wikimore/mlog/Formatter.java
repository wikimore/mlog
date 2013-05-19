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
	 * @param pattern
	 *            message which have {} mark
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
