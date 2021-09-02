package com.tdd.parallel.service;

import com.tdd.parallel.entity.PersonStandard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IService {
  Mono<PersonStandard> save(PersonStandard person);

  Flux<PersonStandard> findAll();

  Mono<Void> deleteById(String id);

  Mono<Void> deleteAll();

  Mono<PersonStandard> findById(String id);
}
