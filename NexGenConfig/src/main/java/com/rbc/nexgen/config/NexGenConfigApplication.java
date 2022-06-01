package com.rbc.nexgen.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class NexGenConfigApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(NexGenConfigApplication.class);
		//application.setBannerMode(Banner.Mode.OFF);
		application.setBanner(new NexGenBanner());
		application.run(args);
	}

}
