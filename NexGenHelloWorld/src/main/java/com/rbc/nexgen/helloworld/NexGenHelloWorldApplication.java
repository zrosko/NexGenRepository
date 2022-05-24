package com.rbc.nexgen.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@RefreshScope
public class NexGenHelloWorldApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(NexGenHelloWorldApplication.class);
		//application.setBannerMode(Banner.Mode.OFF);
		//https://www.baeldung.com/spring-boot-no-web-server
		//application.setWebApplicationType(WebApplicationType.NONE);
		application.setBanner(new NexGenBanner());
		application.run(args);
	}

}
