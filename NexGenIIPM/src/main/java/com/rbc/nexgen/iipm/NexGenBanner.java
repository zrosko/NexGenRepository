package com.rbc.nexgen.iipm;

//https://www.concretepage.com/spring-boot/spring-boot-custom-banner-example
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import com.rbc.nexgen.iipm.model.Application;

public class NexGenBanner implements Banner {

	@Override
	public void printBanner(Environment env, Class<?> sourceClass, PrintStream out) {
		out.println("=================================================================");
		out.println("-------   RBC NexGen Common Data Integration Layer, 2022");
		out.println("-------   Active profile   : " + env.getProperty("spring.profiles.active"));
		out.println("-------   Application name : " + env.getProperty("spring.application.name"));
		// out.println("------- Server port : " + env.getProperty("server.port"));
		out.println("=================================================================");
	}
	public static void main(String[] args) {
		test();
	}
	public static void test() {
		Application a = new Application();
		a.setAppCode("AAPL");
		a.setAppName("Testa Name");
		LinkedHashMap<String, String> _meta_data = new LinkedHashMap<String, String>();
		// Find all fields (name, type)
		Field[] fieldList = a.getClass().getDeclaredFields();
		for (Field name : fieldList) {
			_meta_data.put(name.getName(), name.getType().getCanonicalName());
		}
		System.out.println(_meta_data);
		for (String name : _meta_data.keySet()) {
			if (_meta_data.get(name).equals("java.util.Date")) {
				System.out.println("date");
			} else if (_meta_data.get(name).equals("java.lang.String")) {
				System.out.println("String");
			} else if (_meta_data.get(name).equals("double")) {
				System.out.println("double");
			} else if (_meta_data.get(name).equals("int")) {
				System.out.println("int");
			} else if (_meta_data.get(name).equals("boolean")) {
				System.out.println("boolean");
			}
		}
	}
}