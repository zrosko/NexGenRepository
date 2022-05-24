package com.rbc.nexgen.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

	private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);
	
	@Autowired
	FilterUtility filterUtility;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
		if (isTraceIdPresent(requestHeaders)) {
			logger.debug("Nexgen-trace-id found in tracing filter: {}. ",
					filterUtility.getTraceId(requestHeaders));
		} else {
			String traceID = generateTraceId();
			exchange = filterUtility.setTraceId(exchange, traceID);
			logger.debug("Nexgen-trace-id generated in tracing filter: {}.", traceID);
		}
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			System.out.println("*** POST processing logic goes here");
		}));
	}

	private boolean isTraceIdPresent(HttpHeaders requestHeaders) {
		if (filterUtility.getTraceId(requestHeaders) != null) {
			return true;
		} else {
			return false;
		}
	}

	private String generateTraceId() {
		return java.util.UUID.randomUUID().toString();
	}
}