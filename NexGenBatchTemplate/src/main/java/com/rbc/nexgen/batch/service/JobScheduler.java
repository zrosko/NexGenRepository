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

import com.rbc.nexgen.batch.model.IIPMApplication;
import com.rbc.nexgen.batch.reader.GenericJsonObjectReader;

import lombok.extern.log4j.Log4j2;

//cronmaker.com
@Service
@Log4j2
public class JobScheduler {
	@Autowired /* REST API */
	private StudentService studentService;
	
	@Autowired /* TODO REST API */
	GenericJsonObjectReader<IIPMApplication> applicationService;
	
	@Value("${outputFile}")
	private String outputFile = "hardcoded.csv";
	
	@Value("${currentItemCount}")
	private String currentItemCount = "0";
	
	@Value("${maxItemCount}")
	private String maxItemCount = "1000000000";
	/*
	 * @Autowired JobLauncher jobLauncher;
	 */
	/*
	 * @Qualifier("defaultJob")
	 * 
	 * @Autowired Job defaultJob;
	 */
	
	//@Scheduled(cron = "0 0/1 * 1/1 * ?")
	//@Scheduled(cron = "${nexgen.schedule:0 0 23 ? * MON-FRI *}")
	public void jobStarterScheduler() {
		
		//try {
/*		GenericJsonObjectReader<ApplicationJson> reader = 
				new GenericJsonObjectReader<ApplicationJson>(ApplicationJson.class, "AppDocumentRest");
		jsonService = reader;
		reader.open(new FileSystemResource("C:\\Users\\zrosk\\git\\NexGenRepository\\NexGenBatchTemplate\\InputFiles\\applications.json"));
		}catch(Exception e) {
			System.out.println(e);
		}*/
		//TODO externalize the parameters
		Map<String, JobParameter> params = new HashMap<>();
		params.put("currentTime", new JobParameter(System.currentTimeMillis()));
		params.put("outputFile", new JobParameter(outputFile));
		params.put("currentItemCount", new JobParameter(currentItemCount));
		params.put("maxItemCount", new JobParameter(maxItemCount));		
		JobParameters jobParameters = new JobParameters(params);		

		//TODO this is temp code (quick fix for NOT NULL when reading 1 by 1)
		//TODO see the GenericJsonObjectReader ALSO
		
		/*
		 * try { JobExecution jobExecution = jobLauncher.run(defaultJob, jobParameters);
		 * log.info("Job Execution ID = " + jobExecution.getId()); }catch(Exception e) {
		 * log.info("Exception while starting job"); }
		 */
	}
}