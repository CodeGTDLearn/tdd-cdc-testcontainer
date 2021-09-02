package com.tdd.parallel.resource.standard;

import com.tdd.parallel.entity.PersonStandard;
import com.tdd.parallel.service.IService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.tdd.parallel.core.routes.RoutesStandard.ID_STD;
import static com.tdd.parallel.core.routes.RoutesStandard.REQ_MAP_TPL_REPO_STD;
import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(REQ_MAP_TPL_REPO_STD)
public class ResourceTemplRepo {

  private final IService serviceTemplateRepo;


  @PostMapping
  @ResponseStatus(CREATED)
  public Mono<PersonStandard> save(@RequestBody PersonStandard person) {
    return serviceTemplateRepo.save(person);
  }


  @GetMapping
  @ResponseStatus(OK)
  public Flux<PersonStandard> findAll() {
    return serviceTemplateRepo.findAll();
  }


  @GetMapping(ID_STD)
  @ResponseStatus(OK)
  public Mono<PersonStandard> findById(@PathVariable String id) {
    return serviceTemplateRepo.findById(id);
  }


  @DeleteMapping
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteAll() {
    return serviceTemplateRepo.deleteAll();
  }


  @DeleteMapping(ID_STD)
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteById(@PathVariable String id) {
    return serviceTemplateRepo.deleteById(id);
  }
}
