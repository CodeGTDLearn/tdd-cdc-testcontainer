package com.tdd.parallel.service;

import com.tdd.parallel.entity.Person;
import com.tdd.parallel.repository.mongo.IMongoRepository;
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


  private final IMongoRepository mongoRepo;


  @Override
  public Mono<Person> save(Person person) {
    return mongoRepo.save(person);
  }


  @Override
  public Flux<Person> saveAll(List<Person> personList) {
    return mongoRepo.saveAll(personList);
  }


  @Override
  public Mono<Boolean> collectionExists(String collection) {
    return null;
  }


  @Override
  public Flux<Person> findAll() {
    return mongoRepo.findAll();
  }


  @Override
  public Mono<Person> findById(String id) {
    return mongoRepo.findById(id);
  }


  public Mono<Void> deleteAll() {
    return mongoRepo.deleteAll();
  }


  @Override
  public Mono<Void> deleteById(String id) {
    return mongoRepo.deleteById(id);
  }


}


