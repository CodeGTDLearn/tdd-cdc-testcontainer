package com.tdd.parallel.resource.standard;

import com.tdd.parallel.entity.PersonStandard;
import com.tdd.parallel.service.IService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.tdd.parallel.core.routes.RoutesStandard.ID_STD;
import static com.tdd.parallel.core.routes.RoutesStandard.MGO_REPO_STD;
import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(MGO_REPO_STD)
public class ResourceMongoRepo {

  private final IService serviceMongoRepo;


  @PostMapping
  @ResponseStatus(CREATED)
  public Mono<PersonStandard> save(@RequestBody PersonStandard person) {
    return serviceMongoRepo.save(person);
  }


  @GetMapping
  @ResponseStatus(OK)
  public Flux<PersonStandard> findAll() {
    return serviceMongoRepo.findAll();
  }


  @GetMapping(ID_STD)
  @ResponseStatus(OK)
  public Mono<PersonStandard> findById(@PathVariable String id) {
    return serviceMongoRepo.findById(id);
  }


  @DeleteMapping
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteAll() {
    return serviceMongoRepo.deleteAll();
  }


  @DeleteMapping(ID_STD)
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteById(@PathVariable String id) {
    return serviceMongoRepo.deleteById(id);
  }
}
