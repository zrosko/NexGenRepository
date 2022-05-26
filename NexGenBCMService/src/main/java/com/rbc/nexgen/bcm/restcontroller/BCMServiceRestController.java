package com.rbc.nexgen.bcm.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbc.nexgen.bcm.model.ServiceModel;
import com.rbc.nexgen.bcm.service.ServiceProducerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RefreshScope
@Slf4j
@CrossOrigin
@RequestMapping("/bcmserviceapi")
public class BCMServiceRestController {
	
	@Autowired
	ServiceProducerService serviceService;
	
	@GetMapping("/ping")
	public String pingService() throws Exception {
		log.info(" *** BCMServiceRestController.pingService invoked *** ");
		return "BCMServiceRestController.pingService invoked - OK";
	}
	
	@PostMapping("/service/v1/pull")
	public String createServices(@RequestBody ServiceModel service) {
		log.info("*** BCMServiceRestController.createServices ***");
		serviceService.processService(service);
		return "Services pulled from BCM";
	}
	
	@PostMapping("/service/v1/invoke")
	public String invokeService(@RequestBody ServiceModel service) {
		log.info("*** BCMServiceRestController.invokeService ***");
		//TODO call BCM Solution (Castellan/Premier) API
		return "Service pushed to BCM";
	}
}
