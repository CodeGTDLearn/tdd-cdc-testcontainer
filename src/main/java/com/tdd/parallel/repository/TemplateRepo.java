package com.tdd.parallel.repository;

import com.tdd.parallel.entity.Person;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@AllArgsConstructor
@Repository("templateRepo")
public class TemplateRepo implements ITemplateRepo {

  private final ReactiveMongoTemplate reactiveMongoTemplate;


  @Override
  public Mono<Person> save(Person person) {
    return reactiveMongoTemplate.save(person);
  }


  @Override
  public Flux<Person> findAll() {
    return reactiveMongoTemplate.findAll(Person.class);
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
         .remove(new Query(),Person.class)
         .then(Mono.empty());
  }


  @Override
  public Mono<Boolean> collectionExists(String collection) {
    return reactiveMongoTemplate.collectionExists(collection);

  }


  @Override
  public Mono<Person> findById(String id) {
    return reactiveMongoTemplate.findById(id,Person.class);
  }
}
