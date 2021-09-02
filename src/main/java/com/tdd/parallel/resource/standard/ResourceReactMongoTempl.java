package com.tdd.parallel.resource.standard;

import com.tdd.parallel.entity.PersonStandard;
import com.tdd.parallel.service.IService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.tdd.parallel.core.routes.RoutesStandard.ID_STD;
import static com.tdd.parallel.core.routes.RoutesStandard.RCT_MGO_TPL_REPO_STD;
import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(RCT_MGO_TPL_REPO_STD)
public class ResourceReactMongoTempl {

  private final IService serviceReactMongoTempl;


  @PostMapping
  @ResponseStatus(CREATED)
  public Mono<PersonStandard> save(@RequestBody PersonStandard person) {
    return serviceReactMongoTempl.save(person);
  }


  @GetMapping
  @ResponseStatus(OK)
  public Flux<PersonStandard> findAll() {
    return serviceReactMongoTempl.findAll();
  }


  @GetMapping(ID_STD)
  @ResponseStatus(OK)
  public Mono<PersonStandard> findById(@PathVariable String id) {
    return serviceReactMongoTempl.findById(id);
  }


  @DeleteMapping
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteAll() {
    return serviceReactMongoTempl.deleteAll();
  }


  @DeleteMapping(ID_STD)
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteById(@PathVariable String id) {
    return serviceReactMongoTempl.deleteById(id);
  }
}
