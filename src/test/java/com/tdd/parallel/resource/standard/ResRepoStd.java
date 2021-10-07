package com.tdd.parallel.resource.standard;

import com.tdd.parallel.entity.PersonStandard;
import testsconfig.annotations.MergedResource;
import com.tdd.parallel.service.IService;
import com.tdd.parallel.service.standard.ServRepoStandard;
import testsconfig.tcCompose.TcComposeConfig;
import testsconfig.utils.TestDbUtils;
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

import static com.tdd.parallel.core.routes.RoutesStandard.*;
import static testsconfig.utils.TestUtils.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.*;

@DisplayName("ResRepoStd")
@Import({ServRepoStandard.class})
@MergedResource
public class ResRepoStd {

  //STATIC: one service for ALL tests -> SUPER FASTER
  //NON-STATIC: one service for EACH test
  @Container
  private static final DockerComposeContainer<?> compose = new TcComposeConfig().getTcCompose();

  final ContentType CONT_ANY = ContentType.ANY;
  final ContentType CONT_JSON = ContentType.JSON;
  final private String enabledTest = "true";
  final private int repet = 1;

  // WEB-TEST-CLIENT(non-blocking client)'
  // SHOULD BE USED WITH 'TEST-CONTAINERS'
  // BECAUSE THERE IS NO 'REAL-SERVER' CREATED VIA DOCKER-COMPOSE
  @Autowired
  WebTestClient mockedWebClient;

  @Autowired
  private IService<PersonStandard>  servRepoStandard;

  private final TestDbUtils<PersonStandard> utils = new TestDbUtils<>();


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
  @DisplayName("Save")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void save() {
    PersonStandard localPerson = utils.personStandard_save_check(servRepoStandard);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .body(localPerson)

         .when()
         .post(STD_REQ_MAP + STD_REPO)

         .then()
         .statusCode(CREATED.value())
         .contentType(CONT_JSON)
         .log()
         .headers()
         .and()
         .log()

         .body()
         .body("$",hasKey("id"))
         .body("$",hasKey("name"))
         .body("id",containsString(localPerson.getId()))
         .body("name",containsString(localPerson.getName()))
         .body(matchesJsonSchemaInClasspath("contracts/person/admin.json"))
    ;

    utils.findPersonInDb(servRepoStandard.findById(localPerson.getId()),1L);
  }


  @Test
  @DisplayName("FindAll")
  @EnabledIf(expression = enabledTest, loadContext = true)
  void findAll() {
    PersonStandard localPerson = utils.personStandard_save_check(servRepoStandard);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .when()
         .get(STD_REQ_MAP + STD_REPO)

         .then()
         .statusCode(OK.value())
         .log()
         .headers()
         .and()
         .log()

         .body()
         .body("[0]",hasKey("id"))
         .body("[0]",hasKey("name"))
         .body("[0].id",containsString(localPerson.getId()))
         .body("[0].name",containsString(localPerson.getName()))
         .body("id",hasItem(localPerson.getId()))
         .body("name",hasItem(localPerson.getName()))
         .body(matchesJsonSchemaInClasspath("contracts/person/adminList.json"))
    ;
  }


  @Test
  @DisplayName("FindById")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findById() {
    PersonStandard localPerson = utils.personStandard_save_check(servRepoStandard);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .when()
         .get(STD_REQ_MAP + STD_REPO + STD_ID,localPerson.getId())

         .then()
         .statusCode(OK.value())
         .log()
         .headers()
         .and()
         .log()

         .body()
         .body("$",hasKey("id"))
         .body("$",hasKey("name"))
         .body("id",containsString(localPerson.getId()))
         .body("name",containsString(localPerson.getName()))
         .body(matchesJsonSchemaInClasspath("contracts/person/admin.json"))
    ;

    utils.findPersonInDb(servRepoStandard.findById(localPerson.getId()),1L);
  }


  @Test
  @DisplayName("DeleteById")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void deleteById() {
    PersonStandard localPerson = utils.personStandard_save_check(servRepoStandard);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .body(localPerson)

         .when()
         .delete(STD_REQ_MAP + STD_REPO + STD_ID,localPerson.getId())

         .then()
         .log()
         .headers()
         .statusCode(NO_CONTENT.value())
    ;

    utils.findPersonInDb(servRepoStandard.findById(localPerson.getId()),0L);
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