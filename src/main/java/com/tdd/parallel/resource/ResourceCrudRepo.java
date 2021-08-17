package com.tdd.parallel.resource;

import com.tdd.parallel.entity.Person;
import com.tdd.parallel.service.IService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.tdd.parallel.core.Routes.ID;
import static com.tdd.parallel.core.Routes.REQ_MAP;
import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(REQ_MAP)
public class ResourceCrudRepo {

  private final IService serviceCrudRepo;


  @PostMapping
  @ResponseStatus(CREATED)
  public Mono<Person> save(@RequestBody Person customer) {
    return serviceCrudRepo.save(customer);
  }


  @GetMapping
  @ResponseStatus(OK)
  public Flux<Person> findAll() {
    return serviceCrudRepo.findAll();
  }


  @GetMapping(ID)
  @ResponseStatus(OK)
  public Mono<Person> findById(@PathVariable String id) {
    return serviceCrudRepo.findById(id);
  }


  @DeleteMapping
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteAll() {
    return serviceCrudRepo.deleteAll();
  }


  @DeleteMapping(ID)
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteById(@PathVariable String id) {
    return serviceCrudRepo.deleteById(id);
  }
}
