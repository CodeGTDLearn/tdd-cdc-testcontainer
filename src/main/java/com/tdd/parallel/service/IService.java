package com.tdd.parallel.service;

import com.tdd.parallel.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IService {
  Mono<Person> save(Person person);

  Flux<Person> findAll();

  Mono<Void> deleteById(String id);

  Mono<Void> deleteAll();

  Mono<Person> findById(String id);
}
