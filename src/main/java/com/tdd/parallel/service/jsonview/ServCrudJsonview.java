package com.tdd.parallel.service.jsonview;

import com.tdd.parallel.entity.jsonview.PersonJsonview;
import com.tdd.parallel.entity.standard.PersonStandard;
import com.tdd.parallel.repository.jsonview.ICrudJsonview;
import com.tdd.parallel.service.IService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//TUTORIAL: https://rieckpil.de/mongodb-testcontainers-setup-for-datamongotest/
@Slf4j
@Service("servCrudJsonview")
@AllArgsConstructor
public class ServCrudJsonview implements IService<PersonJsonview> {


  private final ICrudJsonview iCrudJsonview;


  @Override
  public Mono<PersonJsonview> save(PersonJsonview person) {
    return iCrudJsonview.save(person);
  }


  @Override
  public Flux<PersonJsonview> findAll() {
    return iCrudJsonview.findAll();
  }


  @Override
  public Mono<PersonJsonview> findById(String id) {
    return iCrudJsonview.findById(id);
  }


  public Mono<Void> deleteAll() {
    return iCrudJsonview.deleteAll();
  }


  @Override
  public Mono<Void> deleteById(String id) {
    return iCrudJsonview.deleteById(id);

  }
}


