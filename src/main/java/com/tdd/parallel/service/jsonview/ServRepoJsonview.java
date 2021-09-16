package com.tdd.parallel.service.jsonview;

import com.tdd.parallel.entity.PersonJsonview;
import com.tdd.parallel.repository.jsonview.IRepoJsonview;
import com.tdd.parallel.service.IService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//TUTORIAL: https://rieckpil.de/mongodb-testcontainers-setup-for-datamongotest/
@Slf4j
@Service("servRepoJsonview")
@AllArgsConstructor
public class ServRepoJsonview implements IService<PersonJsonview> {


  private final IRepoJsonview iRepoJsonview;


  @Override
  public Mono<PersonJsonview> save(PersonJsonview person) {
    return iRepoJsonview.save(person);
  }


  @Override
  public Flux<PersonJsonview> findAll() {
    return iRepoJsonview.findAll();
  }


  @Override
  public Mono<PersonJsonview> findById(String id) {
    return iRepoJsonview.findById(id);
  }


  public Mono<Void> deleteAll() {
    return iRepoJsonview.deleteAll();
  }


  @Override
  public Mono<Void> deleteById(String id) {
    return iRepoJsonview.deleteById(id);

  }
}


