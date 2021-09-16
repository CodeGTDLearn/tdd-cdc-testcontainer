package com.tdd.parallel.service.standard;

import com.tdd.parallel.entity.PersonStandard;
import com.tdd.parallel.repository.standard.IRepoStandard;
import com.tdd.parallel.service.IService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service("servRepoStandard")
@AllArgsConstructor
public class ServRepoStandard implements IService<PersonStandard>  {


  private final IRepoStandard iRepoStandard;


  @Override
  public Mono<PersonStandard> save(PersonStandard person) {
    return iRepoStandard.save(person);
  }


  @Override
  public Flux<PersonStandard> findAll() {
    return iRepoStandard.findAll();
  }


  @Override
  public Mono<PersonStandard> findById(String id) {
    return iRepoStandard.findById(id);
  }


  public Mono<Void> deleteAll() {
    return iRepoStandard.deleteAll();
  }


  @Override
  public Mono<Void> deleteById(String id) {
    return iRepoStandard.deleteById(id);
  }


}


