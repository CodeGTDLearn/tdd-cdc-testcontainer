package com.tdd.parallel.service.standard;

import com.tdd.parallel.entity.PersonStandard;
import com.tdd.parallel.repository.standard.ICrudStandard;
import com.tdd.parallel.service.IService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//TUTORIAL: https://rieckpil.de/mongodb-testcontainers-setup-for-datamongotest/
@Slf4j
@Service("servCrudStandard")
@AllArgsConstructor
public class ServCrudStandard implements IService<PersonStandard> {


  private final ICrudStandard iCrudStandard;


  @Override
  public Mono<PersonStandard> save(PersonStandard person) {
    return iCrudStandard.save(person);
  }


  @Override
  public Flux<PersonStandard> findAll() {
    return iCrudStandard.findAll();
  }


  @Override
  public Mono<PersonStandard> findById(String id) {
    return iCrudStandard.findById(id);
  }


  public Mono<Void> deleteAll() {
    return iCrudStandard.deleteAll();
  }


  @Override
  public Mono<Void> deleteById(String id) {
    return iCrudStandard.deleteById(id);

  }
}


