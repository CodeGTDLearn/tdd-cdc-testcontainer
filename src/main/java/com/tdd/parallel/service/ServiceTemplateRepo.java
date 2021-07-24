package com.tdd.parallel.service;

import com.tdd.parallel.entity.Person;
import com.tdd.parallel.repository.template.TemplateRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

//TUTORIAL: https://rieckpil.de/mongodb-testcontainers-setup-for-datamongotest/
@Slf4j
@Service("serviceTemplateRepo")
@AllArgsConstructor
public class ServiceTemplateRepo implements IService {


  private final TemplateRepo templateRepo;



  @Override
  public Mono<Person> save(Person person) {
    return templateRepo.save(person);
  }


  @Override
  public Flux<Person> saveAll(List<Person> personList) {
    return templateRepo.saveAll(personList);
  }


  @Override
  public Flux<Person> findAll() {
    return templateRepo.findAll();
  }


  @Override
  public Mono<Person> findById(String id) {
    return templateRepo.findById(id);
  }


  public Mono<Void> deleteAll() {
    return templateRepo.deleteAll();
  }


  @Override
  public Mono<Void> deleteById(String id) {
    return templateRepo.deleteById(id);
  }

  @Override
  public Mono<Boolean> collectionExists(String collection) {
    return templateRepo.collectionExists(collection);
  }


}


