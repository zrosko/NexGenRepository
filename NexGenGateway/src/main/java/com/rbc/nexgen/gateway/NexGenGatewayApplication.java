package com.rbc.nexgen.gateway;
//https://cloud.spring.io/spring-cloud-gateway/reference/html/#gateway-starter
//https://spring.io/guides/gs/gateway/
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class NexGenGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(NexGenGatewayApplication.class, args);
	}
	//Add versions here
	//https://rapidapi.com/blog/how-to-build-an-api-with-java/
	//Can be donein application.properties (refresh only, no need to restart)
	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
	    return builder.routes()
	        .route(p -> p
	            .path("/v1/nexgen/accounts/**")
	            .filters(f -> f.rewritePath("/v1/nexgen/accounts/(?<segment>.*)","/${segment}")
	            				.addResponseHeader("NexGen-Response-Time",new Date().toString()))
	            .uri("lb://ACCOUNTS")).
	        route(p -> p
		            .path("/v2/nexgen/accounts/**")
		            .filters(f -> f.rewritePath("/v2/nexgen/accounts/(?<segment>.*)","/v2/${segment}")
		            				.addResponseHeader("NexGen-Response-Time",new Date().toString()))
		            .uri("lb://ACCOUNTS")).
	        route(p -> p
		            .path("/v1/nexgen/loans/**")
		            .filters(f -> f.rewritePath("/v1/nexgen/loans/(?<segment>.*)","/${segment}")
		            		.addResponseHeader("NexGen-Response-Time",new Date().toString()))
		            .uri("lb://LOANS")).
	        route(p -> p
		            .path("/v1/nexgen/cards/**")
		            .filters(f -> f.rewritePath("/v1/nexgen/cards/(?<segment>.*)","/${segment}")
		            		.addResponseHeader("NexGen-Response-Time",new Date().toString()))
		            .uri("lb://CARDS")).build();
	}
}
