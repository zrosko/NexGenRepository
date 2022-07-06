package com.rbc.nexgen.batch.config;

import java.io.IOException;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

//import com.rbc.nexgen.batch.listener.SkipListener;
import com.rbc.nexgen.batch.listener.SkipListenerImpl;
import com.rbc.nexgen.batch.model.StudentCsv;
import com.rbc.nexgen.batch.model.StudentJdbc;
import com.rbc.nexgen.batch.model.StudentJson;
import com.rbc.nexgen.batch.model.StudentResponse;
import com.rbc.nexgen.batch.model.StudentXml;
import com.rbc.nexgen.batch.mysql.entity.StudentJpa;
import com.rbc.nexgen.batch.postgresql.entity.Student;
import com.rbc.nexgen.batch.processor.FirstItemProcessor;
import com.rbc.nexgen.batch.processor.StudentFromRestAPIProcessor;
import com.rbc.nexgen.batch.service.StudentService;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class DefaultJobConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	KafkaTemplate<Long, Student> template;
	KafkaProperties properties;
	/*
	 * @Autowired private FirstItemReader firstItemReader;
	 */
	
	@Autowired
	private FirstItemProcessor firstItemProcessor;
	
	@Autowired
	private StudentFromRestAPIProcessor studentFromRestAPIProcessor;
	
	/*
	 * @Autowired private FirstItemWriter firstItemWriter;
	 */	
	
	@Autowired /* REST API */
	private StudentService studentService;	 
	
	/*
	 * @Autowired private SkipListener skipListener;
	 */
	
	@Autowired
	private SkipListenerImpl skipListenerImpl;
	
	@Autowired
	@Qualifier("datasource")
	private DataSource datasource;
	
	@Autowired
	@Qualifier("universitydatasource")
	private DataSource universitydatasource;
	
	@Autowired
	@Qualifier("postgresdatasource")
	private DataSource postgresdatasource;
	
	@Autowired
	@Qualifier("postgresqlEntityManagerFactory")
	private EntityManagerFactory postgresqlEntityManagerFactory;
	
	@Autowired
	@Qualifier("mysqlEntityManagerFactory")
	private EntityManagerFactory mysqlEntityManagerFactory;
	
	@Autowired
	private JpaTransactionManager jpaTransactionManager;
	
	@Value("${jobName}")
	private String jobName = "Default Job";
	
	@Bean
	public Job defaultJob() {
		log.info("*** JOB: "+jobName+" - starting.");
		
		return jobBuilderFactory.get(jobName)
				.incrementer(new RunIdIncrementer())
				//.start(Step_1_JDBC_CSV())
				.start(Step_2_REST_CSV())
				//.start(Step_3_JPA_JPA())
				.build();
	}
	/* *************************************************************** */
	/*                         STEPS 							       */
	/*     											    			   */
	/* *************************************************************** */
	
	private Step Step_1_JDBC_CSV() {
		log.info("*** JOB: "+jobName+" - step: "+"Step_1_JDBC_CSV");
		
		return stepBuilderFactory.get("Step_1_JDBC_CSV")
				/* Here change chunk size for production */
				.<StudentJdbc, StudentJdbc>chunk(2223)
				/* JDBC input */
				.reader(jdbcCursorItemReader())
				/* CSV output */
				.writer(flatFileItemWriter(null))
				/* Other settings */
				.faultTolerant()
				.skip(Throwable.class)
				.skipLimit(100)
				.retryLimit(3)
				.retry(Throwable.class)
				.listener(skipListenerImpl)
				.build();
	}
	
	private Step Step_2_REST_CSV() {
		log.info("*** JOB: "+jobName+" - step: "+"Step_2_REST_CSV");
		
		return stepBuilderFactory.get("Step_2_REST_CSV")
				/* Here change chunk size for production */
				.<StudentResponse, StudentJdbc>chunk(3)
				/* REST API input */
				.reader(itemReaderAdapter())
				/* REST API process */
				.processor(studentFromRestAPIProcessor)
				/* CSV output */
				.writer(flatFileItemWriter(null))
				.faultTolerant()
				.skip(Throwable.class)
				//.skip(NullPointerException.class)
				.skipLimit(100)
				//.skipPolicy(new AlwaysSkipItemSkipPolicy())
				.retryLimit(3)
				.retry(Throwable.class)
				//.listener(skipListener)
				.listener(skipListenerImpl)
				.build();
	}
	
	/* Read from PostgreSQL and insert to the MySQL database */
	
	private Step Step_3_JPA_JPA() {
		log.info("*** JOB: "+jobName+" - step: "+"Step_3_JPA_JPA");
		
		return stepBuilderFactory.get("Step_3_JPA_JPA")
				/* Here change chunk size for production */
				.<Student, StudentJpa>chunk(3)
				/* JPA input */
				.reader(jpaCursorItemReader(null, null))
				/* JPA/JPA process*/
				.processor(firstItemProcessor)
				/* JPA output */
				.writer(jpaItemWriter())
				.faultTolerant()
				.skip(Throwable.class)
				//.skip(NullPointerException.class)
				.skipLimit(100)
				//.skipPolicy(new AlwaysSkipItemSkipPolicy())
				.retryLimit(3)
				.retry(Throwable.class)
				//.listener(skipListener)
				.listener(skipListenerImpl)
				/* JPA/JPA */ 
				.transactionManager(jpaTransactionManager)
				.build();
	}
	
	@StepScope
	@Bean
	public FlatFileItemReader<StudentCsv> flatFileItemReader(
			@Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource) {
		FlatFileItemReader<StudentCsv> flatFileItemReader = 
				new FlatFileItemReader<StudentCsv>();
		
		flatFileItemReader.setResource(fileSystemResource);
		
		flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCsv>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames("ID", "First Name", "Last Name", "Email");
					}
				});
				
				setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCsv>() {
					{
						setTargetType(StudentCsv.class);
					}
				});
				
			}
		});
		
		/*
		DefaultLineMapper<StudentCsv> defaultLineMapper = 
				new DefaultLineMapper<StudentCsv>();
		
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setNames("ID", "First Name", "Last Name", "Email");
		
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		
		BeanWrapperFieldSetMapper<StudentCsv> fieldSetMapper = 
				new BeanWrapperFieldSetMapper<StudentCsv>();
		fieldSetMapper.setTargetType(StudentCsv.class);
		
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		
		flatFileItemReader.setLineMapper(defaultLineMapper);
		*/
		
		flatFileItemReader.setLinesToSkip(1);
		
		return flatFileItemReader;
	}

	@StepScope
	@Bean
	public JsonItemReader<StudentJson> jsonItemReader(
			@Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource) {
		JsonItemReader<StudentJson> jsonItemReader = 
				new JsonItemReader<StudentJson>();
		
		jsonItemReader.setResource(fileSystemResource);
		jsonItemReader.setJsonObjectReader(
				new JacksonJsonObjectReader<>(StudentJson.class));
		
		jsonItemReader.setMaxItemCount(8);
		jsonItemReader.setCurrentItemCount(2);
		
		return jsonItemReader;
	}
	
	@StepScope
	@Bean
	public StaxEventItemReader<StudentXml> staxEventItemReader(
			@Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource) {
		StaxEventItemReader<StudentXml> staxEventItemReader = 
				new StaxEventItemReader<StudentXml>();
		
		staxEventItemReader.setResource(fileSystemResource);
		staxEventItemReader.setFragmentRootElementName("student");
		staxEventItemReader.setUnmarshaller(new Jaxb2Marshaller() {
			{
				setClassesToBeBound(StudentXml.class);
			}
		});
		
		return staxEventItemReader;
	}
	
	public JdbcCursorItemReader<StudentJdbc> jdbcCursorItemReader() {
		JdbcCursorItemReader<StudentJdbc> jdbcCursorItemReader = 
				new JdbcCursorItemReader<StudentJdbc>();
		/* JPA */
		jdbcCursorItemReader.setDataSource(universitydatasource);
		jdbcCursorItemReader.setSql(
				"select id, first_name as firstName, last_name as lastName,"
				+ "email from student");
		
		jdbcCursorItemReader.setRowMapper(new BeanPropertyRowMapper<StudentJdbc>() {
			{
				setMappedClass(StudentJdbc.class);
			}
		});
		
		//jdbcCursorItemReader.setCurrentItemCount(2);
		//jdbcCursorItemReader.setMaxItemCount(8);
		
		return jdbcCursorItemReader;
	}
	
	/* REST API */
	public ItemReaderAdapter<StudentResponse> itemReaderAdapter() {
		ItemReaderAdapter<StudentResponse> itemReaderAdapter = 
				new ItemReaderAdapter<StudentResponse>();
		/* Here name of the target service */
		itemReaderAdapter.setTargetObject(studentService);
		/* Here we provide a way how to return item 1 by 1 */
		itemReaderAdapter.setTargetMethod("getStudent");
		/* TODO */
		itemReaderAdapter.setArguments(new Object[] {1L, "Test"});
		
		return itemReaderAdapter;
	}
	
	
	/*  From JDBC to CSV - this method is called for each item (row) */
	
	@StepScope
	@Bean
	public FlatFileItemWriter<StudentJdbc> flatFileItemWriter(
			@Value("#{jobParameters['outputFile']}") FileSystemResource fileSystemResource) {
		
		FlatFileItemWriter<StudentJdbc> flatFileItemWriter = new FlatFileItemWriter<StudentJdbc>();		
		flatFileItemWriter.setResource(fileSystemResource);
		
		//All job repetitions should "append" to same output file
		//flatFileItemWriter.setAppendAllowed(true);
		
		/* Prepare column header (it is possible to set a footer, see below) */
		flatFileItemWriter.setHeaderCallback(new FlatFileHeaderCallback() {
			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.write("Id,First Name,Last Name,Email");
			}
		});
		
		/* Set line delimiter, default is comma (,), you may set "|"; prepare data. */
		flatFileItemWriter.setLineAggregator(new DelimitedLineAggregator<StudentJdbc>() {
			{
				//setDelimiter("|");
				setFieldExtractor(new BeanWrapperFieldExtractor<StudentJdbc>() {
					{
						setNames(new String[] {"id", "firstName", "lastName", "email"});
					}
				});
			}
		});
		
		/* Prepare footer line (if required) */
		flatFileItemWriter.setFooterCallback(new FlatFileFooterCallback() {
			@Override
			public void writeFooter(Writer writer) throws IOException {
				writer.write("Created @ " + new Date());
			}
		});
		
		return flatFileItemWriter;
	}
	
	@StepScope
	@Bean
	public JsonFileItemWriter<StudentJson> jsonFileItemWriter(
			@Value("#{jobParameters['outputFile']}") FileSystemResource fileSystemResource) {
		JsonFileItemWriter<StudentJson> jsonFileItemWriter = 
				new JsonFileItemWriter<StudentJson>(fileSystemResource, 
						new JacksonJsonObjectMarshaller<StudentJson>()) {
			@Override
			public String doWrite(List<? extends StudentJson> items) {
				items.stream().forEach(item -> {
					if(item.getId() == 3) {
						System.out.println("Inside jsonFileItemWriter");
						throw new NullPointerException();
					}
				});
				return super.doWrite(items);
			}
		};
		
		return jsonFileItemWriter;
	}
	
	@StepScope
	@Bean
	public StaxEventItemWriter<StudentJdbc> staxEventItemWriter(
			@Value("#{jobParameters['outputFile']}") FileSystemResource fileSystemResource) {
		StaxEventItemWriter<StudentJdbc> staxEventItemWriter = 
				new StaxEventItemWriter<StudentJdbc>();
		
		staxEventItemWriter.setResource(fileSystemResource);
		staxEventItemWriter.setRootTagName("students");
		
		staxEventItemWriter.setMarshaller(new Jaxb2Marshaller() {
			{
				setClassesToBeBound(StudentJdbc.class);
			}
		});
		
		return staxEventItemWriter;
	}
	
	@Bean
	public JdbcBatchItemWriter<StudentCsv> jdbcBatchItemWriter() {
		JdbcBatchItemWriter<StudentCsv> jdbcBatchItemWriter = 
				new JdbcBatchItemWriter<StudentCsv>();
		
		jdbcBatchItemWriter.setDataSource(universitydatasource);
		jdbcBatchItemWriter.setSql(
				"insert into student(id, first_name, last_name, email) "
				+ "values (:id, :firstName, :lastName, :email)");
		
		jdbcBatchItemWriter.setItemSqlParameterSourceProvider(
				new BeanPropertyItemSqlParameterSourceProvider<StudentCsv>());
		
		return jdbcBatchItemWriter;
	}
	
	@Bean
	public JdbcBatchItemWriter<StudentCsv> jdbcBatchItemWriter1() {
		JdbcBatchItemWriter<StudentCsv> jdbcBatchItemWriter = 
				new JdbcBatchItemWriter<StudentCsv>();
		
		jdbcBatchItemWriter.setDataSource(universitydatasource);
		jdbcBatchItemWriter.setSql(
				"insert into student(id, first_name, last_name, email) "
				+ "values (?,?,?,?)");
		
		jdbcBatchItemWriter.setItemPreparedStatementSetter(
				new ItemPreparedStatementSetter<StudentCsv>() {
			
			@Override
			public void setValues(StudentCsv item, PreparedStatement ps) throws SQLException {
				ps.setLong(1, item.getId());
				ps.setString(2, item.getFirstName());
				ps.setString(3, item.getLastName());
				ps.setString(4, item.getEmail());
			}
		});
		
		return jdbcBatchItemWriter;
	}
	
	/* To REST API (call and create student)
	public ItemWriterAdapter<StudentCsv> itemWriterAdapter() {
		ItemWriterAdapter<StudentCsv> itemWriterAdapter = 
				new ItemWriterAdapter<StudentCsv>();
		
		itemWriterAdapter.setTargetObject(studentService);
		itemWriterAdapter.setTargetMethod("restCallToCreateStudent");
		
		return itemWriterAdapter;
	}
	*/
	
	@StepScope
	@Bean
	public JpaCursorItemReader<Student> jpaCursorItemReader(
			@Value("#{jobParameters['currentItemCount']}") Integer currentItemCount,
			@Value("#{jobParameters['maxItemCount']}") Integer maxItemCount) {
		
		JpaCursorItemReader<Student> jpaCursorItemReader = new JpaCursorItemReader<Student>();		
		jpaCursorItemReader.setEntityManagerFactory(postgresqlEntityManagerFactory);		
		jpaCursorItemReader.setQueryString("From Student");		
		jpaCursorItemReader.setCurrentItemCount(currentItemCount);
		jpaCursorItemReader.setMaxItemCount(maxItemCount);	
		return jpaCursorItemReader;
	}
	
	public JpaItemWriter<StudentJpa> jpaItemWriter() {
		
		JpaItemWriter<StudentJpa> jpaItemWriter = new JpaItemWriter<StudentJpa>();		
		jpaItemWriter.setEntityManagerFactory(mysqlEntityManagerFactory);		
		return jpaItemWriter;
	}
	
	//TODO test Kafka https://www.youtube.com/watch?v=UJesCn731G4
	/*
	 * @Bean public KafkaItemWriter<Long, Student> kafkaItemWriter() { return new
	 * KafkaItemWriterBuilder<Long, Student>() .kafkaTemplate(template)
	 * .itemKeyMapper(Student::getId) .build(); }
	 * 
	 * @Bean public KafkaItemReader<Long, Student> kafkaItemReader() { var props =
	 * new Properties(); props.putAll(this.properties.buildConsumerProperties());
	 * 
	 * return new KafkaItemReaderBuilder<Long, Student>() .partitions(0)
	 * .consumerProperties(props) .name("student-reader") .saveState(true)
	 * .topic("students") .build(); }
	 */
}
