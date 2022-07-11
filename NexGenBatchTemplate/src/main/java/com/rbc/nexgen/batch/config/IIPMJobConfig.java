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

import com.rbc.nexgen.batch.listener.StepExecutionListenerImpl;
import com.rbc.nexgen.batch.model.IIPMApplication;
import com.rbc.nexgen.batch.model.IIPMApplicationResponseJson;
import com.rbc.nexgen.batch.processor.IIPMApplicationValidator;
import com.rbc.nexgen.batch.reader.GenericJsonObjectReader;
import com.rbc.nexgen.batch.writer.ApplicationItemWriter;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class IIPMJobConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired 
	private ApplicationItemWriter applicationItemWriter;
	
	@Autowired /* TODO REST API */
	private GenericJsonObjectReader<IIPMApplication> iipmApplicationService;
	
	@Autowired
	private StepExecutionListenerImpl stepExecutionListenerImpl;
	
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
				.start(Step_IIPM_API_to_SQL_Server())
				.build();
	}
	/* *************************************************************** */
	/*                         STEPS 							       */
	/*     											    			   */
	/* *************************************************************** */
	
	private Step Step_IIPM_API_to_SQL_Server() {
		log.info("*** JOB: "+jobName+" - step: "+"Step_IIPM_API_to_SQL_Server");
		
		return stepBuilderFactory.get("Step_IIPM_API_to_SQL_Server")
				/* Here change chunk size for production */
				.<IIPMApplicationResponseJson, IIPMApplication>chunk(3)
				/* Json input */
				.reader(itemReaderAdapterIIPMApplication())
				/* Json process*/
				//.processor(firstItemProcessor)
				//.processor(validatingItemProcessor())
				/* Json output */
				.writer(applicationItemWriter)
				.listener(stepExecutionListenerImpl)
				.build();
	}
	
	/* REST API */
	public ItemReaderAdapter<IIPMApplicationResponseJson> itemReaderAdapterIIPMApplication() {
		ItemReaderAdapter<IIPMApplicationResponseJson> itemReaderAdapter = 
				new ItemReaderAdapter<IIPMApplicationResponseJson>();
		/* Here name of the target service */
		itemReaderAdapter.setTargetObject(iipmApplicationService);
		/* Here we provide a way how to return item 1 by 1 */
		itemReaderAdapter.setTargetMethod("getObjectFromTheList");
		return itemReaderAdapter;
	}
	
	@Bean
	public JdbcBatchItemWriter<IIPMApplication> jdbcBatchItemWriter() {
		JdbcBatchItemWriter<IIPMApplication> jdbcBatchItemWriter = 
				new JdbcBatchItemWriter<IIPMApplication>();
		
		jdbcBatchItemWriter.setDataSource(sqlserverdatasource);
		jdbcBatchItemWriter.setSql(
				"insert into application(appCode, l5, appCustodian, businessCriticality) "
				+ "values (?,?,?,?)");
		
		jdbcBatchItemWriter.setItemPreparedStatementSetter(
				new ItemPreparedStatementSetter<IIPMApplication>() {
			
			@Override
			public void setValues(IIPMApplication item, PreparedStatement ps) throws SQLException {
				ps.setString(1, item.getAppCode());
				ps.setString(2, item.getL5());
				ps.setString(3, item.getAppCustodian());
				ps.setString(4, item.getBusinessCriticality());
			}
		});
		
		return jdbcBatchItemWriter;
	}
	
	public JpaItemWriter<IIPMApplication> jpaApplicationItemWriterSqlServer() {

		JpaItemWriter<IIPMApplication> jpaItemWriter = new JpaItemWriter<IIPMApplication>();
		jpaItemWriter.setEntityManagerFactory(sqlserverEntityManagerFactory);
		return jpaItemWriter;
	}
	 
	@Bean
	public ValidatingItemProcessor<IIPMApplication> validatingItemProcessor() {
		ValidatingItemProcessor<IIPMApplication> itemProcessor = new ValidatingItemProcessor<>();
		itemProcessor.setValidator(new IIPMApplicationValidator());
		itemProcessor.setFilter(true);

		return itemProcessor;
	}
}
