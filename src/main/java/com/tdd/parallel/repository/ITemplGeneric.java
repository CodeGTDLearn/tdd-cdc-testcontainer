package com.tdd.parallel.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITemplGeneric<E> {
  Mono<E> save(E person);

  Flux<E> findAll();

  Mono<Void> deleteById(String id);

  Mono<Void> deleteAll();

  Mono<Boolean> collectionExists(String collection);

  Mono<E> findById(String id);
}
