package com.tdd.parallel.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IService<E> {
  Mono<E> save(E person);

  Flux<E> findAll();

  Mono<Void> deleteById(String id);

  Mono<Void> deleteAll();

  Mono<E> findById(String id);
}
