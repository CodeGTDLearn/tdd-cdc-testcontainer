package com.tdd.parallel.service.standard;

import com.tdd.parallel.entity.standard.PersonStandard;
import com.tdd.parallel.repository.ITemplGeneric;
import com.tdd.parallel.service.IService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service("servTemplStandard")
@AllArgsConstructor
public class ServTemplStandard implements IService<PersonStandard>  {


  private final ITemplGeneric<PersonStandard> templStandard;


  @Override
  public Mono<PersonStandard> save(PersonStandard person) {
    return templStandard.save(person);
  }


  @Override
  public Flux<PersonStandard> findAll() {
    return templStandard.findAll();
  }


  @Override
  public Mono<Void> deleteById(String id) {
    return templStandard.deleteById(id);
  }


  @Override
  public Mono<Void> deleteAll() {
    return templStandard.deleteAll();
  }


  @Override
  public Mono<PersonStandard> findById(String id) {
    return templStandard.findById(id);
  }
}


