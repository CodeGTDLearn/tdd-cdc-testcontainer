package com.tdd.parallel.service;

import com.tdd.parallel.entity.PersonStandard;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service("serviceReactMongoTempl")
@AllArgsConstructor
public class ServiceReactMongoTempl implements IService {


  private final ReactiveMongoTemplate reactiveMongoTemplate;


  @Override
  public Mono<PersonStandard> save(PersonStandard person) {
    return reactiveMongoTemplate.save(person);
  }


  @Override
  public Flux<PersonStandard> findAll() {
    return reactiveMongoTemplate.findAll(PersonStandard.class);
  }


  @Override
  public Mono<Void> deleteById(String id) {
    return findById(id)
         .flatMap(reactiveMongoTemplate::remove)
         .then(Mono.empty());
  }


  @Override
  public Mono<Void> deleteAll() {
    return reactiveMongoTemplate
         .remove(new Query(),PersonStandard.class)
         .then(Mono.empty());
  }


  @Override
  public Mono<PersonStandard> findById(String id) {
    return reactiveMongoTemplate.findById(id,PersonStandard.class);
  }
}


