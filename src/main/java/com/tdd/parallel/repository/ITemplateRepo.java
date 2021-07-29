package com.tdd.parallel.repository;

import com.tdd.parallel.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITemplateRepo {
  Mono<Person> save(Person person);

  Flux<Person> findAll();

  Mono<Void> deleteById(String id);

  Mono<Void> deleteAll();

  Mono<Boolean> collectionExists(String collection);

  Mono<Person> findById(String id);

  Flux<Person> saveAll(List<Person> personList);
}
