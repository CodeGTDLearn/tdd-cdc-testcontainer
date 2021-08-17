package com.tdd.parallel.core.config.arquive;

import com.tdd.parallel.repository.ICrudRepo;
import com.tdd.parallel.service.ServiceCrudRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ServiceCrudRepoCfg {

  @Autowired
  private ICrudRepo iCrudRepo;


  @Bean
  public ServiceCrudRepo serviceCrudRepo() {
    return new ServiceCrudRepo(iCrudRepo);
  }

}