package com.tdd.parallel.core.config;

import com.tdd.parallel.service.ServiceReactMongoTempl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@TestConfiguration
public class ServiceReactMongoTemplCfg {

  @Autowired
  private ReactiveMongoTemplate reactiveMongoTemplate;


  @Bean
  public ServiceReactMongoTempl serviceTemplRepo() {
    return new ServiceReactMongoTempl(reactiveMongoTemplate);
  }

}