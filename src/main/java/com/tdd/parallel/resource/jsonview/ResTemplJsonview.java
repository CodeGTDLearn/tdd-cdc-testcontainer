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
import static com.tdd.parallel.core.views.Views.PersonViews.*;
import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(JV_REQ_MAP)
public class ResTemplJsonview {

  private final IService<PersonJsonview> servTemplJsonview;


  @PostMapping(JV_TEMPL_ADMIN_POST_REQUEST)
  @JsonView(AdminResponseView.class)
  @ResponseStatus(CREATED)
  public Mono<PersonJsonview> saveAdminRequestView(
       @RequestBody @JsonView(PostFilterRequestView.class) PersonJsonview person) {
    return servTemplJsonview.save(person);
  }


  @PostMapping(JV_TEMPL_ADMIN)
  @JsonView(AdminResponseView.class)
  @ResponseStatus(CREATED)
  public Mono<PersonJsonview> saveAdmin(@RequestBody PersonJsonview person) {
    return servTemplJsonview.save(person);
  }


  @PostMapping(JV_TEMPL_USER)
  @JsonView(UserResponseView.class)
  @ResponseStatus(CREATED)
  public Mono<PersonJsonview> saveUser(@RequestBody PersonJsonview person) {
    return servTemplJsonview.save(person);
  }


  @GetMapping(JV_TEMPL_ADMIN)
  @JsonView(AdminResponseView.class)
  @ResponseStatus(OK)
  public Flux<PersonJsonview> findAllAdmin() {
    return servTemplJsonview.findAll();
  }


  @GetMapping(JV_TEMPL_USER)
  @JsonView(UserResponseView.class)
  @ResponseStatus(OK)
  public Flux<PersonJsonview> findAllUser() {
    return servTemplJsonview.findAll();
  }


  @GetMapping(JV_TEMPL_ADMIN + JV_ID)
  @JsonView(AdminResponseView.class)
  @ResponseStatus(OK)
  public Mono<PersonJsonview> findByIdAdmin(@PathVariable String id) {
    return servTemplJsonview.findById(id);
  }


  @GetMapping(JV_TEMPL_USER + JV_ID)
  @JsonView(UserResponseView.class)
  @ResponseStatus(OK)
  public Mono<PersonJsonview> findByIdUser(@PathVariable String id) {
    return servTemplJsonview.findById(id);
  }


  @DeleteMapping(JV_TEMPL_DEL + JV_ID)
  @ResponseStatus(NO_CONTENT)
  public Mono<Void> deleteById(@PathVariable String id) {
    return servTemplJsonview.deleteById(id);
  }
}

