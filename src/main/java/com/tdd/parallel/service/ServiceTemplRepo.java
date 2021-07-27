package com.tdd.parallel.service;

import com.tdd.parallel.entity.Person;
import com.tdd.parallel.repository.TemplateRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service("serviceTemplRepo")
@AllArgsConstructor
public class ServiceTemplRepo implements IService {


  private final TemplateRepo templRepo;


  @Override
  public Mono<Person> save(Person person) {
    return templRepo.save(person);
  }


  @Override
  public Flux<Person> findAll() {
    return templRepo.findAll();
  }


  @Override
  public Mono<Void> deleteById(String id) {
    return findById(id)
         .flatMap(item -> templRepo.deleteById(item.getId()));
  }


  @Override
  public Mono<Void> deleteAll() {
    return templRepo.deleteAll();
  }


  @Override
  public Mono<Boolean> collectionExists(String collection) {
    return templRepo.collectionExists(collection);

  }


  @Override
  public Mono<Person> findById(String id) {
    return templRepo.findById(id);
  }


  @Override
  public Flux<Person> saveAll(List<Person> personList) {
    return templRepo.saveAll(personList);
  }


}


