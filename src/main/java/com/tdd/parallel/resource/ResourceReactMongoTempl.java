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
@RequestMapping(ROUTE_RCT_MGO_TPL_REPO)
public class ResourceReactMongoTempl {

  private final IService serviceReactMongoTempl;


  @PostMapping
  @ResponseStatus(CREATED)
  public Mono<Person> save(@RequestBody Person customer) {
    return serviceReactMongoTempl.save(customer);
  }


  @GetMapping
  @ResponseStatus(OK)
  public Flux<Person> findAll() {
    return serviceReactMongoTempl.findAll();
  }


  @GetMapping(ID_RCT_MGO_TPL_REPO)
  @ResponseStatus(OK)
  public Mono<Person> findById(@PathVariable String id) {
    return serviceReactMongoTempl.findById(id);
  }


  @DeleteMapping
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteAll() {
    return serviceReactMongoTempl.deleteAll();
  }


  @DeleteMapping(ID_RCT_MGO_TPL_REPO)
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteById(@PathVariable String id) {
    return serviceReactMongoTempl.deleteById(id);
  }
}
