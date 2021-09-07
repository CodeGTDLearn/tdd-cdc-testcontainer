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
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(CRUD_REPO_JVIEW_ADMIN)
public class ResCrudJsonview {

  private final IService<PersonJsonview> servCrudJsonview;


  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(CREATED)
  public Mono<PersonJsonview> save(@RequestBody PersonJsonview person) {
    return servCrudJsonview.save(person);
  }


  @GetMapping(CRUD_REPO_JVIEW_ADMIN)
  @JsonView(AdminFilter.class)
  @ResponseStatus(OK)
  public Flux<PersonJsonview> findAllAdmin() {
    return servCrudJsonview.findAll();
  }


  @GetMapping(CRUD_REPO_JVIEW_USER)
  @JsonView(UserFilter.class)
  @ResponseStatus(OK)
  public Flux<PersonJsonview> findAllUser() {
    return servCrudJsonview.findAll();
  }


  @GetMapping(CRUD_REPO_JVIEW_ADMIN + ID_JVIEW)
  @JsonView(AdminFilter.class)
  @ResponseStatus(OK)
  public Mono<PersonJsonview> findByIdAdmin(@PathVariable String id) {
    return servCrudJsonview.findById(id);
  }


  @GetMapping(CRUD_REPO_JVIEW_USER + ID_JVIEW)
  @JsonView(UserFilter.class)
  @ResponseStatus(OK)
  public Mono<PersonJsonview> findByIdUser(@PathVariable String id) {
    return servCrudJsonview.findById(id);
  }


  @DeleteMapping
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteAll() {
    return servCrudJsonview.deleteAll();
  }


  @DeleteMapping(ID_JVIEW)
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteById(@PathVariable String id) {
    return servCrudJsonview.deleteById(id);
  }
}

//package com.tdd.parallel.resource.jsonview;
//
//import com.fasterxml.jackson.annotation.JsonView;
//import com.tdd.parallel.entity.standard.PersonStandard;
//import com.tdd.parallel.service.IService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import static com.tdd.parallel.core.Views.ResponseViews.AdminFilter;
//import static com.tdd.parallel.core.Views.ResponseViews.UserFilter;
//import static com.tdd.parallel.core.routes.RoutesJsonview.*;
//import static org.springframework.http.HttpStatus.*;
//import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
//
//// JsonView:
//// https://www.linkedin.com/pulse/jackson-jsonview-its-meaningful-use-spring-boot-rest-amit-patil
//// https://www.baeldung.com/jackson-json-view-annotation
//@AllArgsConstructor
//@Slf4j
//@RestController
//@RequestMapping(CRUD_REPO_REQMAP_JSONVIEW)
//public class ResourceCrudRepoJsonview {
//
//  private final IService serviceCrudRepo;
//
//
//  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
//  @ResponseStatus(CREATED)
//  public Mono<PersonStandard> save(@RequestBody PersonStandard person) {
//    return serviceCrudRepo.save(person);
//  }
//
//
//  @GetMapping(CRUD_REPO_ADMIN_JSONVIEW)
//  @JsonView(AdminFilter.class)
//  @ResponseStatus(OK)
//  public Flux<PersonStandard> findAllAdmin() {
//    return serviceCrudRepo.findAll();
//  }
//
//
//  @GetMapping(CRUD_REPO_USER_JSONVIEW)
//  @JsonView(UserFilter.class)
//  @ResponseStatus(OK)
//  public Flux<PersonStandard> findAllUser() {
//    return serviceCrudRepo.findAll();
//  }
//
//
//  @GetMapping(ADMIN_ID_JSONVIEW)
//  @JsonView(AdminFilter.class)
//  @ResponseStatus(OK)
//  public Mono<PersonStandard> findByIdAdmin(@PathVariable String id) {
//    return serviceCrudRepo.findById(id);
//  }
//
//
//  @GetMapping(USER_ID_JSONVIEW)
//  @JsonView(UserFilter.class)
//  @ResponseStatus(OK)
//  public Mono<PersonStandard> findByIdUser(@PathVariable String id) {
//    return serviceCrudRepo.findById(id);
//  }
//
//
//  @DeleteMapping
//  @ResponseStatus(NO_CONTENT)
//  public Mono<Void> deleteAll() {
//    return serviceCrudRepo.deleteAll();
//  }
//
//
//  @DeleteMapping(ID_JSONVIEW)
//  @ResponseStatus(NO_CONTENT)
//  public Mono<Void> deleteById(@PathVariable String id) {
//    return serviceCrudRepo.deleteById(id);
//  }
//}

