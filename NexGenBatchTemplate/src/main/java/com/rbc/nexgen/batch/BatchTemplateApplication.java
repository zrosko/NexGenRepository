package com.rbc.nexgen.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
/*
inputFile=C:\Programs\Spring\Workspaces\NexGenBatchTemplate\InputFiles\students.csv currentItemCount=10 maxItemCount=100
 //@RefreshScope
 */
@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
@EnableAsync
@ComponentScan({
	"com.rbc.nexgen.batch.config", 
	"com.rbc.nexgen.batch.listener", 
	"com.rbc.nexgen.batch.reader", 
	"com.rbc.nexgen.batch.processor", 
	"com.rbc.nexgen.batch.writer", 
	"com.rbc.nexgen.batch.listener"
})
public class BatchTemplateApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(BatchTemplateApplication.class);
		//application.setBannerMode(Banner.Mode.OFF);
		application.setBanner(new NexGenBanner());
		application.run(args);
	}

}
