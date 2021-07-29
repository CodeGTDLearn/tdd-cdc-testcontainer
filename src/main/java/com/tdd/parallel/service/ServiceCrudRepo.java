package com.tdd.parallel.service;

import com.tdd.parallel.entity.Person;
import com.tdd.parallel.repository.ICrudRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

//TUTORIAL: https://rieckpil.de/mongodb-testcontainers-setup-for-datamongotest/
@Slf4j
@Service("serviceCrudRepo")
@AllArgsConstructor
public class ServiceCrudRepo implements IService {


  private final ICrudRepo iCrudRepo;


  @Override
  public Mono<Person> save(Person person) {
    return iCrudRepo.save(person);
  }


  @Override
  public Flux<Person> saveAll(List<Person> personList) {
    return iCrudRepo.saveAll(personList);
  }


  @Override
  public Mono<Boolean> collectionExists(String collection) {
    return null;
  }


  @Override
  public Flux<Person> findAll() {
    return iCrudRepo.findAll();
  }


  @Override
  public Mono<Person> findById(String id) {
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


