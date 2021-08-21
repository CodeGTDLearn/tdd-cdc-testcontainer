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
@RequestMapping(REQ_MAP_TPL_REPO)
public class ResourceTemplRepo {

  private final IService serviceTemplateRepo;


  @PostMapping
  @ResponseStatus(CREATED)
  public Mono<Person> save(@RequestBody Person customer) {
    return serviceTemplateRepo.save(customer);
  }


  @GetMapping
  @ResponseStatus(OK)
  public Flux<Person> findAll() {
    return serviceTemplateRepo.findAll();
  }


  @GetMapping(ID_TPL_REPO)
  @ResponseStatus(OK)
  public Mono<Person> findById(@PathVariable String id) {
    return serviceTemplateRepo.findById(id);
  }


  @DeleteMapping
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteAll() {
    return serviceTemplateRepo.deleteAll();
  }


  @DeleteMapping(ID_TPL_REPO)
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteById(@PathVariable String id) {
    return serviceTemplateRepo.deleteById(id);
  }
}
