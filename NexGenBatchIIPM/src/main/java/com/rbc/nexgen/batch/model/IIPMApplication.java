package com.rbc.nexgen.batch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IIPMApplication {
	private String appCode;
	private String l5;
	private String appCustodian;
	private String businessCriticality;
	private String buExecSponsor;
	private String businessAppSponsor;
	private String appCoordinator;
	private String recoveryPointObjective;
	private String recoveryTimeObjective;
	private String recoveryTimeCapability;
	private String deploymentStyle;
	private String secondaryDataCenters;
	private String vendorManaged;
}
