package com.rbc.nexgen.template.service;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
/*
 * Use cronmaker.com to generat cron schedule, copy the schedule to config file.
 */
@Service
public class NexGenScheduler {
	//Default is every weekday at 11pm (if not defined in property file)
	@Scheduled(cron = "${nexgen.schedule:0 0 23 ? * MON-FRI *}")
	public void firstScheduler () {
		System.out.println("*** Here we run the Scheduler at time specified at = "+ "${nexgen.schedule} " +new Date());
	}
}
