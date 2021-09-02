package com.tdd.parallel.service;

import com.tdd.parallel.entity.PersonStandard;
import com.tdd.parallel.repository.IMongoRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service("serviceMongoRepo")
@AllArgsConstructor
public class ServiceMongoRepo implements IService {


  private final IMongoRepo iMongoRepo;


  @Override
  public Mono<PersonStandard> save(PersonStandard person) {
    return iMongoRepo.save(person);
  }


  @Override
  public Flux<PersonStandard> findAll() {
    return iMongoRepo.findAll();
  }


  @Override
  public Mono<PersonStandard> findById(String id) {
    return iMongoRepo.findById(id);
  }


  public Mono<Void> deleteAll() {
    return iMongoRepo.deleteAll();
  }


  @Override
  public Mono<Void> deleteById(String id) {
    return iMongoRepo.deleteById(id);
  }


}


