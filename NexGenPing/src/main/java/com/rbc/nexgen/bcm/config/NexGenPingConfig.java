package com.rbc.nexgen.bcm.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "ping")
@Data
public class NexGenPingConfig {

	 private String first;
	 private String second;
	 private String third;
	 private String forth;
	 private Map<String, String> mailDetails;
	 private List<String> activeBranches;

}
