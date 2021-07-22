package com.tdd.parallel.controller;

import com.tdd.parallel.entity.Person;
import com.tdd.parallel.service.IPersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.tdd.parallel.core.Routes.REQ_MAP;
import static org.springframework.http.HttpStatus.*;

//@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(REQ_MAP)
public class PersonController {

  @Autowired
  private IPersonService service;


  @PostMapping
  @ResponseStatus(CREATED)
  public Mono<Person> save(@RequestBody Person customer) {
    return service.save(customer);
  }


  @GetMapping
  @ResponseStatus(OK)
  public Flux<Person> findAll() {
    return service.findAll();
  }


  @GetMapping("/{id}")
  @ResponseStatus(OK)
  public Mono<Person> findById(@PathVariable String id) {
    return service.findById(id);
  }


  @DeleteMapping
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteAll() {
    return service.deleteAll();
  }


  @DeleteMapping("/{id}")
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteById(@PathVariable String id) {
    return service.deleteById(id);
  }


}
