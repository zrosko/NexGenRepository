package com.rbc.nexgen.bcm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;

@SpringBootApplication
@EnableEurekaClient
@RefreshScope
public class NexGenBCM {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(NexGenBCM.class);
		//application.setBannerMode(Banner.Mode.OFF);
		//application.setWebApplicationType(WebApplicationType.NONE);
		application.setBanner(new NexGenBanner());
		application.run(args);
	}
	//TODO
	@Bean
	public NewTopics topic() {
		return new NewTopics(
				TopicBuilder.name("bcm_service_source_topic").build(),
				TopicBuilder.name("bcm_service_sink_topic").build());
	}
}
