package com.rbc.nexgen.batch.service.TEST;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.client.RestTemplate;

import com.rbc.nexgen.batch.reader.GenericJsonObjectReader;

public class RestMain {

	public static void main(String[] args) {
		getData();
		
		
	}
	
private static void getData()  {
	try {
		byte[] ret = new RestTemplate().getForObject("https://images-api.nasa.gov/search?q=clouds", byte[].class);

		GenericJsonObjectReader<Item> reader = new GenericJsonObjectReader<Item>(Item.class, "items");		
		reader.open(new ByteArrayResource(ret));
		List<Object> ret_list = reader.getObjectsList();
		 for(Object one:ret_list){  
		     System.out.println(one);  
		 }  
	}catch(Exception e) {
		e.printStackTrace();
	}
}
}
