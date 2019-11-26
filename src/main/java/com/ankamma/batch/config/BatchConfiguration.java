package com.ankamma.batch.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.batch.item.kafka.builder.KafkaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.ankamma.batch.model.Customer;
import com.ankamma.batch.model.CustomerUI;

@Configuration
public class BatchConfiguration extends DefaultBatchConfigurer {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfiguration.class);

	@Autowired
	public EntityManagerFactory entityManagerFactory;
	@Autowired
	private KafkaTemplate<Long, CustomerUI> template;

	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {

	}

	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			ItemProcessor<Customer, CustomerUI> itemProcessor) throws Exception {

		Step step = stepBuilderFactory.get("reader").transactionManager(getTransactionManager())
				.<Customer, CustomerUI>chunk(10).reader(reader()).processor(itemProcessor).writer(kafkaItemWriter())
				.taskExecutor(taskExecutor()).build();

		return jobBuilderFactory.get("result").incrementer(new RunIdIncrementer()).start(step).build();
	}

	@Bean
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor("spring_batch");
		asyncTaskExecutor.setConcurrencyLimit(5);
		return asyncTaskExecutor;
	}

	@StepScope
	@Bean(destroyMethod = "")
	public JpaPagingItemReader<Customer> reader() throws Exception {
		JpaPagingItemReader<Customer> databaseReader = new JpaPagingItemReader<Customer>();
		databaseReader.setEntityManagerFactory(entityManagerFactory);
		databaseReader.setSaveState(false);
		JpaQueryProviderImpl<Customer> jpaQueryProvider = new JpaQueryProviderImpl<>();
		jpaQueryProvider.setQuery("Customer.findById");
		databaseReader.setQueryString("select v from Customer v where v.status=:status");
		Map<String, Object> parameters = new HashMap();
		parameters.put("status", "active");
		databaseReader.setParameterValues(parameters);
		databaseReader.setPageSize(1000);
		return databaseReader;
	}

	private JpaNativeQueryProvider<Customer> getQueryProvider() {
		String query = "select v from Customer v where v.status=:status";
		JpaNativeQueryProvider<Customer> queryProvider = new JpaNativeQueryProvider<Customer>();
		queryProvider.setSqlQuery(query);
		queryProvider.setEntityClass(Customer.class);
		return queryProvider;
	}

	@Bean
	@Override
	public PlatformTransactionManager getTransactionManager() {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Bean
	KafkaItemWriter<Long, CustomerUI> kafkaItemWriter() {
		return new KafkaItemWriterBuilder<Long, CustomerUI>().kafkaTemplate(template).itemKeyMapper(CustomerUI::getId)
				.build();
	}

}
