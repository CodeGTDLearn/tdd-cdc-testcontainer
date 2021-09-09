package com.tdd.parallel.resource.jsonview;

import com.fasterxml.jackson.annotation.JsonView;
import com.tdd.parallel.entity.jsonview.PersonJsonview;
import com.tdd.parallel.service.IService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.tdd.parallel.core.routes.RoutesJsonview.*;
import static com.tdd.parallel.core.views.Views.RequestViews.AdminFilter;
import static com.tdd.parallel.core.views.Views.RequestViews.UserFilter;
import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(REQ_MAP_JV)
public class ResCrudJsonview {

  private final IService<PersonJsonview> servCrudJsonview;


  @PostMapping(CRUD_JV_ADMIN)
  @ResponseStatus(CREATED)
  public Mono<PersonJsonview> saveAdmin(@RequestBody PersonJsonview person) {
    return servCrudJsonview.save(person);
  }


  @PostMapping(CRUD_JV_USER)
  @ResponseStatus(CREATED)
  public Mono<PersonJsonview> saveUser(@RequestBody PersonJsonview person) {
    return servCrudJsonview.save(person);
  }


  @GetMapping(CRUD_JV_ADMIN)
  @JsonView(AdminFilter.class)
  @ResponseStatus(OK)
  public Flux<PersonJsonview> findAllAdmin() {
    return servCrudJsonview.findAll();
  }


  @GetMapping(CRUD_JV_USER)
  @JsonView(UserFilter.class)
  @ResponseStatus(OK)
  public Flux<PersonJsonview> findAllUser() {
    return servCrudJsonview.findAll();
  }


  @GetMapping(CRUD_JV_ADMIN + ID_JV)
  @JsonView(AdminFilter.class)
  @ResponseStatus(OK)
  public Mono<PersonJsonview> findByIdAdmin(@PathVariable String id) {
    return servCrudJsonview.findById(id);
  }


  @GetMapping(CRUD_JV_USER + ID_JV)
  @JsonView(UserFilter.class)
  @ResponseStatus(OK)
  public Mono<PersonJsonview> findByIdUser(@PathVariable String id) {
    return servCrudJsonview.findById(id);
  }


  @DeleteMapping(CRUD_JV_ADMIN)
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteAll() {
    return servCrudJsonview.deleteAll();
  }


  @DeleteMapping(CRUD_JV_ADMIN + ID_JV)
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteById(@PathVariable String id) {
    return servCrudJsonview.deleteById(id);
  }
}

