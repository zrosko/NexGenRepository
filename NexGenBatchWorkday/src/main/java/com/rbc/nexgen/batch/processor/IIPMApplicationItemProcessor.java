package com.rbc.nexgen.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.rbc.nexgen.batch.model.IIPMApplicationResponseJson;
import com.rbc.nexgen.batch.sqlserver.entity.IIPMApplication;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class IIPMApplicationItemProcessor implements ItemProcessor<IIPMApplicationResponseJson, IIPMApplication> {

	@Override
	public IIPMApplication process(IIPMApplicationResponseJson item) throws Exception {
		
		log.info("NexGen we are processing item #: " + item.getAppCode());
		
		IIPMApplication app = new IIPMApplication();
		
		app.setAppCode(item.getAppCode());
		app.setL5(item.getL5());
		app.setAppCustodian(item.getAppCustodian());
		app.setBusinessCriticality(item.getBusinessCriticality());
		app.setBuExecSponsor(item.getBuExecSponsor());
		app.setBusinessAppSponsor(item.getBusinessAppSponsor());
		app.setAppCoordinator(item.getAppCoordinator());		
		app.setRecoveryPointObjective(item.getRecoveryPointObjective());
		app.setRecoveryTimeObjective(item.getRecoveryTimeObjective());
		app.setRecoveryTimeCapability(item.getRecoveryTimeCapability());		
		app.setDeploymentStyle(item.getDeploymentStyle());
		app.setSecondaryDataCenters(item.getSecondaryDataCenters());
		app.setVendorManaged(item.getVendorManaged());
		return app;
	}
}
