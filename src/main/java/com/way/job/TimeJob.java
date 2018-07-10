package com.way.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeJob {
	
	private static final Logger logger = LoggerFactory.getLogger(TimeJob.class);
	
	public void logByTime(){
		logger.debug("定时日志");
	}
	
}
