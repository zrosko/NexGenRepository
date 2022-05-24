package com.rbc.nexgen.bcm.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rbc.nexgen.bcm.model.User;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserConsumerService {

	@KafkaListener(topics = { "iipm_user_sink_topic" })
	@Transactional
	public void consumeUserData(User user) {
		log.info("1. Inside consumeUserData of ConsumerService - invoke WORKER" + user.toString());
	}
}
