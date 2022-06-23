package com.rbc.nexgen.template.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "template")
@Data
public class TemplateConfig {

	private String first;
	private String second;
	private Map<String, String> mailDetails;
	private List<String> activeBranches;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
