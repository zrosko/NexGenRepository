package com.rbc.nexgen.template.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloMessage {
	@Id
	private int id;
	private String subject;
	private String body;		
}
