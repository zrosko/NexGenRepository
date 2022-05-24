package com.rbc.nexgen.iipm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationConsumerService {
	Logger logger = LoggerFactory.getLogger(ApplicationConsumerService.class);

	@KafkaListener(topics = { "iipm_applicaton_sink_topic" })
	@Transactional
	public void consumeUserData(User user) {
		logger.info("1. Inside consumeUserData of ConsumerService" + user.toString());
	}
}
