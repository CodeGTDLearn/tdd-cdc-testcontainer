package com.tdd.parallel.resource.jsonview;

import com.tdd.parallel.entity.jsonview.PersonJsonview;
import com.tdd.parallel.repository.standard.ICrudStandard;
import com.tdd.parallel.resource.MergedAnnotations;
import com.tdd.parallel.service.IService;
import com.tdd.parallel.service.jsonview.ServCrudJsonview;
import com.tdd.testsconfig.tcCompose.TcComposeConfig;
import com.tdd.testsconfig.utils.TestDbUtils;
import io.restassured.http.ContentType;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import reactor.blockhound.BlockingOperationError;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.tdd.parallel.core.routes.RoutesJsonview.*;
import static com.tdd.parallel.core.routes.RoutesStandard.CRUD_STD;
import static com.tdd.parallel.core.routes.RoutesStandard.ID_STD;
import static com.tdd.testsconfig.utils.TestUtils.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.*;

@DisplayName("ResCrudJview")
@Import({ServCrudJsonview.class})
@MergedAnnotations
public class ResCrudJview {

  //STATIC: one service for ALL tests
  //NON-STATIC: one service for EACH test
  @Container
  private static final DockerComposeContainer<?> compose = new TcComposeConfig().getTcCompose();

  final ContentType CONT_ANY = ContentType.ANY;
  final ContentType CONT_JSON = ContentType.JSON;

  final private String enabledTest = "true";
  final private int repet = 1;
  private final TestDbUtils<PersonJsonview> utils = new TestDbUtils<>();
  // WEB-TEST-CLIENT(non-blocking client)'
  // SHOULD BE USED WITH 'TEST-CONTAINERS'
  // BECAUSE THERE IS NO 'REAL-SERVER' CREATED VIA DOCKER-COMPOSE
  @Autowired
  WebTestClient mockedWebClient;

  @Autowired
  ICrudStandard repository;

  @Autowired
  private IService<PersonJsonview> serviceCrudJsonview;


  @BeforeAll
  public static void beforeAll(TestInfo testInfo) {
    globalBeforeAll();
    globalTestMessage(testInfo.getDisplayName(),"class-start");
  }


  @AfterAll
  public static void afterAll(TestInfo testInfo) {
    globalAfterAll();
    globalTestMessage(testInfo.getDisplayName(),"class-end");
  }


  @BeforeEach
  public void setUp(TestInfo testInfo) {
    globalTestMessage(testInfo.getTestMethod()
                              .toString(),"method-start");

    RestAssuredWebTestClient.reset();
    //REAL-SERVER INJECTED IN WEB-TEST-CLIENT(non-blocking client)'
    //SHOULD BE USED WHEN 'DOCKER-COMPOSE' UP A REAL-WEB-SERVER
    //BECAUSE THERE IS 'REAL-SERVER' CREATED VIA DOCKER-COMPOSE
    // realWebClient = WebTestClient.bindToServer()
    //                      .baseUrl("http://localhost:8080/customer")
    //                      .build();
  }


  @AfterEach
  void tearDown(TestInfo testInfo) {
    globalTestMessage(testInfo.getTestMethod()
                              .toString(),"method-end");
  }


  @Test
  @DisplayName("SaveAdmin")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void saveAdmin() {
    PersonJsonview localPerson = utils.personJsonview_save_check(serviceCrudJsonview);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .body(localPerson)

         .when()
         .post(REQ_MAP_JV + CRUD_JV_ADMIN)

         .then()
         .statusCode(CREATED.value())
         .contentType(CONT_JSON)
         .log()
         .headers()
         .and()
         .log()

         .body()
         .body("id",containsString(localPerson.getId()))
         .body("name",containsString(localPerson.getName()))
         .body(matchesJsonSchemaInClasspath("cdc_contracts/person.json"))
    ;

    utils.findPersonInDb(serviceCrudJsonview.findById(localPerson.getId()),1L);
  }


  @Test
  @DisplayName("SaveUser")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void saveUser() {
    PersonJsonview localPerson = utils.personJsonview_save_check(serviceCrudJsonview);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .body(localPerson)

         .when()
         .post(REQ_MAP_JV + CRUD_JV_USER)

         .then()
         .statusCode(CREATED.value())
         .contentType(CONT_JSON)
         .log()
         .headers()
         .and()
         .log()

         .body()
         .body("id",containsString(localPerson.getId()))
         .body("name",containsString(localPerson.getName()))
         .body(matchesJsonSchemaInClasspath("cdc_contracts/person.json"))
    ;

    utils.findPersonInDb(serviceCrudJsonview.findById(localPerson.getId()),1L);
  }


