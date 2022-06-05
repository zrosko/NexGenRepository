package com.rbc.nexgen.bcm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rbc.nexgen.bcm.config.NexGenPingConfig;
import com.rbc.nexgen.bcm.model.NexGenProperties;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
@Retry(name="hello-retry", fallbackMethod = "handleError")
//@RefreshScope
public class PingController {
	
	@Autowired
	NexGenPingConfig pingConfig;
	
	@Value("${ping.first}")
	private String test_property = "9000";
	
	@GetMapping("/ping")
	public String pingService() throws Exception {
		System.out.println(" *** HelloWorldController.pingService invoked *** ");
		return "PingController.pingService invoked - OK";
	}
	
	@GetMapping("/hello")
	public String sayHello() throws Exception {
		System.out.println("*** Here is the ping.first="+test_property);
		//if(true) throw new Exception();
		return "PingController.sayHello inovoked - OK";
	}
	
	@GetMapping("/port")
	public String getPort() throws Exception {
		System.out.println("*** Here is the ping.first: "+test_property);
		//if(true) throw new Exception();
		return "PingController.getPort inovoked - OK";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestBody String user) throws Exception {
		System.out.println("*** Here is the ping.first="+test_property);
		System.out.println("Save user="+user);
		//if(true) throw new Exception();
		return "PingController.save inovoked - OK";
	}
	@GetMapping("/ping/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		NexGenProperties properties = new NexGenProperties(
				pingConfig.getFirst(), 
				pingConfig.getSecond(),
				pingConfig.getThird(),
				pingConfig.getForth(),
				pingConfig.getMailDetails(), 
				pingConfig.getActiveBranches());
		String jsonStr = ow.writeValueAsString(properties);
		return jsonStr;
	}
	//will get executed after retry
	public String handleError(Exception exception) {
		System.out.println("PingController.handleError");
		return "Exception, when saving a user";
	}
}