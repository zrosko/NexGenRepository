package com.rbc.nexgen.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.rbc.nexgen.batch.sqlserver.entity.IIPMApplication;

@Component
public class IIPMApplicationItemWriter implements ItemWriter<IIPMApplication> {

	@Override
	public void write(List<? extends IIPMApplication> items) throws Exception {
		//TODO here we may add SaaS API call
		System.out.println("Inside Application Item Writer");
		items.stream().forEach(System.out::println);
	}

}
