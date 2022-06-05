package com.rbc.nexgen.archer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class NexGenArcherMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(NexGenArcherMasterApplication.class, args);
	}

}
