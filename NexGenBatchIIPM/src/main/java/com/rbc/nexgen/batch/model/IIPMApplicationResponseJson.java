package com.rbc.nexgen.batch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IIPMApplicationResponseJson {

	@JsonProperty("_appCode")
	private String appCode;
	
	@JsonProperty("_l5")
	private String l5;

	@JsonProperty("_appCustodian")
	private String appCustodian;
	
	@JsonProperty("_businessCriticality")
	private String businessCriticality;
	
	@JsonProperty("_buExecSponsor")
	private String buExecSponsor;
	
	@JsonProperty("_businessAppSponsor")
	private String businessAppSponsor;
	
	@JsonProperty("_appCoordinator")
	private String appCoordinator;
	
	@JsonProperty("_recoveryPointObjective")
	private String recoveryPointObjective;
	
	@JsonProperty("_recoveryTimeObjective")
	private String recoveryTimeObjective;
	
	@JsonProperty("_recoveryTimeCapability")
	private String recoveryTimeCapability;
	
	@JsonProperty("_deploymentStyle")
	private String deploymentStyle;
	
	@JsonProperty("_secondaryDataCenters")
	private String secondaryDataCenters;
	
	@JsonProperty("_vendorManaged")
	private String vendorManaged;
}
