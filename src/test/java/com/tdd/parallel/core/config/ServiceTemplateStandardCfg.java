package com.tdd.parallel.core.config;

import com.tdd.parallel.repository.standard.TemplStandard;
import com.tdd.parallel.service.standard.ServTemplStandard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@TestConfiguration
public class ServiceTemplateStandardCfg {

  @Autowired
  private ReactiveMongoTemplate reactiveMongoTemplate;


  @Bean
  public ServTemplStandard serviceTemplateRepo() {
    return new ServTemplStandard(templateRepoStandard());
  }


  private TemplStandard templateRepoStandard() {
    return new TemplStandard(reactiveMongoTemplate);
  }

}