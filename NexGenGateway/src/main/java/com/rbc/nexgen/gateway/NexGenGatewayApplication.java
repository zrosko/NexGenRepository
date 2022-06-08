package com.rbc.nexgen.gateway;
//https://cloud.spring.io/spring-cloud-gateway/reference/html/#gateway-starter
//https://spring.io/guides/gs/gateway/
//https://www.baeldung.com/spring-security-configuring-urls
//https://github.com/spring-cloud-samples/spring-cloud-gateway-sample/blob/main/src/main/java/com/example/demogateway/DemogatewayApplication.java
/*
	When we add Spring Security to the project, it will disable access to all APIs by default. 
	So we'll need to configure Spring Security to allow access to the APIs.
 */
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
//TODO CircuitBreaker https://cloud.spring.io/spring-cloud-gateway/reference/html/#spring-cloud-circuitbreaker-filter-factory
	/*
	 * @RequestMapping("/circuitbreakerfallback") public String
	 * circuitbreakerfallback() { return "This is a fallback"; }
	 */	

	/*
	 * @Bean RedisRateLimiter redisRateLimiter() { return new RedisRateLimiter(1,
	 * 2); }
	 */
	//TODO see SecurityConfiguration class
	/*
	 * @Bean SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http)
	 * throws Exception { return http.httpBasic().and() .csrf().disable()
	 * .authorizeExchange() .pathMatchers("/anything/**").authenticated()
	 * .anyExchange().permitAll() .and() .build(); }
	 */

	//Add versions here
	//https://rapidapi.com/blog/how-to-build-an-api-with-java/
	//Can be donein application.properties (refresh only, no need to restart)
	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
	    return builder.routes()
    		.route(r -> r
    			.path("/v1/nexgen/ping/**")
    			.filters(f -> f.rewritePath("/v1/nexgen/ping/(?<segment>.*)","/${segment}")
    	            			.addResponseHeader("NexGen-Response-Time",new Date().toString()))
    			.uri("lb://PING"))
	    	.route(r -> r
	            .path("/v1/nexgen/iipm/**")
	            .filters(f -> f.rewritePath("/v1/nexgen/iipm/(?<segment>.*)","/${segment}")
	            				.addResponseHeader("NexGen-Response-Time",new Date().toString()))
	            .uri("lb://IIPM"))
	        .route(r -> r
	            .path("/v1/nexgen/accounts/**")
	            .filters(f -> f.rewritePath("/v1/nexgen/accounts/(?<segment>.*)","/${segment}")
	            				.addResponseHeader("NexGen-Response-Time",new Date().toString()))
	            .uri("lb://ACCOUNTS"))
	        .route(r -> r
	            .path("/v2/nexgen/accounts/**")
	            .filters(f -> f.rewritePath("/v2/nexgen/accounts/(?<segment>.*)","/v2/${segment}")
	            				.addResponseHeader("NexGen-Response-Time",new Date().toString()))
		        .uri("lb://ACCOUNTS"))
	        .route(r -> r
	            .path("/v1/nexgen/loans/**")
	            .filters(f -> f.rewritePath("/v1/nexgen/loans/(?<segment>.*)","/${segment}")
	            		.addResponseHeader("NexGen-Response-Time",new Date().toString()))
	            .uri("lb://LOANS"))
	        .route(r -> r
	            .path("/v1/nexgen/cards/**")
	            .filters(f -> f.rewritePath("/v1/nexgen/cards/(?<segment>.*)","/${segment}")
	            		.addResponseHeader("NexGen-Response-Time",new Date().toString()))
	            .uri("lb://CARDS")).build();
	}
	public static void main(String[] args) {
		SpringApplication.run(NexGenGatewayApplication.class, args);
	}
}
