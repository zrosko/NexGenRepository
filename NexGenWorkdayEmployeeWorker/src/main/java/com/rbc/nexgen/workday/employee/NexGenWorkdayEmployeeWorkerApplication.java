package com.rbc.nexgen.workday.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//TODO @EnableFeignClient("")
public class NexGenWorkdayEmployeeWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NexGenWorkdayEmployeeWorkerApplication.class, args);
	}

}
