package com.rbc.nexgen.batch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rbc.nexgen.batch.request.JobParamsRequest;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class IIPMJobService {
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Qualifier("iipmJob")
	@Autowired
	Job iipmJob;
	
	@Async
	public void startIIPMJob(String jobName, List<JobParamsRequest> jobParamsRequestList) {
		Map<String, JobParameter> params = new HashMap<>();
		params.put("currentTime", new JobParameter(System.currentTimeMillis()));
		
		jobParamsRequestList.stream().forEach(jobPramReq -> {
			params.put(jobPramReq.getParamKey(), 
					new JobParameter(jobPramReq.getParamValue()));
		});
		
		JobParameters jobParameters = new JobParameters(params);
		
		try {
			JobExecution jobExecution = null;
			if(jobName.equals("IIPM Job")) {
				jobExecution = jobLauncher.run(iipmJob, jobParameters);
			}
			log.info("**** IIPM - Job Execution ID = " + jobExecution.getId());
		}catch(Exception e) {
			log.error("**** IIPM - Job Execution FAILED: "+e);
		}
	}
}
