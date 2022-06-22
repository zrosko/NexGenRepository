package com.rbc.nexgen.gateway.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Order(1)
@Component
@Slf4j
public class RequestTraceFilter implements GlobalFilter {

	@Autowired
	FilterUtility filterUtility;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
		if (isTraceIdPresent(requestHeaders)) {
			log.debug("Nexgen-trace-id found in tracing filter: {}. ",
					filterUtility.getTraceId(requestHeaders));
		} else {
			String traceID = generateTraceId();
			exchange = filterUtility.setTraceId(exchange, traceID);
			log.debug("Nexgen-trace-id generated in tracing filter: {}.", traceID);
		}
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			log.info("*** POST processing logic goes here");
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