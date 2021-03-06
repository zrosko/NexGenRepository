package com.rbc.nexgen.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.rbc.nexgen.batch.model.StudentResponse;

@Component
public class FirstItemWriter implements ItemWriter<StudentResponse> {

	@Override
	public void write(List<? extends StudentResponse> items) throws Exception {
		System.out.println("Inside First Item Writer");
		items.stream().forEach(System.out::println);
	}

}
