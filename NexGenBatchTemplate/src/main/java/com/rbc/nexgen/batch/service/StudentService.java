package com.rbc.nexgen.batch.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rbc.nexgen.batch.model.StudentCsv;
import com.rbc.nexgen.batch.model.StudentResponse;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class StudentService {

	List<StudentResponse> list;
	
	/**
	 * @return the list
	 */
	public List<StudentResponse> getList() {
		return list;
	}

	/**
	 * When Scheduler calls the REST API to get data, "list" has to be set to NULL.
	 * @param list the list to set
	 */
	public void setList(List<StudentResponse> list) {
		this.list = list;
	}

	public List<StudentResponse> restCallToGetStudents() {
		RestTemplate restTemplate = new RestTemplate();
		StudentResponse[] studentResponseArray = restTemplate.getForObject("http://localhost:8081/api/v1/students",
				StudentResponse[].class);

		list = new ArrayList<>();

		for (StudentResponse sr : studentResponseArray) {
			list.add(sr);
		}

		return list;
	}

	public StudentResponse getStudent(long id, String name) {
		log.info("We are reading 1 by 1 here - id = " + id + " and name = " + name);
		if (list == null) {
			restCallToGetStudents();
		}

		if (list != null && !list.isEmpty()) {
			return list.remove(0);
		}
		return null;
	}

	public StudentResponse restCallToCreateStudent(StudentCsv studentCsv) {
		RestTemplate restTemplate = new RestTemplate();

		return restTemplate.postForObject("http://localhost:8081/api/v1/createStudent", studentCsv,
				StudentResponse.class);
	}
}
