package com.tdd.parallel.service;

import com.tdd.parallel.entity.PersonStandard;
import com.tdd.parallel.repository.ITemplateRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service("serviceTemplateRepo")
@AllArgsConstructor
public class ServiceTemplateRepo implements IService {


  private final ITemplateRepo templateRepo;


  @Override
  public Mono<PersonStandard> save(PersonStandard person) {
    return templateRepo.save(person);
  }


  @Override
  public Flux<PersonStandard> findAll() {
    return templateRepo.findAll();
  }


  @Override
  public Mono<Void> deleteById(String id) {
    return templateRepo.deleteById(id);
  }


  @Override
  public Mono<Void> deleteAll() {
    return templateRepo.deleteAll();
  }

  @Override
  public Mono<PersonStandard> findById(String id) {
    return templateRepo.findById(id);
  }
}


