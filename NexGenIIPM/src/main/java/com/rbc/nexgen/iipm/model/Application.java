package com.rbc.nexgen.iipm.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Application {
	private int id;
	private String appCode;
	@NotBlank(message="Name is mandatory")
	private String appName;
	private int rto;
	private int rpo;
	private int productionDate;
	private String owner;
	private String appType;
	private String updateTime;
}
