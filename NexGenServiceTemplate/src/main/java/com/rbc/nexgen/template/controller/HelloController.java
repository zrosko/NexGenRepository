package com.rbc.nexgen.template.controller;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rbc.nexgen.template.config.TemplateConfig;
import com.rbc.nexgen.template.dao.HelloMessageRepository;
import com.rbc.nexgen.template.model.HelloMessage;
import com.rbc.nexgen.template.service.TemplateService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.log4j.Log4j2;
//TODO trace id
//@Retry(name="hello-retry", fallbackMethod = "handleError")
@RefreshScope
@RestController
@Log4j2
public class HelloController {
	
	@Autowired
	TemplateConfig templateConfig;
	
	@Autowired
	TemplateService templateService;
	
	@Autowired
	private HelloMessageRepository helloMessageRepository;
	
	//When retrieving variable from application.property
	@Value("${template.first}")
	private String template_property_var = "Default";
	
	//When retrieving variable from System Environment
	@Value("${template.first}")
	private String template_environment_var = "Default";
	
	@GetMapping("/hello")
	@CircuitBreaker(name = "helloCircuitBreaker",fallbackMethod ="helloSoryService")
	@Retry(name = "helloRetry", fallbackMethod = "handleError")
	/**
	 * This is test documentation.
	 * @return
	 * @throws Exception
	 */
	public String helloService() throws Exception {
		System.out.println(" *** HelloController.helloService invoked, properties = "+templateConfig);
		HelloMessage message = new HelloMessage();
		message.setId(100);
		message.setSubject("Hello subject");
		message.setBody("Hello message body here");
		
		HelloMessage message_returned = helloMessageRepository.findById(1);
		templateService.processHelloMessage(message_returned);
		return "HelloController.helloService invoked - OK";
	}
	
	@GetMapping("/hello-sorry")
	private String helloSoryService(Throwable t) throws Exception {
		log.info(" *** HelloController.helloSoryService invoked *** "+t);
		return "HelloController.helloSoryService invoked - NOT OK";
	}
	
	@GetMapping("/hello2")
	public String helloService2() throws Exception {
		System.out.println("*** Hello2 is called: var="+template_property_var+" env="+template_environment_var);
		return "HelloController.helloService2 inovoked - OK";
	}
	
	@GetMapping("/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties();
		properties.setProperty("first", templateConfig.getFirst());
		properties.setProperty("second", templateConfig.getSecond()); 
		properties.setProperty("mailDetails", templateConfig.getMailDetails().toString()); 
		properties.setProperty("branches", templateConfig.getActiveBranches().toString()); 
		String jsonStr = ow.writeValueAsString(properties);
		return jsonStr;
	}
	//will get executed after retry
	public String handleError(Exception exception) {
		System.out.println("HelloController.handleError");
		return "Exception, when being called";
	}
	
	@GetMapping("/sayHello2")
	@RateLimiter(name = "sayHello2", fallbackMethod = "sayHelloFallback2")
	public String sayHello2() {
		return "Hello, Welcome to hello2 api.";
	}

	public String sayHelloFallback2(Throwable t) {
		return "Hi, sayHelloFallback2 OK";
	}
}