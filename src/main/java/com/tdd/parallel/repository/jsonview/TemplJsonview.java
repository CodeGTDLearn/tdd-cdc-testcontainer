package com.tdd.parallel.repository.jsonview;

import com.tdd.parallel.entity.jsonview.PersonJsonview;
import com.tdd.parallel.repository.ITemplGeneric;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@AllArgsConstructor
@Repository("templJsonview")
public class TemplJsonview implements ITemplGeneric<PersonJsonview> {

  private final ReactiveMongoTemplate reactiveMongoTemplate;


  @Override
  public Mono<PersonJsonview> save(PersonJsonview person) {
    return reactiveMongoTemplate.save(person);
  }


  @Override
  public Flux<PersonJsonview> findAll() {
    return reactiveMongoTemplate.findAll(PersonJsonview.class);
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
         .remove(new Query(),PersonJsonview.class)
         .then(Mono.empty());
  }


  @Override
  public Mono<Boolean> collectionExists(String collection) {
    return reactiveMongoTemplate.collectionExists(collection);

  }


  @Override
  public Mono<PersonJsonview> findById(String id) {
    return reactiveMongoTemplate.findById(id,PersonJsonview.class);
  }
}
