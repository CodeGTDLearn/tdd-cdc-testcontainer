package com.tdd.parallel.repository;

import com.tdd.parallel.entity.PersonStandard;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@AllArgsConstructor
@Repository("templateRepo")
public class TemplateRepo implements ITemplateRepo {

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
  public Mono<Boolean> collectionExists(String collection) {
    return reactiveMongoTemplate.collectionExists(collection);

  }


  @Override
  public Mono<PersonStandard> findById(String id) {
    return reactiveMongoTemplate.findById(id,PersonStandard.class);
  }
}
