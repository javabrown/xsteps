package com.jbrown.util;

import java.util.Formatter;

import org.apache.log4j.Logger;

public class BrownLogger {
	public static Logger _logger = Logger.getLogger(BrownLogger.class);
	
	public static void log(String logMessage){
		_logger.debug(logMessage);
	}
	
	public static void logf(String pattern, Object... message){
		_logger.debug(String.format(pattern, message));
	}
}
