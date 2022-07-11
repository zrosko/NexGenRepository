package com.rbc.nexgen.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.rbc.nexgen.batch.model.IIPMApplication;

@Component
public class ApplicationItemWriter implements ItemWriter<IIPMApplication> {

	@Override
	public void write(List<? extends IIPMApplication> items) throws Exception {
		System.out.println("Inside Application Item Writer");
		items.stream().forEach(System.out::println);
	}

}
