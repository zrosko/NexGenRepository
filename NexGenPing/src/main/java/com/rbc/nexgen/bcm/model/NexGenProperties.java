package com.rbc.nexgen.bcm.model;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class NexGenProperties {

	private String first;
	private String second;
	private String third;
	private String forth;
	private Map<String, String> mailDetails;
	private List<String> activeBranches;

	public NexGenProperties(String first, String second, String third, String forth, Map<String, String> mailDetails, List<String> activeBranches) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.forth = forth;
		this.mailDetails = mailDetails;
		this.activeBranches = activeBranches;
	}
}