  @Test
  @DisplayName("FindAllAdmin")
  @EnabledIf(expression = enabledTest, loadContext = true)
  void findAllAdmin() {
    PersonJsonview localPerson = utils.personJsonview_save_check(serviceCrudJsonview);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .when()
         .get(REQ_MAP_JV + CRUD_JV_ADMIN)

         .then()
         .statusCode(OK.value())
         .log()
         .headers()
         .and()
         .log()

         .body()
         .body("size()",is(1))
         .body("id",hasItem(localPerson.getId()))
         .body(matchesJsonSchemaInClasspath("cdc_contracts/person.json"))
    ;
  }


  @Test
  @DisplayName("FindAllUser")
  @EnabledIf(expression = enabledTest, loadContext = true)
  void findAllUser() {
    PersonJsonview localPerson = utils.personJsonview_save_check(serviceCrudJsonview);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .when()
         .get(REQ_MAP_JV + CRUD_JV_USER)

         .then()
         .statusCode(OK.value())
         .log()
         .headers()
         .and()
         .log()

         .body()
         .body("size()",is(1))
         .body("id",hasItem(localPerson.getId()))
         .body(matchesJsonSchemaInClasspath("cdc_contracts/person.json"))
    ;
  }


  @Test
  @DisplayName("FindByIdAdmin")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findByIdAdmin() {
    PersonJsonview localPerson = utils.personJsonview_save_check(serviceCrudJsonview);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .when()
         .get(REQ_MAP_JV + CRUD_JV_ADMIN + ID_JV,localPerson.getId())

         .then()
         .statusCode(OK.value())
         .log()
         .headers()
         .and()
         .log()

         .body()
         .body("id",equalTo(localPerson.getId()))
         .body(matchesJsonSchemaInClasspath("cdc_contracts/person.json"))
    ;

    utils.findPersonInDb(serviceCrudJsonview.findById(localPerson.getId()),1L);
  }


  @Test
  @DisplayName("FindByIdUser")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findByIdUser() {
    PersonJsonview localPerson = utils.personJsonview_save_check(serviceCrudJsonview);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .when()
         .get(REQ_MAP_JV + CRUD_JV_USER + ID_JV,localPerson.getId())

         .then()
         .statusCode(OK.value())
         .log()
         .headers()
         .and()
         .log()

         .body()
         .body("id",equalTo(localPerson.getId()))
         .body(matchesJsonSchemaInClasspath("cdc_contracts/person.json"))
    ;

    utils.findPersonInDb(serviceCrudJsonview.findById(localPerson.getId()),1L);
  }


  @Test
  @DisplayName("DeleteAll")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void deleteAll() {
    PersonJsonview localPerson = utils.personJsonview_save_check(serviceCrudJsonview);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .body(localPerson)

         .when()
         .delete(REQ_MAP_JV + CRUD_JV_ADMIN)

         .then()
         .log()
         .headers()
         .statusCode(NO_CONTENT.value())
    ;

    utils.countPersonInDb(serviceCrudJsonview.findAll(),0L);
  }


  @Test
  @DisplayName("DeleteById")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void deleteByIdAdmin() {
    PersonJsonview localPerson = utils.personJsonview_save_check(serviceCrudJsonview);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .body(localPerson)

         .when()
         .delete(REQ_MAP_JV + CRUD_JV_ADMIN + ID_JV,localPerson.getId())

         .then()
         .log()
         .headers()
         .statusCode(NO_CONTENT.value())
    ;

    utils.findPersonInDb(serviceCrudJsonview.findById(localPerson.getId()),0L);
  }


  @Test
  @DisplayName("BHWorks")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void bHWorks() {
    try {
      FutureTask<?> task = new FutureTask<>(() -> {
        Thread.sleep(0);
        return "";
      });

      Schedulers.parallel()
                .schedule(task);

      task.get(10,TimeUnit.SECONDS);
      Assertions.fail("should fail");
    } catch (ExecutionException | InterruptedException | TimeoutException e) {
      assertTrue(e.getCause() instanceof BlockingOperationError,"detected");
    }
  }

}