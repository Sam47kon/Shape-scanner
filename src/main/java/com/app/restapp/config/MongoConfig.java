package com.app.restapp.config;

import com.app.restapp.converter.PointWriterConverter;
import com.app.restapp.converter.PolygonWriterConverter;
import com.app.restapp.converter.ShapeWriterConverter;
import com.app.restapp.event.CascadeSaveMongoEventListener;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableMongoRepositories(basePackages = "com.app.restapp.repository")
@SuppressWarnings("NullableProblems")
public class MongoConfig extends AbstractMongoClientConfiguration {

	private final List<Converter<?, ?>> converters = new ArrayList<>();

	private final MappingMongoConverter mongoConverter;

	/**
	 * Аннотация @Lazy потому что:
	 * The dependencies of some of the beans in the application context form a cycle:
	 * initDB defined in file ... InitDB.class
	 * shapeServiceImpl defined in file ... ShapeServiceImpl.class
	 * shapeRepository defined in ... ShapeRepository defined in @EnableMongoRepositories declared on MongoConfig
	 * mongoConfig defined in file ... MongoConfig.class
	 *
	 * @param mongoConverter - @Autowired
	 */
	@Autowired
	public MongoConfig(@Lazy MappingMongoConverter mongoConverter) {
		this.mongoConverter = mongoConverter;
	}

	@Override
	protected String getDatabaseName() {
		return "shape-app";
	}

	@Override
	public MongoClient mongoClient() {
		return MongoClients.create(MongoClientSettings.builder()
				.applyConnectionString(
						new ConnectionString("mongodb+srv://Sam47kon:4747@cluster0.isrzl.mongodb.net/shape-app?retryWrites=true&w=majority"))
				.build());
	}

	@Override
	public Collection<String> getMappingBasePackages() {
		return Collections.singleton("com.app");
	}

	// @Bean
	// public TODO нужны ли листенеры?

	@Bean
	public CascadeSaveMongoEventListener cascadeSaveMongoEventListener() {
		return new CascadeSaveMongoEventListener();
	}

	@Override
	public MongoCustomConversions customConversions() {
		converters.add(new PointWriterConverter());
		converters.add(new ShapeWriterConverter());
		converters.add(new PolygonWriterConverter());
		// TODO нужны ли конвертеры для подклассов?
		return new MongoCustomConversions(converters);
	}

	@Bean
	public GridFsTemplate gridFsTemplate() {
		return new GridFsTemplate(mongoDbFactory(), mongoConverter);
	}

	// TODO для тестов
	@Bean
	public MongoTransactionManager transactionManager(MongoDatabaseFactory databaseFactory) {
		return new MongoTransactionManager(databaseFactory);
	}
}
