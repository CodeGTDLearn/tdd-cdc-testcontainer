package com.tdd.parallel.repository;

import com.tdd.parallel.entity.PersonStandard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITemplateRepo {
  Mono<PersonStandard> save(PersonStandard person);

  Flux<PersonStandard> findAll();

  Mono<Void> deleteById(String id);

  Mono<Void> deleteAll();

  Mono<Boolean> collectionExists(String collection);

  Mono<PersonStandard> findById(String id);
}
