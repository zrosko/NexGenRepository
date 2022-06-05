package com.rbc.nexgen.gateway;

//https://www.concretepage.com/spring-boot/spring-boot-custom-banner-example
import java.io.PrintStream;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

public class NexGenBanner implements Banner {

	@Override
	public void printBanner(Environment env, Class<?> sourceClass, PrintStream out) {
		out.println("=================================================================");
		out.println("-------   RBC NexGen Common Data Integration Layer, 2022");
		out.println("-------   Active profile   : " + env.getProperty("spring.profiles.active"));
		out.println("-------   Application name : " + env.getProperty("spring.application.name"));
		//out.println("-------   Server port      :   " + env.getProperty("server.port"));
		out.println("=================================================================");
	}
}