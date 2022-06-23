package com.rbc.nexgen.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//cronmaker.com
@EnableEurekaClient
@EnableFeignClients("com.rbc.nexgen.template.service.client")
@RefreshScope
@ComponentScans({ @ComponentScan("com.rbc.nexgen.template.restcontrollers")})
@EnableHystrix
@EnableHystrixDashboard
//@EnableTask
public class ServiceTemplateApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ServiceTemplateApplication.class);
		//application.setBannerMode(Banner.Mode.OFF);
		application.setBanner(new NexGenBanner());
		application.run(args);
	}

}
