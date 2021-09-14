package com.tdd.parallel.resource.standard;

import com.tdd.parallel.entity.standard.PersonStandard;
import com.tdd.parallel.service.IService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.tdd.parallel.core.routes.RoutesStandard.*;
import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(STD_REQ_MAP)
public class ResCrudStandard {

  private final IService<PersonStandard> servCrudStandard;


  @PostMapping(STD_CRUD)
  @ResponseStatus(CREATED)
  public Mono<PersonStandard> save(@RequestBody PersonStandard person) {
    return servCrudStandard.save(person);
  }


  @GetMapping(STD_CRUD)
  @ResponseStatus(OK)
  public Flux<PersonStandard> findAll() {
    return servCrudStandard.findAll();
  }


  @GetMapping(STD_CRUD + STD_ID)
  @ResponseStatus(OK)
  public Mono<PersonStandard> findById(@PathVariable String id) {
    return servCrudStandard.findById(id);
  }


  @DeleteMapping(STD_CRUD + STD_ID)
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteById(@PathVariable String id) {
    return servCrudStandard.deleteById(id);
  }
}
