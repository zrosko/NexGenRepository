package com.rbc.nexgen.batch.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

/**
 * Visit http://cronmaker.com to calculate a cron schedule.
 */
@Service
@Log4j2
public class JobScheduler {

	@Value("${currentItemCount}")
	private String currentItemCount = "0";
	
	@Value("${maxItemCount}")
	private String maxItemCount = "1000000000";

	@Autowired JobLauncher jobLauncher;
	@Qualifier("iipmJob")

	@Autowired Job iipmJob;
	
	//@Scheduled(cron = "0 0/1 * 1/1 * ?")
	@Scheduled(cron = "${nexgen.schedule:0 0 23 ? * MON-FRI *}")
	public void jobStarterScheduler() {

		Map<String, JobParameter> params = new HashMap<>();
		params.put("currentTime", new JobParameter(System.currentTimeMillis()));
		params.put("outputFile", new JobParameter("applications.json"));
		params.put("currentItemCount", new JobParameter(currentItemCount));
		params.put("maxItemCount", new JobParameter(maxItemCount));
		JobParameters jobParameters = new JobParameters(params);

		try {
			JobExecution jobExecution = jobLauncher.run(iipmJob, jobParameters);
			log.info("Job Execution ID = " + jobExecution.getId());
		} catch (Exception e) {
			log.error("Exception while starting job: " + e);
		}		 
	}
}