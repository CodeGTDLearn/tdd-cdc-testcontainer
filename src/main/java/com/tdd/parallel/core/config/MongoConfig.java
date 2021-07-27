package com.tdd.parallel.core.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

//https://www.baeldung.com/spring-data-mongodb-reactive
@Configuration
@EnableReactiveMongoRepositories(basePackages = {"com.tdd.parallel.repository"})
public class MongoConfig extends AbstractReactiveMongoConfiguration  {

  @Autowired
  MongoClient mongoClient;

  @Bean
  public MongoClient mongoClient() {
    return MongoClients.create();
  }

  @Override
  protected String getDatabaseName() {
    return "reactive";
  }


  @Bean("reactiveMongoTemplate")
  public ReactiveMongoTemplate reactiveMongoTemplate() {
    return new ReactiveMongoTemplate(mongoClient,"test");
  }

}
