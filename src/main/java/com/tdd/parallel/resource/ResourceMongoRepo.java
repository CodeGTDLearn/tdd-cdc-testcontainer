package com.tdd.parallel.resource;

import com.tdd.parallel.entity.Person;
import com.tdd.parallel.service.IService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.tdd.parallel.core.Routes.*;
import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(ROUTE_MGO_REPO)
public class ResourceMongoRepo {

  private final IService serviceMongoRepo;


  @PostMapping
  @ResponseStatus(CREATED)
  public Mono<Person> save(@RequestBody Person customer) {
    return serviceMongoRepo.save(customer);
  }


  @GetMapping
  @ResponseStatus(OK)
  public Flux<Person> findAll() {
    return serviceMongoRepo.findAll();
  }


  @GetMapping(ID_MGO_REPO)
  @ResponseStatus(OK)
  public Mono<Person> findById(@PathVariable String id) {
    return serviceMongoRepo.findById(id);
  }


  @DeleteMapping
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteAll() {
    return serviceMongoRepo.deleteAll();
  }


  @DeleteMapping(ID_MGO_REPO)
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteById(@PathVariable String id) {
    return serviceMongoRepo.deleteById(id);
  }
}
