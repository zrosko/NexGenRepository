package com.rbc.nexgen.bcm.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class NexGenBcmEmployeeWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NexGenBcmEmployeeWorkerApplication.class, args);
	}

}
