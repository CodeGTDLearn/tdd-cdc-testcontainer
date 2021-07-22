package com.tdd.parallel.service;

import com.tdd.parallel.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPersonService {
  Mono<Person> save(Person customer);

  Flux<Person> findAll();

  Mono<Void> deleteById(String id);

  Mono<Void> deleteAll();

  Mono<Person> findById(String id);
}
