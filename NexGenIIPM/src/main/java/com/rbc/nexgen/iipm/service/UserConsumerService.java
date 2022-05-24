package com.rbc.nexgen.iipm.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbc.nexgen.iipm.model.User;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserConsumerService {

	@KafkaListener(topics = { "iipm_user_sink_topic" })
	@Transactional
	public void consumeUserData(User user) {
		log.info("1. Inside consumeUserData of ConsumerService" + user.toString());
	}
}
