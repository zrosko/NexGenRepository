package com.rbc.nexgen.batch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IIPMApplicationResponse {

	private String appCode;
	private String appCustodian;
	private String businessCriticality;
}
