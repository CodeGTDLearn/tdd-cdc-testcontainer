package com.tdd.parallel.core.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = {
     "com.tdd.parallel.repository.crud",
     "com.tdd.parallel.repository.mongo",
     "com.tdd.parallel.repository.template"})
public class ReactiveDBConfig extends AbstractReactiveMongoConfiguration {

  @Value("${udemy.mongodb.database}")
  private String database;

  @Value("${udemy.mongodb.uri}")
  private String uri;


  @Override
  public MongoClient reactiveMongoClient() {
    return super.reactiveMongoClient();
//        return MongoClients.create(uri)
  }


  @Override
  protected String getDatabaseName() {
    return database;
  }


  @Bean
  public ReactiveMongoTemplate reactiveMongoTemplate() {
    return new ReactiveMongoTemplate(reactiveMongoClient(),getDatabaseName());
  }

}
