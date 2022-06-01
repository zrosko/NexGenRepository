package com.rbc.nexgen.bcm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;

@SpringBootApplication
public class NexGenBcmServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NexGenBcmServiceApplication.class, args);
	}
/*	@Bean
	public NewTopics topic() {
		return new NewTopics(
				TopicBuilder.name("bcm_service_source_topic").build(),
				TopicBuilder.name("bcm_service_sink_topic").build());
	}*/
}
