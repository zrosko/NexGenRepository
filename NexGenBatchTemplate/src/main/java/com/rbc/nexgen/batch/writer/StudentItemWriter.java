package com.rbc.nexgen.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.rbc.nexgen.batch.model.StudentJson;

@Component
public class StudentItemWriter implements ItemWriter<StudentJson> {

	@Override
	public void write(List<? extends StudentJson> items) throws Exception {
		System.out.println("Inside Student Item Writer");
		items.stream().forEach(System.out::println);
	}

}
