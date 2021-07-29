package com.tdd.parallel.core.config;

import com.tdd.parallel.repository.TemplateRepo;
import com.tdd.parallel.service.ServiceTemplateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@TestConfiguration
public class ServiceTemplateRepoCfg {

  @Autowired
  private ReactiveMongoTemplate reactiveMongoTemplate;

//  @Autowired
//  private ITemplateRepo templateRepo;


  @Bean
  public ServiceTemplateRepo serviceTemplateRepo() {
    return new ServiceTemplateRepo(templateRepo());
  }


  private TemplateRepo templateRepo() {
    return new TemplateRepo(reactiveMongoTemplate);
  }

}