package com.tdd.parallel.core.config;

import com.tdd.parallel.repository.TemplateRepo;
import com.tdd.parallel.service.ServiceTemplRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ServiceTemplTestConfig {

  @Autowired
  private TemplateRepo repo;


  @Bean
  public ServiceTemplRepo serviceTemplRepo() {
    return new ServiceTemplRepo(repo);
  }

}