package com.tdd.parallel.resource.jsonview;

import com.tdd.parallel.entity.PersonJsonview;
import com.tdd.parallel.entity.PersonOnlyName;
import com.tdd.parallel.service.IService;
import com.tdd.parallel.service.jsonview.ServCrudJsonview;
import testsconfig.annotations.MergedResource;
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

import static com.tdd.parallel.core.routes.RoutesJsonview.*;
import static testsconfig.databuilder.PersonSlimBuilder.personOnlyName;
import static testsconfig.utils.TestUtils.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.*;

@DisplayName("ResCrudJview")
@Import({ServCrudJsonview.class})
@MergedResource
public class ResCrudJview {

  //STATIC: one service for ALL tests -> SUPER FASTER
  //NON-STATIC: one service for EACH test
  @Container
  private static final DockerComposeContainer<?> compose =
       new TcComposeConfig().getTcCompose();

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
  private IService<PersonJsonview> servCrudJsonview;


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
  @DisplayName("SaveAdminJsonViewOnlyName")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void saveAdminJsonViewOnlyName() {
    // no Id provided in the input Object (No ID, only Name)
    // in the response, A new ID will be provided/created from the DB
    PersonOnlyName personOnlyName = personOnlyName().create();

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)
         .body(personOnlyName)

         .when()
         .post(JV_REQ_MAP + JV_CRUD_ADMIN_POST_REQUEST)

         .then()
         .statusCode(CREATED.value())
         .contentType(CONT_JSON)
         .log()
         .headers()
         .and()
         .log()

         .body()
         .body("$",hasKey("id")) // the response has an ID provided/created from the DB
         .body("$",hasKey("name"))
         .body("name",containsString(personOnlyName.getName()))
         .body(matchesJsonSchemaInClasspath("contracts/person/admin.json"))
    ;
  }


  @Test
  @DisplayName("SaveAdminJsonViewFullObject")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void SaveAdminJsonViewFullObject() {
    // Id provided in the Object (Full Object Body: ID + Name)
    // However, because jsonView will nullify this initial Id given
    // in the response, A new ID will be provided/created from the DB
    PersonJsonview personOnlyName = utils.personJsonview_save_check(servCrudJsonview);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)
         .body(personOnlyName)

         .when()
         .post(JV_REQ_MAP + JV_CRUD_ADMIN_POST_REQUEST)

         .then()
         .statusCode(CREATED.value())
         .contentType(CONT_JSON)
         .log()
         .headers()
         .and()
         .log()

         .body()
         .body("$",hasKey("id")) // the response has an ID provided/created from the DB
         .body("$",hasKey("name"))
         .body("name",containsString(personOnlyName.getName()))
         .body(matchesJsonSchemaInClasspath("contracts/person/admin.json"))
    ;
  }


  @Test
  @DisplayName("SaveAdmin")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void saveAdmin() {
    PersonJsonview localPerson = utils.personJsonview_save_check(servCrudJsonview);


    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)
         .body(localPerson)

         .when()
         .post(JV_REQ_MAP + JV_CRUD_ADMIN)

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

    utils.findPersonInDb(servCrudJsonview.findById(localPerson.getId()),1L);
  }


  @Test
  @DisplayName("SaveUser")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void saveUser() {
    PersonJsonview localPerson = utils.personJsonview_save_check(servCrudJsonview);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .body(localPerson)

         .when()
         .post(JV_REQ_MAP + JV_CRUD_USER)

         .then()
         .statusCode(CREATED.value())
         .contentType(CONT_JSON)
         .log()
         .headers()
         .and()
         .log()

         .body()
         .body("$",not(hasKey("id")))
         .body("$",hasKey("name"))
         .body("id",emptyOrNullString())
         .body("name",containsString(localPerson.getName()))
         .body(matchesJsonSchemaInClasspath("contracts/person/user.json"))
    ;

    utils.findPersonInDb(servCrudJsonview.findById(localPerson.getId()),1L);
  }


  @Test
  @DisplayName("FindAllAdmin")
  @EnabledIf(expression = enabledTest, loadContext = true)
  void findAllAdmin() {
    PersonJsonview localPerson = utils.personJsonview_save_check(servCrudJsonview);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .when()
         .get(JV_REQ_MAP + JV_CRUD_ADMIN)

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
  @DisplayName("FindAllUser")
  @EnabledIf(expression = enabledTest, loadContext = true)
  void findAllUser() {
    PersonJsonview localPerson = utils.personJsonview_save_check(servCrudJsonview);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .when()
         .get(JV_REQ_MAP + JV_CRUD_USER)

         .then()
         .statusCode(OK.value())
         .log()
         .headers()
         .and()
         .log()

         .body()
         .body("[0]",not(hasKey("id")))
         .body("[0]",hasKey("name"))
         .body("[0].name",containsString(localPerson.getName()))
         .body("name",hasItem(localPerson.getName()))
         .body(matchesJsonSchemaInClasspath("contracts/person/userList.json"))
    ;
  }


  @Test
  @DisplayName("FindByIdAdmin")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findByIdAdmin() {
    PersonJsonview localPerson = utils.personJsonview_save_check(servCrudJsonview);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .when()
         .get(JV_REQ_MAP + JV_CRUD_ADMIN + JV_ID,localPerson.getId())

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

    utils.findPersonInDb(servCrudJsonview.findById(localPerson.getId()),1L);
  }


  @Test
  @DisplayName("FindByIdUser")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void findByIdUser() {
    PersonJsonview localPerson = utils.personJsonview_save_check(servCrudJsonview);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .when()
         .get(JV_REQ_MAP + JV_CRUD_USER + JV_ID,localPerson.getId())

         .then()
         .statusCode(OK.value())
         .log()
         .headers()
         .and()
         .log()

         .body()
         .body("$",not(hasKey("id")))
         .body("$",hasKey("name"))
         .body("id",emptyOrNullString())
         .body("name",containsString(localPerson.getName()))
         .body(matchesJsonSchemaInClasspath("contracts/person/user.json"))
    ;

    utils.findPersonInDb(servCrudJsonview.findById(localPerson.getId()),1L);
  }


  @Test
  @DisplayName("DeleteById")
  @EnabledIf(expression = enabledTest, loadContext = true)
  public void deleteById() {
    PersonJsonview localPerson = utils.personJsonview_save_check(servCrudJsonview);

    RestAssuredWebTestClient

         .given()
         .webTestClient(mockedWebClient)
         .header("Accept",CONT_ANY)
         .header("Content-type",CONT_JSON)

         .body(localPerson)

         .when()
         .delete(JV_REQ_MAP + JV_CRUD_DEL + JV_ID,localPerson.getId())

         .then()
         .log()
         .headers()
         .statusCode(NO_CONTENT.value())
    ;

    utils.findPersonInDb(servCrudJsonview.findById(localPerson.getId()),0L);
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