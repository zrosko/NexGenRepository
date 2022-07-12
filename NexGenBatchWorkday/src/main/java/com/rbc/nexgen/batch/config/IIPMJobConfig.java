package com.rbc.nexgen.batch.config;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import com.rbc.nexgen.batch.listener.StepExecutionListenerImpl;
import com.rbc.nexgen.batch.model.IIPMApplicationResponseJson;
import com.rbc.nexgen.batch.processor.IIPMApplicationItemProcessor;
import com.rbc.nexgen.batch.processor.IIPMApplicationValidator;
import com.rbc.nexgen.batch.service.IIPMService;
import com.rbc.nexgen.batch.sqlserver.entity.IIPMApplication;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
//@EnableTransactionManagement
public class IIPMJobConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired /* REST API */
	private IIPMService iipmApplicationService;
	
	@Autowired
	private IIPMApplicationItemProcessor iipmApplicationItemProcessor;
	
	@Autowired
	private StepExecutionListenerImpl stepExecutionListenerImpl;
	
	@Autowired
	private JpaTransactionManager jpaTransactionManager;
	
	@Autowired
	@Qualifier("datasource")
	private DataSource datasource;
	
	@Autowired
	@Qualifier("sqlserverdatasource") private DataSource sqlserverdatasource;
	
	@Autowired
	@Qualifier("sqlserverEntityManagerFactory")
	private EntityManagerFactory sqlserverEntityManagerFactory;
	
	@Value("${jobName}")
	private String jobName = "IIPM Job";
	
	@Bean
	public Job iipmJob() {
		log.info("*** JOB: "+jobName+" - starting.");
		return jobBuilderFactory.get(jobName)
				.incrementer(new RunIdIncrementer())
				//.start(Step_IIPM_API_to_SQL_Server())
				.start(Step_JPA_Test())
				.build();
	}
	/* *************************************************************** */
	/*                         STEPS 								   *
	 * TODO write JPA to insert to SQL database, TEST start as API     *
	 *     											    			   *
	 * *************************************************************** */
	
	private Step Step_IIPM_API_to_SQL_Server() {
		log.info("*** JOB: "+jobName+" - step: "+"Step_IIPM_API_to_SQL_Server");
		
		return stepBuilderFactory.get("Step_IIPM_API_to_SQL_Server")
				/* Here change chunk size for production */
				.<IIPMApplicationResponseJson, IIPMApplication>chunk(2)//TODO externalise
				/* Json input */
				.reader(itemReaderAdapterIIPMApplication())
				.listener(validatingItemProcessor())
				/* Json process*/
				.processor(iipmApplicationItemProcessor)
				/* Json output */
				.writer(jdbcBatchItemWriter())
				.listener(stepExecutionListenerImpl)
				.transactionManager(jpaTransactionManager)
				.build();
	}	

	private Step Step_JPA_Test() {
		log.info("*** JOB: "+jobName+" - step: "+"Step_JPA_Test");
		
		return stepBuilderFactory.get("Step_JPA_Test")
				/* Here change chunk size for production */
				.<IIPMApplicationResponseJson, IIPMApplication>chunk(2)//TODO externalise
				/* Json input */
				.reader(itemReaderAdapterIIPMApplication())
				/* Json process*/
				.processor(iipmApplicationItemProcessor)
				/* Json output */
				.listener(validatingItemProcessor())
				.writer(jpaItemWriter())
				.listener(stepExecutionListenerImpl)
				.transactionManager(jpaTransactionManager)
				.build();
	}
	
	/* REST API */
	public ItemReaderAdapter<IIPMApplicationResponseJson> itemReaderAdapterIIPMApplication() {
		ItemReaderAdapter<IIPMApplicationResponseJson> itemReaderAdapter = 
				new ItemReaderAdapter<IIPMApplicationResponseJson>();
		/* Here name of the target service */
		itemReaderAdapter.setTargetObject(iipmApplicationService);
		/* Here we provide a way how to return item 1 by 1 */
		itemReaderAdapter.setTargetMethod("getNextApplication");
		return itemReaderAdapter;
	}
	
	@Bean
	public JdbcBatchItemWriter<IIPMApplication> jdbcBatchItemWriter() {
		JdbcBatchItemWriter<IIPMApplication> jdbcBatchItemWriter = 
				new JdbcBatchItemWriter<IIPMApplication>();
		
		jdbcBatchItemWriter.setDataSource(sqlserverdatasource);
		jdbcBatchItemWriter.setSql(
				"insert into application("
				+ "app_code, l5, app_custodian, business_criticality, bu_exec_sponsor, business_app_sponsor, "
				+ "app_coordinator, recovery_point_bjective, recovery_time_objective, recovery_time_capability, "
				+ "deployment_style, secondary_data_centers, vendor_managed "
				+ ") "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		jdbcBatchItemWriter.setItemPreparedStatementSetter(
				new ItemPreparedStatementSetter<IIPMApplication>() {
			
			@Override
			public void setValues(IIPMApplication item, PreparedStatement ps) throws SQLException {
				log.info("**** IIPM - we are writing item #: " + item.getAppCode());
				int i = 0;
				ps.setString(++i, item.getAppCode());
				ps.setString(++i, item.getL5());
				ps.setString(++i, item.getAppCustodian());
				ps.setString(++i, item.getBusinessCriticality());
				ps.setString(++i, item.getBuExecSponsor());
				ps.setString(++i, item.getBusinessAppSponsor());				
				ps.setString(++i, item.getAppCoordinator());
				ps.setString(++i, item.getRecoveryPointObjective());
				ps.setString(++i, item.getRecoveryTimeObjective());
				ps.setString(++i, item.getRecoveryTimeCapability());
				ps.setString(++i, item.getDeploymentStyle());
				ps.setString(++i, item.getSecondaryDataCenters());
				ps.setString(++i, item.getVendorManaged());
			}
		});
		
		return jdbcBatchItemWriter;
	}
	
	public JpaItemWriter<IIPMApplication> jpaItemWriter() {
		log.info("**** IIPM - writing initialization ");
		JpaItemWriter<IIPMApplication> jpaItemWriter = new JpaItemWriter<IIPMApplication>();		
		jpaItemWriter.setEntityManagerFactory(sqlserverEntityManagerFactory);		
		return jpaItemWriter;
	}
	 
	@Bean
	public ValidatingItemProcessor<IIPMApplication> validatingItemProcessor() {
		ValidatingItemProcessor<IIPMApplication> itemProcessor = new ValidatingItemProcessor<IIPMApplication>();
		itemProcessor.setValidator(new IIPMApplicationValidator());
		itemProcessor.setFilter(true);

		return itemProcessor;
	}
}
