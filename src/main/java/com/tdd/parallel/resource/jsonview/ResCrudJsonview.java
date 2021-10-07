package com.tdd.parallel.resource.jsonview;

import com.fasterxml.jackson.annotation.JsonView;
import com.tdd.parallel.entity.PersonJsonview;
import com.tdd.parallel.service.IService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.tdd.parallel.core.routes.RoutesJsonview.*;
import static com.tdd.parallel.core.views.Views.PersonViews.*;
import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(JV_REQ_MAP)
public class ResCrudJsonview {

  private final IService<PersonJsonview> servCrudJsonview;


  @PostMapping(JV_CRUD_ADMIN_POST_REQUEST)
  @JsonView(AdminResponseView.class)
  @ResponseStatus(CREATED)
  public Mono<PersonJsonview> saveAdminRequestViewOnlyName(
       @RequestBody
       @JsonView(PostFilterRequestView.class)
            PersonJsonview person) {
    // NO ID PROVIDED:
    // - no Id provided in the input Object (No ID, only Name)
    // - the response will have an ID provided/created from the DB

    // ID PROVIDED:
    // Id provided in the Object (Full Object Body: ID + Name)
    // However, because jsonView will nullify this initial Id given
    // in the response, A new ID will be provided/created from the DB
    return servCrudJsonview.save(person);
  }


  @PostMapping(JV_CRUD_ADMIN)
  @JsonView(AdminResponseView.class)
  @ResponseStatus(CREATED)
  public Mono<PersonJsonview> saveAdmin(@RequestBody PersonJsonview person) {
    return servCrudJsonview.save(person);
  }


  @PostMapping(JV_CRUD_USER)
  @JsonView(UserResponseView.class)
  @ResponseStatus(CREATED)
  public Mono<PersonJsonview> saveUser(@RequestBody PersonJsonview person) {
    return servCrudJsonview.save(person);
  }


  @GetMapping(JV_CRUD_ADMIN)
  @JsonView(AdminResponseView.class)
  @ResponseStatus(OK)
  public Flux<PersonJsonview> findAllAdmin() {
    return servCrudJsonview.findAll();
  }


  @GetMapping(JV_CRUD_USER)
  @JsonView(UserResponseView.class)
  @ResponseStatus(OK)
  public Flux<PersonJsonview> findAllUser() {
    return servCrudJsonview.findAll();
  }


  @GetMapping(JV_CRUD_ADMIN + JV_ID)
  @JsonView(AdminResponseView.class)
  @ResponseStatus(OK)
  public Mono<PersonJsonview> findByIdAdmin(@PathVariable String id) {
    return servCrudJsonview.findById(id);
  }


  @GetMapping(JV_CRUD_USER + JV_ID)
  @JsonView(UserResponseView.class)
  @ResponseStatus(OK)
  public Mono<PersonJsonview> findByIdUser(@PathVariable String id) {
    return servCrudJsonview.findById(id);
  }


  @DeleteMapping(JV_CRUD_DEL + JV_ID)
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteById(@PathVariable String id) {
    return servCrudJsonview.deleteById(id);
  }
}

