package com.rbc.nexgen.bcm.model;

import lombok.Data;

@Data
public class Service {
	private int id;
	private String name;
	private int rto;
	private int rpo;
	private String type;
}
