package com.tdd.parallel.resource;

import com.tdd.parallel.entity.Person;
import com.tdd.parallel.service.IService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.tdd.parallel.core.Routes.ID_CRUD_REPO;
import static com.tdd.parallel.core.Routes.ROUTE_CRUD_REPO;
import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(ROUTE_CRUD_REPO)
public class ResourceCrudRepo {

  private final IService serviceCrudRepo;


  @PostMapping
  @ResponseStatus(CREATED)
  public Mono<Person> save(@RequestBody Person person) {
    return serviceCrudRepo.save(person);
  }


  @GetMapping
  @ResponseStatus(OK)
  public Flux<Person> findAll() {
    return serviceCrudRepo.findAll();
  }


  @GetMapping(ID_CRUD_REPO)
  @ResponseStatus(OK)
  public Mono<Person> findById(@PathVariable String id) {
    return serviceCrudRepo.findById(id);
  }


  @DeleteMapping
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteAll() {
    return serviceCrudRepo.deleteAll();
  }


  @DeleteMapping(ID_CRUD_REPO)
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteById(@PathVariable String id) {
    return serviceCrudRepo.deleteById(id);
  }
}
