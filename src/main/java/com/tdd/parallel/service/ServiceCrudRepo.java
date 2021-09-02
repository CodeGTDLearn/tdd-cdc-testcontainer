package com.tdd.parallel.service;

import com.tdd.parallel.entity.PersonStandard;
import com.tdd.parallel.repository.ICrudRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//TUTORIAL: https://rieckpil.de/mongodb-testcontainers-setup-for-datamongotest/
@Slf4j
@Service("serviceCrudRepo")
@AllArgsConstructor
public class ServiceCrudRepo implements IService {


  private final ICrudRepo iCrudRepo;


  @Override
  public Mono<PersonStandard> save(PersonStandard person) {
    return iCrudRepo.save(person);
  }


  @Override
  public Flux<PersonStandard> findAll() {
    return iCrudRepo.findAll();
  }


  @Override
  public Mono<PersonStandard> findById(String id) {
    return iCrudRepo.findById(id);
  }


  public Mono<Void> deleteAll() {
    return iCrudRepo.deleteAll();
  }


  @Override
  public Mono<Void> deleteById(String id) {
    return iCrudRepo.deleteById(id);

  }
}


