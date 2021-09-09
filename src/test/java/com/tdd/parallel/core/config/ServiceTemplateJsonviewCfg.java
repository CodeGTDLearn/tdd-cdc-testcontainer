package com.tdd.parallel.core.config;

import com.tdd.parallel.repository.jsonview.TemplJsonview;
import com.tdd.parallel.service.jsonview.ServTemplJsonview;
import com.tdd.parallel.service.standard.ServTemplStandard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@TestConfiguration
public class ServiceTemplateJsonviewCfg {

  @Autowired
  private ReactiveMongoTemplate reactiveMongoTemplate;


  @Bean
  public ServTemplJsonview serviceTemplateRepo() {
    return new ServTemplJsonview(templateRepoJsonview());
  }


  private TemplJsonview templateRepoJsonview() {
    return new TemplJsonview(reactiveMongoTemplate);
  }

}