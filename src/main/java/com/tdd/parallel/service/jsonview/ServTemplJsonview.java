package com.tdd.parallel.service.jsonview;

import com.tdd.parallel.entity.PersonJsonview;
import com.tdd.parallel.repository.jsonview.TemplJsonview;
import com.tdd.parallel.service.IService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//TUTORIAL: https://rieckpil.de/mongodb-testcontainers-setup-for-datamongotest/
@Slf4j
@Service("servTemplJsonview")
@AllArgsConstructor
public class ServTemplJsonview implements IService<PersonJsonview> {


  private final TemplJsonview templJsonview;


  @Override
  public Mono<PersonJsonview> save(PersonJsonview person) {
    return templJsonview.save(person);
  }


  @Override
  public Flux<PersonJsonview> findAll() {
    return templJsonview.findAll();
  }


  @Override
  public Mono<PersonJsonview> findById(String id) {
    return templJsonview.findById(id);
  }


  public Mono<Void> deleteAll() {
    return templJsonview.deleteAll();
  }


  @Override
  public Mono<Void> deleteById(String id) {
    return templJsonview.deleteById(id);

  }
}


