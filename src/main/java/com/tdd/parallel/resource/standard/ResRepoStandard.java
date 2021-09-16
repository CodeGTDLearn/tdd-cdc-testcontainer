package com.tdd.parallel.resource.standard;

import com.tdd.parallel.entity.PersonStandard;
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
public class ResRepoStandard {

  private final IService<PersonStandard> servRepoStandard;


  @PostMapping(STD_REPO)
  @ResponseStatus(CREATED)
  public Mono<PersonStandard> save(@RequestBody PersonStandard person) {
    return servRepoStandard.save(person);
  }


  @GetMapping(STD_REPO)
  @ResponseStatus(OK)
  public Flux<PersonStandard> findAll() {
    return servRepoStandard.findAll();
  }


  @GetMapping(STD_REPO + STD_ID)
  @ResponseStatus(OK)
  public Mono<PersonStandard> findById(@PathVariable String id) {
    return servRepoStandard.findById(id);
  }


  @DeleteMapping(STD_REPO + STD_ID)
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteById(@PathVariable String id) {
    return servRepoStandard.deleteById(id);
  }
}
