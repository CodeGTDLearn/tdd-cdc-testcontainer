package com.tdd.parallel.core.config;

import com.tdd.parallel.repository.standard.ICrudStandard;
import com.tdd.parallel.service.standard.ServCrudStandard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ServiceCrudRepoCfg {

  @Autowired
  private ICrudStandard iCrudStandard;


  @Bean
  public ServCrudStandard serviceCrudRepo() {
    return new ServCrudStandard(iCrudStandard);
  }

}