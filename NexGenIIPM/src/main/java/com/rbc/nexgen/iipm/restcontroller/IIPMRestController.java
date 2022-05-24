package com.rbc.nexgen.iipm.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbc.nexgen.iipm.model.User;
import com.rbc.nexgen.iipm.service.ApplicationProducerService;
import com.rbc.nexgen.iipm.service.UserProducerService;
import com.rbc.nexgen.iipm.service.dao.entity.ApplicationEntity;

import lombok.extern.slf4j.Slf4j;

@RestController
@RefreshScope
@Slf4j
@CrossOrigin
@RequestMapping("/iipmapi")
public class IIPMRestController {

	@Autowired
	ApplicationProducerService applicationService;
	
	@Autowired
	UserProducerService userService;
	
	@GetMapping("/ping")
	public String pingService() throws Exception {
		log.info(" *** IIPMRestController.pingService invoked *** ");
		return "IIPMRestController.pingService invoked - OK";
	}
	
	@PostMapping("/application/v1/create")
	public String createApplication(@RequestBody ApplicationEntity application) {
		log.info("*** IIPMRestController.createApplication ***");
		applicationService.processApplication(application);
		return "Application created";
	}
	
	@PostMapping("/user/v1/create")
	public String createUser(@RequestBody User user) {
		log.info("*** IIPMRestController.createUser ***");
		userService.processUser(user);
		return "User created";
	}
}
