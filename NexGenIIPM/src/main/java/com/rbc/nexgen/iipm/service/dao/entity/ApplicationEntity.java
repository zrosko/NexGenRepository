package com.rbc.nexgen.iipm.service.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name="application")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ApplicationEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String appCode;
	@NotBlank(message="Name is mandatory")
	@Length(min=1, max=30, message="Name min is 1 and max is 30")
	private String appName;
	private int rto;
	private int rpo;
	private int productionDate;
	private String owner;
	private String appType;
	@Transient
	private String updateTime;
}
