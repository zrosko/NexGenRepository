package com.rbc.nexgen.iipm.model;

import lombok.Data;

@Data
public class Location {
	private int id;
	private String name;
	private String address;
	private String city;
	private String zipCode;
	private String state;
}
