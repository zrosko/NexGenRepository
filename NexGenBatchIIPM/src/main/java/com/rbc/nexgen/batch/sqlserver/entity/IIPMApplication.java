package com.rbc.nexgen.batch.sqlserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "application")
public class IIPMApplication {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "app_code")
	private String appCode;
	private String l5;
	
	@Column(name = "app_custodian")
	private String appCustodian;
	
	@Column(name = "business_criticality")
	private String businessCriticality;
	
	@Column(name = "bu_exec_sponsor")
	private String buExecSponsor;
	
	@Column(name = "business_app_sponsor")
	private String businessAppSponsor;
	
	@Column(name = "app_coordinator")
	private String appCoordinator;
	
	@Column(name = "recovery_point_bjective")
	private String recoveryPointObjective;
	
	@Column(name = "recovery_time_objective")
	private String recoveryTimeObjective;
	
	@Column(name = "recovery_time_capability")
	private String recoveryTimeCapability;
	
	@Column(name = "deployment_style")
	private String deploymentStyle;
	
	@Column(name = "secondary_data_centers")
	private String secondaryDataCenters;
	
	@Column(name = "vendor_managed")
	private String vendorManaged;
}
