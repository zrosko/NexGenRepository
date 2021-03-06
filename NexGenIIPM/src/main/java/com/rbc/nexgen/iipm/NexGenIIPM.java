package com.rbc.nexgen.iipm;

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
public class NexGenIIPM {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(NexGenIIPM.class);
		//application.setBannerMode(Banner.Mode.OFF);
		//application.setWebApplicationType(WebApplicationType.NONE);
		application.setBanner(new NexGenBanner());
		application.run(args);
	}
	@Bean
	public NewTopics topic() {
		return new NewTopics(
				TopicBuilder.name("iipm_applicaton_source_topic").build(),
				TopicBuilder.name("iipm_applicaton_sink_topic").replicas(1).build(),
				TopicBuilder.name("iipm_user_source_topic").build(),
				TopicBuilder.name("iipm_user_sink_topic").build());
				//TopicBuilder.name("iipm_transaction_topic").partitions(3).build());
	}
}
