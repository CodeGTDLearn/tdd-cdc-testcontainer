package com.tdd.parallel.service;

import com.tdd.parallel.entity.Person;
import com.tdd.parallel.repository.ICrudRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//TUTORIAL: https://rieckpil.de/mongodb-testcontainers-setup-for-datamongotest/
@Slf4j
@Service
@AllArgsConstructor
public class PersonService implements IPersonService {


  private final ICrudRepository crudRepo;


  @Override
  public Mono<Person> save(Person customer) {
    return crudRepo.save(customer);
  }


  @Override
  public Flux<Person> findAll() {
    return crudRepo.findAll();
  }


  @Override
  public Mono<Person> findById(String id) {
    return crudRepo.findById(id);
  }


  public Mono<Void> deleteAll() {
    return crudRepo.deleteAll();
  }


  @Override
  public Mono<Void> deleteById(String id) {
    return crudRepo.deleteById(id);
  }
}


