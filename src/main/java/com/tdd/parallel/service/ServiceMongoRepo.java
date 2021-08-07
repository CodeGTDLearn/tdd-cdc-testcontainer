package com.tdd.parallel.service;

import com.tdd.parallel.entity.Person;
import com.tdd.parallel.repository.IMongoRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service("serviceMongoRepo")
@AllArgsConstructor
public class ServiceMongoRepo implements IService {


  private final IMongoRepo iMongoRepo;


  @Override
  public Mono<Person> save(Person person) {
    return iMongoRepo.save(person);
  }


  @Override
  public Flux<Person> findAll() {
    return iMongoRepo.findAll();
  }


  @Override
  public Mono<Person> findById(String id) {
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


