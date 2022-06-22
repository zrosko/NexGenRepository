package com.rbc.nexgen.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
// PATTERN: https://microservices.io/patterns/server-side-discovery.html
@SpringBootApplication
@EnableEurekaServer
public class NexGenEurekaApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(NexGenEurekaApplication.class);
		//application.setBannerMode(Banner.Mode.OFF);
		application.setBanner(new NexGenBanner());
		application.run(args);
	}

}
