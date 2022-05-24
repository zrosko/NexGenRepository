package com.rbc.nexgen.helloworld.restcontroller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rbc.nexgen.helloworld.model.User;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@RestController
@RefreshScope
@Slf4j
public class HelloWorldController {
	
	@Value("${server.port}")
	private String PORT = "1111";
	
	@GetMapping("/ping")
	public String pingService() throws Exception {
		log.info(" *** HelloWorldController.pingService invoked *** ");
		return "HelloWorldController.pingService invoked - OK";
	}
	
	@GetMapping("/hello")
	public String sayHello() throws Exception {
		log.info("*** Here is the PORT="+PORT);
		//if(true) throw new Exception();
		return "Hello, Welcome to NexGen Kubernetes cluster";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@Retry(name="hello-retry", fallbackMethod = "handleError")
	public String save(@RequestBody User user) {
		log.info("*** Here is the PORT="+PORT);
		System.out.println("Save user="+user);
		return "Hello, Welcome to NexGen Kubernetes cluster";
	}
	//will get executed after retry
	public String handleError(User user, Exception exception) {
		return "Exception, when saving a user";
	}
}