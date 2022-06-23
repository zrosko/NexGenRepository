package com.rbc.nexgen.template.service;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
/*
 * Use cronmaker.com to generat cron schedule, copy the schedule to config file.
 */
@Service
public class NexGenScheduler {

	@Scheduled(cron = "${nexgen.schedule}")
	public void firstScheduler () {
		System.out.println("*** Here we run the Scheduler at time specified at = "+ "${nexgen.schedule} " +new Date());
	}
}
